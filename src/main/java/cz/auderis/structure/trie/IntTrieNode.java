package cz.auderis.structure.trie;

import cz.auderis.structure.children.ComparableNodeChildren;
import cz.auderis.structure.children.EmptyNodeChildren;
import cz.auderis.structure.children.NodeChildren;

public class IntTrieNode<P> extends AbstractExtendedTrieNode<Integer, P> {

    final int value;
    final IntTrieNode<P> parent;
    private NodeChildren<Integer, IntTrieNode<P>> children;

    IntTrieNode(int value, IntTrieNode<P> parent) {
        super(parent);
        this.value = value;
        this.parent = parent;
    }

    public int getIntValue() {
        return value;
    }

    @Override
    @Deprecated
    public Integer getValue() {
        return value;
    }

    @Override
    public IntTrieNode<P> getParent() {
        return parent;
    }

    @Override
    public NodeChildren<Integer, IntTrieNode<P>> getChildren() {
        if (null == children) {
            return EmptyNodeChildren.getInstance();
        }
        return children;
    }

    NodeChildren<Integer, IntTrieNode<P>> getChildrenInternal() {
        return children;
    }

    IntTrieNode<P> findOrCreateChild(int value) {
        if (null == children) {
            children = new ComparableNodeChildren<>();
        }
        final IntTrieNode<P> result = children.getOrAddChild(value, key -> new IntTrieNode<>(key, this));
        return result;
    }

    IntTrieNode<P> findChild(int value) {
        final IntTrieNode<P> result;
        if (null == children) {
            result = null;
        } else {
            result = children.getChild(value).orElse(null);
        }
        return result;
    }

}
