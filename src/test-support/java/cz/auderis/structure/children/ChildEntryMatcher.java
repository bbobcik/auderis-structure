package cz.auderis.structure.children;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;

class ChildEntryMatcher<E, N> extends TypeSafeMatcher<NodeChildren<E, N>> {

    private final Matcher<? super E> childValueMatcher;
    private final Matcher<? super N> childNodeMatcher;
    private final boolean mustMatchAll;

    ChildEntryMatcher(Matcher<? super E> childValueMatcher, Matcher<? super N> childNodeMatcher, boolean mustMatchAll) {
        super(NodeChildren.class);
        this.childValueMatcher = childValueMatcher;
        this.childNodeMatcher = childNodeMatcher;
        this.mustMatchAll = mustMatchAll;
    }

    @Override
    protected boolean matchesSafely(NodeChildren<E, N> children) {
        final Map<E, N> childMap = children.asMap();
        if (mustMatchAll) {
            for (Map.Entry<E, N> childEntry : childMap.entrySet()) {
                final E key = childEntry.getKey();
                if (null != childValueMatcher && !childValueMatcher.matches(key)) {
                    return false;
                }
                final N node = childEntry.getValue();
                if (null != childNodeMatcher && !childNodeMatcher.matches(node)) {
                    return false;
                }
            }
            return true;
        } else {
            for (Map.Entry<E, N> childEntry : childMap.entrySet()) {
                final E key = childEntry.getKey();
                if (null != childValueMatcher && !childValueMatcher.matches(key)) {
                    continue;
                }
                final N node = childEntry.getValue();
                if (null != childNodeMatcher && !childNodeMatcher.matches(node)) {
                    continue;
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("node children collection");
        String clauseSeparator = " with ";
        if (null != childValueMatcher) {
            description.appendText(clauseSeparator);
            clauseSeparator = " and ";
            if (mustMatchAll) {
                description.appendText("all link values ");
            } else {
                description.appendText("a link value ");
            }
            description.appendDescriptionOf(childValueMatcher);
        }
        if (null != childNodeMatcher) {
            description.appendText(clauseSeparator);
            if (mustMatchAll) {
                description.appendText("all linked nodes");
            } else if (null != childValueMatcher) {
                description.appendText("its respective linked node ");
            } else {
                description.appendText("a linked node ");
            }
            description.appendDescriptionOf(childNodeMatcher);
        }
    }

    @Override
    protected void describeMismatchSafely(NodeChildren<E, N> children, Description mismatchDescription) {
        if (children.isEmpty()) {
            mismatchDescription.appendText("node children collection is empty");
        } else if (mustMatchAll) {
            final Map<E, N> childMap = children.asMap();
            Map.Entry<E, N> failEntry = null;
            for (Map.Entry<E, N> childEntry : childMap.entrySet()) {
                final E key = childEntry.getKey();
                if (null != childValueMatcher && !childValueMatcher.matches(key)) {
                    failEntry = childEntry;
                    break;
                }
                final N node = childEntry.getValue();
                if (null != childNodeMatcher && !childNodeMatcher.matches(node)) {
                    failEntry = childEntry;
                    break;
                }
            }
            assert null != failEntry;
            final E failValue = failEntry.getKey();
            final N failNode = failEntry.getValue();
            mismatchDescription.appendText("node children collection has entry with value ");
            mismatchDescription.appendValue(failValue);
            mismatchDescription.appendText(" linked to node ");
            mismatchDescription.appendValue(failNode);
        } else {
            mismatchDescription.appendText("none of child entries matches");
        }
    }

}
