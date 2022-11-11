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
    public void testDelete() {
        Trie trie = new Trie();

        trie.Insert("patata");
        trie.Insert("patatal");
        trie.Delete("patata");
        assertFalse("El trie no ha trobat la paraula", trie.Find("patata"));
        assertTrue("El trie ha trobat la paraula", trie.Find("patatal"));
    }

    @Test
    public void testFind() {
        Trie trie = new Trie();

        trie.Insert("patata");
        assertFalse("El trie ha trobat la paraula", trie.Find("cogombre"));
        assertTrue("El trie ha trobat la paraula", trie.Find("patata"));
        assertFalse("El trie no ha trobat la paraula", trie.Find("Patata"));
        assertFalse("El trie no ha trobat la paraula", trie.Find("patatá"));
    }

    @Test
    public void testInsert() {
        Trie trie = new Trie();

        trie.Insert("patata");
        trie.Insert("Patata");
        trie.Insert("patatá");
        assertTrue("El trie ha trobat la paraula", trie.Find("patata"));
        assertTrue("El trie ha trobat la paraula", trie.Find("Patata"));
        assertTrue("El trie ha trobat la paraula", trie.Find("patatá"));
    }

    @Test
    public void testSearchWordsPrefix() {
        Trie trie = new Trie();
        List<String> expected1 = Arrays.asList(new String[]{"manol", "manoló", "manolo"});
        List<String> expected2 = Arrays.asList(new String[]{"Manolo"});

        trie.Insert("manolo");
        trie.Insert("manol");
        trie.Insert("Manolo");
        trie.Insert("manoló");
        trie.Insert("patata");

        List<String> obtained1 = trie.SearchWordsPrefix("man");
        List<String> obtained2 = trie.SearchWordsPrefix("Man");

        assertEquals(expected1, obtained1);
        assertEquals(expected2, obtained2);
    }
}
