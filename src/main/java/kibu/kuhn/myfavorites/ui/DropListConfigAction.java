package kibu.kuhn.myfavorites.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.ui.drop.DropList;

public class DropListConfigAction extends KeyAdapter {

  public static boolean isCtrlUpDownKey(KeyEvent e) {
    return (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) &&
           (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN);
  }

  private static UpDownHandler upHandler = new UpDownHandler(idx -> idx - 1, (idx, list) -> idx == 0);
  private static UpDownHandler downHandler = new UpDownHandler(idx -> idx + 1, (idx, list) -> (idx.intValue() >= list.getModel().getSize() - 1));

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
        deleteItem(e);
    }
  }

  private void deleteItem(KeyEvent e) {
    if (e.getKeyCode() != KeyEvent.VK_DELETE) {
      return;
    }

    DropList list = (DropList) e.getSource();
    Item item = list.getSelectedValue();
    if (item == null) {
      return;
    }
    list.getModel().removeElement(item);
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
      if (!DropListConfigAction.isCtrlUpDownKey(e)) {
        return;
      }

      DropList list = (DropList) e.getSource();
      Item item = list.getSelectedValue();
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
