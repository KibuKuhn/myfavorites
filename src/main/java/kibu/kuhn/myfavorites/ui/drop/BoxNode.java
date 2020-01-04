package kibu.kuhn.myfavorites.ui.drop;

import kibu.kuhn.myfavorites.domain.BoxItem;
import kibu.kuhn.myfavorites.domain.Item;

public class BoxNode extends DropTreeNode {

  private static final long serialVersionUID = 1L;

  public BoxNode() {
    super.setUserObject(new BoxItem());
  }

  @Override
  public void setUserObject(Object userObject) {
    throw new UnsupportedOperationException("setUserObject not supported");
  }

  @Override
  public Item getUserObject() {
    return super.getUserObject();
  }
}
