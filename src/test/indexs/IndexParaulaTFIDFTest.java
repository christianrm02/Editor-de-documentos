package test.indexs;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import domini.indexs.IndexParaulaTFIDF;
import transversal.Pair;

/**
 * IndexParaulaTFIDFTest: Per fer el testing de la classe IndexParaulaTFIDF
 * @author Èric Ryhr
 */
public class IndexParaulaTFIDFTest {
    @Test
    public void testAfegirDoc() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();

        List<String> d1 = getTestString(1);
        List<String> d2 = getTestString(2);
        List<String> d3 = getTestString(3);
        List<String> d4 = getTestString(4);
        Pair<String, String> id1 = new Pair<>("RICK", "D1");
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        Pair<String, String> id4 = new Pair<>("RICK", "D4");

        assertFalse("El document no existeix", index.DocExists(id1));
        assertFalse("El document no existeix", index.DocExists(id2));
        assertFalse("El document no existeix", index.DocExists(id3));
        assertFalse("El document no existeix", index.DocExists(id4));

        index.AfegirDoc("RICK", "D1", d1);
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        index.AfegirDoc("RICK", "D4", d4);

        assertTrue("El document existeix", index.DocExists(id1));
        assertTrue("El document existeix", index.DocExists(id2));
        assertTrue("El document existeix", index.DocExists(id3));
        assertTrue("El document existeix", index.DocExists(id4));
    }

    @Test
    public void testEsborrarDoc() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();

        List<String> d1 = getTestString(1);
        List<String> d2 = getTestString(2);
        List<String> d3 = getTestString(3);
        List<String> d4 = getTestString(4);
        Pair<String, String> id1 = new Pair<>("RICK", "D1");
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        Pair<String, String> id4 = new Pair<>("RICK", "D4");

        index.AfegirDoc("RICK", "D1", d1);
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        index.AfegirDoc("RICK", "D4", d4);

        assertTrue("El document existeix", index.DocExists(id1));
        assertTrue("El document existeix", index.DocExists(id2));
        assertTrue("El document existeix", index.DocExists(id3));
        assertTrue("El document existeix", index.DocExists(id4));

        index.EsborrarDoc("RICK", "D1");
        index.EsborrarDoc("RICK", "D3");
        index.EsborrarDoc("RICK", "D4");

        assertFalse("El document no existeix", index.DocExists(id1));
        assertTrue("El document no existeix", index.DocExists(id2));
        assertFalse("El document no existeix", index.DocExists(id3));
        assertFalse("El document no existeix", index.DocExists(id4));
    }
    
    @Test
    public void testActualitzarAutor() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();

        List<String> d1 = getTestString(1);
        List<String> d2 = getTestString(2);
        List<String> d3 = getTestString(3);
        List<String> d4 = getTestString(4);
        Pair<String, String> id1 = new Pair<>("RICK", "D1");
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        Pair<String, String> id4 = new Pair<>("RICK", "D4");
        Pair<String, String> newid1 = new Pair<>("RICK IMPOSTOR1", "D1");
        Pair<String, String> newid2 = new Pair<>("RICK IMPOSTOR2", "D2");
        Pair<String, String> newid3 = new Pair<>("RICK IMPOSTOR3", "D3");
        Pair<String, String> newid4 = new Pair<>("RICK IMPOSTOR4", "D4");

        index.AfegirDoc("RICK", "D1", d1);
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        index.AfegirDoc("RICK", "D4", d4);
        index.ActualitzarAutor("RICK", "D1", "RICK IMPOSTOR1");
        index.ActualitzarAutor("RICK", "D2", "RICK IMPOSTOR2");
        index.ActualitzarAutor("RICK", "D4", "RICK IMPOSTOR4");

        assertFalse("El document no existeix", index.DocExists(id1));
        assertFalse("El document no existeix", index.DocExists(id2));
        assertFalse("El document no existeix", index.DocExists(newid3));
        assertFalse("El document no existeix", index.DocExists(id4));

        assertTrue("El document existeix", index.DocExists(newid1));
        assertTrue("El document existeix", index.DocExists(newid2));
        assertTrue("El document existeix", index.DocExists(id3));
        assertTrue("El document existeix", index.DocExists(newid4));
    }

    @Test
    public void testActualitzarTitol() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();

        List<String> d1 = getTestString(1);
        List<String> d2 = getTestString(2);
        List<String> d3 = getTestString(3);
        List<String> d4 = getTestString(4);
        Pair<String, String> id1 = new Pair<>("RICK", "D1");
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        Pair<String, String> id4 = new Pair<>("RICK", "D4");
        Pair<String, String> newid1 = new Pair<>("RICK", "NEWD1");
        Pair<String, String> newid2 = new Pair<>("RICK", "NEWD2");
        Pair<String, String> newid3 = new Pair<>("RICK", "NEWD3");
        Pair<String, String> newid4 = new Pair<>("RICK", "NEWD4");

        index.AfegirDoc("RICK", "D1", d1);
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        index.AfegirDoc("RICK", "D4", d4);
        index.ActualitzarTitol("RICK", "D1", "NEWD1");
        index.ActualitzarTitol("RICK", "D3", "NEWD3");
        index.ActualitzarTitol("RICK", "D4", "NEWD4");

        assertFalse("El document no existeix", index.DocExists(id1));
        assertFalse("El document no existeix", index.DocExists(newid2));
        assertFalse("El document no existeix", index.DocExists(id3));
        assertFalse("El document no existeix", index.DocExists(id4));

        assertTrue("El document existeix", index.DocExists(newid1));
        assertTrue("El document existeix", index.DocExists(id2));
        assertTrue("El document existeix", index.DocExists(newid3));
        assertTrue("El document existeix", index.DocExists(newid4));
    }

    @Test
    public void testKDocsKekw() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();
        String[] autors = {"Jose", "Jose", "Paco"};
        String[] titols = {"Antonio1", "Antonio2", "Paco va a esquiar"};
        Pair<String, String> at0 = new Pair<>(autors[0], titols[0]);
        Pair<String, String> at1 = new Pair<>(autors[1], titols[1]);
        Pair<String, String> at2 = new Pair<>(autors[2], titols[2]);
        List<String> c0 = Arrays.asList("Jose Antonio");
        List<String> c1 = Arrays.asList("Jose Antonio");
        List<String> c2 = Arrays.asList("Paco");

        index.AfegirDoc(at0.x, at0.y, c0);
        index.AfegirDoc(at1.x, at1.y, c1);
        index.AfegirDoc(at2.x, at2.y, c2);

        List<Pair<String, String>> expected = Arrays.asList(at0, at1);
        List<Pair<String, String>> res = index.CercaPerRellevancia("Jose", 2, false);

        assertEquals(expected, res);
    }

    @Test
    public void testGetKDocsSimilarS() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();

        List<String> d1 = getTestString(1);
        List<String> d2 = getTestString(2);
        List<String> d3 = getTestString(3);
        List<String> d4 = getTestString(4);
        List<String> d5 = getTestString(5);
        index.AfegirDoc("RICK", "D1", d1);

        //Comprovem si nomes hi ha 1 document
        Pair<String, String> id1 = new Pair<>("RICK", "D1");
        assertEquals(new ArrayList<Pair<String, String>>(), index.GetKDocsSimilarS(id1, 2, false));
        
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        index.AfegirDoc("RICK", "D4", d4);
        index.AfegirDoc("RICK", "D5", d5);
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        Pair<String, String> id4 = new Pair<>("RICK", "D4");
        Pair<String, String> id5 = new Pair<>("RICK", "D5");

        //TF
        //Comprovem dos documents iguals
        List<Pair<String, String>> expectedTF1 = Arrays.asList(id1);
        //Comprovem les semblances amb TF
        List<Pair<String, String>> expectedTF3 = Arrays.asList(id1, id2);

        assertEquals(expectedTF1, index.GetKDocsSimilarS(id2, 1, true));
        assertEquals(expectedTF3, index.GetKDocsSimilarS(id3, 2, true));

        //TFIDF
        //Comprovem dos documents iguals
        List<Pair<String, String>> expectedTFIDF1 = Arrays.asList(id2);
        //Comprovem les semblances amb TFIDF(hauria de ser diferent de l'anterior amb TF)
        List<Pair<String, String>> expectedTFIDF3 = Arrays.asList(id4);
        

        assertEquals(expectedTFIDF1, index.GetKDocsSimilarS(id1, 1, false));
        assertEquals(expectedTFIDF3, index.GetKDocsSimilarS(id3, 1, false));

        //Comprovem si pot comparar documents buits i si la k > nombre de documents
        List<Pair<String, String>> expectedTFIDFbuit = Arrays.asList(id1, id4, id3, id2);
        assertEquals(expectedTFIDFbuit, index.GetKDocsSimilarS(id5, 4, false));
    }

    @Test
    public void testActualitzarContingut() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();

        List<String> d1 = getTestString(1);
        List<String> d2 = getTestString(2);
        List<String> d3 = getTestString(3);
        List<String> d4 = getTestString(4);
        index.AfegirDoc("RICK", "D1", d1);
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        Pair<String, String> id1 = new Pair<>("RICK", "D1");
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        
        //Originalment esperem que digui que d2 es el que s'assembla mes a d1
        List<Pair<String, String>> expected1 = Arrays.asList(id2);
        
        assertEquals(expected1, index.GetKDocsSimilarS(id1, 1, false));
        
        //Si posem el contingut de d4 a d2, aleshores d3 es el que mes s'hi assembla
        index.ActualitzarContingut("RICK", "D2", d4);
        List<Pair<String, String>> expected2 = Arrays.asList(id3);

        assertEquals(expected2, index.GetKDocsSimilarS(id1, 1, false));
    }

    private String[] s1 = { "We're no strangers to love.\n"};
    private String[] s2 = { "We're no strangers to love.\n"};
    private String[] s3 = { "We're no love Never gonna\n"};
    private String[] s4 = { "Never gonna give you up\n"};
    private String[] s5 = { };

    private List<String> getTestString(int i) {
        switch (i) {
            case 1: return Arrays.asList(s1);
            case 2: return Arrays.asList(s2);
            case 3: return Arrays.asList(s3);
            case 4: return Arrays.asList(s4);
            case 5: return Arrays.asList(s5);
            default: return null;
        }

    }
}
