package kibu.kuhn.myfavorites.ui.xport;

import javax.swing.Action;
import javax.swing.JButton;

class ActionButton extends JButton {

    private static final long serialVersionUID = 1L;

    ActionButton(Action action) {
      super(action);
      setBorderPainted(false);
      setBorder(null);
      setContentAreaFilled(false);
    }
  }