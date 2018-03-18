package cz.auderis.structure.trie;

import cz.auderis.structure.traversal.TraversalStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static cz.auderis.structure.trie.TrieMatchers.hasDepth;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class BasicTrieTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 3, 5})
    void shouldInsertStrings(int count) throws Exception {
        // Given
        final String[] seq = new String[count];
        for (int i=1; i<=seq.length; ++i) {
            seq[i-1] = String.valueOf(i);
        }

        // When
        final BasicTrie<String, Void> trie = BasicTrie.newSortedInstance();
        final TrieNode<String> insertedNode = trie.registerSequence(seq);
        final TrieNode<String> foundNode = trie.findLongestPrefixOfSequence(seq);

        // Then
        assertThat(trie, hasDepth(seq.length));
        assertThat(foundNode, is(insertedNode));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    void shouldInsertMultipleStrings(int count) throws Exception {
        // Given
        final String[] seq = new String[count];
        for (int i=1; i<=seq.length; ++i) {
            seq[i-1] = String.valueOf(i);
        }

        // When
        final BasicTrie<String, Void> trie = BasicTrie.newSortedInstance();
        final TrieNode<String> insertedNode1 = trie.registerSequence(seq);
        seq[count-1] = "last string";
        final TrieNode<String> insertedNode2 = trie.registerSequence(seq);

        // Then
        assertThat(trie, hasDepth(seq.length));
    }

    @Test
    void shouldTraverseBFS() throws Exception {
        // Given
        final BasicTrie<String, Void> trie = BasicTrie.newSortedInstance();
        trie.registerSequence("abcdef".split(""));
        trie.registerSequence("abxyz".split(""));
        trie.registerSequence("klmn".split(""));
        final List<String> collectedStrings = new ArrayList<>();

        // When
        trie.visit(TraversalStrategy.BREADTH_FIRST_SEARCH, ((node, context) -> collectedStrings.add(node.getValue())));

        // Then
        assertThat(collectedStrings, hasSize(14));
        assertThat(collectedStrings.subList(1, 14), contains("akblcxmdynezf".split("")));
    }

    @Test
    void shouldTraverseTopDownDFS() throws Exception {
        // Given
        final BasicTrie<String, Void> trie = BasicTrie.newSortedInstance();
        trie.registerSequence("abcdef".split(""));
        trie.registerSequence("abxyz".split(""));
        trie.registerSequence("klmn".split(""));
        final List<String> collectedStrings = new ArrayList<>();

        // When
        trie.visit(TraversalStrategy.DEPTH_FIRST_SEARCH_TOP_DOWN, ((node, context) -> collectedStrings.add(node.getValue())));

        // Then
        assertThat(collectedStrings, hasSize(14));
        assertThat(collectedStrings.subList(1, 14), contains("klmnabxyzcdef".split("")));
    }

    @Test
    void shouldTraverseBottomUpDFS() throws Exception {
        // Given
        final BasicTrie<String, Void> trie = BasicTrie.newSortedInstance();
        trie.registerSequence("abcdef".split(""));
        trie.registerSequence("abxyz".split(""));
        trie.registerSequence("klmn".split(""));
        final List<String> collectedStrings = new ArrayList<>();

        // When
        trie.visit(TraversalStrategy.DEPTH_FIRST_SEARCH_BOTTOM_UP, ((node, context) -> collectedStrings.add(node.getValue())));

        // Then
        assertThat(collectedStrings, hasSize(14));
        assertThat(collectedStrings.subList(0, 13), contains("nmlkzyxfedcba".split("")));
    }

}
