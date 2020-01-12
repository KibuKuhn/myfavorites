package kibu.kuhn.myfavorites.ui.xport;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
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
    switch (node.getUserObject().getType()) {
      case BoxItem:
        var boxNode = new BoxNode();
        boxNode.setUserObject(node.getUserObject());
        root.add(boxNode);
        break;
      case DesktopItem:
      case FileSystemItem:
      case HyperlinkItem:
        var parent = node.getParent().isRoot() ? root : getParentForNode(root, (ItemTreeNode)node);
        var newNode = new ItemTreeNode();
        newNode.setUserObject(node.getUserObject());
        parent.add(newNode);
        break;
      case Root:
      default:
    }


  }

  private DropTreeNode getParentForNode(RootNode root, ItemTreeNode node) {
    DropTreeNode parent = node.getParent();
    if (parent == null) {
      return null;
    }

    switch (parent.getUserObject().getType()) {
      case BoxItem:
        //@formatter:off
        BoxNode newParent =
          (BoxNode) Collections.list(root.children())
                               .stream()
                               .filter(ch -> ch instanceof BoxNode
                                             && ((DropTreeNode)ch).getUserObject().getAlias().equals(node.getUserObject().getAlias()))
                               .reduce((n1,n2) -> {
                                 throw new IllegalStateException(String.format("More than 1 BoxNode found with alias '%s'", node.getUserObject().getAlias()));
                               })
                               .orElseGet(() -> {
                                 var bn = new BoxNode();
                                 bn.setUserObject(parent.getUserObject());
                                 root.add(bn);
                                 return bn;
                               });
        return newParent;
        //@formatter:on
      case Root:
        return root;
      default:
        throw new IllegalStateException("Unsupported type: " + parent.getUserObject().getType());

    }
  }
}
