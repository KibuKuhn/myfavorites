package kibu.kuhn.myfavorites.domain;

import java.beans.Transient;
import java.nio.file.Path;
import java.util.Objects;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class Item {

  private Path path;
  private boolean file;
  private Icon icon;

  public static Item of(Path path, boolean file) {
    Item item = new Item();
    item.path = path;
    item.file = file;
    return item;
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

  @Transient
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
