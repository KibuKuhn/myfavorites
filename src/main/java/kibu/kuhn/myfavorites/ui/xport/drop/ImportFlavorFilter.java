package kibu.kuhn.myfavorites.ui.xport.drop;

import java.awt.datatransfer.DataFlavor;
import java.util.function.Predicate;

class ImportFlavorFilter implements Predicate<DataFlavor> {

  @Override
  public boolean test(DataFlavor t) {
    return t.isFlavorJavaFileListType();
  }
}
