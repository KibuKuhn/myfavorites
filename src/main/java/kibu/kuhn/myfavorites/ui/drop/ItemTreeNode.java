package kibu.kuhn.myfavorites.ui.drop;

import kibu.kuhn.myfavorites.domain.Item;

public class ItemTreeNode extends DropTreeNode {

  private static final long serialVersionUID = 1L;

  public static ItemTreeNode of (Item item) {
    var node = new ItemTreeNode();
    node.setItem(item);
    return node;
  }

  @Override
  public boolean isLeaf() {
    return true;
  }

  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  @Override
  public boolean isRoot() {
    return false;
  }


}
