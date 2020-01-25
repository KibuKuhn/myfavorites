package kibu.kuhn.myfavorites.ui;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.prefs.IPreferencesService;

class MainMenuLocationHandler extends KeyAdapter implements MouseMotionListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuLocationHandler.class);

  private JDialog dialog;

  MainMenuLocationHandler(JDialog dialog) {
    this.dialog = dialog;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    LOGGER.debug("Key");
    if (e.getModifiersEx() != KeyEvent.CTRL_DOWN_MASK && e.getExtendedKeyCode() != KeyEvent.VK_S) {
      return;
    }

    IPreferencesService.get().saveMainMenuLocation(dialog.getLocationOnScreen());
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    LOGGER.debug("wheel");

      var locationOnScreen = e.getLocationOnScreen();
      LOGGER.debug("locationOnScreen: " + locationOnScreen);
      dialog.setLocation(locationOnScreen);

  }

  @Override
  public void mouseMoved(MouseEvent e) {

  }
}
