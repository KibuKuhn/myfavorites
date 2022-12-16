package kibu.kuhn.myfavorites.ui;

import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.ui.drop.DropTreeModel;
import kibu.kuhn.myfavorites.ui.drop.ItemTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.util.Optional;


/**
 * Add items manually
 */
class ManualItemHandler {
	private ManualItemDialog dialog;

	private Optional<Item> create(KeyEvent e) {
		if (dialog == null) {
			dialog = new ManualItemDialog(e.getComponent());
		}
		dialog.setLocationRelativeTo(e.getComponent());
		dialog.setVisible(true);
		return dialog.getItem();
	}

	void  createFavorite(KeyEvent e) {
		Optional<Item> itemOpt = create(e);
		if (itemOpt.isEmpty()) {
			return;
		}

		Item item = itemOpt.get();
		ItemTreeNode node = ItemTreeNode.of(item);
		DropTreeModel.instance().appendToRoot(node);
	}
}
