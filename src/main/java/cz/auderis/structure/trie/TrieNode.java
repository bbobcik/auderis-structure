package cz.auderis.structure.trie;

import java.util.List;

public interface TrieNode<E> {

    E getValue();

    int getDepth();
    boolean isRoot();
    TrieNode<E> getParent();

    boolean isLeaf();
    TrieChildren<E> getChildren();

    List<E> getValueChain();

}
