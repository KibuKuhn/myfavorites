package kibu.kuhn.myfavorites.prefs;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.MyFavorites;
import kibu.kuhn.myfavorites.domain.Item;

class PreferencesService implements IPreferencesService {


  private static final Logger LOGGER = LoggerFactory.getLogger(PreferencesService.class);

  static final String LOCALE = "locale";
  static final String LAF = "laf";
  static final String ITEMS = "items";
  private static final String CLEAN = "clean";

  private static IPreferencesService service = new PreferencesService();

   static IPreferencesService get() {
     return service;
   }

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
  public void saveItems (List<Item> items) {
    List<StorableItem> list = items.stream().map(StorableItem::new).collect(Collectors.toList());
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      XMLEncoder encoder = new XMLEncoder(out);
      encoder.writeObject(new ItemPrefs(list));
      encoder.flush();
      encoder.close();
      out.close();
      byte[] bytes = out.toByteArray();
      getPreferences().put(ITEMS, new String(bytes, StandardCharsets.UTF_8));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public  List<Item> getItems() {
    String items = getPreferences().get(ITEMS, null);
    if (items == null) {
      return Collections.emptyList();
    }
    XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(items.getBytes(StandardCharsets.UTF_8)));
    ItemPrefs prefs = (ItemPrefs) decoder.readObject();
    decoder.close();
    return prefs.getItems().stream().map(si -> Item.of(Paths.get(si.path), si.file)).collect(Collectors.toList());
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
    String laf = getPreferences().get(LAF, UIManager.getSystemLookAndFeelClassName());
    //@formatter:off
    return Arrays.stream(UIManager.getInstalledLookAndFeels())
                 .filter(lf -> lf.getClassName().equals(laf))
                 .findFirst()
                 .orElseThrow(() -> new IllegalStateException("Cannot find Look and Feel " + laf));
    //@formatter:on
  }

  @Override
  public Locale getLocale() {
    String locale = getPreferences().get(LOCALE, null);
    if (locale == null) {
      return Locale.getDefault();
    }

    return Locale.forLanguageTag(locale);
  }
}
