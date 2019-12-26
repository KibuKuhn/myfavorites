package kibu.kuhn.myfavorites;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.ui.IGui;

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

    SwingUtilities.invokeLater(MyFavorites::new);
  }


  MyFavorites() {
    if (!IGui.get().checkSupport()) {
      System.exit(0);
    }

    IGui.get().initUI();
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

  private static boolean lockInstance(String lockFile) {
    try {
      File file = new File(lockFile);
      RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
      FileLock fileLock = randomAccessFile.getChannel().tryLock();
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
