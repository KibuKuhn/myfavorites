package kibu.kuhn.myfavorites.ui.drop;

import javax.swing.tree.DefaultTreeModel;

public class DropTreeModel extends DefaultTreeModel {

  private static final long serialVersionUID = 1L;

  private static DropTreeModel model = new DropTreeModel();

  public static DropTreeModel instance() {
    return model;
  }


  private DropTreeModel() {
    super(new RootNode());
  }

  @Override
  public RootNode getRoot() {
    return (RootNode) super.getRoot();
  }

  public DropTreeNode appendToRoot(DropTreeNode node) {
    if (node instanceof RootNode) {
      throw new IllegalStateException("Cannot append root node to root");
    }

    DropTreeNode root = getRoot();
    int childCount = getChildCount(getRoot());
    insertNodeInto(node, root, childCount);
    return root;
  }
}
