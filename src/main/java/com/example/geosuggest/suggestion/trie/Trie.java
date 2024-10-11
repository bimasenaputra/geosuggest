package com.example.geosuggest.suggestion.trie;

import java.util.*;

public class Trie {

    private Map<Character, Trie> children; // To store child nodes
    private boolean isWord; // Flag to mark if the node corresponds to the end of a word

    // Constructor
    public Trie() {
        this.children = new HashMap<>();
        this.isWord = false;
    }

    /**
     * Inserts a word into the trie along with optional metadata.
     *
     * @param word     The word to insert
     */
    public void insert(String word) {
        Trie current = this; // Start from the root of the trie
        for (char c : word.toCharArray()) { // Iterate over each character in the word
            // If the character is not in the current node's children, create a new node
            current.children.putIfAbsent(c, new Trie());
            // Move to the next node
            current = current.children.get(c);
        }
        // Mark the end of the word
        current.isWord = true;
    }

    /**
     * Retrieves all words in the trie that start with the given prefix.
     *
     * @param prefix The prefix to search for
     * @return A list of words that start with the prefix
     */
    public List<String> getLettersStartingWith(String prefix) {
        Trie current = this;
        // Traverse down the trie based on the prefix
        for (char c : prefix.toCharArray()) {
            // If the character is not in the current node, return an empty list (no match)
            if (!current.children.containsKey(c)) {
                return Collections.emptyList();
            }
            current = current.children.get(c);
        }
        // Collect all words starting from this node
        List<String> result = new ArrayList<>();
        collectAllWords(current, new StringBuilder(prefix), result);
        return result;
    }

    // Helper method to collect all words starting from a given node
    private void collectAllWords(Trie node, StringBuilder prefix, List<String> result) {
        if (node.isWord) {
            result.add(prefix.toString());
        }
        // Recur for all children
        for (Map.Entry<Character, Trie> entry : node.children.entrySet()) {
            prefix.append(entry.getKey()); // Add the child character to the prefix
            collectAllWords(entry.getValue(), prefix, result); // Recur to the child
            prefix.deleteCharAt(prefix.length() - 1); // Backtrack by removing the last character
        }
    }
}