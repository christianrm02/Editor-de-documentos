package datatypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Trie {
    
    private TrieNode root;
    private int maxLength = 0;    

    public Trie() {
        root = new TrieNode('\0');
    }

    //Insertar paraula al trie
    public void Insert(String word) {
        TrieNode current = root;
    
        for (char l: word.toCharArray()) {
            current = current.children.computeIfAbsent(l, c -> new TrieNode(l));
        }
        current.isEndWord = true;

        if(maxLength < word.length()) maxLength = word.length();
    }

    //Retorna true si word es al trie
    public boolean Find(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.isEndWord;
    }

    //Retorna les paraules del trie que comencen per prefix
    public List<String> SearchWordsPrefix(String prefix) {   
        List<String> words = new ArrayList<String>();
        
        TrieNode current = root;
        //Fem un recorregut fins al node on acaba el prefix
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            TrieNode node = current.children.get(ch);
            //Si no hi ha cap paraula que comenci per prefix retornem una llista buida
            if (node == null) {
                return words;
            }
            current = node;
        }

        StringBuilder builder = new StringBuilder(prefix);
        getWords(current, prefix.length(), builder, words);

        return words;
    }

    //Esborra una paraula del trie
    public void Delete(String word) {
        delete(root, word, 0);
    }
    
    //Recursivament esborra caracters del trie
    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndWord) {
                return false;
            }
            current.isEndWord = false;
            return current.children.isEmpty();
        }
        char ch = word.charAt(index);
        TrieNode node = current.children.get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(node, word, index + 1) && !node.isEndWord;
    
        if (shouldDeleteCurrentNode) {
            current.children.remove(ch);
            return current.children.isEmpty();
        }
        return false;
    }

    //Retorna a la llista words recursivament les paraules que troba des del node rootNode amb el prefix
    private void getWords(TrieNode node, int level, StringBuilder prefix, List<String> words) {

        if(node.isEndWord){
            words.add(prefix.toString());
        }

        HashMap<Character, TrieNode> children = node.children;
        Iterator<Character> iterator = children.keySet().iterator();
        while (iterator.hasNext()) {
            char character = iterator.next();
            prefix = prefix.insert(level, character); 
            getWords(children.get(character), level+1, prefix, words);
            prefix.deleteCharAt(level);
        }
    }

}

class TrieNode {

    HashMap<Character, TrieNode> children;
    char data;
    boolean isEndWord;

    TrieNode(char data) {
        children = new HashMap<>();
        this.data = data;
    }

}