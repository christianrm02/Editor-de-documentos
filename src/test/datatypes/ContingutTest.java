package test.datatypes;

import org.junit.Before;
import org.junit.Test;

import datatypes.Contingut;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ContingutTest: Per fer el testing de la classe Contingut
 * @author Marc Roman
 */
public class ContingutTest {
    private String f1 = "Hola, em dic Marc. Com estàs?\nJo bé! Bueno adéeeu";
    private String f2 = "Bon dia, com vas? Ara ve el mundial, fins cap d'any no torna a jugar el Barça. Vaja\nQuè hi farem\n";
    private String f3 = "Hola\n\n, què és això?\n\n, no ho sé, però\nbé!\naixò s'acaba...\nAra sí. Està bé? Està bé! a    ";

    private String f4 = "\n\n     \n Hola\n  Com anem?Jo bé, i tu?jajaja\n ";
    private List<String> sol1, sol2, sol3, sol4;

    // Per inicialitzar les solucions
    @Before
    public void setUp() {
        sol1 = new ArrayList<String>();
        sol1.add("Hola, em dic Marc.");
        sol1.add(" Com estàs?\n");
        sol1.add("Jo bé!");
        sol1.add(" Bueno adéeeu");

        sol2 = new ArrayList<String>();
        sol2.add("Bon dia, com vas?");
        sol2.add(" Ara ve el mundial, fins cap d'any no torna a jugar el Barça.");
        sol2.add(" Vaja\n");
        sol2.add("Què hi farem\n");

        sol3 = new ArrayList<String>();
        sol3.add("Hola\n");
        sol3.add("\n");
        sol3.add(", què és això?\n");
        sol3.add("\n");
        sol3.add(", no ho sé, però\n");
        sol3.add("bé!\n");
        sol3.add("això s'acaba.");
        sol3.add(".");
        sol3.add(".\n");
        sol3.add("Ara sí.");
        sol3.add(" Està bé?");
        sol3.add(" Està bé!");
        sol3.add(" a    ");

        sol4 = new ArrayList<String>();
        sol4.add("\n");
        sol4.add("\n");
        sol4.add("     \n");
        sol4.add(" Hola\n");
        sol4.add("  Com anem?");
        sol4.add("Jo bé, i tu?");
        sol4.add("jajaja\n");
        sol4.add(" ");
    }

    /**
     * Objectes de la prova: Es proven les constructores de la classe Contingut.
     * Altres elements integrats de la prova: -
     * Fitxers de dades necessaris: -
     * Valors estudiats: Es fa servir l’estratègia de caixa blanca. Es comprova que funcionen correctament les dues creadores.
     * Efectes estudiats: -
     * Operativa: Executar el jUnit test.
     */
    @Test
    public void testConstructores() {
        Contingut conttest = new Contingut();
        assertEquals(new ArrayList<String>(), conttest.getFrases());
        Contingut conttest2 = new Contingut(f1);
        assertEquals(sol1, conttest2.getFrases());
    }

    /**
     * Objectes de la prova: Es prova l'operació getFrases, que retorna una llista de String, de la classe Contingut.
     * Altres elements integrats de la prova: -
     * Fitxers de dades necessaris: -
     * Valors estudiats: Es fa servir l'estratègia de caixa blanca. Es comprova amb cas buit i amb text, les dues principals opcions.
     * Efectes estudiats: -
     * Operativa: Executar el jUnit test.
     */
    @Test
    public void testGetFrases() {
        Contingut conttest = new Contingut(f1);
        assertEquals(sol1, conttest.getFrases());
        Contingut conttest2 = new Contingut();
        assertEquals(new ArrayList(), conttest2.getFrases());
    }

    /**
     * Objectes de la prova: Es prova l'operació actualitzaContingut de la classe Contingut.
     * Altres elements integrats de la prova: -
     * Fitxers de dades necessaris: -
     * Valors estudiats: Es fa servir l'estratègia de caixa blanca. Es proven diferents tipus de combinacions que pensem que poden ser problemàtiques per dividir bé les frases.
     * Efectes estudiats: -
     * Operativa: Executar el jUnit test.
     */
    @Test
    public void testActualitzaContingut() {
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
        conttest.actualitzaContingut(f4);
        assertEquals(sol4, conttest.getFrases());
    }
}