package kibu.kuhn.myfavorites.ui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.tree.TreePath;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

class DropListActions extends MouseAdapter implements KeyListener, FocusListener {

  private final MainMenu mainMenu;

  DropListActions(MainMenu mainMenu) {
    this.mainMenu = mainMenu;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getClickCount() != 2) {
      return;
    }
    var tree = (DropTree) e.getSource();
    openItem(tree);
  }

  private void openItem(DropTree tree) {
    var selectionPath = tree.getSelectionPath();
    if (selectionPath == null) {
      return;
    }

    var node = (DropTreeNode) selectionPath.getLastPathComponent();
    var type = node.getItem().getType();
    switch (type) {
      case BoxItem:
      case Root:
        toggleNode(node, tree);
        break;
      case FileSystemItem:
      case HyperlinkItem:
        this.mainMenu.openItemHandler.openItem(node.getItem());
        break;
      default:
    }
  }

  private void toggleNode(DropTreeNode node, DropTree tree) {
    var path = new LinkedList<DropTreeNode>();
    while(node != null) {
      path.add(0, node);
      node = node.getParent();
    }
    var treePath = new TreePath(path.toArray());
    var collapsed = tree.isCollapsed(treePath);
    if (collapsed) {
      tree.expandPath(treePath);
    }
    else {
      tree.collapsePath(treePath);
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() != KeyEvent.VK_ENTER) {
      return;
    }

    var tree = (DropTree) e.getSource();
    openItem(tree);
  }

  @Override
  public void keyPressed(KeyEvent e) {}

  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void focusGained(FocusEvent e) {
    var tree = (DropTree) e.getSource();
    if (tree.getSelectionPath() != null) {
      return;
    }

    tree.setSelectionRow(0);
  }

  @Override
  public void focusLost(FocusEvent e) {}

}