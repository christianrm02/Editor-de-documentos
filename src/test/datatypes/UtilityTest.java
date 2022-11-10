package datatypes;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class UtilityTest {
    @Test
    public void testUTF8toASCII() {
        String test = "El goril·la juga a futbol amb el pingüí";
        String expected = "El gorilla juga a futbol amb el pingui";
        String actual = Utility.UTF8toASCII(test);
        assertNotEquals(expected, test);
        assertEquals(expected, actual);
    }

    @Test
    public void testParseFrase() {
        String test = "Ostres! Potser, hauriem... d'anar; alla? No\nno: crec";
        String[] expected = {"Ostres", "Potser", "hauriem", "d'anar", "alla", "No", "no", "crec"};
        String[] actual = Utility.parseFrase(test);
        assertArrayEquals(actual, expected);
    }
}
