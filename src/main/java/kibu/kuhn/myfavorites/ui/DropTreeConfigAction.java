package kibu.kuhn.myfavorites.ui;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.JOptionPane;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

public class DropTreeConfigAction extends KeyAdapter {

  public static boolean isCtrlUpDownKey(KeyEvent e) {
    return (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK)
        && (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN);
  }

  private static UpDownHandler upHandler =
      new UpDownHandler(idx -> idx - 1, (idx, count) -> idx == 0);

  private static UpDownHandler downHandler =
      new UpDownHandler(idx -> idx + 1, (idx, count) -> idx >= count - 1);

  private AliasHandler aliasHandler = new AliasHandler();
  private BoxFactory boxHandler = BoxFactory.get();
  private CopyPasteHandler copyPasteHandler = new CopyPasteHandler();
  private OpenCloseHandler openCloseHandler = new OpenCloseHandler();

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
        boxHandler.createBox(e);
        break;
      case KeyEvent.VK_C:
        copyPasteHandler.copy(e);
        break;
      case KeyEvent.VK_X:
        copyPasteHandler.cut(e);
        break;
      case KeyEvent.VK_V:
        copyPasteHandler.paste(e);
        break;
      case KeyEvent.VK_ENTER:
        openCloseHandler.accept(e);
        break;

    }
  }

  private void deleteElement(KeyEvent e) {
    var tree = (DropTree) e.getSource();
    var selectionPath = tree.getSelectionPath();
    if (selectionPath == null) {
      return;
    }

    var node = (DropTreeNode) selectionPath.getLastPathComponent();
    if (node.isRoot()) {
      return;
    }
    //@formatter:off
    int answer = JOptionPane.showConfirmDialog(
                                  (Component)e.getSource(),
                                  IGui.get().getI18n("droptreeconfigaction.question.delete.message"),
                                  IGui.get().getI18n("droptreeconfigaction.question.delete.title"),
                                  JOptionPane.YES_NO_OPTION,
                                  JOptionPane.QUESTION_MESSAGE,
                                  Icons.getIcon("dead32"));
    //@formatter:on
    if (answer == JOptionPane.YES_OPTION) {
      tree.getModel().removeNodeFromParent(node);
    }
  }

  private static class UpDownHandler implements Consumer<KeyEvent> {

    private Function<Integer, Integer> indexProvider;
    private BiPredicate<Integer, Integer> tester;


    private UpDownHandler(Function<Integer, Integer> f, BiPredicate<Integer, Integer> p) {
      this.indexProvider = f;
      this.tester = p;
    }

    @Override
    public void accept(KeyEvent e) {
      if (!DropTreeConfigAction.isCtrlUpDownKey(e)) {
        return;
      }

      var tree = (DropTree) e.getSource();
      var path = tree.getSelectionPath();
      if (path == null) {
        return;
      }

      var node = (DropTreeNode) path.getLastPathComponent();
      var model = tree.getModel();
      if (node == model.getRoot()) {
        return;
      }

      var parent = node.getParent();
      int index = model.getIndexOfChild(parent, node);
      int childCount = parent.getChildCount();

      if (tester.test(index, childCount)) {
        return;
      }

      int newIndex = indexProvider.apply(index);
      parent.switchNodes(index, newIndex);

      model.nodeStructureChanged(parent);
      tree.getSelectionModel().setSelectionPath(path);
    }
  };

}
