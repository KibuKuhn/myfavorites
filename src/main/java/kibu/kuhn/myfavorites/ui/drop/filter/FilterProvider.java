package kibu.kuhn.myfavorites.ui.drop.filter;

import java.awt.datatransfer.DataFlavor;
import java.util.function.Predicate;
import kibu.kuhn.myfavorites.ui.drop.TransferData;

class FilterProvider {

  private static FilterProvider provider = new FilterProvider();

  static FilterProvider get() {
    return provider;
  }

  private Predicate<TransferData> fileFilter;
  private Predicate<TransferData> hyperlinkFilter;

  Predicate<TransferData> getFilter(DataFlavor flavor) {
    if (flavor.isFlavorJavaFileListType()) {
      return getFileFilter();
    }
    else if (flavor.isMimeTypeEqual(DataFlavor.stringFlavor)) {
      return getHyperlinkFilter();
    }

    throw new UnsupportedOperationException("Flavor " + flavor.getHumanPresentableName() + " is not supported");
  }

  private Predicate<TransferData> getHyperlinkFilter() {
    if (hyperlinkFilter == null) {
      hyperlinkFilter = new HyperlinkFilter();
    }
    return hyperlinkFilter;
  }

  private Predicate<TransferData> getFileFilter() {
    if (fileFilter == null) {
      fileFilter = new FileFilter();
    }
    return fileFilter;
  }
}
