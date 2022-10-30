package indexs;

import java.util.List;

public class IndexAutorPrefix {

    Trie index;

    public IndexAutorPrefix() {
        index = new Trie();
    }

    public void insertAutor(String autor) {
        index.Insert(autor);
    }

    public List<String> getAutorsPrefix(String prefix) {
        return index.SearchWordsPrefix(prefix);
    }

    public void deleteAutor(String autor) {
        index.delete(autor);
    }
    
}
