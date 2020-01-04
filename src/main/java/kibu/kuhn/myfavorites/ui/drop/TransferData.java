package kibu.kuhn.myfavorites.ui.drop;

import java.awt.datatransfer.DataFlavor;
import java.util.Collection;

public class TransferData {
  private Collection<? extends Object> data;
  private DataFlavor flavor;

  TransferData(Collection<? extends Object> data, DataFlavor flavor) {
    this.data = data;
    this.flavor = flavor;
  }

  public Collection<? extends Object> getData() {
    return data;
  }

  public DataFlavor getFlavor() {
    return flavor;
  }
}
