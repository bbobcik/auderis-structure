package cz.auderis.structure.trie;

import java.util.List;
import java.util.Map;

public interface TrieChildren<E> extends Iterable<TrieNode<E>> {

    boolean hasChild(E value);
    TrieNode<E> getChild(E value);

    Map<E, TrieNode<E>> asMap();
    List<TrieChildren<E>> asList();

}
