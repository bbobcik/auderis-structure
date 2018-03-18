package cz.auderis.structure.trie;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util.function.Consumer;

public class IntTrieChildren<P> implements TrieChildren<Integer> {

    private final SortedMap<Integer, IntTrieNode<P>> children;
    private final Map<Integer, TrieNode<Integer>> publicChildren;

    IntTrieChildren() {
        this.children = new TreeMap<>();
        this.publicChildren = Collections.unmodifiableMap(children);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public boolean isEmpty() {
        return children.isEmpty();
    }

    @Override
    public boolean hasChild(Integer value) {
        return children.containsKey(value);
    }

    @Override
    public <N extends TrieNode<Integer>> N getChild(Integer value) {
        return (N) children.get(value);
    }

    @Override
    public Map<Integer, TrieNode<Integer>> asMap() {
        return publicChildren;
    }

    @Override
    public Iterator<TrieNode<Integer>> iterator() {
        return publicChildren.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super TrieNode<Integer>> action) {
        for (IntTrieNode<P> trieNode : children.values()) {
            action.accept(trieNode);
        }
    }

    @Override
    public Spliterator<TrieNode<Integer>> spliterator() {
        return publicChildren.values().spliterator();
    }

    void addChild(IntTrieNode<P> newChildNode) {
        final IntTrieNode<P> prevChildNode = children.put(newChildNode.value, newChildNode);
        assert null == prevChildNode;
    }

    void clear() {
        children.clear();
    }

    void addToCollection(Collection<? super IntTrieNode<P>> target) {
        target.addAll(children.values());
    }

}
