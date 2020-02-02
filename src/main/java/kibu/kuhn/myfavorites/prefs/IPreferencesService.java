package kibu.kuhn.myfavorites.prefs;

import java.awt.Point;
import java.io.File;
import java.util.Locale;
import javax.swing.UIManager.LookAndFeelInfo;
import kibu.kuhn.myfavorites.ui.drop.RootNode;


public interface IPreferencesService {

  void saveItems(RootNode node);

  RootNode getItems();

  void saveLaf(LookAndFeelInfo laf);

  void saveLocale(Locale locale);

  LookAndFeelInfo getLaf();

  Locale getLocale();

  static IPreferencesService get() {
    return PreferencesService.get();
  }

  File getExportPath();

  void saveExportPath(String absolutePath);

  void saveConfirmDeleteItem(boolean selected);

  boolean isConfirmDeleteItem();

  boolean isMainMenuLocationUpdatEnabled();

  void setMainMenuLocationUpdateNabled(boolean enabled);

  void saveMainMenuLocation(Point locationOnScreen);

  Point getMainMenuLocation();

}

