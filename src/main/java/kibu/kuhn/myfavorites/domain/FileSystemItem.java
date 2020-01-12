package kibu.kuhn.myfavorites.domain;

import static kibu.kuhn.myfavorites.domain.Type.FileSystemItem;
import java.nio.file.Path;
import java.util.Objects;
import javax.swing.Icon;
import kibu.kuhn.myfavorites.ui.Icons;
import kibu.kuhn.myfavorites.ui.drop.filter.FileFilter;


public class FileSystemItem implements CloneableItem, Cloneable {

  Path path;
  boolean file;
  private String alias;

  public static FileSystemItem of(Path path, boolean file) {
    if (path.toString().endsWith(FileFilter.DESKTOP_SUFFIX)) {
      return  DesktopItem.of(path);
    }

    FileSystemItem item = new FileSystemItem();
    item.path = path;
    item.file = file;
    return item;
  }

  @Override
  public Type getType() {
    return FileSystemItem;
  }

  @Override
  public String getAlias() {
    return alias;
  }

  @Override
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

  @Override
  public Icon getIcon() {
    return file ? Icons.getIcon("file18") : Icons.getIcon("folder18");
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FileSystemItem other = (FileSystemItem) obj;
    return file == other.file && Objects.equals(path, other.path);
  }

  @Override
  public String getDisplayString() {
    return getPath().toString();
  }

  @Override
  public FileSystemItem clone() throws CloneNotSupportedException {
    return (FileSystemItem) super.clone();
  }
}
