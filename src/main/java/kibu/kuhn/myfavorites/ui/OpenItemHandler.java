package kibu.kuhn.myfavorites.ui;

import java.awt.Desktop;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.domain.HyperlinkItem;
import kibu.kuhn.myfavorites.domain.Item;

class OpenItemHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(OpenItemHandler.class);

  private MainMenu mainMenu;

  OpenItemHandler(MainMenu mainMenu) {
    this.mainMenu = mainMenu;
  }

  void openItem(Item item) {
    try {
      switch (item.getType()) {
          case FileSystemItem:
            File file = ((FileSystemItem)item).getPath().toFile();
            getDesktop().open(file);
            break;
          case HyperlinkItem:
            getDesktop().browse(((HyperlinkItem)item).getURL().toURI());
            break;
        default:
          LOGGER.warn("Not supported type {}", item.getType());
      }
    } catch (URISyntaxException | MalformedURLException ex) {
      var hyperlinkItem = (HyperlinkItem)item;
      LOGGER.error(ex.getMessage(), ex);
      mainMenu.setErrorText(String.format(IGui.get().getI18n("mainmenu.link.invalid"), hyperlinkItem.getURL()));
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      mainMenu
          .setErrorText(String.format(IGui.get().getI18n("mainmenu.path.invalid"), item.getDisplayString()));
    }
  }

  // Stupid hack for JUnit
  Desktop getDesktop() {
    return Desktop.getDesktop();
  }
}
