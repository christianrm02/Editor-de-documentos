package test.persistencia;

import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;

import org.junit.Test;

import persistencia.GestorIndexs;

public class GestorIndexsTest {
    @Test
    public void testExportarIndexs() throws IOException {
        byte[] test = {0x00, 0x01, 0x02, 0x04};

        GestorIndexs.ExportarIndexs(test);
        byte[] res = GestorIndexs.ImportarIndexs();

        assertArrayEquals(test, res);
    }
}
