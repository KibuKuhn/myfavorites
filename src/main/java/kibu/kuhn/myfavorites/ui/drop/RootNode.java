package kibu.kuhn.myfavorites.ui.drop;

import javax.swing.tree.MutableTreeNode;
import kibu.kuhn.myfavorites.domain.RootItem;

public class RootNode extends DropTreeNode {

  private static final long serialVersionUID = 1L;

  public RootNode() {
    super.setUserObject(new RootItem());
  }

  @Override
  public void setParent(MutableTreeNode newParent) {
    throw new UnsupportedOperationException("RootNode does not allow parent");
  }

  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  @Override
  public RootNode getRoot() {
    return this;
  }

  @Override
  public boolean isRoot() {
    return true;
  }

  @Override
  public boolean isLeaf() {
    return false;
  }

  @Override
  public void setUserObject(Object userObject) {
    throw new UnsupportedOperationException("Root node does not allow user object");
  }
}
