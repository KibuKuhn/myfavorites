package kibu.kuhn.myfavorites.ui.buttonbar;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import kibu.kuhn.myfavorites.ui.IGui;
import kibu.kuhn.myfavorites.ui.Icons;

class ConfigAction extends AbstractMenuAction {

  private static final long serialVersionUID = 1L;


  ConfigAction(Consumer<? super ActionEvent> action) {
    super(action);
    putValue(SHORT_DESCRIPTION, IGui.get().getI18n("mainmenu.buttonbar.button.config"));
    putValue(SMALL_ICON, Icons.getIcon("favorites18"));
    putValue(ACTION_COMMAND_KEY, ButtonBar.ACTION_CONFIG);
  }
}