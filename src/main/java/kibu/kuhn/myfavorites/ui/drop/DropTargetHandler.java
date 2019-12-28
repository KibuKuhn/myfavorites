package kibu.kuhn.myfavorites.ui.drop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class DropTargetHandler extends DropTargetAdapter {

  private DropListModel model;

  public DropTargetHandler(DropListModel model) {
    this.model = model;
  }

  @Override
  public void drop(DropTargetDropEvent event) {
    List<File> droppedFiles = null;
    try  {
    Transferable transferable = event.getTransferable();
      DataFlavor[] flavors = transferable.getTransferDataFlavors();
      //@formatter:off
      droppedFiles = Arrays.stream(flavors)
                           .filter(flavor -> flavor.isFlavorJavaFileListType())
                           .map(new FileFlavorMapper(event))
                           .flatMap(files -> files.stream())
                           .filter(new FileFilter())
                           .collect(Collectors.toList());
      //@formatter:on
      event.dropComplete(true);
    }
    catch (Exception ex) {
      event.rejectDrop();
      throw new RuntimeException(ex);
    }
    droppedFiles.forEach(new FileAction(model));
  }
}