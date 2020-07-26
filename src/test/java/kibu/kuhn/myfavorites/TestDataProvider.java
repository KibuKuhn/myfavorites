package kibu.kuhn.myfavorites;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import javax.swing.tree.TreePath;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.ui.drop.BoxNode;
import kibu.kuhn.myfavorites.ui.drop.ItemTreeNode;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

public class TestDataProvider {

  /**
   *  ----------Root-------------------
   *   |         |        |       |
   *  item1     box1     item3   box2
   *            |  |
   *        item2  item4
   */
  @SuppressWarnings("exports")
  public static RootNode createTestNode() {
    try {
      RootNode rootNode = new RootNode();
      FileSystemItem item1 = FileSystemItem.of(Paths.get("myfavorites", "mapperTestFile.txt"), true);
      item1.setAlias("item1");
      FileSystemItem item2 = FileSystemItem.of(Paths.get("myfavorites", "mapperTestFile.txt"), true);
      item2.setAlias("item2");
      FileSystemItem item3 = FileSystemItem.of(Paths.get("myfavorites", "mapperTestFile.txt"), true);
      item3.setAlias("item3");
      FileSystemItem item4 = FileSystemItem.of(Paths.get("myfavorites", "mapperTestFile.txt"), true);
      item4.setAlias("item4");
    
      BoxNode boxNode = new BoxNode();
      boxNode.getItem().setAlias("box1");
      boxNode.add(ItemTreeNode.of(item2));
      boxNode.add(ItemTreeNode.of(item4));
  
      rootNode.add(ItemTreeNode.of(item1));
      rootNode.add(boxNode);
      rootNode.add(ItemTreeNode.of(item3));
      
      boxNode = new BoxNode();
      boxNode.getItem().setAlias("box2");
      rootNode.add(boxNode);
      
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
  @SuppressWarnings("exports")
  public static RootNode createMergeNode() throws URISyntaxException {
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

  /**
   * root    root      root
   *  |       |         |
   * item1   box1      item3
   *          |   
   *        item2
   * @return
   */
  @SuppressWarnings("exports")
  public static TreePath[] createTestSelections() {
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

  /**
   * root    root      root    root
   *  |       |         |       |
   * item1   box1      item3   box1
   *          |                 | 
   *        item2              item4
   * @return
   */
  @SuppressWarnings("exports")
  public static TreePath[] createTestSelections2() {
    RootNode root = createTestNode();
    TreePath[] selectedPaths = new TreePath[4];
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
    
    treePath = new TreePath(new Object[] {
        root,
        root.getChildAt(1),
        root.getChildAt(1).getChildAt(1)
      });
    selectedPaths[3] = treePath;
    
    return selectedPaths;
  }

}
