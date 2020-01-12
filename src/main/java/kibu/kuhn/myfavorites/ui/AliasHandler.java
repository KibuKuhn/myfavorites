package kibu.kuhn.myfavorites.ui;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import kibu.kuhn.myfavorites.domain.Type;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

class AliasHandler {

  void createAlias(KeyEvent e) {
    if (e.getModifiersEx() != KeyEvent.CTRL_DOWN_MASK) {
      return;
    }

    DropTree tree = (DropTree) e.getSource();
    var selectionPath = tree.getSelectionPath();
    if (selectionPath == null) {
      return;
    }
    DropTreeNode node = (DropTreeNode) selectionPath.getLastPathComponent();
    if (node.isRoot()) {
      return;
    }

    String alias = null;
    if (node.getItem().getType() == Type.BoxItem) {
      setBoxName(tree, node);
      return;
    }


    alias = JOptionPane.showInputDialog(Gui.get().getI18n("aliashandler.label"),
        node.getItem().getAlias());
    if (alias == null || alias.length() > 0 && alias.isBlank()) {
      return;
    }

    applyAlias(tree, node, alias);
  }

  private void applyAlias(DropTree tree, DropTreeNode node, String alias) {
    alias = alias.trim();
    node.getItem().setAlias(alias.isBlank() ? null : alias);
    tree.getModel().nodeChanged(node);
  }

  private void setBoxName(DropTree tree, DropTreeNode node) {
    var currentBoxNames = BoxFactory.get().getCurrentBoxNames(tree);
    String alias = null;
    String title = null;
    //@formatter:off
    do {
      title = title == null ? Gui.get().getI18n("aliashandler.label")
                            : Gui.get().getI18n("aliashandler.label.retry");
      alias = JOptionPane.showInputDialog(title, node.getItem().getAlias());
      if (alias == null || alias.isBlank()) {
        return;
      }
    }
    while (currentBoxNames.contains(alias));
    //@formatter:on
    applyAlias(tree, node, alias);
  }
}
