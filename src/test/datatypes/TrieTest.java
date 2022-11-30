package test.datatypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import datatypes.Trie;

/**
 * TrieTest: Per fer el testing de la classe Trie
 * @author Èric Ryhr
 */
public class TrieTest {
    @Test
    public void testInsertFind() {
        Trie trie = new Trie();

        assertFalse("El trie no ha trobat la paraula", trie.FindDoc(""));
        trie.InsertDoc("");
        assertTrue("El trie ha trobat la paraula", trie.FindDoc(""));
        
        trie.InsertDoc("patata");
        trie.InsertDoc("Cogombre");
        assertFalse("El trie no ha trobat la paraula", trie.FindDoc("cogombre"));
        assertTrue("El trie ha trobat la paraula", trie.FindDoc("patata"));
        assertFalse("El trie no ha trobat la paraula", trie.FindDoc("Patata"));
        assertFalse("El trie no ha trobat la paraula", trie.FindDoc("patatá"));
        
        trie.InsertDoc("patatá");
        trie.InsertDoc("Patata");
        assertTrue("El trie ha trobat la paraula", trie.FindDoc("patata"));
        assertTrue("El trie ha trobat la paraula", trie.FindDoc("Patata"));
        assertTrue("El trie ha trobat la paraula", trie.FindDoc("patatá"));
    }
    
    @Test
    public void testDelete() {
        Trie trie = new Trie();

        trie.InsertDoc("patata");
        trie.InsertDoc("patatal");
        trie.DeleteDoc("patata");
        assertFalse("El trie no ha trobat la paraula", trie.FindDoc("patata"));
        assertTrue("El trie ha trobat la paraula", trie.FindDoc("patatal"));
    }

    @Test
    public void testSearchWordsPrefix() {
        Trie trie = new Trie();
        List<String> expected1 = Arrays.asList(new String[]{"manol", "manoló", "manolo"});
        List<String> expected2 = Arrays.asList(new String[]{"Manoló", "Manolo"});
        int expectedSize3 = 6;

        trie.InsertDoc("manolo");
        trie.InsertDoc("manol");
        trie.InsertDoc("Manolo");
        trie.InsertDoc("Manoló");
        trie.InsertDoc("manoló");
        trie.InsertDoc("patata");

        assertEquals(expected1, trie.SearchWordsPrefix("man"));
        assertEquals(expected2, trie.SearchWordsPrefix("Man"));
        assertEquals(expectedSize3, trie.SearchWordsPrefix("").size());

        List<String> expectedd1 = Arrays.asList(new String[]{"manol", "manoló"});
        List<String> expectedd2 = Arrays.asList(new String[]{"Manoló"});
        int expecteddSize3 = 4;

        trie.DeleteDoc("manolo");
        trie.DeleteDoc("Manolo");

        assertEquals(expectedd1, trie.SearchWordsPrefix("man"));
        assertEquals(expectedd2, trie.SearchWordsPrefix("Man"));
        assertEquals(expecteddSize3, trie.SearchWordsPrefix("").size());
    }
}
