package kibu.kuhn.myfavorites.ui.drop;

import java.util.List;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import kibu.kuhn.myfavorites.domain.Item;

public abstract class DropTreeNode extends DefaultMutableTreeNode {

  private static final long serialVersionUID = 1L;

  @Override
  public void setUserObject(Object item) {
    super.setUserObject((Item)item);
  }

  @Override
  public Item getUserObject() {
    return (Item) super.getUserObject();
  }

  public Item getItem() {
    return (Item) getUserObject();
  }

  public void setItem(Item item) {
    setUserObject(item);
  }

  @Override
  public DropTreeNode getChildAt(int index) {
    return (DropTreeNode) super.getChildAt(index);
  }

  @Override
  public DropTreeNode getParent() {
    return (DropTreeNode) super.getParent();
  }

  public void switchNodes(int index1, int index2) {
    var node1 = children.get(index1);
    var node2 = children.get(index2);
    children.set(index1, node2);
    children.set(index2, node1);
  }


  public List<TreeNode> getChildren() {
    if (children == null) {
      children = new Vector<>();
    }
    return children;
  }
}
