package kibu.kuhn.myfavorites.prefs;

import java.util.List;
import java.util.Locale;
import javax.swing.UIManager.LookAndFeelInfo;
import kibu.kuhn.myfavorites.domain.Item;


public interface IPreferencesService {

  @SuppressWarnings("exports")
  void saveItems(List<Item> items);

  @SuppressWarnings("exports")
  List<Item> getItems();

  @SuppressWarnings("exports")
  void saveLaf(LookAndFeelInfo laf);

  void saveLocale(Locale locale);

  @SuppressWarnings("exports")
  LookAndFeelInfo getLaf();

  Locale getLocale();

  static IPreferencesService get() {
    return PreferencesService.get();
  }

}
