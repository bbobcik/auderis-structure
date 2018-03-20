package cz.auderis.structure.trie;

import cz.auderis.structure.children.BasicNodeChildren;
import cz.auderis.structure.children.EmptyNodeChildren;
import cz.auderis.structure.children.NodeChildren;

import java.util.Map;
import java.util.function.Supplier;

class BasicTrieNode<E, P> extends AbstractExtendedTrieNode<E, P> {

    final E value;
    final BasicTrieNode<E, P> parentNode;
    private NodeChildren<E, BasicTrieNode<E, P>> children;

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
    public NodeChildren<E, BasicTrieNode<E, P>> getChildren() {
        if (null == children) {
            return EmptyNodeChildren.getInstance();
        }
        return children;
    }

    NodeChildren<E, BasicTrieNode<E, P>> getChildrenInternal() {
        return children;
    }

    BasicTrieNode<E, P> findOrCreateChild(E value, Supplier<Map<E, BasicTrieNode<E, P>>> childStorageSupplier) {
        if (null == children) {
            children = new BasicNodeChildren<>();
        }
        final BasicTrieNode<E, P> result = children.getOrAddChild(value, key -> new BasicTrieNode<>(key, this));
        return result;
    }

    BasicTrieNode<E, P> findChild(E value) {
        final BasicTrieNode<E, P> result;
        if (null == children) {
            result = null;
        } else {
            result = children.getChild(value).orElse(null);
        }
        return result;
    }

}
