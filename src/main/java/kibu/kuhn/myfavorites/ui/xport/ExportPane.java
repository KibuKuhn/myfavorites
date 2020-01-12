package kibu.kuhn.myfavorites.ui.xport;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.EAST;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.RELATIVE;
import static java.awt.GridBagConstraints.REMAINDER;
import static java.awt.GridBagConstraints.WEST;
import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import static javax.swing.SwingConstants.LEFT;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import org.slf4j.Logger;
import kibu.kuhn.myfavorites.prefs.IPreferencesService;
import kibu.kuhn.myfavorites.ui.IGui;
import kibu.kuhn.myfavorites.ui.Icons;
import kibu.kuhn.myfavorites.ui.SettingsMenu;
import kibu.kuhn.myfavorites.ui.drop.DropTree;

class ExportPane extends JPanel {

  static final String JSON = ".json";
  private static final String DEFAULT_EXPORT_FILE_NAME = "myfavorites";


  private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ExportPane.class);

  private static final long serialVersionUID = 1L;

  private JTextField textField;
  private ActionButton exportButton;
  private DropTree tree;
  private SettingsMenu settingsMenu;
  private ExportValidator validator;

  ExportPane(SettingsMenu settingsMenu) {
    this.settingsMenu = settingsMenu;
    init();
  }

  SettingsMenu getSettingsMenu() {
    return settingsMenu;
  }

  DropTree getTree() {
    return tree;
  }

  private void init() {
    setLayout(new GridBagLayout());
    var constraints = new GridBagConstraints();

    constraints.insets = new Insets(10,2,10,2);
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.gridwidth = REMAINDER;
    constraints.gridheight = 1;
    constraints.weightx = 1;
    constraints.weighty = 0;
    constraints.fill = HORIZONTAL;
    constraints.anchor = WEST;
    add(new JLabel(IGui.get().getI18n("exportpane.info")), constraints);

    constraints.insets = new Insets(2,2,2,2);
    constraints.gridx = 0;
    constraints.gridy = RELATIVE;
    constraints.gridwidth = REMAINDER;
    constraints.gridheight = 1;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = BOTH;
    constraints.anchor = WEST;
    tree = new ExportTree();
    add(new JScrollPane(tree), constraints);

    constraints.insets = new Insets(2,2,2,10);
    constraints.gridx = 0;
    constraints.gridy = RELATIVE;
    constraints.gridwidth = 1;
    constraints.gridheight = 1;
    constraints.weightx = 0;
    constraints.weighty = 0;
    constraints.fill = NONE;
    constraints.anchor = WEST;
    add(new JLabel(IGui.get().getI18n("exportpane.filename")), constraints);

    constraints.insets.right = 2;
    constraints.anchor = WEST;
    constraints.gridx = RELATIVE;
    constraints.gridwidth = 1;
    constraints.weightx = 1;
    constraints.fill = HORIZONTAL;
    textField = new JTextField();
    textField.getDocument().addDocumentListener(new ErrorTextResetAction());
    add(textField, constraints);

    constraints.gridx = RELATIVE;
    constraints.gridwidth = REMAINDER;
    constraints.gridheight = 1;
    constraints.weightx = 0;
    constraints.fill = NONE;
    constraints.anchor = EAST;
    var button = new ActionButton(new FolderAction());
    add(button, constraints);

    constraints.gridx = 0;
    constraints.gridy = RELATIVE;
    constraints.gridwidth = REMAINDER;
    constraints.gridheight = 1;
    constraints.weightx = 1;
    constraints.fill = NONE;
    constraints.anchor = EAST;
    exportButton = new ActionButton(new ExportAction());
    button.setHorizontalTextPosition(LEFT);
    add(exportButton, constraints);

    validator = new ExportValidator(this);
  }

  private void setExportPath(File file) {
   var text = textField.getText();
   if (text.isBlank()) {
     text = DEFAULT_EXPORT_FILE_NAME;
   }
   if (!text.endsWith(JSON)) {
     text = text + JSON;
   }
   var path = file.toPath().resolve(text);
   textField.setText(path.toString());
  }


  private JFileChooser createFileChooser() {
    var chooser = new JFileChooser();
    chooser.setDialogTitle(IGui.get().getI18n("exportpane.export.title"));
    chooser.setCurrentDirectory(IPreferencesService.get().getExportPath());
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.setFileSelectionMode(DIRECTORIES_ONLY);
    chooser.setFileFilter(new FileFilter() {

      @Override
      public String getDescription() {
        return IGui.get().getI18n("exportpane.filefilter.description");
      }

      @Override
      public boolean accept(File f) {
        return f.isDirectory();
      }
    });
    return chooser;
  }


  private class FolderAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private FolderAction() {
      putValue(LARGE_ICON_KEY, Icons.getIcon("folder18"));
      putValue(SHORT_DESCRIPTION, IGui.get().getI18n("exportpane.folderaction.tooltip"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      var chooser = createFileChooser();
      int result = chooser.showSaveDialog(ExportPane.this);
      if (result != JFileChooser.APPROVE_OPTION) {
        return;
      }

      var selectedFile = chooser.getSelectedFile();
      IPreferencesService.get().saveExportPath(selectedFile.getAbsolutePath());
      setExportPath(selectedFile);
    }

  }

  private class ExportAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private ExportAction() {
      putValue(LARGE_ICON_KEY, Icons.getIcon("export18"));
      putValue(NAME, IGui.get().getI18n("exportpane.button.export"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      var text = textField.getText();
      try {
        var file = validator.validate(text);
        if (file == null) {
          return;
        }

        settingsMenu.showMessage(null);
        new Exporter(ExportPane.this).exportFavorites(file);
      }
      catch (Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        settingsMenu.showMessage(String.format(IGui.get().getI18n("error"), ex.getClass().getSimpleName(), ex.getMessage()));
      }
    }
  }

  private class ErrorTextResetAction implements DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
      reset();

    }

    private void reset() {
      settingsMenu.showMessage(null);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      reset();

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
      reset();
    }
  }
}
