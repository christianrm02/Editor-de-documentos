package test.datatypes;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import datatypes.Utility;

/**
 * UtilityTest: Per fer el testing de la classe Utility
 * @author Èric Ryhr
 */
public class UtilityTest {

    @Test
    public void testParseFrase() {
        String test = "Ostres! Potser, hauriem... d'anar; alla? No\nno: crec";
        String[] expected = {"Ostres", "Potser", "hauriem", "d'anar", "alla", "No", "no", "crec"};
        String[] actual = Utility.ParseFrase(test);
        assertArrayEquals(actual, expected);
    }
}
