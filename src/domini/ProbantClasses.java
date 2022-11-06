import datatypes.Pair;
import datatypes.Format;
import datatypes.Document;
import datatypes.Contingut;
import java.util.*;
import java.util.concurrent.*;
public class ProbantClasses {
    public static void main(String[] args) {
        CtrlDocument cD = new CtrlDocument();

        cD.crearDocument("Pep", "Noche");
        cD.crearDocument("Pep", "Dia");
        cD.crearDocument("Pep", "Atardecer");
        cD.crearDocument("Joan", "Vida");
        cD.crearDocument("Alex", "Camino");
        cD.crearDocument("Sara", "Dia");

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
        cD.modificarAutor("Alex", "Camino", "Pep");
        cD.modificarAutor("Pep", "Dia", "Sara");
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
        cD.modificarContingut("La vida es buena. A veces.");
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
        List<Pair<String, String>> docsBorrar = new ArrayList<Pair<String, String>>();
        Pair<String, String> p1 = new Pair<String, String>("Pep", "War"); docsBorrar.add(p1);
        Pair<String, String> p2 = new Pair<String, String>("Pep", "Camino"); docsBorrar.add(p2);
        Pair<String, String> p3 = new Pair<String, String>("Pep", "Atardecer"); docsBorrar.add(p3);
        Pair<String, String> p4 = new Pair<String, String>("Joan", "Dawn"); docsBorrar.add(p4);
        Pair<String, String> p5 = new Pair<String, String>("Joan", "Vida"); docsBorrar.add(p5);
        cD.esborrarDocuments(docsBorrar);
        System.out.println("Autors: " + cD.getAutors());
        System.out.println("Titols: " + cD.getTitols());
        List<Pair<String, String>> claus2 = new ArrayList<Pair<String, String>>();
        claus2 = cD.getClaus();
        System.out.println("Claus:");
        for(Pair<String, String> c2 : claus2) {
            System.out.println("[Autors: " + c2.x + " Titol: " + c2.y + "] ");
        }


    }
}