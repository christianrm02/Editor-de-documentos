package datatypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * TrieTest: Per fer el testing de la classe Trie
 * @author Èric Ryhr
 */
public class TrieTest {
    @Test
    public void testInsertFind() {
        Trie trie = new Trie();

        assertFalse("El trie no ha trobat la paraula", trie.Find(""));
        trie.Insert("");
        assertTrue("El trie ha trobat la paraula", trie.Find(""));
        
        trie.Insert("patata");
        trie.Insert("Cogombre");
        assertFalse("El trie no ha trobat la paraula", trie.Find("cogombre"));
        assertTrue("El trie ha trobat la paraula", trie.Find("patata"));
        assertFalse("El trie no ha trobat la paraula", trie.Find("Patata"));
        assertFalse("El trie no ha trobat la paraula", trie.Find("patatá"));
        
        trie.Insert("patatá");
        trie.Insert("Patata");
        assertTrue("El trie ha trobat la paraula", trie.Find("patata"));
        assertTrue("El trie ha trobat la paraula", trie.Find("Patata"));
        assertTrue("El trie ha trobat la paraula", trie.Find("patatá"));
    }
    
    @Test
    public void testDelete() {
        Trie trie = new Trie();

        trie.Insert("patata");
        trie.Insert("patatal");
        trie.Delete("patata");
        assertFalse("El trie no ha trobat la paraula", trie.Find("patata"));
        assertTrue("El trie ha trobat la paraula", trie.Find("patatal"));
    }

    @Test
    public void testSearchWordsPrefix() {
        Trie trie = new Trie();
        List<String> expected1 = Arrays.asList(new String[]{"manol", "manoló", "manolo"});
        List<String> expected2 = Arrays.asList(new String[]{"Manoló", "Manolo"});
        int expectedSize3 = 6;

        trie.Insert("manolo");
        trie.Insert("manol");
        trie.Insert("Manolo");
        trie.Insert("Manoló");
        trie.Insert("manoló");
        trie.Insert("patata");

        assertEquals(expected1, trie.SearchWordsPrefix("man"));
        assertEquals(expected2, trie.SearchWordsPrefix("Man"));
        assertEquals(expectedSize3, trie.SearchWordsPrefix("").size());

        List<String> expectedd1 = Arrays.asList(new String[]{"manol", "manoló"});
        List<String> expectedd2 = Arrays.asList(new String[]{"Manoló"});
        int expecteddSize3 = 4;

        trie.Delete("manolo");
        trie.Delete("Manolo");

        assertEquals(expectedd1, trie.SearchWordsPrefix("man"));
        assertEquals(expectedd2, trie.SearchWordsPrefix("Man"));
        assertEquals(expecteddSize3, trie.SearchWordsPrefix("").size());
    }
}
