package cz.auderis.structure.trie;

import java.util.Map;
import java.util.function.Supplier;

class BasicTrieNode<E, P> extends AbstractExtendedTrieNode<E, P> {

    final E value;
    final BasicTrieNode<E, P> parentNode;
    private BasicTrieChildren<E, P> children;

    BasicTrieNode(E value, BasicTrieNode<E, P> parentNode) {
        super(parentNode);
        this.value = value;
        this.parentNode = parentNode;
    }

    @Override
    public final E getValue() {
        return value;
    }

    @Override
    public BasicTrieNode<E, P> getParent() {
        return parentNode;
    }

    @Override
    public TrieChildren<E> getChildren() {
        if (null == children) {
            return (TrieChildren<E>) EmptyTrieChildren.INSTANCE;
        }
        return children;
    }

    BasicTrieChildren<E, P> getChildrenInternal() {
        return children;
    }

    BasicTrieNode<E, P> findOrCreateChild(E value, Supplier<Map<E, BasicTrieNode<E, P>>> childStorageSupplier) {
        BasicTrieNode<E, P> result;
        if (null == children) {
            children = new BasicTrieChildren<>(childStorageSupplier);
            result = null;
        } else {
            result = children.getChild(value);
        }
        if (null == result) {
            result = new BasicTrieNode<>(value, this);
            children.addChild(result);
        }
        return result;
    }

    BasicTrieNode<E, P> findChild(E value) {
        final BasicTrieNode<E, P> result;
        if (null == children) {
            result = null;
        } else {
            result = children.getChild(value);
        }
        return result;
    }

}
