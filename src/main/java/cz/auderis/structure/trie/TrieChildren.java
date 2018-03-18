package cz.auderis.structure.trie;

import java.util.Map;

public interface TrieChildren<E> extends Iterable<TrieNode<E>> {

    int getChildCount();
    boolean isEmpty();

    boolean hasChild(E value);
    <N extends TrieNode<E>> N getChild(E value);

    Map<E, TrieNode<E>> asMap();

}
