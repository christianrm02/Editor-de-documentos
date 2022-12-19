package test.persistencia;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import domini.controladores.CtrlIndex;
import domini.datatypes.Utility;
import persistencia.GestorIndexs;

public class GestorIndexsTest {
    @Test
    public void testFuncionalitatBasica() throws IOException {
        byte[] test = {0x00, 0x01, 0x02, 0x04};

        GestorIndexs.ExportarIndexs(test);
        byte[] res = GestorIndexs.ImportarIndexs();

        assertArrayEquals(test, res);
    }

    @Test
    public void testAmbIndexs() throws IOException {
        CtrlIndex ci = new CtrlIndex();
        String autor = "Manolo";
        String titol = "Manolo se va a la playa";
        String contingut = "Manolo se aburre en la playa.\nManolo vuelve a casa";
        List<String> cont = Utility.converteix_a_frases(contingut);
        ci.AfegirDoc(autor, titol, cont);

        assertTrue("Doc trobat", ci.FindDoc(autor, titol));

        byte[] test = ci.ExportarIndexs();
        GestorIndexs.ExportarIndexs(test);

        byte[] res = GestorIndexs.ImportarIndexs();
        assertArrayEquals(test, res);

        ci.ImportarIndexs(res);

        assertTrue("Doc trobat (again)", ci.FindDoc(autor, titol));
    }
}
