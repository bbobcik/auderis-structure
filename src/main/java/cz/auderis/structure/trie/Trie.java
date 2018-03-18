package cz.auderis.structure.trie;

import cz.auderis.structure.traversal.VisitableStructure;

import java.util.Iterator;
import java.util.List;

public interface Trie<E> extends VisitableStructure<TrieNode<E>> {

    <E2 extends E, N extends TrieNode<E>> N registerSequence(E2... valueArray);
    <E2 extends E, N extends TrieNode<E>> N registerSequencePart(int offset, int size, E2... valueArray);
    <N extends TrieNode<E>> N registerSequence(List<? extends E> valueList);
    <N extends TrieNode<E>> N registerSequence(Iterator<? extends E> valueIterator);

    <E2 extends E, N extends TrieNode<E>> N findLongestPrefixOfSequence(E2... valueArray);
    <E2 extends E, N extends TrieNode<E>> N findLongestPrefixOfSequencePart(int offset, int size, E2... valueArray);
    <N extends TrieNode<E>> N findLongestPrefixOfSequence(List<? extends E> valueList);
    <N extends TrieNode<E>> N findLongestPrefixOfSequence(Iterator<? extends E> valueIterator);

    boolean containsSequence(E... valueArray);
    boolean containsSequence(List<? extends E> valueList);
    boolean containsSequence(Iterator<? extends E> valueIterator);
    boolean containsSequencePart(int offset, int size, E... valueArray);

    TrieNode<E> getRootNode();
    int getDepth();

    void clear();

}
