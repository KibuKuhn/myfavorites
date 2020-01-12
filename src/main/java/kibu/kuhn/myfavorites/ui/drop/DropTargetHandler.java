package kibu.kuhn.myfavorites.ui.drop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.ui.drop.filter.FlavorFilter;
import kibu.kuhn.myfavorites.ui.drop.filter.TransferDataFilter;


public final class DropTargetHandler extends DropTargetAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(DropTargetHandler.class);

  private DropTreeModel model;

  public DropTargetHandler(DropTreeModel model) {
    this.model = model;
  }

  @Override
  public void drop(DropTargetDropEvent event) {
    boolean dropState = true;
    try {
      event.acceptDrop(event.getDropAction());
      Transferable transferable = event.getTransferable();
      DataFlavor[] flavors = transferable.getTransferDataFlavors();
      //@formatter:off
      List<ItemTreeNode> nodes = Arrays.stream(flavors)
                                       .filter(new FlavorFilter())
                                       .map(new FlavorMapper(event))
                                       .filter(new TransferDataFilter())
                                       .map(new ItemGenerator())
                                       .flatMap(items -> items.stream())
                                       .map(ItemTreeNode::of)
                                       .collect(Collectors.toList());
      //@formatter:on
      if (nodes.isEmpty()) {
        LOGGER.debug("No nodes to add");
        dropState = false;
      } else {
        nodes.forEach(model::appendToRoot);
        LOGGER.debug("Nodes in root: {}", model.getRoot().getChildCount());
      }
    } catch (Exception ex) {
      dropState = false;
      throw new IllegalStateException(ex);
    } finally {
      event.dropComplete(dropState);
    }
  }
}
