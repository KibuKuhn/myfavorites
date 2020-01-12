package kibu.kuhn.myfavorites.ui;

import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import kibu.kuhn.myfavorites.domain.Type;
import kibu.kuhn.myfavorites.ui.drop.BoxNode;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

class BoxFactory {

  private static BoxFactory handler = new BoxFactory();

  public static BoxFactory get() {
    return handler;
  }

  private BoxFactory() {}

  public void createBox(KeyEvent e) {
    if (e.getModifiersEx() != KeyEvent.CTRL_DOWN_MASK) {
      return;
    }

    DropTree tree = (DropTree) e.getSource();
    String name = getBoxName(tree);
    if (name == null) {
      return;
    }

    BoxNode boxNode = new BoxNode();
    boxNode.getUserObject().setAlias(name);
    tree.getModel().appendToRoot(boxNode);
  }

  private String getBoxName(DropTree tree) {
    //@formatter:off
    Set<String> currentBoxNames = getCurrentBoxNames(tree);
    String name = null;
    String title = null;
    do {
      title = title == null ? IGui.get().getI18n("boxhandler.box.new")
                            : IGui.get().getI18n("boxhandler.box.new.retry");
      name = JOptionPane.showInputDialog(title);
      if (name == null) {
        return null;
      }
    } while (currentBoxNames.contains(name));
    //@formatter:on
    return name;
  }

  Set<String> getCurrentBoxNames(DropTree tree) {
    RootNode root = tree.getModel().getRoot();
    Set<String> currentBoxNames =
        Collections.list(root.children())
                   .stream()
                   .filter(node -> ((DropTreeNode)node).getUserObject().getType() == Type.BoxItem)
                   .map(node -> ((DropTreeNode)node).getUserObject().getAlias())
                   .collect(Collectors.toSet());
    return currentBoxNames;
  }
}
