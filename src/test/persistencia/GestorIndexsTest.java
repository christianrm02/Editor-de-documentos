package test.persistencia;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;

import org.junit.Test;

import persistencia.GestorIndexs;

public class GestorIndexsTest {
    @Test
    public void testExportarIndexs() throws IOException {
        GestorIndexs gi = new GestorIndexs();

        byte[] test = {0x00, 0x01, 0x02, 0x04};

        gi.ExportarIndexs(test);
        byte[] res = gi.ImportarIndexs();

        assertArrayEquals(test, res);
    }
}
