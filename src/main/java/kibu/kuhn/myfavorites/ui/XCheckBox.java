package kibu.kuhn.myfavorites.ui;

import javax.swing.JCheckBox;

public class XCheckBox extends JCheckBox {

  private static final long serialVersionUID = 1L;

  public XCheckBox(String text) {
    super(text);
    setSelectedIcon(Icons.getIcon("checkbox_selected18"));
    setIcon(Icons.getIcon("checkbox_unselected18"));
    setRolloverEnabled(false);
  }

}
