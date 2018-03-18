package cz.auderis.structure.trie;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

class TrieDepthMatcher extends TypeSafeMatcher<Trie<?>> {

    private final Matcher<? super Integer> depthMatcher;
    private final int expectedDepth;

    TrieDepthMatcher(Matcher<? super Integer> depthMatcher, int expectedDepth) {
        super(Trie.class);
        this.depthMatcher = depthMatcher;
        this.expectedDepth = expectedDepth;
    }

    @Override
    protected boolean matchesSafely(Trie<?> trie) {
        final int depth = trie.getDepth();
        final boolean result;
        if (null != depthMatcher) {
            result = depthMatcher.matches(depth);
        } else {
            result = (expectedDepth == depth);
        }
        return result;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendText("trie with depth ");
        if (null != depthMatcher) {
            desc.appendDescriptionOf(depthMatcher);
        } else {
            desc.appendValue(expectedDepth);
        }
    }

    @Override
    protected void describeMismatchSafely(Trie<?> trie, Description desc) {
        desc.appendText("depth was ");
        desc.appendValue(trie.getDepth());
    }

}
