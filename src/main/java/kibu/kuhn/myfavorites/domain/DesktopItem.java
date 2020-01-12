package kibu.kuhn.myfavorites.domain;


import static kibu.kuhn.myfavorites.domain.Type.DesktopItem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import javax.swing.Icon;
import kibu.kuhn.myfavorites.ui.Icons;

import kibu.kuhn.myfavorites.ui.drop.LineMapper;
import kibu.kuhn.myfavorites.ui.drop.filter.FileFilter;

public class DesktopItem extends FileSystemItem {

  private String url;

  public static DesktopItem of(Path path) {
    DesktopItem item = new DesktopItem();
    item.path = path;
    item.file = true;
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

  @Override
  public DesktopItem clone() throws CloneNotSupportedException {
    return (DesktopItem) super.clone();
  }

  @Override
  public Icon getIcon() {
    return Icons.getIcon("desktoplink18");
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(url);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    DesktopItem other = (DesktopItem) obj;
    return Objects.equals(url, other.url);
  }


}
