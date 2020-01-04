package kibu.kuhn.myfavorites.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.KeyStore;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.ui.drop.DropList;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

public class DropTreeConfigAction extends KeyAdapter {

  public static boolean isCtrlUpDownKey(KeyEvent e) {
    return (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK)
        && (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN);
  }

  private static UpDownHandler upHandler =
      new UpDownHandler(idx -> idx - 1, (idx, list) -> idx == 0);
  private static UpDownHandler downHandler = new UpDownHandler(idx -> idx + 1,
      (idx, list) -> (idx.intValue() >= list.getModel().getSize() - 1));

  private AliasHandler aliasHandler = new AliasHandler();
  private BoxHandler slipBoxHandler = BoxHandler.get();
  private CopyPasteHandler copyPasteHandler = new CopyPasteHandler();

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        upHandler.accept(e);
        break;
      case KeyEvent.VK_DOWN:
        downHandler.accept(e);
        break;
      case KeyEvent.VK_DELETE:
        deleteElement(e);
        break;
      case KeyEvent.VK_A:
        aliasHandler.createAlias(e);
        break;
      case KeyEvent.VK_B:
        slipBoxHandler.createBox(e);
        break;
      case KeyEvent.VK_X:
        copyPasteHandler.cut(e);
        break;
      case KeyEvent.VK_P:
        copyPasteHandler.paste(e);
        break;
    }
  }

  private void deleteElement(KeyEvent e) {
    if (e.getKeyCode() != KeyEvent.VK_DELETE) {
      return;
    }

    var tree = (DropTree) e.getSource();
    TreePath selectionPath = tree.getSelectionPath();
    if (selectionPath == null) {
      return;
    }
    DropTreeNode node = (DropTreeNode) selectionPath.getLastPathComponent();
    tree.getModel().removeNodeFromParent(node);
  }

  private static class UpDownHandler implements Consumer<KeyEvent> {

    private Function<Integer, Integer> f;
    private BiPredicate<Integer, DropList> p;


    private UpDownHandler(Function<Integer, Integer> f, BiPredicate<Integer, DropList> p) {
      this.f = f;
      this.p = p;
    }

    @Override
    public void accept(KeyEvent e) {
      if (!DropTreeConfigAction.isCtrlUpDownKey(e)) {
        return;
      }

      DropList list = (DropList) e.getSource();
      FileSystemItem item = list.getSelectedValue();
      if (item == null) {
        return;
      }

      int selectedIndex = list.getSelectedIndex();
      if (p.test(selectedIndex, list)) {
        return;
      }

      item = list.getModel().remove(selectedIndex);
      int newIdx = f.apply(selectedIndex);
      list.getModel().add(newIdx, item);
      list.setSelectedIndex(newIdx);
    }
  };

}
