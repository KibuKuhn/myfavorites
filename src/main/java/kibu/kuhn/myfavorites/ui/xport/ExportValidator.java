package kibu.kuhn.myfavorites.ui.xport;

import static kibu.kuhn.myfavorites.ui.xport.ExportPane.JSON;
import java.io.File;
import javax.swing.JOptionPane;
import kibu.kuhn.myfavorites.ui.IGui;

class ExportValidator {

  private ExportPane exportPane;

  ExportValidator(ExportPane pane) {
    this.exportPane = pane;
  }

  File validate(String text) {
    if (text.isBlank()) {
      exportPane.getSettingsMenu().showMessage(IGui.get().getI18n("exportpane.message.filename.invalid"));
      return null;
    }
    if (exportPane.getTree().getSelectionPaths() == null) {
      exportPane.getSettingsMenu().showMessage(IGui.get().getI18n("exportpane.message.no.favorites"));
      return null;
    }
    if (!text.toLowerCase().endsWith(JSON)) {
      text = text + JSON;
    }
    var file = new File(text);
    if (file.isDirectory()) {
      exportPane.getSettingsMenu().showMessage(IGui.get().getI18n("exportpane.message.folder"));
      return null;
    }
    File parentFile = file.getParentFile();
    if (!parentFile.exists()) {
      exportPane.getSettingsMenu().showMessage(IGui.get().getI18n("exportpane.message.folder.invalid"));
      return null;
    }
    if (file.exists()) {
      int result = JOptionPane.showConfirmDialog(exportPane, IGui.get().getI18n("exportpane.message.overwrite"));
      if (result != JOptionPane.OK_OPTION) {
        return null;
      }
    }
    return file;
  }

}
