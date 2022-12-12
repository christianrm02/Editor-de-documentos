package test.persistencia;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import persistencia.GestorDocuments;
import transversal.FileFormat;

public class GestorDocumentsTest {

    @Test
    public void testObrirDesar() {
        String autor = "Manolo";
        String titol = "Manolo se va a la playa";
        String contingut = "Manolo se aburre en la playa.\nManolo vuelve a casa";
        GestorDocuments gd = new GestorDocuments();

        gd.DesaDocument(autor, titol, contingut);
        String result = gd.ObrirDocument(autor, titol);

        assertEquals(contingut, result);
    }

    @Test
    public void testTXT() {
        String autor = "Manolo";
        String titol = "Manolo se va a la playa";
        String contingut = "Manolo se aburre en la playa.\nManolo vuelve a casa.\n";
        GestorDocuments gd = new GestorDocuments();
        String path = "D:\\Documents\\Eric\\Universitat\\Quad5\\testProp\\test.txt";
        String[] expected = new String[]{autor, titol, contingut};

        gd.DesaDocument(autor, titol, contingut);
        gd.ExportaDocument(autor, titol, path, FileFormat.txt);
        String[] result = gd.CarregaDocument(path, FileFormat.txt);

        assertArrayEquals(expected, result);
    }

    @Test
    public void testXML() {
        String autor = "Manolo";
        String titol = "Manolo se va a la playa";
        String contingut = "Manolo se aburre en la playa.\nManolo vuelve a casa.\n";
        GestorDocuments gd = new GestorDocuments();
        String path = "D:\\Documents\\Eric\\Universitat\\Quad5\\testProp\\test.xml";
        String[] expected = new String[]{autor, titol, contingut};

        gd.DesaDocument(autor, titol, contingut);
        gd.ExportaDocument(autor, titol, path, FileFormat.xml);
        String[] result = gd.CarregaDocument(path, FileFormat.xml);

        assertArrayEquals(expected, result);
    }
}
