package kibu.kuhn.myfavorites.prefs;

import kibu.kuhn.myfavorites.domain.DesktopItem;
import kibu.kuhn.myfavorites.domain.Item;

public class StorableItem {

  String path;
  boolean file;
  String alias;
  String url;

  public StorableItem() {}

  StorableItem(Item item) {
    this.path = item.getPath().toString();
    this.file = item.isFile();
    this.alias = item.getAlias();
    if (item.getType() == Item.Type.DesktopItem) {
      this.url =   ((DesktopItem)item).getUrl();
    }
  }



  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
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