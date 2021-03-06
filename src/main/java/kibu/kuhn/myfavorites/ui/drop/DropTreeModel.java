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

  public DropTreeModel appendToRoot(DropTreeNode node) {
    if (node instanceof RootNode) {
      throw new IllegalStateException("Cannot append root node to root");
    }

    var root = getRoot();
    int childCount = getChildCount(getRoot());
    insertNodeInto(node, root, childCount);
    return this;
  }

  public DropTreeModel appendToNode(DropTreeNode targetNode, DropTreeNode node) {
    if (node.getParent() != null) {
      removeNodeFromParent(node);
    }
    insertNodeInto(node, targetNode, targetNode.getChildCount());
    return this;
  }
}
