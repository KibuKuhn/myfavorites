package kibu.kuhn.myfavorites.ui;

import java.awt.Color;
import javax.swing.JLabel;

public class InfoLabel extends JLabel {

  private static final long serialVersionUID = 1L;

  public InfoLabel() {
    setForeground(Color.RED);
  }

  @Override
  public void setText(String text) {
    super.setText(text);
    setToolTipText(text);
  }


}
