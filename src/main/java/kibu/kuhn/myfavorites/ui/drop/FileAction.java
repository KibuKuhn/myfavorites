package kibu.kuhn.myfavorites.ui.drop;

import java.io.File;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.domain.Item;

class FileAction implements Consumer<File> {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(FileAction.class);
  
  private DropListModel model;

  FileAction(DropListModel model) {
    this.model = model;
  }

  @Override
  public void accept(File f) {
    LOGGER.debug("Dropped file {}", f);
    Item item = Item.of(f.toPath(), f.isFile());
    if (model.contains(item)) {
      LOGGER.debug("Model already contains {}", f);
      return;
    }
    
    model.addElement(item);
  }
  
}