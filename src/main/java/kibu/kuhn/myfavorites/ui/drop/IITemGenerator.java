package kibu.kuhn.myfavorites.ui.drop;

import java.util.List;
import java.util.function.Function;

import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.util.ISystemUtils;

interface IITemGenerator extends Function<TransferData, List<Item>> {

  static IITemGenerator get() {
    return ISystemUtils.get().isWindows() ? new WindowsItemGenerator() : new ItemGenerator();
  }

}