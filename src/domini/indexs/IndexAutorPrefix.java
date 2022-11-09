package indexs;

import java.util.List;

import datatypes.Trie;
import datatypes.Utility;

public class IndexAutorPrefix {

    Trie index;

    public IndexAutorPrefix() {
        index = new Trie();
    }

    public void InsertAutor(String autor) {
        index.Insert(Utility.UTF8toASCII(autor));
    }

    public List<String> GetAutorsPrefix(String prefix) {
        return index.SearchWordsPrefix(prefix);
    }

    public void DeleteAutor(String autor) {
        index.Delete(autor);
    }
    
}
