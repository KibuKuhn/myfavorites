package kibu.kuhn.myfavorites.ui;

public interface IGui {

  String getI18n(String key);

  void init();

  boolean checkSupport();

  static IGui get() {
    return Gui.get();
  }

}
