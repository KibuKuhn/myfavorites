package kibu.kuhn.myfavorites.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.domain.DesktopItem;
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
        case DesktopItem:
          openDesktopFile((DesktopItem) item);
          break;
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
      LOGGER.error(ex.getMessage(), ex);
      DesktopItem di = (DesktopItem) item;
      mainMenu.setErrorText(String.format(IGui.get().getI18n("mainmenu.link.invalid"), di.getUrl(),
          di.getPath().getFileName()));
    } catch (IOException ex) {
      LOGGER.error(ex.getMessage(), ex);
      mainMenu
          .setErrorText(String.format(IGui.get().getI18n("mainmenu.path.invalid"), item.getDisplayString()));
    }
  }

  private void openDesktopFile(DesktopItem item)
      throws MalformedURLException, IOException, URISyntaxException {
    getDesktop().browse(new URL(item.getUrl()).toURI());
  }

  // Stupid hack for JUnit
  Desktop getDesktop() {
    return Desktop.getDesktop();
  }
}
