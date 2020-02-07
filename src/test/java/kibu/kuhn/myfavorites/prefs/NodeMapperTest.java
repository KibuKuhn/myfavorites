package kibu.kuhn.myfavorites.prefs;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;
import javax.swing.tree.TreeNode;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.domain.HyperlinkItem;
import kibu.kuhn.myfavorites.ui.drop.BoxNode;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;
import kibu.kuhn.myfavorites.ui.drop.ItemTreeNode;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

public class NodeMapperTest {

  @Test
  public void TestMapping() throws JsonProcessingException, MalformedURLException, URISyntaxException {
    NodeMapper mapper = new NodeMapper();
    DropTreeNode expected = createTestNode();
    String json = mapper.mapToJson(expected);

    DropTreeNode actual = mapper.mapToNode(json);
    assertNode(expected, actual);
    assertChildren(expected, actual);
  }

  private void assertChildren(DropTreeNode expectedNode, DropTreeNode actualNode) {
    Iterator<TreeNode> actualChildren = actualNode.getChildren().iterator();
    Iterator<TreeNode> expectedChildren = expectedNode.getChildren().iterator();
    while(expectedChildren.hasNext()) {
      DropTreeNode expected = (DropTreeNode) expectedChildren.next();
      DropTreeNode actual = (DropTreeNode) actualChildren.next();
      assertNode(expected, actual);
    }
  }

  private void assertNode(DropTreeNode expected, DropTreeNode actual) {
    assertThat(actual).isExactlyInstanceOf(expected.getClass());
    assertThat(actual.getItem()).isEqualTo(expected.getItem());
    assertChildren(expected, actual);
  }

  private DropTreeNode createTestNode() throws MalformedURLException, URISyntaxException {
    RootNode rootNode = new RootNode();

    ItemTreeNode child = new ItemTreeNode();
    FileSystemItem fileSystemItem = FileSystemItem.of(Paths.get(getClass().getResource("/mapperTestFile.txt").toURI()), true);
    fileSystemItem.setAlias("falias");
    child.setItem(fileSystemItem);
    rootNode.add(child);

    BoxNode boxNode = new BoxNode();
    boxNode.getItem().setAlias("balias");
    rootNode.add(boxNode);

    child = new ItemTreeNode();
    HyperlinkItem hyperlinkItem = HyperlinkItem.of(new URL("http://mappertest.xy"));
    hyperlinkItem.setAlias("halias");
    child.setItem(hyperlinkItem);
    boxNode.add(child);

    return rootNode;
  }

}
