package test.persistencia;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import excepcions.FormatInvalid;
import persistencia.GestorDocuments;

public class GestorDocumentsTest {

    @Test
    public void testObrirDesar() throws IOException {
        String autor = "Manolo";
        String titol = "Manolo se va a la playa";
        String contingut = "Manolo se aburre en la playa.\nManolo vuelve a casa";

        GestorDocuments.DesaContingut(autor, titol, contingut);
        String result = GestorDocuments.GetContingut(autor, titol);

        assertEquals(contingut, result);
    }

    @Test
    public void testTXT() throws IOException, FormatInvalid {
        String autor = "Manolo";
        String titol = "Manolo se va a la playa";
        String contingut = "Manolo se aburre en la playa.\nManolo vuelve a casa.";
        String path = "D:\\Documents\\Universitat\\Quad5\\PROP\\Projecte\\testProp\\test.txt";
        String[] expected = new String[]{autor, titol, contingut};

        GestorDocuments.DesaContingut(autor, titol, contingut);
        GestorDocuments.ExportaDocument(autor, titol, path);
        String[] result = GestorDocuments.ImportaDocument(path);

        assertArrayEquals(expected, result);
    }

    @Test
    public void testXML() throws IOException, FormatInvalid {
        String autor = "Manolo";
        String titol = "Manolo se va a la playa";
        String contingut = "Manolo se aburre en la playa.\nManolo vuelve a casa.";
        String path = "D:\\Documents\\Universitat\\Quad5\\PROP\\Projecte\\testProp\\test.xml";
        String[] expected = new String[]{autor, titol, contingut};

        GestorDocuments.DesaContingut(autor, titol, contingut);
        GestorDocuments.ExportaDocument(autor, titol, path);
        String[] result = GestorDocuments.ImportaDocument(path);

        assertArrayEquals(expected, result);
    }
}
