package kibu.kuhn.myfavorites.ui;

import static java.awt.event.KeyEvent.*;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.JOptionPane;
import kibu.kuhn.myfavorites.prefs.IPreferencesService;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

public class DropTreeConfigActions extends KeyAdapter {

  public static boolean isCtrlUpDownKey(KeyEvent e) {
    return (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK)
        && (e.getKeyCode() == VK_UP || e.getKeyCode() == VK_DOWN);
  }

  private static UpDownHandler upHandler =
      new UpDownHandler(idx -> idx - 1, (idx, count) -> idx == 0);

  private static UpDownHandler downHandler =
      new UpDownHandler(idx -> idx + 1, (idx, count) -> idx >= count - 1);

  private AliasHandler aliasHandler = new AliasHandler();
  private BoxFactory boxHandler = BoxFactory.get();
  private CopyPasteHandler copyPasteHandler = new CopyPasteHandler();
  private OpenCloseHandler openCloseHandler = new OpenCloseHandler();

  private ManualItemHandler manualItemHandler = new ManualItemHandler();

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case VK_UP:
        upHandler.accept(e);
        break;
      case VK_DOWN:
        downHandler.accept(e);
        break;
      case VK_DELETE:
        deleteElement(e);
        break;
      case VK_A:
        aliasHandler.createAlias(e);
        break;
      case VK_B:
        boxHandler.createBox(e);
        break;
      case VK_C:
        copyPasteHandler.copy(e);
        break;
      case VK_X:
        copyPasteHandler.cut(e);
        break;
      case VK_V:
        copyPasteHandler.paste(e);
        break;
      case VK_ENTER:
        openCloseHandler.accept(e);
        break;
      case VK_F:
        manualItemHandler.createFavorite(e);
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

    var answer = YES_OPTION;
    if (IPreferencesService.get().isConfirmDeleteItem()) {
    //@formatter:off
      answer = JOptionPane.showOptionDialog(
                                  (Component)e.getSource(),
                                  IGui.get().getI18n("droptreeconfigactions.question.delete.message"),
                                  IGui.get().getI18n("droptreeconfigactions.question.delete.title"),                                  
                                  YES_NO_OPTION,
                                  QUESTION_MESSAGE,
                                  Icons.getIcon("help36"),
                                  new Object[]{
                                      IGui.get().getI18n("droptreeconfigactions.delete.yes"),
                                      IGui.get().getI18n("droptreeconfigactions.delete.no")},
                                  IGui.get().getI18n("droptreeconfigactions.delete.no"));
    }

    //@formatter:on
    if (answer == YES_OPTION) {
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
      if (!DropTreeConfigActions.isCtrlUpDownKey(e)) {
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
      var index = model.getIndexOfChild(parent, node);
      var childCount = parent.getChildCount();

      if (tester.test(index, childCount)) {
        return;
      }

      var newIndex = indexProvider.apply(index);
      parent.switchNodes(index, newIndex);

      model.nodeStructureChanged(parent);
      tree.getSelectionModel().setSelectionPath(path);
    }
  }
}
