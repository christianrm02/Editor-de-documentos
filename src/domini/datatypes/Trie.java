package datatypes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import transversal.Pair;

/**
 * Trie: Estructura de dades per emmagatzemar strings
 * Implementació bàsica del trie extreta de https://www.baeldung.com/trie-java
 * @author Èric Ryhr
 */
public class Trie implements Serializable{
    
    private TrieNode root;
    private int maxLength = 0;

    private class TrieNode {

        HashMap<Character, TrieNode> children;
        boolean isEndWord = false;
        Set<String> titols;
    
        TrieNode() {
            children = new HashMap<>();
            titols = new HashSet<>();
        }
    }

    public Trie() {
        root = new TrieNode();
    }

    //Insertar un nou document al trie
    public void AfegirDoc(String autor, String titol) {
        TrieNode current = root;
    
        for (char l: autor.toCharArray()) {
            current = current.children.computeIfAbsent(l, c -> new TrieNode());
        }
        current.isEndWord = true;
        current.titols.add(titol);

        if(maxLength < autor.length()) maxLength = autor.length();
    }

    //Retorna true si un document es al trie
    public boolean FindDoc(String autor, String titol) {
        TrieNode current = root;
        for (int i = 0; i < autor.length(); i++) {
            char ch = autor.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.titols.contains(titol);
    }

    //Esborra un document del trie. Si un autor es queda sense titols s'esborra tambe
    public void EsborrarDoc(String autor, String titol) {
        delete(root, autor, titol, 0);
    }

    //Actualitza el titol d'un document amb la precondicio de que existeixi
    public void ActualitzarTitol(String autor, String titol, String newTitol) {
        TrieNode current = root;
        for (int i = 0; i < autor.length(); i++) {
            char ch = autor.charAt(i);
            TrieNode node = current.children.get(ch);
            current = node;
        }
        current.titols.remove(titol);
        current.titols.add(newTitol);
    }

    //Actualitza l'autor d'un document amb la precondicio de que existeixi
    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        EsborrarDoc(autor, titol);
        AfegirDoc(newAutor, titol);
    }

    public Set<Pair<String, String>> GetKeys() {
        Set<Pair<String, String>> keys = new HashSet<>();
        StringBuilder s = new StringBuilder();
        getKeys(root, maxLength, s, keys);
        return keys;
    }

    //Retorna els titols d'un autor o un set buit si no existeix l'autor
    public Set<String> GetTitolsAutor(String autor) {
        TrieNode current = root;
        for (int i = 0; i < autor.length(); i++) {
            char ch = autor.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return new HashSet<String>();
            }
            current = node;
        }
        return current.titols;
    }

    //Retorna les paraules del trie que comencen per prefix
    public Set<String> SearchWordsPrefix(String prefix) {   
        Set<String> words = new HashSet<String>();
        
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
    
    //Recursivament esborra nodes del trie
    private boolean delete(TrieNode current, String autor, String titol, int index) {
        //Quan arribem a l'ultim node
        if (index == autor.length()) {
            current.titols.remove(titol);
            if (!current.isEndWord) {
                return false;
            }
            current.isEndWord = false;
            return current.children.isEmpty() && current.titols.isEmpty();
        }

        char ch = autor.charAt(index);
        TrieNode node = current.children.get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(node, autor, titol, index + 1) && !node.isEndWord;
    
        if (shouldDeleteCurrentNode) {
            current.children.remove(ch);
            return current.children.isEmpty();
        }
        return false;
    }

    //Retorna al set words recursivament les paraules que troba des del node rootNode amb el prefix
    private void getWords(TrieNode node, int level, StringBuilder prefix, Set<String> words) {

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

    //Retorna al set keys recursivament les keys autor+titol que troba des del node root
    private void getKeys(TrieNode node, int level, StringBuilder autor, Set<Pair<String,String>> keys) {

        if(node.isEndWord){
            for (String titol : node.titols) {
                Pair<String, String> key = new Pair<>(autor.toString(), titol);
                keys.add(key);
            }
        }

        HashMap<Character, TrieNode> children = node.children;
        Iterator<Character> iterator = children.keySet().iterator();
        while (iterator.hasNext()) {
            char character = iterator.next();
            autor = autor.insert(level, character); 
            getKeys(children.get(character), level+1, autor, keys);
            autor.deleteCharAt(level);
        }
    }
}