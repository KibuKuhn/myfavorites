package kibu.kuhn.myfavorites.ui;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.tree.TreePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.domain.CloneableItem;
import kibu.kuhn.myfavorites.domain.Type;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;
import kibu.kuhn.myfavorites.ui.drop.ItemTreeNode;

class CopyPasteHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(CopyPasteHandler.class);

  private static Set<Type> copySupport = new HashSet<>();
  static {
    copySupport.add(Type.FileSystemItem);
    copySupport.add(Type.HyperlinkItem);
  }

  private TreePath selectionPath;

  public void cut(KeyEvent e) {
   var path = canCopyCut(e);
   if (path == null) {
     return;
   }

    selectionPath = path;
  }

  public void copy(KeyEvent e) {
    var path = canCopyCut(e);
    if (path == null) {
      return;
    }

    var node = (DropTreeNode) path.getLastPathComponent();
    if (!(node.getItem() instanceof CloneableItem)) {
      return;
    }

    var currentItem = (CloneableItem) node.getItem();
    try {
      var clone = currentItem.clone();
      var newNode = ItemTreeNode.of(clone);
      selectionPath = new TreePath(newNode);
    } catch (CloneNotSupportedException ex) {
      LOGGER.error(ex.getMessage(), ex);
      selectionPath = null;
    }
  }

  private TreePath canCopyCut(KeyEvent e) {
    if (e.getModifiersEx() != KeyEvent.CTRL_DOWN_MASK) {
      return null;
    }

    var tree = (DropTree) e.getSource();
    var path = tree.getSelectionPath();
    if (path == null) {
      return null;
    }

    if (((DropTreeNode)path.getLastPathComponent()).isRoot()) {
      return null;
    }

    if (!copySupport.contains(((DropTreeNode)path.getLastPathComponent()).getItem().getType())) {
      return null;
    }

    return path;
  }

  public void paste(KeyEvent e) {
    if (selectionPath == null) {
      return;
    }

    if (e.getModifiersEx() != KeyEvent.CTRL_DOWN_MASK) {
      return;
    }

    var tree = (DropTree) e.getSource();
    var path = tree.getSelectionPath();
    if (path == null) {
      return;
    }

    var targetNode = (DropTreeNode) path.getLastPathComponent();
    if (targetNode instanceof ItemTreeNode) {
      return;
    }

    tree.getModel().appendToNode(targetNode, (DropTreeNode)selectionPath.getLastPathComponent());
    selectionPath = null;
  }

}
