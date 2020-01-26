package kibu.kuhn.myfavorites.ui;

import java.awt.Point;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.prefs.IPreferencesService;
import kibu.kuhn.myfavorites.ui.drop.DropTree;

class MainMenuLocationHandler implements MouseMotionListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuLocationHandler.class);

  private JDialog dialog;

  private MouseEvent mouseEvent;

  private DropTree tree;

  MainMenuLocationHandler(MouseEvent e, JDialog dialog, DropTree tree) {
    this.mouseEvent = e;
    this.dialog = dialog;
    this.tree = tree;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    var locationOnScreen = e.getLocationOnScreen();
    LOGGER.debug("locationOnScreen: {}", locationOnScreen);
    dialog.setLocation(locationOnScreen);
  }

  @Override
  public void mouseMoved(MouseEvent e) {

  }

  void initLocation() {
    var locationOnScreen = (Point) null;
    if (IPreferencesService.get().isMainMenuLocationUpdatEnabled()) {
      tree.addMouseMotionListener(this);
    } else {
      var trayIcon = (TrayIcon) mouseEvent.getSource();
      locationOnScreen = mouseEvent.getLocationOnScreen();
      var size = trayIcon.getSize();
      if (IPreferencesService.get().getMainMenuLocation() == null) {
        locationOnScreen.y = locationOnScreen.y + size.height;
        locationOnScreen.x = locationOnScreen.x - size.width / 2;
      } else {
        locationOnScreen = IPreferencesService.get().getMainMenuLocation();
      }
    }

    if (locationOnScreen == null) {
      dialog.setLocationRelativeTo(null);
    } else {
      dialog.setLocation(locationOnScreen);
    }
  }

  void saveLocation() {
    IPreferencesService.get().saveMainMenuLocation(dialog.getLocationOnScreen());
  }
}
