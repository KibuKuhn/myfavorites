package kibu.kuhn.myfavorites.ui.buttonbar;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.JPanel;

public class ButtonBar extends JPanel {

  public static final String ACTION_EXIT = "exit";
  public static final String ACTION_CONFIG = "config";
  public static final String ACTION_SETTINGS = "settings";
  public static final String ACTION_HELP = "help";

  private static final long serialVersionUID = 1L;

  public ButtonBar(Consumer<? super ActionEvent> action) {
    init(action);
  }


  private void init(Consumer<? super ActionEvent> action) {
    setLayout(new GridLayout(1, 4));
    add(new ButtonBarButton(new ConfigAction(action)));
    add(new ButtonBarButton(new SettingsAction(action)));
    add(new ButtonBarButton(new HelpAction(action)));
    add(new ButtonBarButton(new ExitAction(action)));
  }

}
