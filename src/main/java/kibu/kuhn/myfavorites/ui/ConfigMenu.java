package kibu.kuhn.myfavorites.ui;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.dnd.DropTarget;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import kibu.kuhn.myfavorites.prefs.IPreferencesService;
import kibu.kuhn.myfavorites.ui.drop.DropLabel;
import kibu.kuhn.myfavorites.ui.drop.DropTargetHandler;
import kibu.kuhn.myfavorites.ui.drop.DropTree;

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
    dialog.setVisible(visible);
  }


  private void init() {
    dialog =
        new JDialog(null, IGui.get().getI18n("configmenu.title"), APPLICATION_MODAL);
    dialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        doClose(e);
      };
    });

    dialog.setIconImage(Icons.getImage("list36_filled"));
    dialog.getContentPane().setLayout(new BorderLayout());
    var dropLabel = new DropLabel();
    dialog.getContentPane().add(dropLabel, NORTH);
    dropTree = new DropTree();
    dialog.getContentPane().add(new JScrollPane(dropTree), CENTER);
    DropTreeConfigAction action = new DropTreeConfigAction();
    dropTree.addKeyListener(action);
    initDrop(dropLabel);
    dialog.pack();
    dialog.setSize(400, 400);
    dialog.setLocationRelativeTo(null);
  }


  private void initDrop(Component dndComp) {
    new DropTarget(dndComp, new DropTargetHandler(dropTree.getModel()));
  }



  void setWindowCloseAction(Consumer<? super ComponentEvent> c) {
    this.windowCloseAction = c;
  }

  private void doClose(WindowEvent e) {
    dialog.dispose();
    dialog = null;
    saveFavorites();
    if (windowCloseAction == null) {
      return;
    }
    windowCloseAction.accept(e);
  }

  private void saveFavorites() {
    IPreferencesService.get().saveItems(dropTree.getModel().getRoot());
  }
}
