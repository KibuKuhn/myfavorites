package kibu.kuhn.myfavorites.ui.xport;

import static kibu.kuhn.myfavorites.ui.xport.TestDataProvider.createMergeNode;
import static kibu.kuhn.myfavorites.ui.xport.TestDataProvider.createTestNode;
import static org.assertj.core.api.Assertions.assertThat;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.junit.jupiter.api.Test;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.domain.Type;
import kibu.kuhn.myfavorites.ui.drop.BoxNode;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;
import kibu.kuhn.myfavorites.ui.drop.ItemTreeNode;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

public class NodeImportMergeHandlerTest {

  @Test
  public void testMerge() throws URISyntaxException {
    NodeImportMergeHandler mergeHandler = new NodeImportMergeHandler();
    RootNode testNode = createTestNode();
    RootNode mergeNode = createMergeNode();
    mergeHandler.merge(testNode, mergeNode);

    validate(testNode);

  }

  /**
   *  ----------Root----------------------------------
   *   |         |        |          |           |
   *  item1     box1     item3    item1Merge  boxMerge
   *            |  |                             |
   *        item2  item2Merge                 item3Merge
   */
  private void validate(RootNode node) {
    List<TreeNode> children = node.getChildren();
    assertThat(children).hasSize(5);
    DropTreeNode child0 = (DropTreeNode)children.get(0);
    assertThat(child0.getItem().getType()).isEqualTo(Type.FileSystemItem);
    assertThat(child0.getItem().getAlias()).isEqualTo("item1");

    DropTreeNode child1 = (DropTreeNode)children.get(1);
    assertThat(child1).isInstanceOf(BoxNode.class);
    assertThat(child1.getItem().getType()).isEqualTo(Type.BoxItem);
    assertThat(child1.getItem().getAlias()).isEqualTo("box1");

    DropTreeNode child2 = (DropTreeNode)children.get(2);
    assertThat(child2).isInstanceOf(ItemTreeNode.class);
    assertThat(child2.getItem().getType()).isEqualTo(Type.FileSystemItem);
    assertThat(child2.getItem().getAlias()).isEqualTo("item3");

    DropTreeNode child3 = (DropTreeNode)children.get(3);
    assertThat(child3).isInstanceOf(ItemTreeNode.class);
    assertThat(child3.getItem().getType()).isEqualTo(Type.FileSystemItem);
    assertThat(child3.getItem().getAlias()).isEqualTo("item1Merge");

    DropTreeNode child4 = (DropTreeNode)children.get(4);
    assertThat(child4).isInstanceOf(BoxNode.class);
    assertThat(child4.getItem().getType()).isEqualTo(Type.BoxItem);
    assertThat(child4.getItem().getAlias()).isEqualTo("boxMerge");

    children = child1.getChildren();
    assertThat(children).hasSize(2);
    child0 = (DropTreeNode)children.get(0);
    assertThat(child0.getItem().getType()).isEqualTo(Type.FileSystemItem);
    assertThat(child0.getItem().getAlias()).isEqualTo("item2");

    child1 = (DropTreeNode)children.get(1);
    assertThat(child1.getItem().getType()).isEqualTo(Type.FileSystemItem);
    assertThat(child1.getItem().getAlias()).isEqualTo("item2Merge");

    children = child4.getChildren();
    assertThat(children).hasSize(1);
    child0 = (DropTreeNode)children.get(0);
    assertThat(child0.getItem().getType()).isEqualTo(Type.FileSystemItem);
    assertThat(child0.getItem().getAlias()).isEqualTo("item3Merge");
  }

}
