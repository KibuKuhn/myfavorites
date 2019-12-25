package kibu.kuhn.myfavorites.prefs;

import kibu.kuhn.myfavorites.domain.Item;

public class StorableItem {

  String path;
  boolean file;

  public StorableItem() {}

  StorableItem(Item item) {
    this.path = item.getPath().toString();
    this.file = item.isFile();
  }


  public String getPath() {
    return path;
  }


  public void setPath(String path) {
    this.path = path;
  }


  public boolean isFile() {
    return file;
  }


  public void setFile(boolean file) {
    this.file = file;
  }
}