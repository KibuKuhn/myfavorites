package kibu.kuhn.myfavorites.ui.buttonbar;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.AbstractAction;

abstract class AbstractMenuAction extends AbstractAction {
  
  private static final long serialVersionUID = 1L;
  
  protected Consumer<? super ActionEvent> action;

  AbstractMenuAction(Consumer<? super ActionEvent> action) {
    this.action = action;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    action.accept(e);
  }
}