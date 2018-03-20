package cz.auderis.structure.trie;

import cz.auderis.structure.children.NodeChildren;

import java.util.List;

public interface TrieNode<E> {

    E getValue();

    int getDepth();
    boolean isRoot();
    <N extends TrieNode<E>> N getParent();

    boolean isLeaf();
    <N extends TrieNode<E>> NodeChildren<E, N> getChildren();

    List<E> getValueChain();

    boolean hasAncestorReference(TrieNode<E> ancestorNode);

}
