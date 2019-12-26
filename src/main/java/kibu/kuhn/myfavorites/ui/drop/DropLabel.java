package kibu.kuhn.myfavorites.ui.drop;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import kibu.kuhn.myfavorites.ui.IGui;

public final class DropLabel extends JLabel {

  private static final long serialVersionUID = 1L;

  public DropLabel() {
    super(IGui.get().getI18n("configmenu.text"));
    setForeground(Color.GRAY);
    setHorizontalAlignment(CENTER);
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension size = super.getPreferredSize();
    size.height = 100;
    return size;
  }
}