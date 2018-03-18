package cz.auderis.structure.trie;

import org.hamcrest.Matcher;

public class TrieMatchers {

    public static Matcher<? super Trie<?>> hasDepth(int expectedDepth) {
        return new TrieDepthMatcher(null, expectedDepth);
    }

    public static Matcher<? super Trie<?>> hasDepth(Matcher<? super Integer> expectedDepthMatcher) {
        return new TrieDepthMatcher(expectedDepthMatcher, -1);
    }

}
