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
import static javax.swing.tree.TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import org.slf4j.Logger;
import kibu.kuhn.myfavorites.prefs.NodeMapper;
import kibu.kuhn.myfavorites.ui.IGui;
import kibu.kuhn.myfavorites.ui.Icons;
import kibu.kuhn.myfavorites.ui.SettingsMenu;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

class ExportPane extends JPanel {

  private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ExportPane.class);

  private static final long serialVersionUID = 1L;

  private JTextField textField;
  private ActionButton exportButton;
  private DropTree tree;
  private SettingsMenu settingsMenu;

  ExportPane(SettingsMenu settingsMenu) {
    this.settingsMenu = settingsMenu;
    init();
  }

  private void init() {
    setLayout(new GridBagLayout());
    var constraints = new GridBagConstraints();

    constraints.insets = new Insets(2,2,2,2);
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.gridwidth = REMAINDER;
    constraints.gridheight = 1;
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = BOTH;
    constraints.anchor = WEST;
    add(new JScrollPane(createTree()), constraints);

    constraints.insets = new Insets(2,2,2,10);
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.gridwidth = 1;
    constraints.gridheight = 1;
    constraints.weightx = 0;
    constraints.weighty = 0;
    constraints.fill = NONE;
    constraints.anchor = WEST;
    add(new JLabel("Dateiname:"), constraints);

    constraints.insets.right = 2;
    constraints.anchor = WEST;
    constraints.gridx = RELATIVE;
    constraints.gridwidth = 1;
    constraints.weightx = 1;
    constraints.fill = HORIZONTAL;
    textField = new JTextField();
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
    constraints.gridy = 2;
    constraints.gridwidth = REMAINDER;
    constraints.gridheight = 1;
    constraints.weightx = 1;
    constraints.fill = NONE;
    constraints.anchor = EAST;
    exportButton = new ActionButton(new ExportAction());
    button.setHorizontalTextPosition(LEFT);
    add(exportButton, constraints);
  }

  private DropTree createTree() {
    tree = new DropTree();
    tree.getSelectionModel().setSelectionMode(DISCONTIGUOUS_TREE_SELECTION);
    return tree;
  }

  private void setExportPath(File file) {
   var text = textField.getText();
   if (text.isBlank()) {
     text = "myfavorites.json";
   }
   var path = file.toPath().resolve(text);
   textField.setText(path.toString());
  }


  private JFileChooser createFileChooser() {
    var chooser = new JFileChooser();
    chooser.setDialogTitle(IGui.get().getI18n("exportpane.export.title"));
    chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
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

      setExportPath(chooser.getSelectedFile());
    }

  }

  private class ExportAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private ExportAction() {
      putValue(LARGE_ICON_KEY, Icons.getIcon("export18"));
      putValue(NAME, "Exportieren");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      var text = textField.getText();
      try {
        var file = validate(text);
        if (file == null) {
          return;
        }

        exportFavorites(file);
      }
      catch (Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        settingsMenu.showMessage(String.format(IGui.get().getI18n("error"), ex.getLocalizedMessage()));
      }
    }

    private File validate(String text) {
      if (text.isBlank()) {
        JOptionPane.showMessageDialog(ExportPane.this, IGui.get().getI18n("exportpane.message.filename.invalid"));
        return null;
      }
      if (tree.getSelectionPaths() == null) {
        JOptionPane.showMessageDialog(ExportPane.this, IGui.get().getI18n("exportpane.message.no.favorites"));
        return null;
      }

      var file = new File(text);
      if (file.isDirectory()) {
        JOptionPane.showMessageDialog(ExportPane.this, IGui.get().getI18n("exportpane.message.folder"));
        return null;
      }
      if (file.exists()) {
        int result = JOptionPane.showConfirmDialog(ExportPane.this, IGui.get().getI18n("exportpane.message.overwrite"));
        if (result != JOptionPane.OK_OPTION) {
          return null;
        }
      }
      return file;
    }
  }

  private void exportFavorites(File file) throws IOException {
    var root = Arrays.stream(tree.getSelectionPaths())
                          .map(p -> (DropTreeNode)p.getLastPathComponent())
                          .filter(n -> !n.isRoot())
                          .collect(new ExportMapper());
    var nodeMapper = new NodeMapper();
    var json = nodeMapper.mapToJson(root);
    Files.writeString(file.toPath(), json, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
  }
}
