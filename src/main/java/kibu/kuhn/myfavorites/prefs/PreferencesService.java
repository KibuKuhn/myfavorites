package kibu.kuhn.myfavorites.prefs;

import java.awt.Point;
import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import kibu.kuhn.myfavorites.MyFavorites;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

class PreferencesService implements IPreferencesService {

  private static final String DARK_MODE = "darkMode";
  private static final String MAIN_MENU_LOCATION = "mainMenuLocation";
  private static final Logger LOGGER = LoggerFactory.getLogger(PreferencesService.class);
  private static final String MAIN_MENU_LOCATION_UPDATE = "mainMenuLocationUpdate";
  private static final String EXPORT_PATH = "exportpath";
  static final String LOCALE = "locale";
  static final String LAF = "laf";
  static final String ITEMS = "items";
  private static final String CLEAN = "clean";
  private static final String CONFIRM_DELETE_ITEM = "confirmDeleteItem";

  private static IPreferencesService service = new PreferencesService();

  static IPreferencesService get() {
    return service;
  }

  private NodeMapper mapper = new NodeMapper();

  PreferencesService() {
    if (System.getProperty(CLEAN) != null) {
      try {
        getPreferences().clear();
        LOGGER.info("Preferences cleaned");
      } catch (BackingStoreException e) {
        throw new IllegalStateException("Cannot delete preferences");
      }
    }
  }


  Preferences getPreferences() {
    return Preferences.userNodeForPackage(MyFavorites.class);
  }

  @Override
  public void saveItems(RootNode node) {
    try {
      var json = mapper.mapToJson(node);
      getPreferences().put(ITEMS, json);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public RootNode getItems() {
    var items = getPreferences().get(ITEMS, null);
    if (items == null) {
      return null;
    }

    try {
      var node = (RootNode)mapper.mapToNode(items);
      return node;
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void saveLaf(LookAndFeelInfo laf) {
    getPreferences().put(LAF, laf.getClassName());
  }

  @Override
  public void saveLocale(Locale locale) {
    getPreferences().put(LOCALE, locale.getLanguage());
  }

  @Override
  public LookAndFeelInfo getLaf() {
    var laf = getPreferences().get(LAF, UIManager.getSystemLookAndFeelClassName());
    //@formatter:off
    return Arrays.stream(UIManager.getInstalledLookAndFeels())
                 .filter(lf -> lf.getClassName().equals(laf))
                 .findFirst()
                 .orElseThrow(() -> new IllegalStateException("Cannot find Look and Feel " + laf));
    //@formatter:on
  }

  @Override
  public Locale getLocale() {
    var locale = getPreferences().get(LOCALE, null);
    if (locale == null) {
      return Locale.getDefault();
    }

    return Locale.forLanguageTag(locale);
  }


  @Override
  public File getExportPath() {
    var path = getPreferences().get(EXPORT_PATH, System.getProperty("user.dir"));
    return new File(path);
  }


  @Override
  public void saveExportPath(String path) {
    getPreferences().put(EXPORT_PATH, path);
  }


  @Override
  public void saveConfirmDeleteItem(boolean confirm) {
    getPreferences().putBoolean(CONFIRM_DELETE_ITEM, confirm);
  }


  @Override
  public boolean isConfirmDeleteItem() {
    return getPreferences().getBoolean(CONFIRM_DELETE_ITEM, true);
  }


  @Override
  public boolean isMainMenuLocationUpdatEnabled() {
    return getPreferences().getBoolean(MAIN_MENU_LOCATION_UPDATE, true);
  }


  @Override
  public void setMainMenuLocationUpdateNabled(boolean enabled) {
    getPreferences().putBoolean(MAIN_MENU_LOCATION_UPDATE, enabled);
  }


  @Override
  public void saveMainMenuLocation(Point locationOnScreen) {
    getPreferences().put(MAIN_MENU_LOCATION, toString(locationOnScreen));

  }


  private String toString(Point locationOnScreen) {
    if (locationOnScreen == null) {
      return null;
    }
    return locationOnScreen.x + "/" + locationOnScreen.y;
  }


  @Override
  public Point getMainMenuLocation() {
    var s = getPreferences().get(MAIN_MENU_LOCATION, null);
    if (s == null) {
      return null;
    }

    var split = s.split("/");
    return new Point(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
  }


  @Override
  public void setDarkMode(boolean selected) {
    getPreferences().putBoolean(DARK_MODE, selected); 
  }


  @Override
  public boolean isDarkMode() {
    return getPreferences().getBoolean(DARK_MODE, false);
  }
}
