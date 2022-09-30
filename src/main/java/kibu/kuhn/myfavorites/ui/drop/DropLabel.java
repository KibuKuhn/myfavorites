package kibu.kuhn.myfavorites.ui.drop;

import java.awt.Color;

import javax.swing.JLabel;

import kibu.kuhn.myfavorites.ui.IGui;

public final class DropLabel extends JLabel {

  private static final long serialVersionUID = 1L;

  public DropLabel() {
    this(IGui.get().getI18n("configmenu.text"));
  }

  public DropLabel(String text) {
    super(text);
    setForeground(Color.GRAY);
    setHorizontalAlignment(CENTER);
  }
}