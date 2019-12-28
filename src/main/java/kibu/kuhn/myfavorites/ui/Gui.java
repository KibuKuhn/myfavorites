package kibu.kuhn.myfavorites.ui;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.prefs.IPreferencesService;

class Gui implements IGui {

  private static final Logger LOGGER = LoggerFactory.getLogger(Gui.class);

  private static final IGui GUI = new Gui();

  static IGui get() {
    return GUI;
  }

  private ResourceBundle bundle;
  private TrayIcon trayIcon;

  private Gui() {
    initI18n();
  }

  @Override
  public String getI18n(String key) {
    return bundle.getString(key);
  }

  private void configure(TrayIcon trayIcon) {
    trayIcon.setImageAutoSize(true);
    trayIcon.setToolTip(bundle.getString("trayicon.tooltip"));
    trayIcon.addMouseListener(new MainMenu());
  }

  private TrayIcon createTrayIcon() {
    trayIcon = new TrayIcon(Icons.getImage("list36"));
    configure(trayIcon);
    return trayIcon;
  }

  @Override
  public void init() {
    try {
      UIManager.put("swing.boldMetal", Boolean.FALSE);
      Toolkit.getDefaultToolkit().getSystemEventQueue().push(new XEventQueue(this::handleEventQueueException));
      LookAndFeelInfo laf = IPreferencesService.get().getLaf();
      UIManager.setLookAndFeel(laf.getClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException ex) {
      throw new IllegalStateException(ex.getMessage(), ex);
    }
    SystemTray tray = SystemTray.getSystemTray();
    TrayIcon trayIcon = createTrayIcon();

    try {
      tray.add(trayIcon);
    } catch (AWTException e) {
      LOGGER.error("TrayIcon could not be added.");
      return;
    }
  }

  private void initI18n() {
    Locale locale = IPreferencesService.get().getLocale();
    Locale.setDefault(locale);
    bundle = ResourceBundle.getBundle("i18n");
  }

  @Override
  public boolean checkSupport() {
    if (!SystemTray.isSupported()) {
      LOGGER.error("SystemTray is not supported");
      return false;
    }

    if (!Desktop.isDesktopSupported()) {
      LOGGER.error("Desktop is not supported");
      return false;
    }

    return true;
  }

  private void handleEventQueueException(Throwable th) {
    trayIcon.setImage(Icons.getImage("list36error"));
    trayIcon.setToolTip(bundle.getString("trayicon.tooltip.error"));
  }
}
