package cz.auderis.structure.trie;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public enum EmptyTrieChildren implements TrieChildren<Object> {

    INSTANCE;

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean hasChild(Object value) {
        return false;
    }

    @Override
    public TrieNode<Object> getChild(Object value) {
        return null;
    }

    @Override
    public Map<Object, TrieNode<Object>> asMap() {
        return Collections.emptyMap();
    }

    @Override
    public Iterator<TrieNode<Object>> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public void forEach(Consumer<? super TrieNode<Object>> action) {
        // Does nothing
    }

    @Override
    public Spliterator<TrieNode<Object>> spliterator() {
        return Spliterators.emptySpliterator();
    }

}
