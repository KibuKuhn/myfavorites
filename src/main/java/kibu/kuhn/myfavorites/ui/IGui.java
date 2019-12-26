package kibu.kuhn.myfavorites.ui;

import javax.swing.ImageIcon;


public interface IGui {

  String getI18n(String key);

  void initUI();

  ImageIcon createImage(String imageName, String description);

  boolean checkSupport();

  static IGui get() {
    return Gui.get();
  }

}
