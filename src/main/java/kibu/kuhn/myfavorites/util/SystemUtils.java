package kibu.kuhn.myfavorites.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class SystemUtils implements ISystemUtils {

  private static ISystemUtils utils = new SystemUtils();

  private SystemUtils() {

  }

  static ISystemUtils get() {
    return utils;
  }


  @Override
  public boolean isWindows() {
    String osName = System.getProperty("os.name", "");
    String osArch = System.getProperty("os.atch", "");
    return osName.contains("Windows") || osArch.contains("Windows");
  }

  @Override
  public boolean isTempFile(File f) {
    Path tempFile = null;
    try {
      tempFile = Files.createTempFile(null, null);
      if (tempFile.getParent().equals(f.toPath().getParent())) {
        return true;
      }

      return false;
    } catch (IOException e) {
      return false;
    }
    finally {
      try {
        Files.deleteIfExists(tempFile);
      }
      catch (Exception ex) {

      }
    }
  }
}
