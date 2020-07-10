package kibu.kuhn.myfavorites.ui.xport;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.ui.drop.BoxNode;
import kibu.kuhn.myfavorites.ui.drop.ItemTreeNode;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

class TestDataProvider {

  /**
   *  ----------Root------------
   *   |         |        |
   *  item1     box1     item3
   *             |
   *           item2
   */
  static RootNode createTestNode() {
    try {
      RootNode rootNode = new RootNode();
      FileSystemItem item1 = FileSystemItem.of(Paths.get("myfavorites", "mapperTestFile.txt"), true);
      item1.setAlias("item1");
      FileSystemItem item2 = FileSystemItem.of(Paths.get("myfavorites", "mapperTestFile.txt"), true);
      item2.setAlias("item2");
      FileSystemItem item3 = FileSystemItem.of(Paths.get("myfavorites", "mapperTestFile.txt"), true);
      item3.setAlias("item3");
    
      BoxNode boxNode = new BoxNode();
      boxNode.getItem().setAlias("box1");
      boxNode.add(ItemTreeNode.of(item2));
  
      rootNode.add(ItemTreeNode.of(item1));
      rootNode.add(boxNode);
      rootNode.add(ItemTreeNode.of(item3));
      return rootNode;
    }
    
    catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   *  ----------Root------------
   *   |         |            |
   *  box1   item1Merge   boxMerge
   *   |                      |
   * item2Merge           item3Merge
   */
  static RootNode createMergeNode() throws URISyntaxException {
    RootNode rootNode = new RootNode();
    FileSystemItem item1 = FileSystemItem.of(Paths.get(TestDataProvider.class.getResource("/mapperTestFile.txt").toURI()), true);
    item1.setAlias("item1Merge");
    FileSystemItem item2 = FileSystemItem.of(Paths.get(TestDataProvider.class.getResource("/mapperTestFile.txt").toURI()), true);
    item2.setAlias("item2Merge");
    FileSystemItem item3 = FileSystemItem.of(Paths.get(TestDataProvider.class.getResource("/mapperTestFile.txt").toURI()), true);
    item3.setAlias("item3Merge");
  
    BoxNode boxNode = new BoxNode();
    boxNode.getItem().setAlias("box1");
    boxNode.add(ItemTreeNode.of(item2));
  
    BoxNode boxNode2 = new BoxNode();
    boxNode2.getItem().setAlias("boxMerge");
    boxNode2.add(ItemTreeNode.of(item3));
  
    rootNode.add(boxNode);
    rootNode.add(ItemTreeNode.of(item1));
    rootNode.add(boxNode2);
  
    return rootNode;
  }

}
