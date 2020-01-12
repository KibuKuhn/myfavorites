package kibu.kuhn.myfavorites.ui.drop;

import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import kibu.kuhn.myfavorites.ui.DropTreeConfigAction;

public class DropTree extends JTree {

  private static final long serialVersionUID = 1L;

  public DropTree() {
    super(DropTreeModel.instance());
    ToolTipManager.sharedInstance().registerComponent(this);
    setRowHeight(20);
    setRootVisible(true);
    setCellRenderer(new DropTreeCellRender());
    setShowsRootHandles(true);
    DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
    selectionModel.setSelectionMode(SINGLE_TREE_SELECTION);
    setSelectionModel(selectionModel);
  }

  @Override
  public DropTreeModel getModel() {
    return (DropTreeModel) super.getModel();
  }

  /**
   * @param newModel {@link DropTreeModel}
   */
  @Override
  public void setModel(TreeModel model) {
    super.setModel((DropTreeModel) model);
  }

  @Override
  public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf,
      int row, boolean hasFocus) {

    var node = (DropTreeNode) value;
    var item = node.getItem();
    if (item == null) {
      return null;
    }

    return item.getAlias() == null ? item.getDisplayString() : item.getAlias();
  }

  @Override
  protected void processKeyEvent(KeyEvent e) {
    if (DropTreeConfigAction.isCtrlUpDownKey(e)) {
      e.consume();
    }
    super.processKeyEvent(e);
  }

  @Override
  public String getToolTipText(MouseEvent event) {

    int row = getRowForLocation(event.getX(), event.getY());
    if (row < 0) {
      return null;
    }
    var path = getPathForRow(row);
    if (path == null) {
      return null;
    }
    var node = (DropTreeNode) path.getLastPathComponent();
    return node.getItem().getDisplayString();
  }
}
