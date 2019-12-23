package kibu.kuhn.myfavorites.ui.drop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FileFlavorMapper implements Function<DataFlavor, List<File>> {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(FileFlavorMapper.class);
  
  private DropTargetDropEvent event;

  FileFlavorMapper(DropTargetDropEvent event) {
    this.event = event;
  }

  @Override
  public List<File> apply(DataFlavor flavor) {
    event.acceptDrop(event.getDropAction());
    Transferable transferable = event.getTransferable();
    try {
      @SuppressWarnings("unchecked")
      List<File> files = (List<File>) transferable.getTransferData(flavor);
      LOGGER.debug("Files dropped: {}", files);
      return files;
    } catch (UnsupportedFlavorException | IOException e) {
      LOGGER.error(e.getMessage(), e);
      throw new IllegalStateException(e);
    }
  }
}