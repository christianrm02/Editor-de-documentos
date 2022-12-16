package test.indexs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import domini.indexs.IndexExpBooleana;
import transversal.Pair;

/**
 * IndexExpBooleanaTest: Per fer el testing de la classe IndexExpBooleana
 * @author Èric Ryhr
 */
public class IndexExpBooleanaTest {
    @Test
    public void testAfegirDoc() {
        IndexExpBooleana index = new IndexExpBooleana();
        String autor0 = "Null";
        String titol0 = "NullTitle";
        String autor1 = "Rick1";
        String titol1 = "NGGYU1";
        String autor2 = "Rick2";
        String titol2 = "NGGYU2";
        String autor3 = "Rick3";
        String titol3 = "NGGYU3";

        List<String> contingutbuit = new ArrayList<String>();
        List<String> contingut1 = getTestString(1);
        List<String> contingut2 = getTestString(2);
        List<String> contingut3 = getTestString(3);
        index.AfegirDoc(autor0, titol0, contingutbuit);
        index.AfegirDoc(autor1, titol1, contingut1);
        index.AfegirDoc(autor2, titol2, contingut2);
        index.AfegirDoc(autor3, titol3, contingut3);
        Set<Integer> expectedYou = new HashSet<Integer>(Arrays.asList(1, 3));
        Set<Integer> expectedWouldnt = new HashSet<Integer>(Arrays.asList(3));
        Set<Integer> expectedNever = new HashSet<Integer>(Arrays.asList(6, 7, 8, 9, 10, 11));

        assertEquals(expectedYou, index.GetFrases("You"));
        assertEquals(expectedWouldnt, index.GetFrases("wouldn't"));
        assertEquals(expectedNever, index.GetFrases("Never"));
    }
    
    @Test
    public void testEsborrarDoc() {
        IndexExpBooleana index = new IndexExpBooleana();
        String autor1 = "Rick1";
        String titol1 = "NGGYU1";
        String autor2 = "Rick2";
        String titol2 = "NGGYU2";
        String autor3 = "Rick3";
        String titol3 = "NGGYU3";

        List<String> contingut1 = getTestString(1);
        List<String> contingut2 = getTestString(2);
        List<String> contingut3 = getTestString(3);
        index.AfegirDoc(autor1, titol1, contingut1);
        index.AfegirDoc(autor2, titol2, contingut2);
        index.AfegirDoc(autor3, titol3, contingut3);
        Set<Integer> expectedYou = new HashSet<Integer>(Arrays.asList(0, 1));
        Set<Integer> expectedWouldnt = new HashSet<Integer>(Arrays.asList(3));
        Set<Integer> expectedNever = new HashSet<Integer>(Arrays.asList(6, 7, 8, 9, 10, 11));
        index.EsborrarDoc(autor1, titol1);
        index.EsborrarDoc(autor3, titol3);

        assertEquals(expectedYou, index.GetFrases("you"));
        assertNotEquals(expectedWouldnt, index.GetFrases("wouldn't"));
        assertNotEquals(expectedNever, index.GetFrases("Never"));
    }

    @Test
    public void testGetNumFrases() {
        IndexExpBooleana index = new IndexExpBooleana();
        String autor1 = "Rick1";
        String titol1 = "NGGYU1";
        String autor2 = "Rick2";
        String titol2 = "NGGYU2";
        String autor3 = "Rick3";
        String titol3 = "NGGYU3";

        List<String> contingut1 = getTestString(1);
        List<String> contingut2 = getTestString(2);
        List<String> contingut3 = getTestString(3);
        index.AfegirDoc(autor1, titol1, contingut1);
        index.AfegirDoc(autor2, titol2, contingut2);
        index.AfegirDoc(autor3, titol3, contingut3);

        assertEquals(12, index.GetNumFrases());
        
        index.EsborrarDoc(autor2, titol2);

        assertEquals(10, index.GetNumFrases());

        index.ActualitzarContingut(autor3, titol3, contingut2);

        assertEquals(6, index.GetNumFrases());
    }

    @Test
    public void testGetDocuments() {
        IndexExpBooleana index = new IndexExpBooleana();
        String autor1 = "Rick1";
        String titol1 = "NGGYU1";
        String autor2 = "Rick2";
        String titol2 = "NGGYU2";
        String autor3 = "Rick3";
        String titol3 = "NGGYU3";
        Pair<String, String> pair1 = new Pair<>(autor1, titol1);
        Pair<String, String> pair2 = new Pair<>(autor2, titol2);
        Pair<String, String> pair3 = new Pair<>(autor3, titol3);
        List<String> contingut1 = getTestString(1);
        List<String> contingut2 = getTestString(2);
        List<String> contingut3 = getTestString(3);
        index.AfegirDoc(autor1, titol1, contingut1);
        index.AfegirDoc(autor2, titol2, contingut2);
        index.AfegirDoc(autor3, titol3, contingut3);
        
        Set<Integer> indexs1 = new HashSet<Integer>(Arrays.asList(2, 3, 4));
        Set<Integer> indexs2 = new HashSet<Integer>(Arrays.asList(4, 6, 10));
        Set<Integer> indexs3 = new HashSet<Integer>(Arrays.asList(8, 11));
        List<Pair<String, String>> expected1 = Arrays.asList(pair1, pair2);
        List<Pair<String, String>> expected2 = Arrays.asList(pair2, pair3);
        List<Pair<String, String>> expected3 = Arrays.asList(pair3);

        assertEquals(expected1, index.GetDocuments(indexs1));
        assertEquals(expected2, index.GetDocuments(indexs2));
        assertEquals(expected3, index.GetDocuments(indexs3));
    }

    @Test
    public void testGetSequencia() {
        IndexExpBooleana index = new IndexExpBooleana();
        String autor1 = "Rick1";
        String titol1 = "NGGYU1";
        String autor2 = "Rick2";
        String titol2 = "NGGYU2";
        String autor3 = "Rick3";
        String titol3 = "NGGYU3";

        List<String> contingut1 = getTestString(1);
        List<String> contingut2 = getTestString(2);
        List<String> contingut3 = getTestString(3);
        index.AfegirDoc(autor1, titol1, contingut1);
        index.AfegirDoc(autor2, titol2, contingut2);
        index.AfegirDoc(autor3, titol3, contingut3);
        Set<Integer> candidatsbuit = new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        Set<Integer> candidats1 = new HashSet<Integer>(Arrays.asList(5, 7, 9));
        Set<Integer> candidats2 = new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        Set<Integer> candidats3 = new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        Set<Integer> expectedbuit = new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        Set<Integer> expected1 = new HashSet<Integer>(Arrays.asList(5, 9));
        Set<Integer> expected2 = new HashSet<Integer>(Arrays.asList());
        Set<Integer> expected3 = new HashSet<Integer>(Arrays.asList(6, 7, 8, 9, 10, 11));

        assertEquals(expectedbuit, index.GetSequencia("", candidatsbuit));
        assertEquals(expected1, index.GetSequencia("make you", candidats1));
        assertEquals(expected2, index.GetSequencia("Rick Astley", candidats2));
        assertEquals(expected3, index.GetSequencia("Never gonna", candidats3));
    }

    @Test
    public void testActualitzarAutor() {
        IndexExpBooleana index = new IndexExpBooleana();
        String autor1 = "Rick1";
        String titol1 = "NGGYU1";
        String autor2 = "Rick2";
        String titol2 = "NGGYU2";
        String autor3 = "Rick3";
        String titol3 = "NGGYU3";
        List<String> contingut1 = getTestString(1);
        List<String> contingut2 = getTestString(2);
        List<String> contingut3 = getTestString(3);
        index.AfegirDoc(autor1, titol1, contingut1);
        index.AfegirDoc(autor2, titol2, contingut2);
        index.AfegirDoc(autor3, titol3, contingut3);

        Pair<String, String> pair1 = new Pair<>("Rick Impostor", titol1);
        Pair<String, String> pair3 = new Pair<>(autor3, titol3);
        
        //Frases a buscar
        Set<Integer> test = new HashSet<Integer>(Arrays.asList(1, 10));
        List<Pair<String, String>> expected = Arrays.asList(pair3, pair1);
        index.ActualitzarAutor(autor1, titol1, "Rick Impostor");
        List<Pair<String, String>> actual = index.GetDocuments(test);
        assertEquals(expected, actual);
    }

    @Test
    public void testActualitzarTitol() {
        IndexExpBooleana index = new IndexExpBooleana();
        String autor1 = "Rick1";
        String titol1 = "NGGYU1";
        String autor2 = "Rick2";
        String titol2 = "NGGYU2";
        String autor3 = "Rick3";
        String titol3 = "NGGYU3";
        List<String> contingut1 = getTestString(1);
        List<String> contingut2 = getTestString(2);
        List<String> contingut3 = getTestString(3);
        index.AfegirDoc(autor1, titol1, contingut1);
        index.AfegirDoc(autor2, titol2, contingut2);
        index.AfegirDoc(autor3, titol3, contingut3);

        Pair<String, String> pair1 = new Pair<>(autor1, "NGGYU Impostor");
        Pair<String, String> pair3 = new Pair<>(autor3, titol3);
        
        //Frases a buscar
        Set<Integer> test = new HashSet<Integer>(Arrays.asList(1, 10));
        List<Pair<String, String>> expected = Arrays.asList(pair3, pair1);
        index.ActualitzarTitol(autor1, titol1, "NGGYU Impostor");
        List<Pair<String, String>> actual = index.GetDocuments(test);
        assertEquals(expected, actual);
    }

    @Test
    public void testActualitzarContingut() {
        IndexExpBooleana index = new IndexExpBooleana();
        String autor1 = "Rick1";
        String titol1 = "NGGYU1";
        String autor2 = "Rick2";
        String titol2 = "NGGYU2";
        List<String> contingut1 = getTestString(1);
        List<String> contingut2 = getTestString(2);
        List<String> contingut3 = getTestString(3);
        index.AfegirDoc(autor1, titol1, contingut1);
        index.AfegirDoc(autor2, titol2, contingut2);
        Set<Integer> expectedYou = new HashSet<Integer>(Arrays.asList(1, 3));
        Set<Integer> expectedWanna = new HashSet<Integer>(Arrays.asList());
        Set<Integer> expectedNever = new HashSet<Integer>(Arrays.asList(4, 5, 6, 7, 8, 9));
        
        assertEquals(6, index.GetNumFrases());
        assertNotEquals(expectedNever, index.GetFrases("Never"));
        
        index.ActualitzarContingut(autor2, titol2, contingut3);

        assertEquals(10, index.GetNumFrases());
        assertEquals(expectedYou, index.GetFrases("You"));
        assertEquals(expectedWanna, index.GetFrases("wanna"));
        assertEquals(expectedNever, index.GetFrases("Never"));
    }

    private String[] s1 = { "We're no strangers to love.\n",
                            "You know the rules and so do I (do I)!\n",
                            "A full commitment's what I'm thinking of\n",
                            "You wouldn't get this from any other guy?"};

    private String[] s2 = { "I just wanna tell you how I'm feeling\n",
                            "Gotta make you understand!"};

    private String[] s3 = { "Never gonna give you up\n",
                            "Never gonna let you down\n",
                            "Never gonna run around and desert you\n",
                            "Never gonna make you cry\n",
                            "Never gonna say goodbye\n",
                            "Never gonna tell a lie and hurt you"};

    private List<String> getTestString(int i) {
        switch (i) {
            case 1: return Arrays.asList(s1);
            case 2: return Arrays.asList(s2);
            case 3: return Arrays.asList(s3);
            default: return null;
        }

    }
}
