package cz.auderis.structure.trie;

import cz.auderis.structure.traversal.BasicVisitorContext;
import cz.auderis.structure.traversal.NodeVisitor;
import cz.auderis.structure.traversal.TraversalStrategy;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

public class BasicTrie<E, P> implements ExtendedTrie<E, P> {

    private final BasicTrieNode<E, P> root;
    private final Supplier<Map<E, BasicTrieNode<E, P>>> childStorageSupplier;
    private int maxDepth;


    public static <E1, P1> BasicTrie<E1, P1> newInstance() {
        return new BasicTrie<>(() -> new HashMap<E1, BasicTrieNode<E1, P1>>(4));
    }

    public static <E1 extends Comparable, P1> BasicTrie<E1, P1> newSortedInstance() {
        return new BasicTrie<>(() -> new TreeMap<E1, BasicTrieNode<E1, P1>>());
    }

    public static <E1 extends Comparable, P1> BasicTrie<E1, P1> newSortedInstance(Comparator<E1> comparator) {
        return new BasicTrie<>(() -> new TreeMap<>(comparator));
    }

    BasicTrie(Supplier<Map<E, BasicTrieNode<E, P>>> childStorageSupplier) {
        this.root = new BasicTrieNode<>(null, null);
        this.childStorageSupplier = childStorageSupplier;
    }

    @Override
    public TrieNode<E> getRootNode() {
        return root;
    }

    @Override
    public int getDepth() {
        return maxDepth;
    }

    @Override
    public void clear() {
        visit(TraversalStrategy.DEPTH_FIRST_SEARCH_BOTTOM_UP, AbstractExtendedTrieNode::resetExtendedNode);
        final BasicTrieChildren<E, P> rootChildren = root.getChildrenInternal();
        if (null != rootChildren) {
            rootChildren.clear();
        }
    }

    @Override
    public void resetMarks() {
        visit(TraversalStrategy.DEPTH_FIRST_SEARCH_BOTTOM_UP, AbstractExtendedTrieNode::resetMark);
    }

    @Override
    public <E2 extends E, N extends TrieNode<E>> N registerSequence(E2... valueArray) {
        BasicTrieNode<E, P> node = root;
        for (E value : valueArray) {
            node.registerHit(false);
            node = node.findOrCreateChild(value, childStorageSupplier);
        }
        registerTerminalNode(node);
        return (N) node;
    }

    @Override
    public <E2 extends E, N extends TrieNode<E>> N registerSequencePart(int offset, int size, E2... valueArray) {
        if ((offset < 0) || (size < 0) || (offset+size > valueArray.length)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        BasicTrieNode<E, P> node = root;
        for (int i=offset; i<offset+size; ++i) {
            node.registerHit(false);
            final E value = valueArray[i];
            node = node.findOrCreateChild(value, childStorageSupplier);
        }
        registerTerminalNode(node);
        return (N) node;
    }

    @Override
    public <N extends TrieNode<E>> N registerSequence(List<? extends E> valueList) {
        BasicTrieNode<E, P> node = root;
        for (E value : valueList) {
            node.registerHit(false);
            node = node.findOrCreateChild(value, childStorageSupplier);
        }
        registerTerminalNode(node);
        return (N) node;
    }

    @Override
    public <N extends TrieNode<E>> N registerSequence(Iterator<? extends E> valueIterator) {
        BasicTrieNode<E, P> node = root;
        while (valueIterator.hasNext()) {
            node.registerHit(false);
            final E value = valueIterator.next();
            node = node.findOrCreateChild(value, childStorageSupplier);
        }
        registerTerminalNode(node);
        return (N) node;
    }

    @Override
    public <E2 extends E, N extends TrieNode<E>> N findLongestPrefixOfSequence(E2... valueArray) {
        BasicTrieNode<E, P> node = root;
        for (E value : valueArray) {
            final BasicTrieNode<E, P> child = node.findChild(value);
            if (null == child) {
                break;
            }
            node = child;
        }
        return (N) node;
    }

    @Override
    public <E2 extends E, N extends TrieNode<E>> N findLongestPrefixOfSequencePart(int offset, int size, E2... valueArray) {
        if ((offset < 0) || (size < 0) || (offset+size > valueArray.length)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        BasicTrieNode<E, P> node = root;
        for (int i=offset; i<offset+size; ++i) {
            final E value = valueArray[i];
            final BasicTrieNode<E, P> child = node.findChild(value);
            if (null == child) {
                break;
            }
            node = child;
        }
        return (N) node;
    }

    @Override
    public <N extends TrieNode<E>> N findLongestPrefixOfSequence(List<? extends E> valueList) {
        // TODO implement
        return null;
    }

    @Override
    public <N extends TrieNode<E>> N findLongestPrefixOfSequence(Iterator<? extends E> valueIterator) {
        // TODO implement
        return null;
    }

    @Override
    public boolean containsSequence(E... valueArray) {
        // TODO implement
        return false;
    }

    @Override
    public boolean containsSequence(List<? extends E> valueList) {
        // TODO implement
        return false;
    }

    @Override
    public boolean containsSequence(Iterator<? extends E> valueIterator) {
        // TODO implement
        return false;
    }

    @Override
    public boolean containsSequencePart(int offset, int size, E... valueArray) {
        // TODO implement
        return false;
    }

    @Override
    public void visit(TraversalStrategy strategy, NodeVisitor<TrieNode<E>> visitor) {
        if ((null == strategy) || (null == visitor)) {
            throw new NullPointerException();
        }
        final Deque<BasicTrieNode<E, P>> nodeDeque = new ArrayDeque<>(maxDepth + 1);
        final Supplier<BasicTrieNode<E, P>> nextNodeSupplier = strategy.nextEntrySupplier(nodeDeque);
        //
        final BasicVisitorContext context = new BasicVisitorContext();
        if (null != nextNodeSupplier) {
            nodeDeque.add(root);
            while (!nodeDeque.isEmpty() && !context.isTerminated()) {
                final BasicTrieNode<E, P> node = nextNodeSupplier.get();
                visitor.visitNode(node, context);
                if (context.isPruning()) {
                    context.resetPruning();
                } else {
                    final BasicTrieChildren<E, P> children = node.getChildrenInternal();
                    if (null != children) {
                        children.addToCollection(nodeDeque);
                    }
                }
            }
        } else {
            int lastDepth = -1;
            nodeDeque.add(root);
            while (!nodeDeque.isEmpty() && !context.isTerminated()) {
                final BasicTrieNode<E, P> node = nodeDeque.peekLast();
                final BasicTrieChildren<E, P> children = node.getChildrenInternal();
                if ((node.depth >= lastDepth) && (null != children) && !children.isEmpty()) {
                    children.addToCollection(nodeDeque);
                } else {
                    nodeDeque.removeLast();
                    lastDepth = node.depth;
                    visitor.visitNode(node, context);
                }
            }
        }
    }

    private void registerTerminalNode(BasicTrieNode<E, P> terminalNode) {
        terminalNode.registerHit(true);
        if (terminalNode.depth > maxDepth) {
            maxDepth = terminalNode.depth;
        }
    }

}
