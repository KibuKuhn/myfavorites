package kibu.kuhn.myfavorites;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.prefs.PreferencesService;
import kibu.kuhn.myfavorites.ui.Gui;

public class MyFavorites {

  static {
    initLogging();
  }
  private static final Logger LOGGER = LoggerFactory.getLogger(MyFavorites.class);

  public static void main(String[] args) throws IOException {
    if (!lockInstance("MyFavorites.lock")) {
      LOGGER.info("MyFavorites already running. Exiting.");
      System.exit(0);
    }

    UIManager.put("swing.boldMetal", Boolean.FALSE);
    SwingUtilities.invokeLater(MyFavorites::new);
  }


  public MyFavorites() {
    if (!SystemTray.isSupported()) {
      LOGGER.error("SystemTray is not supported");
      return;
    }

    if (!Desktop.isDesktopSupported()) {
      LOGGER.error("Desktop is not supported");
    }

    initUI();
  }

  private void initUI() {
    try {
      LookAndFeelInfo laf = PreferencesService.get().getLaf();
      UIManager.setLookAndFeel(laf.getClassName());
      Locale locale = PreferencesService.get().getLocale();
      Locale.setDefault(locale);
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


  private TrayIcon createTrayIcon() {
    TrayIcon trayIcon = new TrayIcon(createImage("list36", "tray icon").getImage());
    Gui.get().configure(trayIcon);
    return trayIcon;
  }

  @SuppressWarnings("exports")
  public static ImageIcon createImage(String imageName, String description) {
    return new ImageIcon(MyFavorites.class.getResource("/" + imageName + ".png"), description);
  }

  private static void initLogging() {
    String logDir = System.getProperty("logDir");
    if (logDir == null) {
      logDir = System.getProperty("user.home");
      System.setProperty("logDir", logDir);
    }
    String logLevel = System.getProperty("logLevel");
    if (logLevel == null) {
      System.setProperty("logLevel", "INFO");
    }
  }

  private static boolean lockInstance(final String lockFile) {
    try {
      final File file = new File(lockFile);
      final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
      final FileLock fileLock = randomAccessFile.getChannel().tryLock();
      if (fileLock != null) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
              fileLock.release();
              randomAccessFile.close();
              file.delete();
            } catch (Exception e) {
              LOGGER.error("Unable to remove lock file: " + lockFile, e);
            }
        }));
        return true;
      }
    } catch (Exception e) {
      LOGGER.error("Unable to create and/or lock file: " + lockFile, e);
    }
    return false;
  }
}
