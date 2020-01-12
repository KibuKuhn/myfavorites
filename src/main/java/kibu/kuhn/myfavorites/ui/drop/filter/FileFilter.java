package kibu.kuhn.myfavorites.ui.drop.filter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.ui.drop.LineMapper;
import kibu.kuhn.myfavorites.ui.drop.TransferData;

public class FileFilter implements Predicate<TransferData> {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileFilter.class);

  public static final String DESKTOP_SUFFIX = ".desktop";
  private static final String KEY_TYPE = "Type";
  public static final String KEY_URL = "URL";
  private static final String TYPE_LINK = "Link";

  @Override
  public boolean test(TransferData t) {
    @SuppressWarnings("unchecked")
    var files = (List<File>) t.getData();
    return files.stream().allMatch(file -> {
      try {
        if (file.isDirectory()) {
          return true;
        } else if (file.getName().endsWith(DESKTOP_SUFFIX)) {
          return analyze(file);
        } else if (file.canExecute()) {
          return false;
        } else {
          return true;
        }
      } catch (Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return false;
      }
    });
  }

  private boolean analyze(File file) throws IOException {
    var map = Files.readAllLines(file.toPath()).stream().collect(new LineMapper());
    String value = map.get(KEY_TYPE);
    if (!TYPE_LINK.equals(value)) {
      return false;
    }
    return true;
  }

}
