package datatypes;

import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {

    /**
     * Objecte de la prova: Es prova la constructora de la classe Pair.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats:
     * Efectes estudiats: -
     * Operativa: Es crean dos pairs i mitjançant que els seus atributs són públics, es
     * comproba que els objectes estan correctament inicialitzats
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

    @Test
    public void testEquals() {
        Pair<String, Integer> p1 = new Pair<>("Primer", 1);
        Pair<String, Integer> p2 = new Pair<>("Segon", 1);
        Pair<String, Integer> p3 = new Pair<>("Primer", 2);
        Pair<String, Integer> p4 = new Pair<>("Primer", 1);
        assertFalse(p1.equals(p2));
        assertFalse(p1.equals(p3));
        assertTrue(p1.equals(p4));
        assertTrue(p1.equals(p1));
        assertFalse(p1.equals(null));
    }

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