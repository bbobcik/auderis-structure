package cz.auderis.structure.children;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

public final class NodeChildrenMatchers {

    public static Matcher<? super NodeChildren<?, ?>>  empty() {
        final ChildrenCountMatcher matcher1 = new ChildrenCountMatcher(0, null);
        final Matcher<? super NodeChildren<?, ?>> matcher2 = CoreMatchers.describedAs("empty node children collection", matcher1);
        return matcher2;
    }

    public static Matcher<? super NodeChildren<?, ?>> hasChildrenCount(int expectedCount) {
        final ChildrenCountMatcher matcher = new ChildrenCountMatcher(expectedCount, null);
        return matcher;
    }

    public static Matcher<? super NodeChildren<?, ?>> hasChildrenCount(Matcher<? super Integer> expectedCountMatcher) {
        final ChildrenCountMatcher matcher = new ChildrenCountMatcher(0, expectedCountMatcher);
        return matcher;
    }

    public static <E, N> Matcher<? super NodeChildren<E, N>> hasAnyChildValue(E expectedValue) {
        final Matcher<? super E> valueMatcher = CoreMatchers.is(expectedValue);
        final Matcher<? super E> valueMatcher1 = CoreMatchers.describedAs("<%0>", valueMatcher, expectedValue);
        final ChildEntryMatcher<E, N> matcher = new ChildEntryMatcher<>(valueMatcher1, null, false);
        return matcher;
    }

    public static <E, N> Matcher<? super NodeChildren<E, N>> hasAnyChildValue(Matcher<? super E> expectedValueMatcher) {
        final ChildEntryMatcher<E, N> matcher = new ChildEntryMatcher<>(expectedValueMatcher, null, false);
        return matcher;
    }

    public static <E, N> Matcher<? super NodeChildren<E, N>> hasAnyChildNode(N expectedNode) {
        final Matcher<? super N> nodeMatcher = CoreMatchers.is(expectedNode);
        final Matcher<? super N> nodeMatcher1 = CoreMatchers.describedAs("<%0>", nodeMatcher, expectedNode);
        final ChildEntryMatcher<E, N> matcher = new ChildEntryMatcher<>(null, nodeMatcher1, false);
        return matcher;
    }

    public static <E, N> Matcher<? super NodeChildren<E, N>> hasAnyChildNode(Matcher<? super N> expectedNodeMatcher) {
        final ChildEntryMatcher<E, N> matcher = new ChildEntryMatcher<>(null, expectedNodeMatcher, false);
        return matcher;
    }

    public static <E, N> Matcher<? super NodeChildren<E, N>> hasAllChildValues(Matcher<? super E> expectedValueMatcher) {
        final ChildEntryMatcher<E, N> matcher = new ChildEntryMatcher<>(expectedValueMatcher, null, true);
        return matcher;
    }

    public static <E, N> Matcher<? super NodeChildren<E, N>> hasAllChildNodes(Matcher<? super N> expectedNodeMatcher) {
        final ChildEntryMatcher<E, N> matcher = new ChildEntryMatcher<>(null, expectedNodeMatcher, true);
        return matcher;
    }

    public static <E, N> Matcher<? super NodeChildren<E, N>> hasAnyChildEntry(E expectedValue, N expectedNode) {
        final Matcher<? super E> valueMatcher1;
        if (null != expectedValue) {
            final Matcher<? super E> valueMatcher = CoreMatchers.is(expectedValue);
            valueMatcher1 = CoreMatchers.describedAs("<%0>", valueMatcher, expectedValue);
        } else {
            valueMatcher1 = null;
        }
        final Matcher<? super N> nodeMatcher1;
        if (null != expectedNode) {
            final Matcher<? super N> nodeMatcher = CoreMatchers.is(expectedNode);
            nodeMatcher1 = CoreMatchers.describedAs("<%0>", nodeMatcher, expectedNode);
        } else {
            nodeMatcher1 = null;
        }
        final ChildEntryMatcher<E, N> matcher = new ChildEntryMatcher<>(valueMatcher1, nodeMatcher1, false);
        return matcher;
    }

    public static <E, N> Matcher<? super NodeChildren<E, N>> hasAnyChildEntry(Matcher<? super E> expectedValueMatcher, Matcher<? super N> expectedNodeMatcher) {
        final ChildEntryMatcher<E, N> matcher = new ChildEntryMatcher<>(expectedValueMatcher, expectedNodeMatcher, false);
        return matcher;
    }

    public static <E, N> Matcher<? super NodeChildren<E, N>> hasAllChildEntries(Matcher<? super E> expectedValueMatcher, Matcher<? super N> expectedNodeMatcher) {
        final ChildEntryMatcher<E, N> matcher = new ChildEntryMatcher<>(expectedValueMatcher, expectedNodeMatcher, true);
        return matcher;
    }


    private NodeChildrenMatchers() {
        throw new AssertionError();
    }

}
