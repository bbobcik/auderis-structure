package cz.auderis.structure.children;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class BasicNodeChildren<E, N> implements NodeChildren<E, N> {

    private Map<E, N> nodes;
    private Map<E, N> publicChildren;

    @Override
    public int getChildCount() {
        return (null != nodes) ? nodes.size() : 0;
    }

    @Override
    public boolean isEmpty() {
        return (null == nodes) || nodes.isEmpty();
    }

    @Override
    public boolean hasChild(E value) {
        if ((null == nodes) || (null == value)) {
            return false;
        }
        return nodes.containsKey(value);
    }

    @Override
    public Optional<N> getChild(E value) {
        if ((null == nodes) || (null == value)) {
            return Optional.empty();
        }
        final N node = nodes.get(value);
        final Optional<N> optionalNode = Optional.ofNullable(node);
        return optionalNode;
    }

    @Override
    public Map<E, N> asMap() {
        return (null != publicChildren) ? publicChildren : Collections.emptyMap();
    }

    @Override
    public Collection<N> allChildren() {
        return (null != publicChildren) ? publicChildren.values() : Collections.emptySet();
    }

    @Override
    public void addToCollection(Collection<? super N> target) {
        if (null != nodes) {
            target.addAll(nodes.values());
        }
    }

    @Override
    public N getOrAddChild(E key, Function<E, N> nodeSupplier) {
        if ((null == key) || (null == nodeSupplier)) {
            throw new NullPointerException();
        }
        final N foundNode;
        if (null == nodes) {
            nodes = createNodeMap();
            publicChildren = Collections.unmodifiableMap(nodes);
            foundNode = null;
        } else {
            foundNode = nodes.get(key);
        }
        if (null != foundNode) {
            return foundNode;
        }
        final N newNode = nodeSupplier.apply(key);
        if (null == newNode) {
            throw new IllegalStateException();
        }
        final N oldNode = nodes.put(key, newNode);
        assert null == oldNode;
        return newNode;
    }

    @Override
    public void clear() {
        if (null != nodes) {
            nodes = null;
            publicChildren = null;
        }
    }

    protected Map<E, N> createNodeMap() {
        return new HashMap<>(8);
    }

}
