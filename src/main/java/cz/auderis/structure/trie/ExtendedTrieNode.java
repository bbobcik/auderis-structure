package cz.auderis.structure.trie;

/**
 * Created on 15.3.2018.
 */
public interface ExtendedTrieNode<E, P> extends TrieNode<E> {

    boolean isMarked();
    void setMark(boolean newMark);

    int getLeafCount();
    int getAggregateCount();

    P getPayload();
    void setPayload(P newPayload);

}
