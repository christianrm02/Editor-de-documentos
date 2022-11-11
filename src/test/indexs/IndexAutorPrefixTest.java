package indexs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import datatypes.TrieStub;
public class IndexAutorPrefixTest {
    @Test
    public void testDeleteAutor() {
        TrieStub trie = new TrieStub();
        trie.Insert("patata");
        trie.Insert("patatal");

        IndexAutorPrefix index = new IndexAutorPrefix(trie);
        index.DeleteAutor("patata");
        assertFalse("L'índex no ha trobat la paraula", trie.Find("patata"));
        assertTrue("L'índex ha trobat la paraula", trie.Find("patatal"));
    }

    @Test
    public void testFindAutor() {
        IndexAutorPrefix index = new IndexAutorPrefix();

        index.InsertAutor("patata");
        assertFalse("El index ha trobat la paraula", index.FindAutor("cogombre"));
        assertTrue("El index ha trobat la paraula", index.FindAutor("patata"));
        assertFalse("El index no ha trobat la paraula", index.FindAutor("Patata"));
        assertFalse("El index no ha trobat la paraula", index.FindAutor("patatá"));
    }

    @Test
    public void testGetAutorsPrefix() {
        IndexAutorPrefix index = new IndexAutorPrefix();
        String prefix = "man";
        String[] expectedArray = {"manol", "manoló", "manolo"};
        List<String> expected = Arrays.asList(expectedArray);

        index.InsertAutor("manolo");
        index.InsertAutor("manol");
        index.InsertAutor("Manolo");
        index.InsertAutor("manoló");
        index.InsertAutor("patata");

        List<String> obtained = index.GetAutorsPrefix(prefix);

        assertEquals(expected, obtained);
    }

    @Test
    public void testInsertAutor() {
        IndexAutorPrefix index = new IndexAutorPrefix();

        index.InsertAutor("patata");
        index.InsertAutor("Patata");
        index.InsertAutor("patatá");
        assertTrue("El index ha trobat la paraula", index.FindAutor("patata"));
        assertTrue("El index ha trobat la paraula", index.FindAutor("Patata"));
        assertTrue("El index ha trobat la paraula", index.FindAutor("patatá"));
    }
}
