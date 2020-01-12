package kibu.kuhn.myfavorites.ui;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

class OpenCloseHandler implements Consumer<KeyEvent> {

  @Override
  public void accept(KeyEvent e) {
    var tree = (DropTree) e.getSource();
    var path = tree.getSelectionPath();
    if (path == null) {
      return;
    }

    if (((DropTreeNode)path.getLastPathComponent()).isLeaf()) {
      return;
    }

    if (tree.isCollapsed(path)) {
      tree.expandPath(path);
    }
    else {
      tree.collapsePath(path);
    }
  }
}
