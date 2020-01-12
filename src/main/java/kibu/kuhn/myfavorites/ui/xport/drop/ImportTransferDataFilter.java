package kibu.kuhn.myfavorites.ui.xport.drop;

import java.io.File;
import java.util.Collection;
import java.util.function.Predicate;
import kibu.kuhn.myfavorites.ui.drop.TransferData;

class ImportTransferDataFilter implements Predicate<TransferData> {

  @Override
  public boolean test(TransferData t) {
    @SuppressWarnings("unchecked")
    Collection<File> data = (Collection<File>) t.getData();
    return data.size() == 1;
  }

}
