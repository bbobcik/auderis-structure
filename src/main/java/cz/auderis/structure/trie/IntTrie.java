package cz.auderis.structure.trie;

import cz.auderis.structure.traversal.BasicVisitorContext;
import cz.auderis.structure.traversal.NodeVisitor;
import cz.auderis.structure.traversal.TraversalStrategy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class IntTrie<P> implements ExtendedTrie<Integer, P> {

    private final IntTrieNode<P> root;
    private int maxDepth;

    public IntTrie() {
        this.root = new IntTrieNode<>(Integer.MIN_VALUE, null);
    }

    @Override
    public TrieNode<Integer> getRootNode() {
        return root;
    }

    @Override
    public int getDepth() {
        return maxDepth;
    }

    @Override
    public void clear() {
        visit(TraversalStrategy.DEPTH_FIRST_SEARCH_BOTTOM_UP, AbstractExtendedTrieNode::resetExtendedNode);
        final IntTrieChildren<P> rootChildren = root.getChildrenInternal();
        if (null != rootChildren) {
            rootChildren.clear();
        }
    }

    @Override
    public void resetMarks() {
        visit(TraversalStrategy.DEPTH_FIRST_SEARCH_BOTTOM_UP, AbstractExtendedTrieNode::resetMark);
    }

    @Override
    @Deprecated
    public <E2 extends Integer, N extends TrieNode<Integer>> N registerSequence(E2... valueArray) {
        IntTrieNode<P> node = root;
        for (Integer value : valueArray) {
            assert null != value;
            node.registerHit(false);
            node = node.findOrCreateChild(value);
        }
        registerTerminalNode(node);
        return (N) node;
    }

    public <N extends TrieNode<Integer>> N registerIntSequence(int... valueArray) {
        IntTrieNode<P> node = root;
        for (int value : valueArray) {
            node.registerHit(false);
            node = node.findOrCreateChild(value);
        }
        registerTerminalNode(node);
        return (N) node;
    }

    @Override
    @Deprecated
    public <E2 extends Integer, N extends TrieNode<Integer>> N registerSequencePart(int offset, int size, E2... valueArray) {
        if ((offset < 0) || (size < 0) || (offset+size > valueArray.length)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        IntTrieNode<P> node = root;
        for (int i=offset; i<offset+size; ++i) {
            final Integer value = valueArray[i];
            assert null != value;
            node.registerHit(false);
            node = node.findOrCreateChild(value);
        }
        registerTerminalNode(node);
        return (N) node;
    }

    public <N extends TrieNode<Integer>> N registerIntSequencePart(int offset, int size, int... valueArray) {
        if ((offset < 0) || (size < 0) || (offset+size > valueArray.length)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        IntTrieNode<P> node = root;
        for (int i=offset; i<offset+size; ++i) {
            final int value = valueArray[i];
            node.registerHit(false);
            node = node.findOrCreateChild(value);
        }
        registerTerminalNode(node);
        return (N) node;
    }

    @Override
    public <N extends TrieNode<Integer>> N registerSequence(List<? extends Integer> valueList) {
        IntTrieNode<P> node = root;
        for (Integer value : valueList) {
            assert null != value;
            node.registerHit(false);
            node = node.findOrCreateChild(value);
        }
        registerTerminalNode(node);
        return (N) node;
    }

    @Override
    public <N extends TrieNode<Integer>> N registerSequence(Iterator<? extends Integer> valueIterator) {
        IntTrieNode<P> node = root;
        while (valueIterator.hasNext()) {
            final Integer value = valueIterator.next();
            assert null != value;
            node.registerHit(false);
            node = node.findOrCreateChild(value);
        }
        registerTerminalNode(node);
        return (N) node;
    }

    @Override
    @Deprecated
    public <E2 extends Integer, N extends TrieNode<Integer>> N findLongestPrefixOfSequence(E2... valueArray) {
        IntTrieNode<P> node = root;
        for (Integer value : valueArray) {
            assert null != value;
            final IntTrieNode<P> child = node.findChild(value);
            if (null == child) {
                break;
            }
            node = child;
        }
        return (N) node;
    }

    public <N extends TrieNode<Integer>> N findLongestPrefixOfIntSequence(int... valueArray) {
        IntTrieNode<P> node = root;
        for (int value : valueArray) {
            final IntTrieNode<P> child = node.findChild(value);
            if (null == child) {
                break;
            }
            node = child;
        }
        return (N) node;
    }

    @Override
    @Deprecated
    public <E2 extends Integer, N extends TrieNode<Integer>> N findLongestPrefixOfSequencePart(int offset, int size, E2... valueArray) {
        if ((offset < 0) || (size < 0) || (offset+size > valueArray.length)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        IntTrieNode<P> node = root;
        for (int i=offset; i<offset+size; ++i) {
            final Integer value = valueArray[i];
            assert null != value;
            final IntTrieNode<P> child = node.findChild(value);
            if (null == child) {
                break;
            }
            node = child;
        }
        return (N) node;
    }

    public <N extends TrieNode<Integer>> N findLongestPrefixOfIntSequencePart(int offset, int size, int... valueArray) {
        if ((offset < 0) || (size < 0) || (offset+size > valueArray.length)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        IntTrieNode<P> node = root;
        for (int i=offset; i<offset+size; ++i) {
            final int value = valueArray[i];
            final IntTrieNode<P> child = node.findChild(value);
            if (null == child) {
                break;
            }
            node = child;
        }
        return (N) node;
    }

    @Override
    public <N extends TrieNode<Integer>> N findLongestPrefixOfSequence(List<? extends Integer> valueList) {
        // TODO implement
        return null;
    }

    @Override
    public <N extends TrieNode<Integer>> N findLongestPrefixOfSequence(Iterator<? extends Integer> valueIterator) {
        // TODO implement
        return null;
    }

    @Override
    @Deprecated
    public boolean containsSequence(Integer... valueArray) {
        // TODO implement
        return false;
    }

    public boolean containsIntSequence(int... valueArray) {
        // TODO implement
        return false;
    }

    @Override
    public boolean containsSequence(List<? extends Integer> valueList) {
        // TODO implement
        return false;
    }

    @Override
    public boolean containsSequence(Iterator<? extends Integer> valueIterator) {
        // TODO implement
        return false;
    }

    @Override
    @Deprecated
    public boolean containsSequencePart(int offset, int size, Integer... valueArray) {
        // TODO implement
        return false;
    }

    public boolean containsIntSequencePart(int offset, int size, int... valueArray) {
        // TODO implement
        return false;
    }

    @Override
    public void visit(TraversalStrategy strategy, NodeVisitor<TrieNode<Integer>> visitor) {
        if ((null == strategy) || (null == visitor)) {
            throw new NullPointerException();
        }
        final Deque<IntTrieNode<P>> nodeDeque = new ArrayDeque<>(maxDepth + 1);
        final Supplier<IntTrieNode<P>> nextNodeSupplier = strategy.nextEntrySupplier(nodeDeque);
        //
        final BasicVisitorContext context = new BasicVisitorContext();
        if (null != nextNodeSupplier) {
            nodeDeque.add(root);
            while (!nodeDeque.isEmpty() && !context.isTerminated()) {
                final IntTrieNode<P> node = nextNodeSupplier.get();
                visitor.visitNode(node, context);
                if (context.isPruning()) {
                    context.resetPruning();
                } else {
                    final IntTrieChildren<P> children = node.getChildrenInternal();
                    if (null != children) {
                        children.addToCollection(nodeDeque);
                    }
                }
            }
        } else {
            int lastDepth = -1;
            nodeDeque.add(root);
            while (!nodeDeque.isEmpty() && !context.isTerminated()) {
                final IntTrieNode<P> node = nodeDeque.peekLast();
                final IntTrieChildren<P> children = node.getChildrenInternal();
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

    private void registerTerminalNode(IntTrieNode<P> terminalNode) {
        terminalNode.registerHit(true);
        if (terminalNode.depth > maxDepth) {
            maxDepth = terminalNode.depth;
        }
    }

}
