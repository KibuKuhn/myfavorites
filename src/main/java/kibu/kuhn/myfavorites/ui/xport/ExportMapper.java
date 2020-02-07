package kibu.kuhn.myfavorites.ui.xport;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
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
        var boxNode = new BoxNode((BoxItem)node.getItem());
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

  private DropTreeNode getParentForNode(RootNode root, ItemTreeNode node) {
    var parent = node.getParent();
    if (parent == null) {
      return null;
    }

    switch (parent.getItem().getType()) {
      case BoxItem:
        //@formatter:off
        var newParent =
                 (BoxNode) root.getChildren()
                               .stream()
                               .filter(ch -> ch instanceof BoxNode && (ch == node))
                               .reduce((n1,n2) -> {
                                 throw new IllegalStateException(String.format("More than 1 BoxNode found with alias '%s'", node.getItem().getAlias()));
                               })
                               .orElseGet(() -> {
                                 var bn = new BoxNode((BoxItem)parent.getItem());
                                 root.add(bn);
                                 return bn;
                               });
        return newParent;
        //@formatter:on
      case Root:
        return root;
      default:
        throw new IllegalStateException("Unsupported type: " + parent.getItem().getType());

    }
  }
}
