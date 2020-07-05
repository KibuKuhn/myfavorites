package kibu.kuhn.myfavorites.ui.xport;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import kibu.kuhn.myfavorites.prefs.NodeMapper;
import kibu.kuhn.myfavorites.ui.IGui;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

class Exporter {

  private ExportPane exportPane;

  Exporter(ExportPane pane) {
    this.exportPane = pane;
  }
  
  void exportFavorites(File file) throws IOException {
    //@formatter:off
    var root = Arrays.stream(exportPane.getTree().getSelectionPaths())
                          .map(p -> (DropTreeNode)p.getLastPathComponent())
                          .filter(n -> !n.isRoot())
                          .collect(new ExportMapper());
    var nodeMapper = new NodeMapper();
    var json = nodeMapper.mapToJson(root);
    toFile(file, json);
    //@formatter:on
  }

  void toFile(File file, String json) throws IOException {
    Files.writeString(file.toPath(), json, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    exportPane.getSettingsMenu().showMessage(String.format(IGui.get().getI18n("exporter.success"), file.getAbsolutePath()));
  }

}
