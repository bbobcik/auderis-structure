package cz.auderis.structure.trie;

public interface ExtendedTrieNode<E, P> extends TrieNode<E> {

    boolean isMarked();
    void setMark(boolean newMark);

    int getTerminalCount();
    int getAggregateCount();

    P getPayload();
    void setPayload(P newPayload);

}
