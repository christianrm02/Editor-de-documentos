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
        List<Pair> claus = new ArrayList<Pair>();
        claus = cD.getClaus();
        System.out.println("Claus:");
        for(Pair c : claus) {
            System.out.println("[Autors: " + c.x + " Titol: " + c.y + "] ");
        }

        System.out.println("Autors: " + cD.getAutors());
        System.out.println("Titols: " + cD.getTitols());

        cD.modificarAutor("Joan", "Dawn", "Joan");

        //getDocument
        Document d = new Document();
        d = cD.getDocument("Pep", "Camino");
        System.out.println("Autor d: " + d.getAutor() + " Titol d: " + d.getTitol());

        //getAll
        String txt = """
                La vida. Que es la vida? Mi vida eres tu...
                Aunque
                Tambien...
                Soy yo.
                """;
        Contingut c = new Contingut(txt);
        cD.modificarContingut("Joan", "Vida", c);
        d = cD.getDocument("Joan", "Vida");
        //System.out.println("Autor d: " + d.getAutor() + " Titol d: " + d.getTitol() + " Contingut: " + d.getContingut().getFrases());

        List<Document> docs = new ArrayList<Document>();
        docs = cD.getAll();
        for (Document doc : docs) {
            if(doc.getAutor() == "Joan" && doc.getTitol() == "Vida") {
                System.out.println("Autor d: " + d.getAutor() + " Titol d: " + d.getTitol() + " Contingut: " + d.getContingut().getFrases());
            }
        }

        //esborrarDocuments
        System.out.println("BORRANDO TODO");
        List<Pair> docsBorrar = new ArrayList<Pair>();
        Pair p1 = new Pair("Pep", "War"); docsBorrar.add(p1);
        Pair p2 = new Pair("Pep", "Camino"); docsBorrar.add(p2);
        Pair p3 = new Pair("Pep", "Atardecer"); docsBorrar.add(p3);
        Pair p4 = new Pair("Joan", "Dawn"); docsBorrar.add(p4);
        Pair p5 = new Pair("Joan", "Vida"); docsBorrar.add(p5);
        cD.esborrarDocuments(docsBorrar);
        System.out.println("Autors: " + cD.getAutors());
        System.out.println("Titols: " + cD.getTitols());
        List<Pair> claus2 = new ArrayList<Pair>();
        claus2 = cD.getClaus();
        System.out.println("Claus:");
        for(Pair c2 : claus2) {
            System.out.println("[Autors: " + c2.x + " Titol: " + c2.y + "] ");
        }

    }
}