package kibu.kuhn.myfavorites.prefs;

import static kibu.kuhn.myfavorites.TestDataProvider.createTestNode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

public class NodeMapperTest {

  private String expectedJson;
  private NodeMapper mapper;



  @BeforeEach
  public void init() throws Exception {
    expectedJson = Files.readString(Path.of(getClass().getResource("/testNode.json").toURI()));    
    mapper = new NodeMapper();
  }
  
  
  @Test
  public void TestToJson() throws Exception {
    var expected = createTestNode();
    var json = mapper.mapToJson(expected);
    
    assertEquals(expectedJson, json, true);
  }
  
  @Test
  public void testFromJson() throws Exception {
    var actualNode = mapper.mapToNode(expectedJson);
    var expectedNode = createTestNode();
    
    assertNode(expectedNode, actualNode);
  }



  private void assertNode(DropTreeNode expected, DropTreeNode actual) {
      assertThat(actual.getClass()).isSameAs(actual.getClass());
      switch (expected.getItem().getType()) {
        case BoxItem:
          assertThat(expected.getItem().getAlias()).isEqualTo(actual.getItem().getAlias());
          assertThat(expected.getItem().getClass()).isSameAs(actual.getItem().getClass());
        case Root:
          for (int c=0; c < expected.getChildCount(); c++) {
            assertNode(expected.getChildAt(c), actual.getChildAt(c));
          }
          return;
        case FileSystemItem:
          assertThat(expected.getItem().getAlias()).isEqualTo(actual.getItem().getAlias());
          assertThat(expected.getItem().getClass()).isSameAs(actual.getItem().getClass());
          return;
        default:
          throw new IllegalStateException("Unexpected type " + expected.getItem().getType());
      }
  }
}
