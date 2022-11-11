package datatypes;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * PairTest: Per fer el testing de la classe Pair
 * @author Christian Rivero
 */
public class PairTest {

    /**
     * Objecte de la prova: Es prova la constructora de la classe Pair.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa blanca. Es proven amb diferents tipus d'elements.
     * Efectes estudiats: -
     * Operativa: Es crean dos pairs i mitjançant que els seus atributs són públics, es comprova que els
     * objectes estan correctament inicialitzats.
     */
    @Test
    public void testConstructora() {
        Pair<String, Integer> p1 = new Pair<>("Primer", 1);
        Pair<Double, Boolean> p2 = new Pair<>(2.145, false);
        assertEquals("Primer", p1.x);
        assertEquals((Integer)1, p1.y);
        assertEquals((Double)2.145, p2.x);
        assertEquals(false, p2.y);
    }

    /**
     * Objecte de la prova: Es prova l'operació equals() de la classe Pair.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa blanca. Es proven tot tipus de combinacions.
     * Efectes estudiats: -
     * Operativa: Es crean diversos pairs i es compara per saber si són iguals.
     */
    @Test
    public void testEquals() {
        Pair<String, Integer> p1 = new Pair<>("Primer", 1);
        Pair<String, Integer> p2 = new Pair<>("Segon", 1);
        Pair<String, Integer> p3 = new Pair<>("Primer", 2);
        Pair<String, Integer> p4 = new Pair<>("Primer", 1);
        Pair<String, Double> p5 = new Pair<>("Primer", 1.);
        assertFalse(p1.equals(p2));
        assertFalse(p1.equals(p3));
        assertTrue(p1.equals(p4));
        assertTrue(p1.equals(p1));
        assertFalse(p1.equals(null));
        assertFalse(p1.equals(p5));
    }

    /**
     * Objecte de la prova: Es prova l'operació compareTo() de la classe Pair.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa blanca. Es proven tot tipus de combinacions.
     * Efectes estudiats: -
     * Operativa: Es crean diversos pairs i es compara per saber si els ordena correctament.
     */
    @Test
    public void testCompareTo() {
        Pair<String, String> p1 = new Pair<>("Pep", "Dia");
        Pair<String, String> p2 = new Pair<>("Pep", "Noche");
        Pair<String, String> p3 = new Pair<>("Alex", "Dia");
        Pair<String, String> p4 = new Pair<>("Pep", "Diurno");
        Pair<String, String> p5 = new Pair<>("Zacarias", "Alas");
        Pair<String, String> p6 = new Pair<>("Alex", "Dia");
        assertEquals(-1, p2.compareTo(p1));
        assertEquals(0, p3.compareTo(p6));
        assertEquals(1, p1.compareTo(p2));
        assertEquals(-1, p6.compareTo(p1));
    }

    /**
     * Objecte de la prova: Es prova l'operació hashCode() de la classe Pair.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa blanca. Com és una funcionalitat bàsica, el test també
     * ho és.
     * Efectes estudiats: -
     * Operativa: Es crean diversos pairs i es comparen per saber si tenen el mateix hashCode.
     */
    @Test
    public void testHashCode() {
        Pair<String, Integer> p1 = new Pair<>("Primer", 1);
        Pair<String, Integer> p2 = new Pair<>("Segon", 1);
        Pair<String, Integer> p3 = new Pair<>("Primer", 2);
        Pair<String, Integer> p4 = new Pair<>("Primer", 1);
        assertNotEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertEquals(p1.hashCode(), p4.hashCode());
        assertEquals(p1.hashCode(), p1.hashCode());
    }
}