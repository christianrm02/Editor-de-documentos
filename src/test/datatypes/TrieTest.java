package datatypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TrieTest {
    @Test
    public void testDelete() {

    }

    @Test
    public void testFind() {

    }

    @Test
    public void testInsert() {
        Trie trie = new Trie();
        trie.Insert("patata");
        assertTrue("El trie ha trobat la paraula", trie.Find("patata"));
        assertFalse("El trie no ha trobat la paraula", trie.Find("pesol"));
    }

    @Test
    public void testSearchWordsPrefix() {

    }
}
