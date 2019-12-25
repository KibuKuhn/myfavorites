package kibu.kuhn.myfavorites.ui;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;

class ErrorPane extends JTextArea {

  private static final long serialVersionUID = 1L;

  ErrorPane() {
    setForeground(Color.RED);
    setBorder(BorderFactory.createLineBorder(Color.RED));
    setVisible(false);
    setEditable(false);
    setLineWrap(true);
    setWrapStyleWord(true);
  }

  @Override
  public void setText(String t) {
    super.setText(t);
    setVisible(true);
  }



}
