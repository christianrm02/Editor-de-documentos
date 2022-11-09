import datatypes.Document;
import datatypes.Format;
import datatypes.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class CtrlDocumentTest {
    private CtrlDocument cd;

    @Test
    public void getDocument() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Dia");
        assertNull(cd.getDocument("Joan", "Dia"));
        Document d = new Document("Pep", "Dia", Format.txt);
        assertEquals(d, cd.getDocument("Pep", "Dia"));
    }

    @Test
    public void existsDocument() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        assertTrue(cd.existsDocument("Pep", "Noche"));
        assertTrue(cd.existsDocument("Pep", "Dia"));
        assertTrue(cd.existsDocument("Joan", "Vida"));
        assertFalse(cd.existsDocument("Pep", "Camino"));
        assertFalse(cd.existsDocument("Alex", "Dia"));
    }

    @Test
    public void getAll() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        Document d1 = new Document("Alex", "Camino", Format.txt);
        Document d2 = new Document("Joan", "Vida", Format.txt);
        Document d3 = new Document("Pep", "Dia", Format.txt);
        Document d4 = new Document("Pep", "Noche", Format.txt);
        List<Document> docs = new ArrayList<>();
        docs.add(d1); docs.add(d2); docs.add(d3); docs.add(d4);
        assertEquals(docs, cd.getAll());
    }

    @Test
    public void getAutors() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        Set<String> autors = new TreeSet<>();
        autors.add("Pep"); autors.add("Joan"); autors.add("Alex");
        assertEquals(autors, cd.getAutors());
    }

    @Test
    public void getTitols() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        Set<String> titols = new TreeSet<>();
        titols.add("Noche"); titols.add("Dia"); titols.add("Vida"); titols.add("Camino");
        assertEquals(titols, cd.getTitols());
    }

    @Test
    public void getClaus() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        List<Pair<String, String>> claus = new ArrayList<>();
        Pair<String, String> p1 = new Pair<>("Pep", "Noche");
        Pair<String, String> p2 = new Pair<>("Pep", "Dia");
        Pair<String, String> p3 = new Pair<>("Joan", "Vida");
        Pair<String, String> p4 = new Pair<>("Alex", "Camino");
        claus.add(p4); claus.add(p3); claus.add(p2); claus.add(p1);
        assertEquals(claus, cd.getClaus());
    }

    @Test
    public void getContingut() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.modificarContingut("Nou cont.");
        List<String> frases = new ArrayList<>();
        frases.add("Nou cont.");
        assertEquals(frases, cd.getContingut());
    }

    @Test
    public void getTitolsAutor() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        Set<String> titolsPep = new TreeSet<>();
        titolsPep.add("Dia"); titolsPep.add("Noche");
        Set<String> titolsJoan = new TreeSet<>();
        titolsJoan.add("Vida");
        assertEquals(titolsPep, cd.getTitolsAutor("Pep"));
        assertEquals(titolsJoan, cd.getTitolsAutor("Joan"));
    }

    @Test
    public void crearDocument() {
        cd = new CtrlDocument();
        assertFalse(cd.existsDocument("Pep", "Noche"));
        cd.crearDocument("Pep", "Noche");
        assertTrue(cd.existsDocument("Pep", "Noche"));
        assertFalse(cd.existsDocument("Pep", "Dia"));
        cd.crearDocument("Pep", "Dia");
        assertTrue(cd.existsDocument("Pep", "Dia"));
    }

    @Test
    public void obreDocument() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.obreDocument("Pep", "Noche");
        assertEquals(cd.getDocument("Pep", "Noche"), cd.docAct);
        cd.obreDocument("Pep", "Dia");
        assertEquals(cd.getDocument("Pep", "Dia"), cd.docAct);
    }

    @Test
    public void esborrarDocument() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        assertTrue("El document amb claus (Alex, Camino) existeix", cd.existsDocument("Alex", "Camino"));
        assertFalse("L'autor Alex ja no continua al sistema", cd.esborrarDocument("Alex", "Camino"));
        assertFalse("El document amb claus (Alex, Camino) no existeix", cd.existsDocument("Alex", "Camino"));
        assertTrue("L'autor Pep continua al sistema", cd.esborrarDocument( "Pep", "Noche"));
    }

    @Test
    public void modificarAutor() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        assertFalse("El document amb claus (Alex, Vida) no existeix", cd.existsDocument("Alex", "Vida"));
        assertFalse("L'autor Joan ja no continua al sistema", cd.modificarAutor("Joan", "Vida", "Alex"));
        assertFalse("El document amb claus (Alex, Vida) existeix", cd.existsDocument("Alex", "Vida"));
        assertTrue("L'autor Pep continua al sistema", cd.modificarAutor("Pep", "Dia", "Sandra"));
    }

    @Test
    public void modificarTitol() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        assertFalse("El document amb claus (Pep, Day) no existeix", cd.existsDocument("Pep", "Day"));
        cd.modificarTitol("Pep", "Dia", "Day");
        assertTrue("El document amb claus (Pep, Day) existeix", cd.existsDocument("Pep", "Day"));
    }

    @Test
    public void modificarContingut() {
        cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.modificarContingut("Hola, este es el old contenido.");
        List<String> frases = new ArrayList<>();
        frases.add("Hola, este es el old contenido.");
        assertEquals(frases, cd.getContingut());
        cd.modificarContingut("Hola, este es el old contenido. Y este es el nuevo.");
        frases.add("Y este es el nuevo.");
        assertEquals(frases, cd.getContingut());
    }
}