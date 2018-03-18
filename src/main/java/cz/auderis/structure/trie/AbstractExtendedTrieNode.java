package cz.auderis.structure.trie;

import cz.auderis.structure.traversal.NodeVisitorContext;

import java.util.List;

public abstract class AbstractExtendedTrieNode<E, P> implements ExtendedTrieNode<E, P> {

    final int depth;

    protected boolean mark;
    protected int markedChildrenCount;
    protected P payload;
    protected int terminalCount;
    protected int aggregateCount;

    protected AbstractExtendedTrieNode(TrieNode<E> parentNode) {
        if (null == parentNode) {
            this.depth = 0;
        } else {
            this.depth = parentNode.getDepth() + 1;
        }
        assert ((0 == depth) == (null == parentNode));
    }

    @Override
    public final int getDepth() {
        return depth;
    }

    @Override
    public final boolean isRoot() {
        return 0 == depth;
    }

    @Override
    public List<E> getValueChain() {
        final ValueChain<E> valueChain = new ValueChain<>(null, this);
        return valueChain;
    }

    @Override
    public final boolean hasAncestorReference(TrieNode<E> ancestorNode) {
        TrieNode<E> node = this;
        do {
            if (ancestorNode == node) {
                return true;
            }
            node = node.getParent();
        } while (null != node);
        return false;
    }

    @Override
    public final boolean isLeaf() {
        final TrieChildren<E> children = getChildren();
        return (null == children) || children.isEmpty();
    }

    @Override
    public final int getTerminalCount() {
        return terminalCount;
    }

    @Override
    public final int getAggregateCount() {
        return aggregateCount;
    }

    @Override
    public final boolean isMarked() {
        return mark;
    }

    @Override
    public void setMark(boolean newMark) {
        if (this.mark != newMark) {
            this.mark = newMark;
            final TrieNode<E> parent = getParent();
            if (parent instanceof AbstractExtendedTrieNode) {
                ((AbstractExtendedTrieNode) parent).childMarkChanged(newMark);
            }
        }
    }

    @Override
    public final P getPayload() {
        return payload;
    }

    @Override
    public final void setPayload(P newPayload) {
        this.payload = newPayload;
    }

    static void resetExtendedNode(TrieNode<?> node, NodeVisitorContext unusedContext) {
        assert node instanceof AbstractExtendedTrieNode;
        final AbstractExtendedTrieNode<?, ?> extNode = (AbstractExtendedTrieNode<?, ?>) node;
        extNode.payload = null;
        extNode.mark = false;
        extNode.markedChildrenCount = 0;
        extNode.terminalCount = 0;
        extNode.aggregateCount = 0;
    }

    static void resetMark(TrieNode<?> node, NodeVisitorContext unusedContext) {
        assert node instanceof AbstractExtendedTrieNode;
        final AbstractExtendedTrieNode<?, ?> extNode = (AbstractExtendedTrieNode<?, ?>) node;
        extNode.mark = false;
        extNode.markedChildrenCount = 0;
    }

    protected void childMarkChanged(boolean newChildMark) {
        final boolean shouldChangeOwnMark;
        final int childrenCount = getChildren().getChildCount();
        if (newChildMark) {
            ++markedChildrenCount;
            assert markedChildrenCount <= childrenCount;
            shouldChangeOwnMark = (markedChildrenCount == childrenCount);
        } else {
            --markedChildrenCount;
            assert markedChildrenCount >= 0;
            shouldChangeOwnMark = (markedChildrenCount < childrenCount);
        }
        if (shouldChangeOwnMark) {
            final boolean newMarkState = !mark;
            setMark(newMarkState);
        }
    }

    protected void registerHit(boolean terminal) {
        ++aggregateCount;
        if (terminal) {
            ++terminalCount;
        }
    }

}
