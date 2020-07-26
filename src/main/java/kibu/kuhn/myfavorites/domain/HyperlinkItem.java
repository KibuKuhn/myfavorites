package kibu.kuhn.myfavorites.domain;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import javax.swing.Icon;
import kibu.kuhn.myfavorites.ui.Icons;
import kibu.kuhn.myfavorites.ui.drop.LineMapper;
import kibu.kuhn.myfavorites.ui.drop.filter.FileFilter;

public class HyperlinkItem implements CloneableItem, Cloneable {

  private URL link;
  private String alias;

  public static HyperlinkItem of(URL link) {
    HyperlinkItem item = new HyperlinkItem();
    item.link = link;
    return item;
  }

  @Override
  public Type getType() {
    return Type.HyperlinkItem;
  }

  @Override
  public String getAlias() {
    return alias;
  }

  @Override
  public void setAlias(String alias) {
    this.alias = alias;
  }

  @Override
  public Icon getIcon() {
    return Icons.getIcon("link18");
  }

  @Override
  public String getDisplayString() {
    return link.toExternalForm();
  }

  @Override
  public int hashCode() {
    return Objects.hash(link);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    HyperlinkItem other = (HyperlinkItem) obj;
    return Objects.equals(link, other.link);
  }

  @Override
  public HyperlinkItem clone() throws CloneNotSupportedException {
    return (HyperlinkItem) super.clone();
  }

  public URL getURL() {
    return link;
  }

  public static HyperlinkItem of(Path path) {
    var item = new HyperlinkItem();
    try {
      var map = Files.readAllLines(path).stream().collect(new LineMapper());
      item.link = new URL(map.get(FileFilter.KEY_URL));
      return item;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
