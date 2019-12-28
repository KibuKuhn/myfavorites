package kibu.kuhn.myfavorites.prefs;

import java.util.List;
import java.util.Locale;
import javax.swing.UIManager.LookAndFeelInfo;
import kibu.kuhn.myfavorites.domain.Item;


public interface IPreferencesService {

  void saveItems(List<Item> items);

  List<Item> getItems();

  void saveLaf(LookAndFeelInfo laf);

  void saveLocale(Locale locale);

  LookAndFeelInfo getLaf();

  Locale getLocale();

  static IPreferencesService get() {
    return PreferencesService.get();
  }
}
