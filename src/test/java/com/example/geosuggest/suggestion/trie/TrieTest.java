package com.example.geosuggest.suggestion.trie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {

    private Trie trie;

    @BeforeEach
    public void setUp() {
        trie = new Trie();
        trie.insert("apple");
        trie.insert("app");
        trie.insert("application");
        trie.insert("bat");
        trie.insert("batman");
        trie.insert("batch");
    }

    @Test
    public void testInsertAndSearchExactWord() {
        // Search for words that were inserted
        List<String> result1 = trie.getLettersStartingWith("app");
        assertTrue(result1.contains("app"));
        assertTrue(result1.contains("apple"));
        assertTrue(result1.contains("application"));
        assertEquals(3, result1.size());

        List<String> result2 = trie.getLettersStartingWith("bat");
        assertTrue(result2.contains("bat"));
        assertTrue(result2.contains("batman"));
        assertTrue(result2.contains("batch"));
        assertEquals(3, result2.size());
    }

    @Test
    public void testSearchNonExistentPrefix() {
        // Search for a prefix that does not exist in the Trie
        List<String> result = trie.getLettersStartingWith("cat");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testInsertAndSearchPartialPrefix() {
        // Test for a partial prefix "appl"
        List<String> result = trie.getLettersStartingWith("appl");
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("application"));
        assertEquals(2, result.size());
    }

    @Test
    public void testInsertAndSearchSingleLetter() {
        // Test for a single letter "b"
        List<String> result = trie.getLettersStartingWith("b");
        assertTrue(result.contains("bat"));
        assertTrue(result.contains("batman"));
        assertTrue(result.contains("batch"));
        assertEquals(3, result.size());
    }

    @Test
    public void testInsertAndSearchEmptyPrefix() {
        // Test for an empty prefix, should return all words
        List<String> result = trie.getLettersStartingWith("");
        assertTrue(result.contains("app"));
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("application"));
        assertTrue(result.contains("bat"));
        assertTrue(result.contains("batman"));
        assertTrue(result.contains("batch"));
        assertEquals(6, result.size());
    }

    @Test
    public void testInsertAndSearchNoWordsMatch() {
        // Test for a prefix that partially matches but no complete word
        List<String> result = trie.getLettersStartingWith("zoo");
        assertTrue(result.isEmpty());
    }
}
