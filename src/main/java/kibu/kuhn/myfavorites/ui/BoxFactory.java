package kibu.kuhn.myfavorites.ui;

import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import kibu.kuhn.myfavorites.domain.Type;
import kibu.kuhn.myfavorites.ui.drop.BoxNode;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

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
    var name = getBoxName(tree);
    if (name == null) {
      return;
    }

    var boxNode = new BoxNode();
    boxNode.getItem().setAlias(name);
    tree.getModel().appendToRoot(boxNode);
  }

  private String getBoxName(DropTree tree) {
    //@formatter:off
    var currentBoxNames = getCurrentBoxNames(tree);
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
    var root = tree.getModel().getRoot();
    //@formatter:off
    var currentBoxNames =
               root.getChildren()
                   .stream()
                   .filter(node -> ((DropTreeNode)node).getItem().getType() == Type.BoxItem)
                   .map(node -> ((DropTreeNode)node).getItem().getAlias())
                   .collect(Collectors.toSet());
    //@formatter:on
    return currentBoxNames;
  }
}
