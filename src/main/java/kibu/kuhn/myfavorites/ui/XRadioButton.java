package kibu.kuhn.myfavorites.ui;

import javax.swing.Action;
import javax.swing.JRadioButton;

public class XRadioButton extends JRadioButton {

  private static final long serialVersionUID = 1L;

  public XRadioButton(Action action) {
    super(action);
    setSelectedIcon(Icons.getIcon("radiobutton_checked18"));
    setIcon(Icons.getIcon("radiobutton_unchecked18"));
    setRolloverEnabled(false);
  }

}
