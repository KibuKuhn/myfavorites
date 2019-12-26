package kibu.kuhn.myfavorites.ui.buttonbar;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import kibu.kuhn.myfavorites.ui.IGui;

class ExitAction extends AbstractMenuAction {

  private static final long serialVersionUID = 1L;

  ExitAction(Consumer<? super ActionEvent> action) {
    super(action);
    putValue(SHORT_DESCRIPTION, IGui.get().getI18n("mainmenu.buttonbar.button.exit"));
    putValue(SMALL_ICON, IGui.get().createImage("cancel18", "exit"));
    putValue(ACTION_COMMAND_KEY, ButtonBar.ACTION_EXIT);
  }
}