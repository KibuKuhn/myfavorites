package kibu.kuhn.myfavorites.ui.drop;

import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.util.Arrays;
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
      var transferable = event.getTransferable();
      var flavors = transferable.getTransferDataFlavors();

      //@formatter:off
      var nodes = Arrays.stream(flavors)
                        .filter(new FlavorFilter())
                        .map(new FlavorMapper(event))
                        .filter(new TransferDataFilter())
                        .map(IITemGenerator.get())
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
