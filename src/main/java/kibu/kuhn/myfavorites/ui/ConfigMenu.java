package kibu.kuhn.myfavorites.ui;

import kibu.kuhn.myfavorites.prefs.IPreferencesService;
import kibu.kuhn.myfavorites.ui.drop.DropLabel;
import kibu.kuhn.myfavorites.ui.drop.DropTargetHandler;
import kibu.kuhn.myfavorites.ui.drop.DropTree;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;

class ConfigMenu {

  private JDialog dialog;

  private Consumer<? super ComponentEvent> windowCloseAction;

  private DropTree dropTree;

  ConfigMenu() {
    init();
  }

  void setDialogVisible(boolean visible) {
    if (dialog == null) {
      return;
    }

    if (visible) {
      var configMenuLocation = IPreferencesService.get().getConfigMenuLocation();
      if (configMenuLocation.isEmpty()) {
        dialog.setLocationRelativeTo(null);
      } else {
        dialog.setLocation(configMenuLocation.get());
      }
      dialog.setVisible(true);
    }
  }


  private void init() {
    dialog =
        new JDialog(null, IGui.get().getI18n("configmenu.title"), APPLICATION_MODAL);
    dialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        doClose(e);
      }
    });

    dialog.setIconImage(Icons.getImage("list36_filled"));
    dialog.getContentPane().setLayout(new BorderLayout());
    dialog.getContentPane().add(new DropLabel(), NORTH);
    dropTree = new DropTree();
    dialog.getContentPane().add(new JScrollPane(dropTree), CENTER);
    DropTreeConfigActions action = new DropTreeConfigActions();
    dropTree.addKeyListener(action);
    Component glassPane = dialog.getGlassPane();
    glassPane.setVisible(true);
	initDrop(glassPane);
    dialog.pack();
    dialog.setSize(400, 400);
  }


  private void initDrop(Component dndComp) {
    new DropTarget(dndComp, new DropTargetHandler(dropTree.getModel()));
  }



  void setWindowCloseAction(Consumer<? super ComponentEvent> c) {
    this.windowCloseAction = c;
  }

  private void doClose(WindowEvent e) {
    saveFavorites();
    dialog.dispose();
    dialog = null;
    if (windowCloseAction == null) {
      return;
    }
    windowCloseAction.accept(e);
  }

  private void saveFavorites() {
    IPreferencesService.get().saveItems(dropTree.getModel().getRoot());
    var locationOnScreen = dialog.getLocationOnScreen();
    IPreferencesService.get().saveConfigMenuLocation(locationOnScreen);
  }
}
