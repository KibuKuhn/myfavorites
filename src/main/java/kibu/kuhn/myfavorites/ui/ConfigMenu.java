package kibu.kuhn.myfavorites.ui;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;
import static java.util.Collections.list;
import static kibu.kuhn.myfavorites.MyFavorites.createImage;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import kibu.kuhn.myfavorites.prefs.IPreferencesService;
import kibu.kuhn.myfavorites.ui.drop.DropLabel;
import kibu.kuhn.myfavorites.ui.drop.DropList;
import kibu.kuhn.myfavorites.ui.drop.DropTargetHandler;

class ConfigMenu {

  private DropList dropList;

  private JDialog dialog;

  private Consumer<? super ComponentEvent> windowCloseAction;

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
        new JDialog(null, Gui.get().getI18n("configmenu.title"), APPLICATION_MODAL);
    dialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        doClose(e);
      };
    });

    dialog.setIconImage(createImage("list36_filled", "list36").getImage());
    dialog.getContentPane().setLayout(new BorderLayout());
    DropLabel dropLabel = new DropLabel();
    dialog.getContentPane().add(dropLabel, NORTH);
    dropList = new DropList();
    DropListConfigAction action = new DropListConfigAction();
    dropList.addKeyListener(action);
    dialog.getContentPane().add(new JScrollPane(dropList), CENTER);
    initDrop(dropLabel);
    dialog.pack();
    dialog.setSize(400, 400);
    dialog.setLocationRelativeTo(null);
  }


  private void initDrop(Component dndComp) {
    DropTargetListener listener = new DropTargetHandler(dropList.getModel());
    new DropTarget(dndComp, listener);
  }



  void setWindowCloseAction(Consumer<? super ComponentEvent> c) {
    this.windowCloseAction = c;
  }

  private void doClose(WindowEvent e) {
    dialog.dispose();
    dialog = null;
    IPreferencesService.get().saveItems(list(dropList.getModel().elements()));
    if (windowCloseAction == null) {
      return;
    }
    windowCloseAction.accept(e);
  }
}
