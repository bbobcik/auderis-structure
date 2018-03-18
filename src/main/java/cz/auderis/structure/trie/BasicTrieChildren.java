package cz.auderis.structure.trie;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

class BasicTrieChildren<E, P> implements TrieChildren<E> {

    private final Map<E, BasicTrieNode<E, P>> nodeMap;
    private final Map<E, TrieNode<E>> publicMap;

    BasicTrieChildren(Supplier<Map<E, BasicTrieNode<E, P>>> childStorageSupplier) {
        this.nodeMap = childStorageSupplier.get();
        this.publicMap = Collections.unmodifiableMap(nodeMap);
    }

    @Override
    public int getChildCount() {
        return nodeMap.size();
    }

    @Override
    public boolean isEmpty() {
        return nodeMap.isEmpty();
    }

    @Override
    public boolean hasChild(E value) {
        if (null == value) {
            return false;
        }
        return nodeMap.containsKey(value);
    }

    @Override
    public <N extends TrieNode<E>> N getChild(E value) {
        if (null == value) {
            return null;
        }
        return (N) nodeMap.get(value);
    }

    @Override
    public Map<E, TrieNode<E>> asMap() {
        return publicMap;
    }

    @Override
    public Iterator<TrieNode<E>> iterator() {
        return publicMap.values().iterator();
    }

    @Override
    public Spliterator<TrieNode<E>> spliterator() {
        return publicMap.values().spliterator();
    }

    @Override
    public void forEach(Consumer<? super TrieNode<E>> action) {
        nodeMap.values().forEach(action);
    }

    void addToCollection(Collection<? super BasicTrieNode<E, P>> target) {
        target.addAll(nodeMap.values());
    }

    void addChild(BasicTrieNode<E, P> newChildNode) {
        final BasicTrieNode<E, P> prevChildNode = nodeMap.put(newChildNode.value, newChildNode);
        assert null == prevChildNode;
    }

    void clear() {
        nodeMap.clear();
    }

}
