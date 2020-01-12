package kibu.kuhn.myfavorites.prefs;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Enumeration;
import javax.swing.tree.TreeNode;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import kibu.kuhn.myfavorites.domain.DesktopItem;
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
    Enumeration<TreeNode> actualChildren = actualNode.children();
    Enumeration<TreeNode> expectedChildren = expectedNode.children();
    while(expectedChildren.hasMoreElements()) {
      DropTreeNode expected = (DropTreeNode) expectedChildren.nextElement();
      DropTreeNode actual = (DropTreeNode) actualChildren.nextElement();
      assertNode(expected, actual);
    }
  }

  private void assertNode(DropTreeNode expected, DropTreeNode actual) {
    assertThat(actual).isExactlyInstanceOf(expected.getClass());
    assertThat(actual.getUserObject()).isEqualTo(expected.getUserObject());
    assertChildren(expected, actual);
  }

  private DropTreeNode createTestNode() throws MalformedURLException, URISyntaxException {
    RootNode rootNode = new RootNode();

    ItemTreeNode child = new ItemTreeNode();
    FileSystemItem fileSystemItem = FileSystemItem.of(Paths.get(getClass().getResource("/mapperTestFile.txt").toURI()), true);
    fileSystemItem.setAlias("falias");
    child.setUserObject(fileSystemItem);
    rootNode.add(child);

    child = new ItemTreeNode();
    DesktopItem desktopItem = DesktopItem.of(Paths.get(getClass().getResource("/OpenItemHandlerTest.desktop").toURI()));
    desktopItem.setAlias("dalias");
    child.setUserObject(desktopItem);
    rootNode.add(child);

    BoxNode boxNode = new BoxNode();
    boxNode.getUserObject().setAlias("balias");
    rootNode.add(boxNode);

    child = new ItemTreeNode();
    HyperlinkItem hyperlinkItem = HyperlinkItem.of(new URL("http://mappertest.xy"));
    hyperlinkItem.setAlias("halias");
    child.setUserObject(hyperlinkItem);
    boxNode.add(child);

    return rootNode;
  }

}
