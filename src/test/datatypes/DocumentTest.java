package datatypes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DocumentTest {

    /**
     * Objecte de la prova: Es prova la constructora de la classe Document.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats:
     * Efectes estudiats: -
     * Operativa: Es crea un nou document i mitjançant els getters es comproba si s'ha inicialitzat correctament.
     */
    @Test
    public void testConstructora() {
        Document doctest = new Document("Pep", "Dia", Format.txt);
        assertEquals("Pep", doctest.getAutor());
        assertEquals("Dia", doctest.getTitol());
        assertEquals(Format.txt, doctest.getFormat());
    }

    @Test
    public void getAutor() {
        Document doctest = new Document("Pep", "Dia", Format.txt);
        assertEquals("Pep", doctest.getAutor());
    }

    @Test
    public void getTitol() {
        Document doctest = new Document("Pep", "Dia", Format.txt);
        assertEquals("Dia", doctest.getTitol());
    }

    //stub necesario
    @Test
    public void getContingut() {
        Document doctest = new Document("Pep", "Dia", Format.txt);
        doctest.setContingut("En un lugar de la Mancha, de cuyo nombre no quiero acordarme.");
        List<String> contingut = new ArrayList<>();
        contingut.add("En un lugar de la Mancha, de cuyo nombre no quiero acordarme.");
        assertEquals(contingut, doctest.getContingut());
    }

    @Test
    public void getFormat() {
        Document doctest = new Document("Pep", "Dia", Format.txt);
        assertEquals(Format.txt, doctest.getFormat());
    }

    @Test
    public void setAutor() {
        Document doctest = new Document();
        doctest.setAutor("Pep");
        assertEquals("Pep", doctest.getAutor());
    }

    @Test
    public void setTitol() {
        Document doctest = new Document();
        doctest.setTitol("Vida");
        assertEquals("Vida", doctest.getTitol());
    }

    //stub necesario
    @Test
    public void setContingut() {
        Document doctest = new Document();
        doctest.setContingut("En un lugar de la Mancha, de cuyo nombre no quiero acordarme.");
        List<String> contingut = new ArrayList<>();
        contingut.add("En un lugar de la Mancha, de cuyo nombre no quiero acordarme.");
        assertEquals(contingut, doctest.getContingut());

        doctest.setContingut("Nou llibre");
        contingut.clear();
        contingut.add("Nou llibre");
        assertEquals(contingut, doctest.getContingut());
    }
}