package kibu.kuhn.myfavorites.ui.drop;

import kibu.kuhn.myfavorites.domain.Item;

public class ItemTreeNode extends DropTreeNode {

  private static final long serialVersionUID = 1L;

  public static ItemTreeNode of (Item item) {
    ItemTreeNode node = new ItemTreeNode();
    node.setUserObject(item);
    return node;
  }

  @Override
  public Item getUserObject() {
    return (Item) super.getUserObject();
  }

  public void setUserObject(Item userObject) {
    super.setUserObject(userObject);
  }

  @Override
  public void setUserObject(Object userObject) {
    super.setUserObject((Item)userObject);
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
