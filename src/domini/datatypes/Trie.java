package domini.datatypes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import transversal.Pair;

/**
 * Trie: Estructura de dades per emmagatzemar documents
 * Implementació bàsica del trie extreta de https://www.baeldung.com/trie-java
 * @author Èric Ryhr
 */
public class Trie implements Serializable{
    
    /**
     * Node arrel del Trie
     */
    private TrieNode root;

    /**
     * TrieNode: Estructura de dades pels nodes del Trie
     */
    private class TrieNode implements Serializable{

        /**
         * Punter als fills d'aquest node
         */
        HashMap<Character, TrieNode> children;

        /**
         * Indica si un node es final d'alguna paraula
         */
        boolean isEndWord = false;

        /**
         * Set amb els títols de l'autor que té el final de paraula en aquest node
         */
        Set<String> titols;
        
        /**
         * Constructora del TrieNode
         */
        TrieNode() {
            children = new HashMap<>();
            titols = new HashSet<>();
        }
    }

    /**
     * Constructora del Trie
     */
    public Trie() {
        root = new TrieNode();
    }

    
    /** 
     * Mètode per insertar documents al Trie
     * @param autor - Autor del document
     * @param titol - Títol del document
     */
    public void AfegirDoc(String autor, String titol) {
        TrieNode current = root;
    
        for (char l: autor.toCharArray()) {
            current = current.children.computeIfAbsent(l, c -> new TrieNode());
        }
        current.isEndWord = true;
        current.titols.add(titol);
    }

    
    /** 
     * Mètode per buscar documents al Trie
     * @param autor - Autor del document a buscar
     * @param titol - Títol del document a buscar
     * @return boolean - True si el document es troba al Trie, False si no
     */
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

    
    /** 
     * Mètode per esborrar documents del Trie
     * @param autor - Autor del document
     * @param titol - Títol del document
     */
    public void EsborrarDoc(String autor, String titol) {
        delete(root, autor, titol, 0);
    }

    
    /** 
     * Mètode per actualitzar el títol d'un document del Trie
     * @param autor - Autor del document
     * @param titol - Títol del document
     * @param newTitol - Nou títol del document
     */
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

    
    /** 
     * Mètode per actualitzar l'autor d'un document del Trie
     * @param autor - Autor del document
     * @param titol - Títol del document
     * @param newAutor - Nou autor del document
     */
    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        EsborrarDoc(autor, titol);
        AfegirDoc(newAutor, titol);
    }

    
    /** 
     * Mètode per obtenir tots els documents del Trie
     * @return Set<Pair<String, String>> - Set amb tots els parells (autor, títol) del Trie
     */
    public Set<Pair<String, String>> GetKeys() {
        Set<Pair<String, String>> keys = new HashSet<>();
        StringBuilder s = new StringBuilder();
        getKeys(root, 0, s, keys);
        return keys;
    }

    
    /** 
     * Mètode per obtenir tots els títols d'un autor
     * @param autor - Autor del que s'obtenen els títols
     * @return Set<String> - Set amb els títols de l'autor. Buit si no existeix
     */
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

    
    /** 
     * Mètode per obtenir els autors que comencen per prefix
     * @param prefix - Prefix pel que han de començar els noms dels autors
     * @return Set<String> - Set amb els autors obtinguts
     */
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
    
    
    /** 
     * Mètode per esborrar nodes del Trie de forma recursiva
     * @param current - Node actual
     * @param autor - Autor del document que estem esborrant del Trie
     * @param titol - Títol del document que estem esborrant del Trie
     * @param index - Profunditat actual del node current
     * @return boolean - True si s'hauria d'esborrar el node current, False si no
     */
    private boolean delete(TrieNode current, String autor, String titol, int index) {
        //Quan arribem a l'ultim node
        if (index == autor.length()) {
            current.titols.remove(titol);
            if (!current.isEndWord) {
                return false;
            }
            if(current.titols.isEmpty()) current.isEndWord = false;
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

    
    /** 
     * Mètode per obtenir els autors que comencen per prefix fent una cerca en profunditat
     * @param node - Node actual
     * @param level - Profunditat actual del node current
     * @param prefix - Prefix pel que han de començar els noms dels autors
     * @param words - Set amb els autors ja obtinguts
     */
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

    
    /** 
     * Mètode per obtenir els documents des del node paràmetre fent una cerca en profunditat
     * @param node - Node actual
     * @param level - Profunditat actual del node current
     * @param autor - Identificador d'autor del node actual
     * @param words - Set amb els documents ja obtinguts
     */
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