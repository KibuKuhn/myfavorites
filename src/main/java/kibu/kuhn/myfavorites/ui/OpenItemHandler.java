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
import kibu.kuhn.myfavorites.domain.Item;

class OpenItemHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(OpenItemHandler.class);

  private MainMenu mainMenu;

  OpenItemHandler(MainMenu mainMenu) {
    this.mainMenu = mainMenu;
  }

  void openItem(Item item) {
    try {
      if (item.getType() == Item.Type.DesktopItem) {
        openDesktopFile((DesktopItem) item);
      } else {
        File file = item.getPath().toFile();
        getDesktop().open(file);
      }
    } catch (URISyntaxException | MalformedURLException ex) {
      LOGGER.error(ex.getMessage(), ex);
      DesktopItem di = (DesktopItem) item;
      mainMenu.setErrorText(String.format(IGui.get().getI18n("mainmenu.link.invalid"), di.getUrl(),
          di.getPath().getFileName()));
    } catch (IOException ex) {
      LOGGER.error(ex.getMessage(), ex);
      mainMenu
          .setErrorText(String.format(IGui.get().getI18n("mainmenu.path.invalid"), item.getPath()));
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
