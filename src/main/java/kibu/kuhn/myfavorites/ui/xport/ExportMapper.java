package kibu.kuhn.myfavorites.ui.xport;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import javax.swing.tree.TreeNode;
import kibu.kuhn.myfavorites.domain.BoxItem;
import kibu.kuhn.myfavorites.ui.drop.BoxNode;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;
import kibu.kuhn.myfavorites.ui.drop.ItemTreeNode;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

class ExportMapper
    implements Collector<DropTreeNode, RootNode, RootNode>, BiConsumer<RootNode, DropTreeNode> {

  @Override
  public Supplier<RootNode> supplier() {
    return RootNode::new;
  }

  @Override
  public BiConsumer<RootNode, DropTreeNode> accumulator() {
    return this;
  }

  @Override
  public BinaryOperator<RootNode> combiner() {
    return (n1, n2) -> n1;
  }

  @Override
  public Function<RootNode, RootNode> finisher() {
    return n -> n;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.singleton(Characteristics.IDENTITY_FINISH);
  }

  @Override
  public void accept(RootNode root, DropTreeNode node) {
    switch (node.getItem().getType()) {
      case BoxItem:
        var boxNode = findBoxNode((BoxItem)node.getItem(), root);
        root.add(boxNode);
        break;
      case FileSystemItem:
      case HyperlinkItem:
        var parent = node.getParent().isRoot() ? root : getParentForNode(root, (ItemTreeNode)node);
        var newNode = new ItemTreeNode();
        newNode.setItem(node.getItem());
        parent.add(newNode);
        break;
      case Root:
      default:
    }


  }

  private BoxNode findBoxNode(BoxItem item, RootNode root) {
    for (TreeNode child : root.getChildren()) {
      if (((DropTreeNode)child).getItem().equals(item)) {
        return (BoxNode)child;
      }
    }
    
    return new BoxNode(item);
  }

  private DropTreeNode getParentForNode(RootNode root, ItemTreeNode node) {
    var parent = node.getParent();
    if (parent == null) {
      return null;
    }

    switch (parent.getItem().getType()) {
      case BoxItem:
        //@formatter:off
        var newParent = findBoxNode((BoxItem)parent.getItem(), root);
        if (newParent.getParent() == null) {
          root.add(newParent);
        }
        return newParent;
        //@formatter:on
      case Root:
        return root;
      default:
        throw new IllegalStateException("Unsupported type: " + parent.getItem().getType());

    }
  }
}
