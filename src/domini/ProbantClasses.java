import controladores.CtrlDocument;
import datatypes.Document;
import datatypes.Pair;

import java.util.*;
public class ProbantClasses {
    public static void main(String[] args) {
        CtrlDocument cD = new CtrlDocument();

        cD.crearDocument("Pep", "Noche");
        cD.crearDocument("Pep", "Dia");
        cD.crearDocument("Pep", "Atardecer");
        cD.crearDocument("Joan", "Vida");
        cD.crearDocument("Alex", "Camino");
        cD.crearDocument("Sara", "Dia");

        cD.getDocument("Pep", "Day");

        System.out.println("Autors: " + cD.getAutors());
        System.out.println("Titols: " + cD.getTitols());
        //System.out.println("Claus: " + cD.getClaus());
        //System.out.println("Existe Pep Noche: " + cD.existsDocument("Pep", "Noche"));
        //System.out.println("Existe Pep asdafsdaad: " + cD.existsDocument("Pep", "asdafsdaad"));

        /*
        List<PairAutorTitol> esborrar = new ArrayList<PairAutorTitol>();
        PairAutorTitol p = new PairAutorTitol("Pep", "asasasa");
        esborrar.add(p);
        p.setTitol("Noche");
        esborrar.add(p);
        cD.esborrarDocuments(esborrar);
        */

        cD.modificarTitol("Pep", "Noche", "War");
        System.out.println(cD.modificarAutor("Alex", "Camino", "Pep")); //true
        System.out.println(cD.modificarAutor("Pep", "Dia", "Sara")); //false
        cD.modificarTitol("Sara", "Dia", "Dawn");
        cD.modificarAutor("Sara", "Dawn", "Joan");
        cD.modificarTitol("Pep", "War", "Camino");

        //System.out.println("Existe Pep Camino: " + cD.existsDocument("Pep", "Camino"));
        //System.out.println("Existe Pep Noche: " + cD.existsDocument("Pep", "Noche"));

        //getClaus
        List<Pair<String, String>> claus = new ArrayList<Pair<String, String>>();
        claus = cD.getClaus();
        System.out.println("Claus:");
        for(Pair<String, String> c : claus) {
            System.out.println("[Autors: " + c.x + " Titol: " + c.y + "] ");
        }

        //getTitolsAutor
        Set<String> ttls = new TreeSet<String>();
        ttls = cD.getTitolsAutor("Pep");
        System.out.println(ttls);

        System.out.println("Autors: " + cD.getAutors());
        System.out.println("Titols: " + cD.getTitols());

        cD.modificarAutor("Joan", "Dawn", "Joan");

        //getDocument
        Document d = new Document();
        d = cD.getDocument("Pep", "Camino");
        System.out.println("Autor d: " + d.getAutor() + " Titol d: " + d.getTitol());

        //getAll, Joan Vida
        /*String txt = "La vida es buena. Solo a veces.";
        Contingut c = new Contingut(txt);
        cD.modificarContingut("Joan", "Vida", c);
        d = cD.getDocument("Joan", "Vida");
        //System.out.println("Autor d: " + d.getAutor() + " Titol d: " + d.getTitol() + " Contingut: " + d.getContingut());

        List<Document> docs = new ArrayList<Document>();
        docs = cD.getAll();
        for (Document doc : docs) {
            if(doc.getAutor() == "Joan" && doc.getTitol() == "Vida") {
                System.out.println("Autor d: " + d.getAutor() + " Titol d: " + d.getTitol() + " Contingut: " + d.getContingut());
            }
        }*/

        //nuevas
        cD.obreDocument("Joan", "Vida");
        cD.modificarContingut("Hola...");
        List<String> oldc = cD.getContingut();
        System.out.println(oldc);
        cD.modificarContingut("VIVA ONE PIECE. Pero no a ratos! Siempre");
        List<String> newc = cD.getContingut();
        System.out.println(newc);
        //System.out.println(cD.getContingut("Joan", "Vida"));
        List<Document> docs = new ArrayList<Document>();
        docs = cD.getAll();
        for (Document doc : docs) {
            if(doc.getAutor() == "Joan" && doc.getTitol() == "Vida") {
                System.out.println("Autor d: " + doc.getAutor() + " Titol d: " + doc.getTitol() + " Contingut: " + doc.getContingut());
            }
        }

        //esborrarDocuments
        System.out.println("BORRANDO TODO");
        cD.esborrarDocument("Pep", "War");
        System.out.println(cD.esborrarDocument("Pep", "Camino")); //devuelve false
        System.out.println(cD.esborrarDocument("Pep", "Atardecer")); //devuelve true
        cD.esborrarDocument("Joan", "Dawn");
        cD.esborrarDocument("Joan", "Vida");
        System.out.println("Autors: " + cD.getAutors());
        System.out.println("Titols: " + cD.getTitols());
        List<Pair<String, String>> claus2 = new ArrayList<Pair<String, String>>();
        claus2 = cD.getClaus();
        System.out.println("Claus:");
        for(Pair<String, String> c2 : claus2) {
            System.out.println("[Autors: " + c2.x + " Titol: " + c2.y + "] ");
        }

        //pairs
        Set<Pair<String, String>> pairs = new TreeSet<Pair<String, String>>();
        Pair<String, String> p1 = new Pair<String, String>("Joan", "A");
        Pair<String, String> p2 = new Pair<String, String>("Joan", "alomejor");
        Pair<String, String> p3 = new Pair<String, String>("Alvaro", "Zas");
        Pair<String, String> p4 = new Pair<String, String>("Alvaro", "Al");
        Pair<String, String> p5 = new Pair<String, String>("Zacarias", "Pinta");
        Pair<String, String> p6 = new Pair<String, String>("Gernio", "S");
        Pair<String, String> p7 = new Pair<String, String>("Gernia", "A");
        Pair<String, String> p8 = new Pair<String, String>("Joan", "alomejor");
        pairs.add(p1);
        pairs.add(p2);
        pairs.add(p3);
        pairs.add(p4);
        pairs.add(p5);
        pairs.add(p6);
        pairs.add(p7);
        for(Pair<String, String> pair2 : pairs) {
            System.out.println("[Autor: " + pair2.x + " Titol: " + pair2.y + "] ");
        }
        /*System.out.println(p1.equals(p2));
        System.out.println(p2.equals(p8));
        System.out.println(p1.equals(p1));*/

        ///////////
        HashMap<Pair<String, String>, Integer> test = new HashMap<>();
        test.put(p1, 1);
        /*test.put(p2, 2);
        test.put(p3, 3);
        test.put(p4, 4);
        test.put(p5, 5);
        test.put(p6, 6);
        test.put(p7, 7);
        test.put(p8, 8);
        for(Pair<String, String> pt : test.keySet()) {
            System.out.println("[Autor: " + pt.x + " Titol: " + pt.y + "] ");
        }

        /*System.out.println("Benvingut al driver de ctrlDocument, per probar un metode ingressa per teclat el seu numero");
        System.out.println("**Constructors**");
        System.out.println("1-  Constructora");
        System.out.println("**Getters**");
        System.out.println("2-  getDocument");
        System.out.println("3-  existsDocument");
        System.out.println("4-  getAll");
        System.out.println("5-  getAutors");
        System.out.println("6-  getTitols");
        System.out.println("7-  getClaus");
        System.out.println("8-  getContingut");
        System.out.println("9-  getTitolsAutor");
        System.out.println("**Setters**");
        System.out.println("10-  crearDocument");
        System.out.println("11-  obreDocument");
        System.out.println("12-  modificarAutor");
        System.out.println("13-  modificarTitol");
        System.out.println("14-  modificarContingut");*/
    }
}