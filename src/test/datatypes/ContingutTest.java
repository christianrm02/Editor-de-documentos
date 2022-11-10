package datatypes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class ContingutTest {
    private String f1 = "Hola, em dic Marc. Com estàs?\nJo bé! Bueno adéeeu";
    private String f2 = "Bon dia, com vas? Ara ve el mundial, fins cap d'any no torna a jugar el Barça. Vaja\nQuè hi farem\n";
    private String f3 = "Hola\n\n, què és això?\n\n, no ho sé, però\nbé!\naixò s'acaba.\nAra sí. Està bé? Està bé! a";
    private List<String> sol1, sol2, sol3;

    @Before
    public void setUp() throws Exception {
        sol1 = new ArrayList<String>();
        sol1.add("Hola, em dic Marc.");
        sol1.add("Com estàs?\n");
        sol1.add("Jo bé!");
        sol1.add("Bueno adéeeu");

        sol2 = new ArrayList<String>();
        sol2.add("Bon dia, com vas?");
        sol2.add("Ara ve el mundial, fins cap d'any no torna a jugar el Barça.");
        sol2.add("Vaja\n");
        sol2.add("Què hi farem\n");

        sol3 = new ArrayList<String>();
        sol3.add("Hola\n");
        sol3.add("\n");
        sol3.add(", què és això?\n");
        sol3.add("\n");
        sol3.add(", no ho sé, però\n");
        sol3.add("bé!\n");
        sol3.add("això s'acaba.\n");
        sol3.add("Ara sí.");
        sol3.add("Està bé?");
        sol3.add("Està bé!");
        sol3.add("a");
    }

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
        Contingut conttest2 = new Contingut(f1);
        assertEquals(sol1, conttest2.getFrases());
    }

    @Test
    public void getFrases() {
        Contingut conttest = new Contingut(f1);
        assertEquals(sol1, conttest.getFrases());
    }

    @Test
    public void actualitzaContingut() {
        Contingut conttest = new Contingut();
        conttest.actualitzaContingut(f1);
        assertEquals(sol1, conttest.getFrases());
        conttest.actualitzaContingut(f1);
        assertEquals(sol1, conttest.getFrases());
        conttest.actualitzaContingut(f2);
        assertEquals(sol2, conttest.getFrases());
        conttest.actualitzaContingut(f3);
        assertEquals(sol3, conttest.getFrases());
        conttest.actualitzaContingut("");
        assertEquals(new ArrayList<String>(), conttest.getFrases());
    }
}