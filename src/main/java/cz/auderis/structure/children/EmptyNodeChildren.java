package cz.auderis.structure.children;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public enum  EmptyNodeChildren implements NodeChildren<Object, Object> {
    INSTANCE;

    public static <E, N> NodeChildren<E, N> getInstance() {
        return (NodeChildren<E, N>) INSTANCE;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public Optional<Object> getChild(Object value) {
        return Optional.empty();
    }

    @Override
    public boolean hasChild(Object value) {
        return false;
    }

    @Override
    public Map<Object, Object> asMap() {
        return null;
    }

    @Override
    public Collection<Object> allChildren() {
        return null;
    }

    @Override
    public void addToCollection(Collection<? super Object> target) {
        // Does nothing
    }

    @Override
    public Object getOrAddChild(Object key, Function<Object, Object> nodeSupplier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        // Ignore
    }

}
