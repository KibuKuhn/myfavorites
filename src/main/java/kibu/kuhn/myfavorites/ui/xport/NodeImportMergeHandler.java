package kibu.kuhn.myfavorites.ui.xport;

import java.util.Collections;

import kibu.kuhn.myfavorites.domain.Type;
import kibu.kuhn.myfavorites.ui.drop.DropTreeModel;
import kibu.kuhn.myfavorites.ui.drop.DropTreeNode;
import kibu.kuhn.myfavorites.ui.drop.RootNode;

class NodeImportMergeHandler implements NodeImportHandler
{

    @Override
    public void accept(RootNode node) {
        RootNode root = DropTreeModel.instance().getRoot();
        merge(root, node);
        save(root);
    }

    /**
     * Do not invoke. Unit test
     */
    void merge(final DropTreeNode target, DropTreeNode node) {
        Collections.list(node.children()).forEach(n -> {
            DropTreeNode dn = (DropTreeNode) n;
            if (dn.getItem().getType() == Type.BoxItem) {
                var existingNode = findNode(target, dn);
                if (existingNode == null) {
                    target.add(dn);
                } else {
                    merge(existingNode, dn);
                }
            } else {
                target.add(dn);
            }
        });
    }

    private DropTreeNode findNode(DropTreeNode target, DropTreeNode child) {
        for (var e = target.children(); e.hasMoreElements();) {
            DropTreeNode dn = (DropTreeNode) e.nextElement();
            if ((child.getItem().getType() == dn.getItem().getType())
                    && (child.getItem().getAlias().equals(dn.getItem().getAlias()))) {
                return dn;
            }
        }
        return null;
    }
}
