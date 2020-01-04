package kibu.kuhn.myfavorites.ui.drop;

import java.util.Enumeration;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class RootNode extends DropTreeNode {

  private static final long serialVersionUID = 1L;

  @Override
  public void setParent(MutableTreeNode newParent) {
    throw new UnsupportedOperationException("RootNode does not allow parent");
  }

  @Override
  public TreeNode getParent() {
    // TODO Auto-generated method stub
    return super.getParent();
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
}
