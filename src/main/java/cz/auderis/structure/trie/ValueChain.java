package cz.auderis.structure.trie;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class ValueChain<E> implements List<E> {

    private final TrieNode<E> topNode;
    private final TrieNode<E> bottomNode;
    private transient List<E> valueList;


    ValueChain(TrieNode<E> topNode, TrieNode<E> bottomNode) {
        // Top node is excluded, but the bottom node is included
        assert null != bottomNode;
        if (null == topNode) {
            TrieNode<E> root = bottomNode;
            while (!root.isRoot()) {
                root = root.getParent();
            }
            topNode = root;
        } else {
            assert bottomNode.hasAncestorReference(topNode);
        }
        this.topNode = topNode;
        this.bottomNode = bottomNode;
    }

    @Override
    public int size() {
        return bottomNode.getDepth() - topNode.getDepth();
    }

    @Override
    public boolean isEmpty() {
        return topNode.getDepth() == bottomNode.getDepth();
    }

    @Override
    public E get(int index) {
        prepareValueList();
        return valueList.get(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        final int topDepth = topNode.getDepth();
        final int bottomDepth = bottomNode.getDepth();
        final int size = bottomDepth - topDepth;
        if ((fromIndex < 0) || (toIndex > size) || (fromIndex > toIndex)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        //
        final int subBottomDepth = topDepth + toIndex;
        TrieNode<E> subBottom = bottomNode;
        for (int i=0; i<subBottomDepth; ++i) {
            subBottom = subBottom.getParent();
        }
        //
        final int subSize = toIndex - fromIndex;
        TrieNode<E> subTop = subBottom;
        for (int i=0; i<subSize; ++i) {
            subTop = subTop.getParent();
        }
        //
        return new ValueChain<>(subTop, subBottom);
    }

    @Override
    public Iterator<E> iterator() {
        prepareValueList();
        return valueList.iterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        prepareValueList();
        return valueList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        prepareValueList();
        return valueList.listIterator(index);
    }

    private void prepareValueList() {
        if (null != valueList) {
            return;
        }
        final int size = bottomNode.getDepth() - topNode.getDepth();
        if (0 == size) {
            this.valueList = Collections.emptyList();
            return;
        }
        this.valueList = new ArrayList<>(Collections.nCopies(size, null));
        TrieNode<E> node = bottomNode;
        for (int idx = size - 1; idx >= 0; --idx) {
            final E value = node.getValue();
            valueList.set(idx, value);
            node = node.getParent();
        }
    }


    @Override
    public int indexOf(Object o) {
        if (null == o) {
            return -1;
        }
        prepareValueList();
        return valueList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        if (null == o) {
            return -1;
        }
        prepareValueList();
        return valueList.lastIndexOf(o);
    }

    @Override
    public boolean contains(Object o) {
        if (null == o) {
            return false;
        }
        for (TrieNode<E> node = bottomNode; node != topNode; node = node.getParent()) {
            final E value = node.getValue();
            if (value.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        prepareValueList();
        return valueList.containsAll(c);
    }

    @Override
    public Object[] toArray() {
        final int size = bottomNode.getDepth() - topNode.getDepth();
        if (0 == size) {
            return new Object[0];
        }
        final Object[] result = new Object[size];
        TrieNode<E> node = bottomNode;
        for (int idx = size - 1; idx >= 0; --idx) {
            final E value = node.getValue();
            result[idx] = value;
            node = node.getParent();
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] target) {
        final int size = bottomNode.getDepth() - topNode.getDepth();
        if (0 != size) {
            final Class<T> targetType = (Class<T>) target.getClass().getComponentType();
            if (target.length < size) {
                target = (T[]) Array.newInstance(targetType, size);
            }
            TrieNode<E> node = bottomNode;
            for (int idx = size - 1; idx >= 0; --idx) {
                final E value = node.getValue();
                if (!targetType.isAssignableFrom(value.getClass())) {
                    throw new ArrayStoreException();
                }
                target[idx] = (T) value;
                node = node.getParent();
            }
        }
        if (target.length > size) {
            target[size] = null;
        }
        return target;
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

}
