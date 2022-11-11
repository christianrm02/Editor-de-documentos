package indexs;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import datatypes.Pair;

/**
 * IndexParaulaTFIDFTest: Per fer el testing de la classe IndexParaulaTFIDF
 * @author Èric Ryhr
 */
public class IndexParaulaTFIDFTest {
    @Test
    public void testActualitzarAutor() {

    }

    @Test
    public void testActualitzarContingut() {

    }

    @Test
    public void testActualitzarTitol() {

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

    }

    private String[] s1 = { "We're no strangers to love.\n",
                            "You know the rules and so do I (do I)!\n"};
    private String[] s2 = { "We're no strangers to love.\n",
                            "You know the rules and so do I (do I)!\n"};
    private String[] s3 = { "We're no to love.\n",
                            "You know the rules and so do I (do I)!\n"};
    private String[] s4 = { "We're no strangers to love.\n",
                            "do I (do I)!\n"};

    private List<String> getTestString(int i) {
        switch (i) {
            case 1: return Arrays.asList(s1);
            case 2: return Arrays.asList(s2);
            case 3: return Arrays.asList(s3);
            case 4: return Arrays.asList(s4);
            default: return null;
        }

    }
}
