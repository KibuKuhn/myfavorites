package kibu.kuhn.myfavorites;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.ui.IGui;

public class MyFavorites {

  public static final String LOG_DIR = "logDir";
  public static final String LOG_FILE = "logFile";

  static {
    initLogging();
  }
  
  private static final Logger LOGGER = LoggerFactory.getLogger(MyFavorites.class);

  public static void main(String[] args) throws IOException {
    if (!lockInstance("MyFavorites.lock")) {
      LOGGER.info("MyFavorites already running. Exiting.");
      System.exit(0);
    }

    if (System.getProperty("delay") != null) {
      try {
        var delayProp = System.getProperty("delay");
        var delay = Integer.parseInt(delayProp);
        TimeUnit.MILLISECONDS.sleep(delay);
       
      } catch (Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        throw new IllegalStateException(ex);
      }
    }

    SwingUtilities.invokeLater(MyFavorites::new);
  }


  MyFavorites() {
    if (!IGui.get().checkSupport()) {
      System.exit(0);
    }

    IGui.get().init();
  }


  private static void initLogging() {
    var logDir = System.getProperty(LOG_DIR);
    if (logDir == null) {
      logDir = System.getProperty("user.home");
      System.setProperty(LOG_DIR, logDir);
    }
    System.setProperty(LOG_FILE, ".myfavorites.log");
    String logLevel = System.getProperty("logLevel");
    if (logLevel == null) {
      System.setProperty("logLevel", "INFO");
    }
  }

  private static boolean lockInstance(String lockFile) {
    try {
      var file = new File(lockFile);
      var randomAccessFile = new RandomAccessFile(file, "rw");
      var fileLock = randomAccessFile.getChannel().tryLock();
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
