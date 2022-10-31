package indexs;

import java.util.List;

public class IndexAutorPrefix {

    Trie index;

    public IndexAutorPrefix() {
        index = new Trie();
    }

    public void InsertAutor(String autor) {
        index.Insert(autor);
    }

    public List<String> GetAutorsPrefix(String prefix) {
        return index.SearchWordsPrefix(prefix);
    }

    public void DeleteAutor(String autor) {
        index.Delete(autor);
    }
    
}
