package kibu.kuhn.myfavorites.ui.buttonbar;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import kibu.kuhn.myfavorites.MyFavorites;
import kibu.kuhn.myfavorites.ui.Gui;

class SettingsAction extends AbstractMenuAction {

  private static final long serialVersionUID = 1L;

  SettingsAction(Consumer<? super ActionEvent> action) {
    super(action);
    putValue(SHORT_DESCRIPTION, Gui.get().getBundle().getString("mainmenu.buttonbar.button.settings"));
    putValue(SMALL_ICON, MyFavorites.createImage("menu18", "menu"));
    putValue(ACTION_COMMAND_KEY, ButtonBar.ACTION_SETTINGS);
  }
}
