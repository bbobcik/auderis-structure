package cz.auderis.structure.trie;

import java.util.List;

public interface TrieNode<E> {

    E getValue();

    int getDepth();
    boolean isRoot();
    <N extends TrieNode<E>> N getParent();

    boolean isLeaf();
    TrieChildren<E> getChildren();

    List<E> getValueChain();

    boolean hasAncestorReference(TrieNode<E> ancestorNode);

}
