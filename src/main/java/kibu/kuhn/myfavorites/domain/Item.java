package kibu.kuhn.myfavorites.domain;

import static kibu.kuhn.myfavorites.domain.Item.Type.Item;
import java.nio.file.Path;
import java.util.Objects;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;
import kibu.kuhn.myfavorites.ui.drop.FileFilter;

public class Item {

  public enum Type {Item, DesktopItem};

  Path path;
  boolean file;
  Icon icon;
  private String alias;

  public static Item of(Path path, boolean file) {
    if (path.toString().endsWith(FileFilter.DESKTOP_SUFFIX)) {
      return  DesktopItem.of(path);
    }

    Item item = new Item();
    item.path = path;
    item.file = file;
    return item;
  }

  public Type getType() {
    return Item;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public Path getPath() {
    return path;
  }

  public boolean isFile() {
    return file;
  }

  @Override
  public int hashCode() {
    return Objects.hash(file, path);
  }

  public Icon getIcon() {
    if (icon == null) {
        icon = FileSystemView.getFileSystemView().getSystemIcon(path.toFile());
    }
    return icon;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Item other = (Item) obj;
    return file == other.file && Objects.equals(path, other.path);
  }
}
