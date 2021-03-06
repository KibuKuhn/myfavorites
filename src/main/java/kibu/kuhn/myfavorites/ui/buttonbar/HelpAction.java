package kibu.kuhn.myfavorites.ui.buttonbar;

import static kibu.kuhn.myfavorites.ui.buttonbar.ButtonBar.ACTION_HELP;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import kibu.kuhn.myfavorites.ui.IGui;
import kibu.kuhn.myfavorites.ui.Icons;

class HelpAction extends AbstractMenuAction {

  private static final long serialVersionUID = 1L;


  HelpAction(Consumer<? super ActionEvent> action) {
    super(action);
    putValue(SHORT_DESCRIPTION, IGui.get().getI18n("mainmenu.buttonbar.button.help"));
    putValue(SMALL_ICON, Icons.getIcon("help18"));
    putValue(ACTION_COMMAND_KEY, ACTION_HELP);
  }
}