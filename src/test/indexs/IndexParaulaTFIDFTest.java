package indexs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import transversal.Pair;

/**
 * IndexParaulaTFIDFTest: Per fer el testing de la classe IndexParaulaTFIDF
 * @author Èric Ryhr
 */
public class IndexParaulaTFIDFTest {
    @Test
    public void testActualitzarAutor() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();

        List<String> d1 = getTestString(1);
        List<String> d2 = getTestString(2);
        List<String> d3 = getTestString(3);
        List<String> d4 = getTestString(4);
        index.AfegirDoc("RICK", "D1", d1);
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        index.AfegirDoc("RICK", "D4", d4);
        Pair<String, String> id1 = new Pair<>("RICK", "D1");
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        
        List<Pair<String, String>> expected1 = Arrays.asList(id2, id3);
        
        assertEquals(expected1, index.GetKDocsSimilarS(id1, 2));
        
        Pair<String, String> id4 = new Pair<>("ASTLEY", "D3");
        List<Pair<String, String>> expected2 = Arrays.asList(id4, id2);
        index.ActualitzarAutor("RICK", "D3", "ASTLEY");

        assertEquals(expected2, index.GetKDocsSimilarS(id1, 2));
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
        
        assertEquals(expected1, index.GetKDocsSimilarS(id1, 1));
        
        //Si posem el contingut de d4 a d2, aleshores d3 es el que mes s'hi assembla
        index.ActualitzarContingut("RICK", "D2", d4);
        List<Pair<String, String>> expected2 = Arrays.asList(id3);

        assertEquals(expected2, index.GetKDocsSimilarS(id1, 1));
    }

    @Test
    public void testActualitzarTitol() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();

        List<String> d1 = getTestString(1);
        List<String> d2 = getTestString(2);
        List<String> d3 = getTestString(3);
        List<String> d4 = getTestString(4);
        index.AfegirDoc("RICK", "D1", d1);
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        index.AfegirDoc("RICK", "D4", d4);
        Pair<String, String> id1 = new Pair<>("RICK", "D1");
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        
        List<Pair<String, String>> expected1 = Arrays.asList(id2, id3);
        
        assertEquals(expected1, index.GetKDocsSimilarS(id1, 2));
        
        Pair<String, String> id4 = new Pair<>("RICK", "D4");
        List<Pair<String, String>> expected2 = Arrays.asList(id2, id4);
        index.ActualitzarTitol("RICK", "D3", "D4");

        assertEquals(expected2, index.GetKDocsSimilarS(id1, 2));
    }

    @Test
    public void testAfegirDoc() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();

        List<String> d1 = getTestString(1);
        List<String> d2 = getTestString(2);
        List<String> d3 = getTestString(3);
        List<String> d4 = getTestString(4);
        index.AfegirDoc("RICK", "D1", d1);
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        index.AfegirDoc("RICK", "D4", d4);
        Pair<String, String> id1 = new Pair<>("RICK", "D1");
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        Pair<String, String> id4 = new Pair<>("RICK", "D4");

        List<Pair<String, String>> expected = Arrays.asList(id2, id3);

        assertEquals(expected, index.GetKDocsSimilarS(id1, 2));
    }

    @Test
    public void testEsborrarDoc() {
        IndexParaulaTFIDF index = new IndexParaulaTFIDF();

        List<String> d1 = getTestString(1);
        List<String> d2 = getTestString(2);
        List<String> d3 = getTestString(3);
        List<String> d4 = getTestString(4);
        index.AfegirDoc("RICK", "D1", d1);
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        index.AfegirDoc("RICK", "D4", d4);
        Pair<String, String> id1 = new Pair<>("RICK", "D1");
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        Pair<String, String> id4 = new Pair<>("RICK", "D4");

        List<Pair<String, String>> expected1 = Arrays.asList(id2, id3);

        assertEquals(expected1, index.GetKDocsSimilarS(id1, 2));

        List<Pair<String, String>> expected2 = Arrays.asList(id2, id4);
        index.EsborrarDoc(id3.x, id3.y);

        assertEquals(expected2, index.GetKDocsSimilarS(id1, 2));
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
        assertEquals(new ArrayList<Pair<String, String>>(), index.GetKDocsSimilarS(id1, 2));
        
        index.AfegirDoc("RICK", "D2", d2);
        index.AfegirDoc("RICK", "D3", d3);
        index.AfegirDoc("RICK", "D4", d4);
        index.AfegirDoc("RICK", "D5", d5);
        Pair<String, String> id2 = new Pair<>("RICK", "D2");
        Pair<String, String> id3 = new Pair<>("RICK", "D3");
        Pair<String, String> id4 = new Pair<>("RICK", "D4");
        Pair<String, String> id5 = new Pair<>("RICK", "D5");

        List<Pair<String, String>> expected1 = Arrays.asList(id2, id3);
        List<Pair<String, String>> expected4 = Arrays.asList(id1, id2);
        List<Pair<String, String>> expected5 = Arrays.asList(id1, id4, id3, id2);

        assertEquals(expected1, index.GetKDocsSimilarS(id1, 2));
        assertEquals(expected4, index.GetKDocsSimilarS(id4, 2));
        //Comprovem si pot comparar documents nuls
        assertEquals(expected5, index.GetKDocsSimilarS(id5, 4));
    }

    private String[] s1 = { "We're no strangers to love.\n",
                            "You know the rules and so do I (do I)!\n"};
    private String[] s2 = { "We're no strangers to love.\n",
                            "You know the rules and so do I (do I)!\n"};
    private String[] s3 = { "We're no to love.\n",
                            "You know the rules and so do I (do I)!\n"};
    private String[] s4 = { "We're no strangers to love.\n",
                            "do I (do I)!\n"};
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
