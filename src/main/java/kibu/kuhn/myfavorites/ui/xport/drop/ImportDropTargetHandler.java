package kibu.kuhn.myfavorites.ui.xport.drop;

import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.prefs.NodeMapper;
import kibu.kuhn.myfavorites.ui.drop.FlavorMapper;
import kibu.kuhn.myfavorites.ui.drop.RootNode;
import kibu.kuhn.myfavorites.ui.xport.NodeImportHandler;


public class ImportDropTargetHandler extends DropTargetAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImportDropTargetHandler.class);

  private Consumer<Exception> exceptionHandler;

  private NodeImportHandler nodeImportHandler;

  public ImportDropTargetHandler(Consumer<Exception> exceptionHandler) {
    this.exceptionHandler = exceptionHandler;
  }

  @Override
  public void drop(DropTargetDropEvent event) {
    boolean dropState = true;
    try {
      event.acceptDrop(event.getDropAction());
      var transferable = event.getTransferable();
      var flavors = transferable.getTransferDataFlavors();
      //@formatter:off
      var data = Arrays.stream(flavors)
                       .filter(new ImportFlavorFilter())
                       .map(new FlavorMapper(event))
                       .filter(new ImportTransferDataFilter())
                       .findFirst();
      //@formatter:on
      if (data.isEmpty()) {
        LOGGER.debug("No imports found");
        dropState = false;
      }

      var json = Files.readString(((File)data.get().getData().iterator().next()).toPath(), StandardCharsets.UTF_8);
      var root = (RootNode) new NodeMapper().mapToNode(json);
      nodeImportHandler.accept(root);
    } catch (Exception ex) {
      dropState = false;
      LOGGER.error(ex.getMessage(), ex);
      exceptionHandler.accept(ex);
    } finally {
      event.dropComplete(dropState);
    }
  }

  public void setImportHandler(NodeImportHandler importHandler) {
    Objects.requireNonNull(importHandler);
    nodeImportHandler = importHandler;
  }
}
