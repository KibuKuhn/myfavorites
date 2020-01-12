package kibu.kuhn.myfavorites.ui.drop;

import kibu.kuhn.myfavorites.domain.BoxItem;
import kibu.kuhn.myfavorites.domain.Item;

public class BoxNode extends DropTreeNode {

  private static final long serialVersionUID = 1L;

  public BoxNode() {
    this(new BoxItem());
  }

  public BoxNode(BoxItem item) {
    super.setUserObject(item);
  }

  @Override
  public void setUserObject(Object item) {
    throw new UnsupportedOperationException("setItem not supported");
  }

  @Override
  public void setItem(Item item) {
    throw new UnsupportedOperationException("setItem not supported");
  }

  @Override
  public boolean isLeaf() {
    return false;
  }


}
