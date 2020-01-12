package kibu.kuhn.myfavorites.prefs;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kibu.kuhn.myfavorites.domain.DesktopItem;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.domain.HyperlinkItem;
import kibu.kuhn.myfavorites.ui.drop.BoxNode;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;
import kibu.kuhn.myfavorites.ui.drop.ItemTreeNode;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

public class NodeMapper {

  public String mapToJson(DropTreeNode node) throws JsonProcessingException {
    var jsonNode = mapNode(node, null);
    return new ObjectMapper().writeValueAsString(jsonNode);
  }

  private JsonTreeNode mapNode(DropTreeNode node, JsonTreeNode parent) {
    var jsonNode = new JsonTreeNode();
    var item = node.getItem();
    jsonNode.setAlias(item.getAlias());
    jsonNode.setType(item.getType());
    switch (item.getType()) {
      case BoxItem:
        jsonNode.setFile(false);
        break;
      case DesktopItem:
        jsonNode.setPath(((DesktopItem) item).getPath().toString());
        jsonNode.setURL((((DesktopItem) item).getUrl()));
        jsonNode.setFile(true);
        break;
      case FileSystemItem:
        jsonNode.setPath(((FileSystemItem) item).getPath().toString());
        jsonNode.setFile(((FileSystemItem) item).isFile());
        break;
      case HyperlinkItem:
        jsonNode.setURL(((HyperlinkItem) item).getDisplayString());
        jsonNode.setFile(true);
        break;
      default:
    }
    jsonNode.setParent(parent);
    if (parent != null) {
      parent.getChildren().add(jsonNode);
    }

    if (!node.isLeaf()) {
      node.getChildren().forEach(n -> mapNode((DropTreeNode)n, jsonNode));
    }

    return jsonNode;
  }

  public DropTreeNode mapToNode(String json) throws JsonProcessingException  {
    var node = new ObjectMapper().readValue(json, JsonTreeNode.class);
    return mapNode(node, null);
  }

  private DropTreeNode mapNode(JsonTreeNode node, DropTreeNode parent) {
    try {
      DropTreeNode treeNode = null;
      switch (node.getType()) {
        case BoxItem:
          treeNode = new BoxNode();
          treeNode.getItem().setAlias(node.getAlias());
          break;
        case DesktopItem:
          var item = DesktopItem.of(Paths.get(node.getPath()));
          item.setAlias(node.getAlias());
          treeNode = ItemTreeNode.of(item);
          break;
        case FileSystemItem:
          var fileSystemItem =
              FileSystemItem.of(Paths.get(node.getPath()), node.isFile());
          fileSystemItem.setAlias(node.getAlias());
          treeNode = ItemTreeNode.of(fileSystemItem);
          break;
        case HyperlinkItem:
          var hyperlinkItem = HyperlinkItem.of(new URL(node.getURL()));
          hyperlinkItem.setAlias(node.getAlias());
          treeNode = ItemTreeNode.of(hyperlinkItem);
          break;
        case Root:
          treeNode = new RootNode();
          break;
        default:
          throw new IllegalArgumentException("Unsupported node type: " + node.getType());
      }

      if (parent != null) {
        parent.add(treeNode);
      }

      final DropTreeNode p = treeNode;
      node.getChildren().forEach(n -> mapNode(n, p));

      return treeNode;

    } catch (MalformedURLException ex) {
      throw new IllegalStateException(ex);
    }
  }
}
