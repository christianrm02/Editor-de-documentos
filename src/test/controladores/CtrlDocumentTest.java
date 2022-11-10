package controladores;

import datatypes.Document;
import datatypes.Format;
import datatypes.Pair;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CtrlDocumentTest {

    @Test
    public void getDocument() {
        CtrlDocument cd = new CtrlDocument();
        cd.crearDocument("Pep", "Dia");
        assertNull(cd.getDocument("Pep", "Day"));
        assertNull(cd.getDocument("Joan", "Day"));
        Document d = new Document("Pep", "Dia", Format.txt);
        assertEquals(d, cd.getDocument("Pep", "Dia"));
    }

    @Test
    public void existsDocument() {
        CtrlDocument cd = new CtrlDocument();
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
        CtrlDocument cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        Document d1 = new Document("Alex", "Camino", Format.txt);
        Document d2 = new Document("Joan", "Vida", Format.txt);
        Document d3 = new Document("Pep", "Dia", Format.txt);
        Document d4 = new Document("Pep", "Noche", Format.txt);
        List<Document> docs = new ArrayList<>();
        //docs.add(d1); docs.add(d2); docs.add(d3); docs.add(d4);
        docs = cd.getAll();

        System.out.println(d1.getAutor());
        System.out.println(docs.get(0).getAutor());
        System.out.println(d1.getTitol());
        System.out.println(docs.get(0).getTitol());
        for(int i = 1; i < 5; ++i) {
            switch (i) {
                case 1: {
                    assertEquals(d1, docs.get(0));
                    break;
                }
                case 2: {
                    assertEquals(d2, docs.get(1));
                    break;
                }
                case 3: {
                    assertEquals(d3, docs.get(2));
                    break;
                }
                case 4: {
                    assertEquals(d4, docs.get(3));
                    break;
                }
                default: {
                    break;
                }
            }
        }

    }

    @Test
    public void getAutors() {
        CtrlDocument cd = new CtrlDocument();
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
        CtrlDocument cd = new CtrlDocument();
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
        CtrlDocument cd = new CtrlDocument();
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

    //mock
    @Test
    public void getContingut() {
        Document d = mock(Document.class);
        when(d.getContingut()).thenReturn(Collections.singletonList("Vaca Nou cont.")); //no funciona bien el mock

        CtrlDocument cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.modificarContingut("Nou cont.");
        List<String> frases = new ArrayList<>();
        frases.add("Nou cont.");
        assertEquals(frases, cd.getContingut());
    }

    @Test
    public void getTitolsAutor() {
        CtrlDocument cd = new CtrlDocument();
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
        CtrlDocument cd = new CtrlDocument();
        assertFalse(cd.existsDocument("Pep", "Noche"));
        cd.crearDocument("Pep", "Noche");
        assertTrue(cd.existsDocument("Pep", "Noche"));
        assertFalse(cd.existsDocument("Pep", "Dia"));
        cd.crearDocument("Pep", "Dia");
        assertTrue(cd.existsDocument("Pep", "Dia"));
    }

    @Test
    public void obreDocument() {
        CtrlDocument cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.obreDocument("Pep", "Noche");
        assertEquals(cd.getDocument("Pep", "Noche"), cd.docAct);
        cd.obreDocument("Pep", "Dia");
        assertEquals(cd.getDocument("Pep", "Dia"), cd.docAct);
    }

    @Test
    public void esborrarDocument() {
        CtrlDocument cd = new CtrlDocument();
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
        CtrlDocument cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        assertFalse("El document amb claus (Alex, Vida) no existeix", cd.existsDocument("Alex", "Vida"));
        assertFalse("L'autor Joan ja no continua al sistema", cd.modificarAutor("Joan", "Vida", "Alex"));
        assertTrue("El document amb claus (Alex, Vida) existeix", cd.existsDocument("Alex", "Vida"));
        assertTrue("L'autor Pep continua al sistema", cd.modificarAutor("Pep", "Dia", "Sandra"));
    }

    @Test
    public void modificarTitol() {
        CtrlDocument cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.crearDocument("Pep", "Dia");
        cd.crearDocument("Joan", "Vida");
        cd.crearDocument("Alex", "Camino");
        assertFalse("El document amb claus (Pep, Day) no existeix", cd.existsDocument("Pep", "Day"));
        cd.modificarTitol("Pep", "Dia", "Day");
        assertTrue("El document amb claus (Pep, Day) existeix", cd.existsDocument("Pep", "Day"));
    }

    //mock
    @Test
    public void modificarContingut() {
        Document d = mock(Document.class);
        when(d.getContingut()).thenReturn(Collections.singletonList("Hola, este es el old contenido."));

        CtrlDocument cd = new CtrlDocument();
        cd.crearDocument("Pep", "Noche");
        cd.modificarContingut("Hola, este es el old contenido.");
        List<String> frases = new ArrayList<>();
        frases.add("Hola, este es el old contenido.");
        assertEquals(frases, cd.getContingut());
    }

    /*public static void main(String[] args) {
        CtrlDocumentTest cdt = new CtrlDocumentTest();
        cdt.modificarTitol();
        cdt.modificarAutor();
        cdt.modificarContingut();

    }*/
}