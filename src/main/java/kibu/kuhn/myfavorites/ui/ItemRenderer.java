package kibu.kuhn.myfavorites.ui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import kibu.kuhn.myfavorites.domain.Item;

public class ItemRenderer extends DefaultListCellRenderer {

  private static final long serialVersionUID = 1L;

  @Override
  public Component getListCellRendererComponent(JList<?> list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {
    Item item = (Item) value;
    value = getString(item);
    JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    l.setIcon(item.getIcon());
    l.setToolTipText(item.getPath().toString());
    return l;
  }

  private String getString(Item item) {
    return item.getPath().getFileName().toString();
  }
}
