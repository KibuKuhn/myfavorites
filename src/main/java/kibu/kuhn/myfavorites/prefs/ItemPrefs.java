package kibu.kuhn.myfavorites.prefs;

import java.util.List;

public class ItemPrefs {
  List<StorableItem> items;

  ItemPrefs(List<StorableItem> items) {
    this.items = items;
  }

  public ItemPrefs() {}

  public List<StorableItem> getItems() {
    return items;
  }


  public void setItems(List<StorableItem> items) {
    this.items = items;
  }


}