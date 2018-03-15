package cz.auderis.structure.trie;

import java.util.Iterator;
import java.util.List;

public interface Trie<E> {

    void registerSequence(E... valueArray);
    void registerSequence(List<? extends E> valueList);
    void registerSequence(Iterator<? extends E> valueIterator);
    void registerSequencePart(int offset, int size, E... valueArray);

    TrieNode<E> findLongestPrefixOfSequence(E... valueArray);
    TrieNode<E> findLongestPrefixOfSequence(List<? extends E> valueList);
    TrieNode<E> findLongestPrefixOfSequence(Iterator<? extends E> valueIterator);
    TrieNode<E> findLongestPrefixOfSequencePart(int offset, int size, E... valueArray);

    boolean containsSequence(E... valueArray);
    boolean containsSequence(List<? extends E> valueList);
    boolean containsSequence(Iterator<? extends E> valueIterator);
    boolean containsSequencePart(int offset, int size, E... valueArray);

    void clear();

}
