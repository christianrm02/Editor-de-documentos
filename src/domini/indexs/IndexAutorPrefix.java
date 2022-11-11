package indexs;

import java.util.List;

import datatypes.Trie;

public class IndexAutorPrefix {

    Trie index;

    public IndexAutorPrefix() {
        index = new Trie();
    }
    public IndexAutorPrefix(Trie trie) {
        index = trie;
    }

    public void InsertAutor(String autor) {
        index.Insert(autor);
    }

    public boolean FindAutor(String autor) {
        return index.Find(autor);
    }

    public List<String> GetAutorsPrefix(String prefix) {
        return index.SearchWordsPrefix(prefix);
    }

    public void DeleteAutor(String autor) {
        index.Delete(autor);
    }
    
}
