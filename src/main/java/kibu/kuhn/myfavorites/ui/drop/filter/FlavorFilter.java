package kibu.kuhn.myfavorites.ui.drop.filter;

import java.awt.datatransfer.DataFlavor;
import java.util.function.Predicate;

public class FlavorFilter implements Predicate<DataFlavor> {

  @Override
  public boolean test(DataFlavor flavor) {
    return flavor.isFlavorJavaFileListType() || flavor.isMimeTypeEqual(DataFlavor.stringFlavor);
  }

}
