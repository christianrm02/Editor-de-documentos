package test.datatypes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ContingutTest {

    /**
     * Objecte de la prova: Es proven les constructores de la classe Contingut.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No es necessari cap fitxer de dades.
     * Valors estudiats:
     * Efectes estudiats: -
     * Operativa: Es crea un nou Contingut buit i un nou Contingut amb una sequencia de frases i mitjançant els getters es comproba si s'ha inicialitzat correctament.
     */
    @Test
    public void testConstructores() {
        Contingut conttest = new Contingut();
        assertEquals(new ArrayList<String>(), conttest.getFrases());

        Contingut conttest2 = new Contingut("Hola, em dic Marc. Com estàs?\n Jo bé! Bueno adéeeu");
        List<String> l = new ArrayList<String>();
        l.add("Hola, em dic Marc."); l.add(" Com estàs?"); l.add("\n"); l.add(" Jo bé!"); l.add(" Bueno adéeeu");
        assertEquals(l, conttest2.getFrases());
    }

    @Test
    public void getFrases() {
        Contingut conttest = new Contingut();
        List<String> l = new ArrayList<Stri"Hola, em dic Marc. Com estàs?\n Jo bé! Bueno adéeeu"ng>();
        l.add("Hola, em dic Marc."); l.add(" Com estàs?"); l.add("\n"); l.add(" Jo bé!"); l.add(" Bueno adéeeu");
        assertEquals(l, conttest.getFrases());
    }

    @Test
    public void actualitzaContingut() {
        Contingut conttest = new Contingut();
        conttest.actualitzaContingut("Hola, em dic Marc. Com estàs?\n Jo bé! Bueno adéeeu");
        List<String> l = new ArrayList<String>();
        l.add("Hola, em dic Marc."); l.add(" Com estàs?"); l.add("\n"); l.add(" Jo bé!"); l.add(" Bueno adéeeu");
        assertEquals(l, conttest.getFrases());
        conttest.actualitzaContingut("Hola, em dic Marc. Com estàs?\n Jo bé! Bueno adéeeu");
        assertEquals(l, conttest.getFrases());
        conttest.actualitzaContingut("Bon dia, com vas? Ara ve el mundial, fins cap d'any no torna a jugar el Barça. Vaja\n Què hi farem");
        List<String> l2 = new ArrayList<String>();
        l2.add("Bon dia, com vas?"); l2.add(" Ara ve el mundial, fins cap d'any no torna a jugar el Barça."); l2.add(" Vaja\n"); l2.add(" Què hi farem");
        assertEquals(l2, conttest.getFrases());
    }
}