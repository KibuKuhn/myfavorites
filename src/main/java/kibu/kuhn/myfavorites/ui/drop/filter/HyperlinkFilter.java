package kibu.kuhn.myfavorites.ui.drop.filter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.ui.drop.TransferData;

class HyperlinkFilter implements Predicate<TransferData> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HyperlinkFilter.class);

  @Override
  public boolean test(TransferData t) {
    Collection<? extends Object> data = t.getData();
    if (data.size() != 1) {
      return false;
    }

    boolean valid = false;
    String s = (String) data.iterator().next().toString();
      try {
        URL url = new URL(s);
        valid = true;
        new File(url.toURI());
        valid = false;
      } catch (Exception e) {
        LOGGER.error(e.getMessage());
      }
      return valid;
  }
}
