package kibu.kuhn.myfavorites.ui.drop;

import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;
import java.awt.event.KeyEvent;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.ui.DropTreeConfigAction;

public class DropTree extends JTree {

  private static final long serialVersionUID = 1L;

  public DropTree() {
    super(DropTreeModel.instance());
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

    DropTreeNode node = (DropTreeNode) value;
    Item item = node.getUserObject();
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

}
