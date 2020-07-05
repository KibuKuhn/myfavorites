package kibu.kuhn.myfavorites.ui.xport;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.tree.TreePath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import kibu.kuhn.myfavorites.domain.BoxItem;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.ui.drop.BoxNode;
import kibu.kuhn.myfavorites.ui.drop.DropTree;
import kibu.kuhn.myfavorites.ui.drop.ItemTreeNode;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

@ExtendWith(MockitoExtension.class)
public class ExporterTest {
  
  @Spy
  @InjectMocks
  private Exporter exporter;
  @Mock
  private ExportPane pane;
  @Mock
  private DropTree tree;
  @Captor
  private ArgumentCaptor<String> jsonCaptor;
  
  private TreePath[] selectionPaths;
  private RootNode root;
  
  
  @BeforeEach
  public void init() throws IOException {
    createTestData();
    when(pane.getTree()).thenReturn(tree);
    when(tree.getSelectionPaths()).thenReturn(selectionPaths);
    doNothing().when(exporter).toFile(any(), jsonCaptor.capture());
  }
  
  @Test
  public void testExport() throws Exception {
    exporter.exportFavorites(null);
    var json = jsonCaptor.getValue();
    var expectedJson = Files.readString(Paths.get(getClass().getResource("/exported.json").toURI()), java.nio.charset.StandardCharsets.UTF_8);
    assertEquals(expectedJson, json, true);
  }
  
  
  private void createTestData() {
    selectionPaths = new TreePath[4];
    root = new RootNode();
    var item = FileSystemItem.of(Paths.get("/home/user/desktop/myFolder"), false);
    var node = ItemTreeNode.of(item);
    selectionPaths[0] = new TreePath(new Object[] {root, node});
    root.add(node);
    
    item = FileSystemItem.of(Paths.get("/home/user/desktop/myDoc1.txt"), true);
    node = ItemTreeNode.of(item);
    selectionPaths[1] = new TreePath(new Object[] {root, node});
    root.add(node);
    
    var box = new BoxItem();
    box.setAlias("myBox1");
    var bn = new BoxNode(box);
    selectionPaths[2] = new TreePath(new Object[] {root, bn});
    root.add(bn);
    
    box = new BoxItem();
    box.setAlias("myBox2");
    bn = new BoxNode(box);
    item = FileSystemItem.of(Paths.get("/home/user/desktop/myDoc2.txt"), true);
    node = ItemTreeNode.of(item);
    bn.add(node);
    root.add(bn);
    selectionPaths[3] = new TreePath(new Object[] {root, bn, node});
  }

}
