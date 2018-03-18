package cz.auderis.structure.trie;

public class IntTrieNode<P> extends AbstractExtendedTrieNode<Integer, P> {

    final int value;
    final IntTrieNode<P> parent;
    private IntTrieChildren<P> children;

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
    public TrieChildren<Integer> getChildren() {
        if (null == children) {
            return (TrieChildren<Integer>) ((TrieChildren) EmptyTrieChildren.INSTANCE);
        }
        return children;
    }

    IntTrieChildren<P> getChildrenInternal() {
        return children;
    }

    IntTrieNode<P> findOrCreateChild(int value) {
        IntTrieNode<P> result;
        if (null == children) {
            children = new IntTrieChildren<>();
            result = null;
        } else {
            result = children.getChild(value);
        }
        if (null == result) {
            result = new IntTrieNode<>(value, this);
            children.addChild(result);
        }
        return result;
    }

    IntTrieNode<P> findChild(int value) {
        final IntTrieNode<P> result;
        if (null == children) {
            result = null;
        } else {
            result = children.getChild(value);
        }
        return result;
    }


}
