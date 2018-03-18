package cz.auderis.structure.traversal;

public interface NodeVisitorContext {

    void terminateSearch();

    void pruneBranch();

    <T> T getPayload();

    void setPayload(Object newPayload);

}
