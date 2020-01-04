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
}
