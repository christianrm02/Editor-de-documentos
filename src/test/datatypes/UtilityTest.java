package datatypes;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * UtilityTest: Per fer el testing de la classe Utility
 * @author Èric Ryhr
 */
public class UtilityTest {

    /**
     * Objecte de la prova: Es prova la funció ParseFrase
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa blanca.
     * Efectes estudiats: -
     * Operativa: Es prova que la funció elimini correctament els símbols de puntuació i separi les paraules per espais
     */
    @Test
    public void testParseFrase() {
        String test = "Ostres! Potser, hauriem... d'anar; alla? No\nno: crec";
        String[] expected = {"Ostres", "Potser", "hauriem", "d'anar", "alla", "No", "no", "crec"};
        String[] actual = Utility.ParseFrase(test);
        assertArrayEquals(actual, expected);
    }
}
