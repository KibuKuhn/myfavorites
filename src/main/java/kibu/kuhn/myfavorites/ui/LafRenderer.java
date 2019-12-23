package kibu.kuhn.myfavorites.ui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.UIManager.LookAndFeelInfo;

class LafRenderer extends DefaultListCellRenderer {

  private static final long serialVersionUID = 1L;

  @Override
  public Component getListCellRendererComponent(JList<? extends Object> list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {

    LookAndFeelInfo laf = (LookAndFeelInfo) value;
    return super.getListCellRendererComponent(list, laf.getName(), index, isSelected, cellHasFocus);
  }

}
