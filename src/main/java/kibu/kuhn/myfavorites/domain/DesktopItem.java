package kibu.kuhn.myfavorites.domain;

import static kibu.kuhn.myfavorites.domain.Item.Type.DesktopItem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import kibu.kuhn.myfavorites.ui.Icons;
import kibu.kuhn.myfavorites.ui.drop.FileFilter;
import kibu.kuhn.myfavorites.ui.drop.LineMapper;

public class DesktopItem extends Item {

  private String url;

  static DesktopItem of(Path path) {
    DesktopItem item = new DesktopItem();
    item.path = path;
    item.file = true;
    item.icon = Icons.getIcon("link18");
    try {
      Map<String, String> map = Files.readAllLines(path).stream().collect(new LineMapper());
      item.url = map.get(FileFilter.KEY_URL);
      return item;
    } catch (IOException e) {
     throw new IllegalStateException(e);
    }
  }

  public String getUrl() {
    return url;
  }

  @Override
  public Type getType() {
    return DesktopItem;
  }
}
