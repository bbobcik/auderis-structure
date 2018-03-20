package cz.auderis.structure.children;

import java.util.Map;
import java.util.TreeMap;

public class ComparableNodeChildren<E extends Comparable<E>, N> extends BasicNodeChildren<E, N> {

    @Override
    protected Map<E, N> createNodeMap() {
        return new TreeMap<>();
    }

}
