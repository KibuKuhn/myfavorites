package kibu.kuhn.myfavorites.ui;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.prefs.IPreferencesService;
import kibu.kuhn.myfavorites.ui.buttonbar.ButtonBar;
import kibu.kuhn.myfavorites.ui.drop.DropList;

class MainMenu extends MouseAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainMenu.class);

  private JDialog dialog;
  private ConfigMenu configMenu;
  private SettingsMenu settingsMenu;
  private HelpMenu helpMenu;

  private Consumer<? super ActionEvent> buttonbarAction = ae -> {
    switch (ae.getActionCommand()) {
      case ButtonBar.ACTION_CONFIG:
        if (configMenu == null) {
          configMenu = createConfigMenu();
        }
        configMenu.setDialogVisible(true);
        break;
      case ButtonBar.ACTION_EXIT:
        System.exit(0);
        break;
      case ButtonBar.ACTION_SETTINGS:
        if (settingsMenu == null) {
          settingsMenu = createSettingsMenu();
        }
        settingsMenu.setDialogVisible(true);
        break;
      case ButtonBar.ACTION_HELP:
          if (helpMenu == null) {
            helpMenu = createHelpMenu();
          }
          helpMenu.setDialogVisible(true);
          break;
    }

  };

  private JTextArea errorPane;

  MainMenu() {}

  private HelpMenu createHelpMenu() {
    HelpMenu menu = new HelpMenu();
    menu.setWindowCloseAction(e -> helpMenu = null);
    return menu;
  }

  private SettingsMenu createSettingsMenu() {
    SettingsMenu menu = new SettingsMenu();
    menu.setWindowCloseAction(e -> settingsMenu = null);
    return menu;
  }

  private ConfigMenu createConfigMenu() {
    ConfigMenu menu = new ConfigMenu();
    menu.setWindowCloseAction(e -> configMenu = null);
    return menu;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    showDialog(e);
  }


  private void showDialog(MouseEvent e) {
    if (dialog != null) {
      return;
    }

    dialog = new JDialog((Frame) null, false);
    Close close = new Close();
    dialog.addWindowFocusListener(close);
    dialog.addWindowListener(close);
    Container pane = dialog.getContentPane();
    pane.setLayout(new BorderLayout());
    pane.add(new ButtonBar(buttonbarAction), NORTH);
    DropList items = new DropList();
    initDropListActions(items);
    initItems(items);
    pane.add(new JScrollPane(items), CENTER);
    errorPane = new ErrorPane();
    pane.add(errorPane, SOUTH);
    dialog.setUndecorated(true);
    dialog.setPreferredSize(new Dimension(200, 400));
    TrayIcon trayIcon = (TrayIcon) e.getSource();
    Point locationOnScreen = e.getLocationOnScreen();
    Dimension size = trayIcon.getSize();
    locationOnScreen.y = locationOnScreen.y + size.height;
    locationOnScreen.x = locationOnScreen.x - size.width/2;
    dialog.setLocation(locationOnScreen);
    dialog.pack();
    dialog.setVisible(true);
  }

  private void initDropListActions(DropList list) {
    DropListActions openItem = new DropListActions();
    list.addKeyListener(openItem);
    list.addMouseListener(openItem);
    list.addFocusListener(openItem);
  }

  private void initItems(DropList list) {
    if (list.getModel().size() == 0) {
      List<Item> items = IPreferencesService.get().getItems();
      list.getModel().addAll(items);
    }
  }

  private void closeDialog() {
    if (dialog == null) {
      return;
    }

    dialog.setVisible(false);
    dialog.dispose();
    dialog = null;
  }

  private class Close extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
      closeDialog();
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
      closeDialog();
    }
  }

  private class DropListActions extends MouseAdapter implements KeyListener, FocusListener {


    @Override
    public void mouseClicked(MouseEvent e) {
      if (e.getClickCount() != 2) {
        return;
      }
      DropList list = (DropList) e.getSource();
      int index = list.locationToIndex(e.getPoint());
      if (index < 0) {
        return;
      }

      if (index != list.getSelectedIndex()) {
        return;
      }

      Item item = list.getSelectedValue();
      openItem(item);
    }

    private void openItem(Item item) {
      try {
        File file = item.getPath().toFile();
        Desktop.getDesktop().open(file);
      } catch (IOException ex) {
        LOGGER.error(ex.getMessage(), ex);
        errorPane.setText(String.format(IGui.get().getI18n("mainmenu.path.invalid"), item.getPath()));
      }
    }

    @Override
    public void keyTyped(KeyEvent e) {
      DropList list = (DropList) e.getSource();
      Item item = list.getSelectedValue();
      if (item == null) {
        return;
      }
      openItem(item);
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void focusGained(FocusEvent e) {
      DropList list = (DropList) e.getSource();
      if (list.getSelectedIndex() >= 0) {
        return;
      }

      list.setSelectedIndex(0);
    }

    @Override
    public void focusLost(FocusEvent e) {}

  }
}
