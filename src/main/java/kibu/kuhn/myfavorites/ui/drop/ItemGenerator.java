package kibu.kuhn.myfavorites.ui.drop;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import kibu.kuhn.myfavorites.domain.DesktopItem;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.domain.HyperlinkItem;
import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.ui.drop.filter.FileFilter;

class ItemGenerator implements Function<TransferData, List<Item>> {

  @SuppressWarnings("unchecked")
  @Override
  public List<Item> apply(TransferData t) {
    Collection<? extends Object> data = t.getData();
    if (t.getFlavor().isFlavorJavaFileListType()) {
      return createItems((Collection<File>)data);
    }
    else if (t.getFlavor().getRepresentationClass() == String.class) {
      return createItems(data.iterator().next().toString());
    }

    throw new IllegalStateException("Unsupported data: "  + t.getFlavor().getHumanPresentableName());
  }

  private List<Item> createItems(Collection<File> data) {
    return data.stream().map(this::createItem).collect(Collectors.toList());
  }

  private List<Item> createItems(String link) {
    try {
      return Collections.singletonList(HyperlinkItem.of(new URL(link)));
    } catch (MalformedURLException e) {
      throw new IllegalStateException(e);
    }
  }

  private Item createItem(File f) {
    if (f.getName().endsWith(FileFilter.DESKTOP_SUFFIX)) {
      return DesktopItem.of(f.toPath(), true);
    }

    return FileSystemItem.of(f.toPath(), !f.isDirectory());
  }
}
