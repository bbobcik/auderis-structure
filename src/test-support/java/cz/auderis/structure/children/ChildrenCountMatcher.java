package cz.auderis.structure.children;

import org.hamcrest.*;

class ChildrenCountMatcher extends TypeSafeMatcher<NodeChildren<?, ?>> {

    private final int expectedCount;
    private final Matcher<? super Integer> countMatcher;

    ChildrenCountMatcher(int expectedCount, Matcher<? super Integer> countMatcher) {
        super(NodeChildren.class);
        this.expectedCount = expectedCount;
        this.countMatcher = countMatcher;
    }

    @Override
    protected boolean matchesSafely(NodeChildren<?, ?> children) {
        final int count = children.getChildCount();
        final boolean hasExpectedCount;
        if (null != countMatcher) {
            hasExpectedCount = countMatcher.matches(count);
        } else {
            hasExpectedCount = (count == expectedCount);
        }
        return hasExpectedCount;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("node children collection with ");
        if (null != countMatcher) {
            description.appendDescriptionOf(countMatcher);
        } else {
            description.appendValue(expectedCount);
        }
        description.appendText(" entries");
    }

    @Override
    protected void describeMismatchSafely(NodeChildren<?, ?> children, Description mismatchDescription) {
        mismatchDescription.appendText("node children collection contains ");
        mismatchDescription.appendValue(children.getChildCount());
        mismatchDescription.appendText(" entries");
    }

}
