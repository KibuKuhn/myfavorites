package kibu.kuhn.myfavorites.ui.xport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kibu.kuhn.myfavorites.TestDataProvider.createTestSelections;
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
import kibu.kuhn.myfavorites.ui.drop.DropTree;

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
    TreePath[] selectionPaths = createTestSelections();
    when(tree.getSelectionPaths()).thenReturn(selectionPaths);
    exporter.exportFavorites(null);
    var json = jsonCaptor.getValue();
    var expectedJson = Files.readString(Paths.get(getClass().getResource("/testExport.json").toURI()), UTF_8);
    assertEquals(expectedJson, json, true);
  }
}
