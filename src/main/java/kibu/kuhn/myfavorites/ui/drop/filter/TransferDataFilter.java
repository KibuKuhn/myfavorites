package kibu.kuhn.myfavorites.ui.drop.filter;

import java.util.function.Predicate;
import kibu.kuhn.myfavorites.ui.drop.TransferData;

public class TransferDataFilter implements Predicate<TransferData> {

  @Override
  public boolean test(TransferData t) {
      return FilterProvider.get().getFilter(t.getFlavor()).test(t);
  }
}