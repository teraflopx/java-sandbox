package org.teraflopx.jsandboox.struct;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

// simple implementation for trie data structure (map-based)
public final class Trie {

  private Character key; // null for root
  private Map<Character, Trie> children = new HashMap<Character, Trie>();

  private boolean isWord = true; // by default everything is a word
  private boolean isLeaf = true; // by default everything is a leaf

  // builds a trie from a list of words
  public static Optional<Trie> build(Collection<String> words) {
    if (words == null || words.isEmpty())
      return Optional.empty();

    return Optional.of(new Trie(words));
  }

  // finds the trie corresponding to a certain word
  public Optional<Trie> find(String word) {
    if (word == null || word.isEmpty())
      return Optional.empty();

    Character c = Character.valueOf(word.charAt(0));

    Trie child = children.get(c);
    if (child == null)
      return Optional.empty();

    return (word.length() > 1) ? child.find(word.substring(1)) : Optional.of(child);
  }

  public Optional<Character> getKey() {
    return Optional.ofNullable(key);
  }

  public Map<Character, Trie> getChildren() {
    return Collections.unmodifiableMap(children);
  }

  public boolean isLeaf() {
    return isLeaf;
  }

  public boolean isWord() {
    return isWord;
  }

  private Trie(Collection<String> words) {
    words.stream().filter(Objects::nonNull).forEach(word -> put(word));
  }

  private Trie(Character key) {
    this.key = key;
  }

  private void put(String word) {
    Character c = Character.valueOf(word.charAt(0));

    Trie child = children.get(c);
    if (child == null) {
      child = new Trie(c);
      children.put(c, child);
    }
    isLeaf = false;

    if (word.length() > 1) {
      child.put(word.substring(1));
      isWord = false;
    }
  }

  @Override
  public String toString() {
    return key + "," + isLeaf + "," + isWord + "," + children.size();
  }
}

// TODO protect constructor againts empty strings ("")
// TODO add new attribute to store value (word) and parent
// TODO allow nodes to be added / removed
// TODO explore usage of array ([]) instead of map
// TODO explore evolution towards DAWG
// TODO leverage capabilities to auto-complete words (from a prefix)
// TODO override equals and hashcode
