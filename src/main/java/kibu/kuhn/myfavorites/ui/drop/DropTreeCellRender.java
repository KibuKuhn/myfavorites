package kibu.kuhn.myfavorites.ui.drop;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class DropTreeCellRender extends DefaultTreeCellRenderer {

  private static final long serialVersionUID = 1L;

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
      boolean expanded, boolean leaf, int row, boolean hasFocus) {

    Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    this.setIcon(getIcon((DropTreeNode)value));
    return component;
  }

  private Icon getIcon(DropTreeNode node) {
    return node.getUserObject().getIcon();
  }
}
