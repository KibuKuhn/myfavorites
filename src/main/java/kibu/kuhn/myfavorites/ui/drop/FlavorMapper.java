package kibu.kuhn.myfavorites.ui.drop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FlavorMapper implements Function<DataFlavor, TransferData> {

  private static final Logger LOGGER = LoggerFactory.getLogger(FlavorMapper.class);

  private DropTargetDropEvent event;

  FlavorMapper(DropTargetDropEvent event) {
    this.event = event;
  }

  @Override
  public TransferData apply(DataFlavor flavor) {
    Transferable transferable = event.getTransferable();
    try {
      var transferData = getData(transferable, flavor);
      LOGGER.debug("Files dropped: {}", transferData);
      return new TransferData(getData(transferable, flavor), flavor);
    } catch (UnsupportedFlavorException | IOException e) {
      LOGGER.error(e.getMessage(), e);
      throw new IllegalStateException(e);
    }
  }

  @SuppressWarnings("unchecked")
  private Collection<? extends Object> getData(Transferable t, DataFlavor f) throws UnsupportedFlavorException, IOException {
    LOGGER.debug("Files dropped: {}", t);
    Object data = t.getTransferData(f);
    Collection<? extends Object> result = null;
    if (!(data instanceof Collection)) {
      result = Collections.singleton(data);
    } else {
      result = (Collection<? extends Object>) data;
    }
    return result;
  }
}