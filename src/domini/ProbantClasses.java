import datatypes.PairAutorTitol;
import datatypes.Format;
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

        System.out.println("Autors: " + cD.getAutors());
        System.out.println("Titols: " + cD.getTitols());
        System.out.println("Claus: " + cD.getClaus());
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
        //System.out.println("Existe Pep Camino: " + cD.existsDocument("Pep", "Camino"));
        //System.out.println("Existe Pep Noche: " + cD.existsDocument("Pep", "Noche"));

        System.out.println("Autors: " + cD.getAutors());
        System.out.println("Titols: " + cD.getTitols());
    }
}