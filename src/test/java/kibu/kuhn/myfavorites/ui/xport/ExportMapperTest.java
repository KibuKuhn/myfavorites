package kibu.kuhn.myfavorites.ui.xport;

import static kibu.kuhn.myfavorites.TestDataProvider.createTestSelections2;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.domain.Type;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;

public class ExportMapperTest {
  
  private ExportMapper mapper;
  
  @BeforeEach
  public void init() {
    mapper = new ExportMapper();
  }
  
  @Test
  public void test() {
    var selections = createTestSelections2();
    var root = Arrays.stream(selections)
                     .map(p -> (DropTreeNode)p.getLastPathComponent())
                     .filter(n -> !n.isRoot())
                     .collect(mapper);
    
    assertThat(root).isNotNull();
    List<TreeNode> children = root.getChildren();
    assertThat(children).hasSize(3);
    
    Item item = ((DropTreeNode)children.get(0)).getItem();
    assertThat(item.getType() == Type.FileSystemItem);
    assertThat(item.getAlias()).isEqualTo("item1");
    
    item = ((DropTreeNode)children.get(1)).getItem();
    assertThat(item.getType()).isSameAs(Type.BoxItem);
    assertThat(item.getAlias()).isEqualTo("box1");
    
    item = ((DropTreeNode)children.get(2)).getItem();
    assertThat(item.getType()).isSameAs(Type.FileSystemItem);
    assertThat(item.getAlias()).isEqualTo("item3");
    
     item = ((DropTreeNode)children.get(1).getChildAt(0)).getItem();
     assertThat(item.getType()).isSameAs(Type.FileSystemItem);
     assertThat(item.getAlias()).isEqualTo("item2");
     item = ((DropTreeNode)children.get(1).getChildAt(1)).getItem();
     assertThat(item.getType()).isSameAs(Type.FileSystemItem);
     assertThat(item.getAlias()).isEqualTo("item4");
  }
  
}
