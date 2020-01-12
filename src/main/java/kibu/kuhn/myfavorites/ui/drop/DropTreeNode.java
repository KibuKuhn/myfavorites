package kibu.kuhn.myfavorites.ui.drop;

import javax.swing.tree.DefaultMutableTreeNode;
import kibu.kuhn.myfavorites.domain.Item;

public abstract class DropTreeNode extends DefaultMutableTreeNode {

  private static final long serialVersionUID = 1L;

  @Override
  public void setUserObject(Object userObject) {
    super.setUserObject((Item)userObject);
  }

  @Override
  public Item getUserObject() {
    return (Item) super.getUserObject();
  }

  @Override
  public DropTreeNode getParent() {
    return (DropTreeNode) super.getParent();
  }

  @Override
  public DropTreeNode getChildAt(int index) {
    return (DropTreeNode) super.getChildAt(index);
  }

  public void switchNodes(int index1, int index2) {
    var node1 = children.get(index1);
    var node2 = children.get(index2);
    children.set(index1, node2);
    children.set(index2, node1);
  }
}
