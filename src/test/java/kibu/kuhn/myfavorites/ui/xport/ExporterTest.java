package kibu.kuhn.myfavorites.ui.xport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kibu.kuhn.myfavorites.ui.xport.TestDataProvider.createTestNode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
  
  
  @BeforeEach
  public void init() throws IOException {
    when(pane.getTree()).thenReturn(tree);
    doNothing().when(exporter).toFile(any(), jsonCaptor.capture());
  }
  
  @Test
  public void testExport() throws Exception {
    TreePath[] selectionPaths = createTestData();
    when(tree.getSelectionPaths()).thenReturn(selectionPaths);
    exporter.exportFavorites(null);
    var json = jsonCaptor.getValue();
    var expectedJson = Files.readString(Paths.get(getClass().getResource("/testExport.json").toURI()), UTF_8);
    assertEquals(expectedJson, json, true);
  }
  
  private TreePath[] createTestData() {
    RootNode root = createTestNode();
    TreePath[] selectedPaths = new TreePath[3];
    TreePath treePath = new TreePath(new Object[] {
      root,
      root.getChildAt(0),
    });
    selectedPaths[0] = treePath;
    
    treePath = new TreePath(new Object[] {
        root,
        root.getChildAt(1),
        root.getChildAt(1).getChildAt(0)
      });
    selectedPaths[1] = treePath;
    
    treePath = new TreePath(new Object[] {
        root,
        root.getChildAt(2)
      });
    selectedPaths[2] = treePath;
    
    return selectedPaths;
  }
}
