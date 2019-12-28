package kibu.kuhn.myfavorites.ui.drop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.function.Predicate;

public class FileFilter implements Predicate<File> {

  public static final String DESKTOP_SUFFIX = ".desktop";
  private static final String KEY_TYPE = "Type";
  public static final String KEY_URL = "URL";
  private static final String TYPE_LINK = "Link";

  private boolean analyze(File file) throws IOException {
      Map<String, String> map = Files.readAllLines(file.toPath()).stream().collect(new LineMapper());
      String value = map.get(KEY_TYPE);
      if (!TYPE_LINK.equals(value)) {
        return false;
      }
      return true;
  }


  @Override
  public boolean test(File file) {
    if (file.isDirectory()) {
      return true;
    }

    if (file.canExecute()) {
      return false;
    }

    if (file.getName().endsWith(DESKTOP_SUFFIX)) {
      try {
        return analyze(file);
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }

    return true;
  }
}