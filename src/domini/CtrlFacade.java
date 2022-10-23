import java.util.Vector;
import java.util.Set;
public class CtrlFacade {
    public ctrlFacade() {

    }

    public void carregaFitxers(Vector<String> locs) {

    }

    public Vector<String> getTitols() {

    }

    public Vector<String> getAutors() {

    }

    public Vector<Pair<String, String>> getTitolsAutors() {

    }

    public String getContingut(String titol, String autor) {

    }

    public String creaDocument(String titol, String autor) {

    }

    public void exportaFitxer(String titol, String autor, String loc, int format) {

    }

    public void esborrarDocuments(Vector<Pair<String, String>> docs) {

    }

    public void modificarTitol(String titol, String autor, String newT) {

    }
    public void modificarAutor(String titol, String autor, String newA) {

    }
    public void modificarContingut(String titol, String autor, String cont) {

    }

    public Vector<String> llistarTitolsdAutor(String autor) {

    }
    public Vector<String> llistarAutorsPrefix(String prefix) {

    }
    public Vector<Pair<String,String>> llistarKDocumentsS(String titol, String autor, int K) {

    }

    public Vector<Pair<String,String>> cercaExpressioBooleana(exp: String) {

    }
    public Vector<Pair<String,String>> cercaPerRellevancia(Vector<String> paraules, int K) {

    }

    public String getExpressioBooleana(String nom) {

    }

    public void setExpressioBooleana(String nom, String exp) {

    }

    public void deleteExpressioBooleana(String nom) {

    }
}
