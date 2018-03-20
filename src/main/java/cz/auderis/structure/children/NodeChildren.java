package cz.auderis.structure.children;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface NodeChildren<E, N> {

    int getChildCount();
    boolean isEmpty();

    boolean hasChild(E value);
    Optional<N> getChild(E value);

    Map<E, N> asMap();
    Collection<N> allChildren();
    void addToCollection(Collection<? super N> target);

    N getOrAddChild(E key, Function<E, N> nodeSupplier);
    void clear();

}
