package cz.auderis.structure.traversal;

public class BasicVisitorContext implements NodeVisitorContext {

    private boolean terminated;
    private boolean pruning;
    private Object payload;

    @Override
    public void terminateSearch() {
        this.terminated = true;
    }

    public boolean isTerminated() {
        return terminated;
    }

    @Override
    public void pruneBranch() {
        this.pruning = true;
    }

    public boolean isPruning() {
        return (terminated || pruning);
    }

    public void resetPruning() {
        this.pruning = false;
    }

    @Override
    public <T> T getPayload() {
        return (T) payload;
    }

    @Override
    public void setPayload(Object newPayload) {
        this.payload = newPayload;
    }

}
