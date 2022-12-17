package test.datatypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import domini.datatypes.Trie;

/**
 * TrieTest: Per fer el testing de la classe Trie
 * @author Èric Ryhr
 */
public class TrieTest {
    @Test
    public void testAfegeixFind() {
        Trie trie = new Trie();

        String autor = "Manolo";
        String titol1 = "Manolo va a la playa";
        String titol2 = "Manolo va a la montaña";

        assertFalse("No trobat 1", trie.FindDoc(autor, titol1));
        assertFalse("No trobat 2", trie.FindDoc(autor, titol2));

        trie.AfegirDoc(autor, titol1);
        trie.AfegirDoc(autor, titol2);

        assertTrue("Trobat 1", trie.FindDoc(autor, titol1));
        assertTrue("Trobat 2", trie.FindDoc(autor, titol2));
    }

    @Test
    public void testDelete() {
        Trie trie = new Trie();

        String autor = "Manolo";
        String titol1 = "Manolo va a la playa";
        String titol2 = "Manolo va a la montaña";

        assertFalse("No trobat 1", trie.FindDoc(autor, titol1));
        assertFalse("No trobat 2", trie.FindDoc(autor, titol2));

        trie.AfegirDoc(autor, titol1);
        trie.AfegirDoc(autor, titol2);

        assertTrue("Trobat 1", trie.FindDoc(autor, titol1));
        assertTrue("Trobat 2", trie.FindDoc(autor, titol2));

        trie.EsborrarDoc(autor, titol1);

        assertFalse("No trobat 1", trie.FindDoc(autor, titol1));
        
        trie.EsborrarDoc(autor, titol2);

        assertFalse("No trobat 2", trie.FindDoc(autor, titol2));
    }

    @Test
    public void testActualitzarAutor() {
        Trie trie = new Trie();

        String autor = "a";
        String autor2 = "Po";
        String titol1 = "a";
        String titol2 = "b";

        trie.AfegirDoc(autor, titol1);
        trie.AfegirDoc(autor, titol2);

        Set<String> expected1 = new HashSet<String>(Arrays.asList(titol1, titol2));
        Set<String> res1 = trie.GetTitolsAutor(autor);
        assertEquals(expected1, res1);

        trie.ActualitzarAutor(autor, titol2, autor2);

        assertTrue("Trobat 1", trie.FindDoc(autor, titol1));
        assertTrue("Trobat 2", trie.FindDoc(autor2, titol2));
        assertFalse("No trobat 2", trie.FindDoc(autor, titol2));

        Set<String> expected2 = new HashSet<String>(Arrays.asList(titol1));
        Set<String> res2 = trie.GetTitolsAutor(autor);
        assertEquals(expected2, res2);
    }
}
