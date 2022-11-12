package datatypes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * DocumentTest: Per fer el testing de la classe Document
 * @author Christian Rivero
 */
public class DocumentTest {

    /**
     * Objecte de la prova: Es prova la constructora de la classe Document.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa grisa. Com és una funcionalitat bàsica, el test també ho és.
     * Efectes estudiats: -
     * Operativa: Es crea un nou document i mitjançant els getters es comprova si s'ha inicialitzat correctament.
     */
    @Test
    public void testConstructora() {
        Document doctest = new Document("Pep", "Dia");
        assertEquals("Pep", doctest.getAutor());
        assertEquals("Dia", doctest.getTitol());
    }

    /**
     * Objecte de la prova: Es prova l'operació getAutor() de la classe Document.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa grisa. Com és una funcionalitat bàsica, el test també ho és.
     * Efectes estudiats: -
     * Operativa: Una vegada comprovat que la creadora funciona bé, el testeig d'aquest mètode és immediat.
     */
    @Test
    public void getAutor() {
        Document doctest = new Document("Pep", "Dia");
        assertEquals("Pep", doctest.getAutor());
    }

    /**
     * Objecte de la prova: Es prova l'operació getTitol() de la classe Document.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa grisa. Com és una funcionalitat bàsica, el test també ho és.
     * Efectes estudiats: -
     * Operativa: Una vegada comprovat que la creadora funciona bé, el testeig d'aquest mètode és immediat.
     */
    @Test
    public void getTitol() {
        Document doctest = new Document("Pep", "Dia");
        assertEquals("Dia", doctest.getTitol());
    }

    /**
     * Objecte de la prova: Es prova l'operació getContingut() de la classe Document.
     * Altres elements integrats a la prova: -
     * Stubs: Es fa ús de l'operació getFrases de la classe DocumentStub, retorna el contingut que es posa amb setContingut.
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa grisa. Com és una funcionalitat bàsica, el test també ho és.
     * Efectes estudiats: -
     * Operativa: Amb el setContingut guardem el nou contingut, i la funció del stub és retornar el mateix.
     */
    @Test
    public void getContingut() {
        Document doctest = new Document("Pep", "Vida");
        ContingutStub c = new ContingutStub();
        doctest.Contingut(c);
        doctest.setContingut("En un lugar de la Mancha, de cuyo nombre no quiero acordarme.");
        List<String> contingut = new ArrayList<>();
        contingut.add("En un lugar de la Mancha, de cuyo nombre no quiero acordarme.");
        assertEquals(contingut, doctest.getContingut());
    }

    /*
    /**
     * Objecte de la prova: Es prova l'operació getFormat() de la classe Document.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa grisa. Com és una funcionalitat bàsica, el test també ho és.
     * Efectes estudiats: -
     * Operativa: Una vegada comprovat que la creadora funciona bé, el testeig d'aquest mètode és immediat.
     *
    @Test
    public void getFormat() {
        Document doctest = new Document("Pep", "Dia", Format.txt);
        assertEquals(Format.txt, doctest.getFormat());
    }
    */

    /**
     * Objecte de la prova: Es prova l'operació setAutor() de la classe Document.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa grisa. Com és una funcionalitat bàsica, el test també ho és.
     * Efectes estudiats: -
     * Operativa: Es crea un nou document, mitjançant l'operació getAutor es comprova que els canvis amb setAutor es fan correctament.
     */
    @Test
    public void setAutor() {
        Document doctest = new Document();
        doctest.setAutor("Pep");
        assertEquals("Pep", doctest.getAutor());
        doctest.setAutor("Joan");
        assertEquals("Joan", doctest.getAutor());
    }

    /**
     * Objecte de la prova: Es prova l'operació setTitol() de la classe Document.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa grisa. Com és una funcionalitat bàsica, el test també ho és.
     * Efectes estudiats: -
     * Operativa: Es crea un nou document, mitjançant l'operació getTitol es comprova que els canvis amb setAutor es fan correctament.
     */
    @Test
    public void setTitol() {
        Document doctest = new Document();
        doctest.setTitol("Vida");
        assertEquals("Vida", doctest.getTitol());
        doctest.setTitol("Life");
        assertEquals("Life", doctest.getTitol());
    }

    /**
     * Objecte de la prova: Es prova l'operació getContingut() de la classe Document.
     * Altres elements integrats a la prova: -
     * Stubs: Es fa ús de l'operació getFrases de la classe DocumentStub, retorna el contingut que es posa amb setContingut.
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa grisa. Com és una funcionalitat bàsica, el test també ho és.
     * Efectes estudiats: -
     * Operativa: Amb el setContingut guardem el nou contingut, i la funció del stub és retornar el mateix.
     */
    @Test
    public void setContingut() {
        Document doctest = new Document("Pep", "Vida");
        ContingutStub c = new ContingutStub();
        doctest.Contingut(c);
        doctest.setContingut("En un lugar de la Mancha, de cuyo nombre no quiero acordarme.");
        List<String> contingut = new ArrayList<>();
        contingut.add("En un lugar de la Mancha, de cuyo nombre no quiero acordarme.");
        assertEquals(contingut, doctest.getContingut());
    }
}