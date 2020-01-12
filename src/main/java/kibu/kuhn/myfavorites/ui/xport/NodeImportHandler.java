package kibu.kuhn.myfavorites.ui.xport;

import java.util.function.Consumer;
import kibu.kuhn.myfavorites.prefs.IPreferencesService;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

public interface NodeImportHandler extends Consumer<RootNode> {

  default void save(RootNode node) {
    IPreferencesService.get().saveItems(node);
  }

}
