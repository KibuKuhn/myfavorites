package kibu.kuhn.myfavorites.ui.xport;

import kibu.kuhn.myfavorites.ui.drop.DropTreeModel;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

class NodeImportReplaceHandler implements NodeImportHandler {

  @Override
  public void accept(RootNode node) {
    DropTreeModel.instance().getRoot().removeAllChildren();
    new NodeImportMergeHandler().accept(node);
  }

}
