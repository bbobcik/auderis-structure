package cz.auderis.structure.traversal;

import java.util.Deque;
import java.util.function.Supplier;

public enum TraversalStrategy {

    BREADTH_FIRST_SEARCH {
        @Override
        public <T> Supplier<T> nextEntrySupplier(Deque<T> entryDeque) {
            return entryDeque::pollFirst;
        }
    },

    DEPTH_FIRST_SEARCH_TOP_DOWN {
        @Override
        public <T> Supplier<T> nextEntrySupplier(Deque<T> entryDeque) {
            return entryDeque::pollLast;
        }
    },

    DEPTH_FIRST_SEARCH_BOTTOM_UP {
        @Override
        public <T> Supplier<T> nextEntrySupplier(Deque<T> entryDeque) {
            return null;
        }
    },
    ;

    public abstract <T> Supplier<T> nextEntrySupplier(Deque<T> entryDeque);


}
