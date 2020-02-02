package kibu.kuhn.myfavorites.ui.xport;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import kibu.kuhn.myfavorites.ui.IGui;
import kibu.kuhn.myfavorites.ui.SettingsMenu;
import kibu.kuhn.myfavorites.ui.XRadioButton;
import kibu.kuhn.myfavorites.ui.drop.DropLabel;
import kibu.kuhn.myfavorites.ui.xport.drop.ImportDropTargetHandler;

public class ImportPane extends JPanel {

  private static final long serialVersionUID = 1L;
  private SettingsMenu settingsMenu;
  private ImportDropTargetHandler importDropTargetHandler;


  public ImportPane(SettingsMenu settingsMenu) {
    this.settingsMenu = settingsMenu;
    init();
  }

  private void init() {
    setLayout(new BorderLayout());
    var buttonGroup = new ButtonGroup();
    var replaceButton = new XRadioButton(new ReplaceAction());
    buttonGroup.add(replaceButton);
    var mergeButton = new XRadioButton(new MergeAction());
    buttonGroup.add(mergeButton);

    var buttonPane = new JPanel(new GridLayout(2, 1));
    add(buttonPane, NORTH);
    buttonPane.add(replaceButton);
    buttonPane.add(mergeButton);


    var label = new DropLabel(IGui.get().getI18n("importpane.label.dnd"));
    label.setForeground(Color.GRAY);
    add(label, CENTER);

    initDrop(label);
    replaceButton.setSelected(true);
  }

  private void initDrop(Component dndComp) {
    importDropTargetHandler = new ImportDropTargetHandler(this::handleDropException);
    importDropTargetHandler.setImportHandler(new NodeImportReplaceHandler());
    new DropTarget(dndComp, importDropTargetHandler);
  }

  private void handleDropException(Exception  ex) {
    settingsMenu.showMessage(String.format(IGui.get().getI18n("importpane.error"), ex.getMessage()));
  }

  private class MergeAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private MergeAction() {
      putValue(NAME, IGui.get().getI18n("importpane.button.merge"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      importDropTargetHandler.setImportHandler(new NodeImportMergeHandler());
    }
  }

  private class ReplaceAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private ReplaceAction() {
      putValue(NAME, IGui.get().getI18n("importpane.button.replace"));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      importDropTargetHandler.setImportHandler(new NodeImportReplaceHandler());
    }
  }

}
