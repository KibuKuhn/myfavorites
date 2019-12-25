package kibu.kuhn.myfavorites.ui;

import java.awt.TrayIcon;
import java.util.ResourceBundle;

public class Gui {

  private static final Gui GUI = new Gui();

  public static Gui get() {
    return GUI;
  }

  private final ResourceBundle bundle;

  private Gui() {
    bundle = ResourceBundle.getBundle("i18n");
  }

  public String getI18n(String key) {
    return bundle.getString(key);
  }

  public void configure(TrayIcon trayIcon) {
    trayIcon.setImageAutoSize(true);
    trayIcon.setToolTip(bundle.getString("trayicon.tooltip"));
    trayIcon.addMouseListener(new MainMenu());
  }
}
