package kibu.kuhn.myfavorites.ui;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.ui.drop.DropList;

class AliasHandler {

  void createAlias(KeyEvent e) {
    DropList list = (DropList) e.getSource();
    Item item = list.getSelectedValue();
    if (item == null) {
      return;
    }

    String alias = JOptionPane.showInputDialog(Gui.get().getI18n("aliashandler.label"));
    if (alias == null) {
      return;
    }
    if (alias.isBlank()) {
      return;
    }

    alias = alias.trim();
    item.setAlias(alias);
  }
}
