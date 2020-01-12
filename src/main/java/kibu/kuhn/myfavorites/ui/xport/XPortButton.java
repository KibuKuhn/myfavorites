package kibu.kuhn.myfavorites.ui.xport;

import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import kibu.kuhn.myfavorites.ui.IGui;
import kibu.kuhn.myfavorites.ui.Icons;

public class XPortButton extends JCheckBox {

  private static final long serialVersionUID = 1L;

  public XPortButton() {
    setContentAreaFilled(false);
    setIcon(Icons.getIcon("right18"));
    setText(IGui.get().getI18n("xportbutton.label"));
  }

  @Override
  protected void fireActionPerformed(ActionEvent event) {
    setIcon(isSelected() ? Icons.getIcon("down18") : Icons.getIcon("right18"));
    super.fireActionPerformed(event);
  }
}
