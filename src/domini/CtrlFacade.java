import java.util.List;
import java.util.Set;
import java.lang.Exception;
import java.lang.String;

import datatypes.PairAutorTitol;

public class CtrlFacade {
    private CtrlDocument cd;
    private CtrlIndex ci;
    private CtrlExpressioBooleana ce;

    static private List<String> SetAList(Set<String> s) {
        List<String> l = new List<String>();
        for (String st : s) {
            l.add(st);
        }
        return l;
    }

    // Constructora
    public CtrlFacade() {
        cd = new CtrlDocument();
        ci = new CtrlIndex();
        ce = new CtrlExpressioBooleana();
    }

    // Interaccio amb capa de persistencia
    public void carregaFitxers(Vector<String> locs) {

    }

    public void exportarFitxer(String autor, String titol, String loc, int format) {

    }

    // Getters de de document
    public List<String> getTitols() {
        Set<String> s = cd.getTitols();
        return SetAList(s);
    }

    public List<String> getAutors() {
        Set<String> s = cd.getAutors();
        return SetAList(s);
    }

    public List<PairAutorTitol> getTitolsAutors() {
        return cd.getClaus();
    }

    public List<String> getContingut(String autor, String titol) {
        return cd.getContingut(autor, titol);
    }

    // Creacio de document
    public void crearDocument(String autor, String titol, F) throws Exception {
        boolean ed = cd.existsDocument(autor, titol);
        if (!ed) {
            cd.crearDocument(autor, titol); // PRE: no existeix Document
            ci.AfegirDoc(autor, titol);
        }
        else
    }

    // Destruccio de documents
    public void esborrarDocuments(List<PairAutorTitol> docs) {
        cd.esborrarDocuments(docs);
        ci.EsborrarDocs(docs);
    }

    // Modificadores de document
    public void modificarTitol(String autor, String titol, String newT) throws Exception {
        cd.modificarTitol(autor, titol, newT);
        ci.ActualitzarTitol(autor, titol, newT);
    }

    public void modificarAutor(String autor, String titol, String newA) throws Exception {
        cd.modificarAutor(autor, titol, newA);
        ci.ActualitzarAutor(autor, titol, newA);
    }

    public void modificarContingut(String autor, String titol, String cont) {
        cd.modificarContingut(autor, titol, cont);
        List<String> c = cd.getContingut(autor, titol);
        ci.ActualitzarContingut(autor, titol, c);
    }

    // Cerques a indexos
    public List<String> llistarTitolsdAutors(String autor) {
        return ci.GetTitolsAutor(autor);
    }

    public List<String> llistarAutorsPrefix(String prefix) {
        return ci.GetAutorsPrefix(prefix);
    }

    public List<PairAutorTitol> llistarKDocumentsS(String autor, String titol, int K) {
        return ci.GetKDocsSimilarS(autor, titol, K);
    }

    public List<PairAutorTitol> cercarExpressioBooleana(String exp) {
        Tree expA = ce.creaArbre(exp); // s'hauria de posar la classe d'arbre corresponent be
        return ci.cercarExpressioBooleana(expA);
    }

    // OPCIONAL
    public List<PairAutorTitol> cercarPerRellevancia(Vector<String> paraules, int K) {
        return null;
    }

    // Getter d'expressio booleana
    public String getExpressioBooleana(String nom) {
        return ce.getExpressioBooleana(nom); // aqui dependra de l'implementacio del ctrl
    }

    // Creadora d'expressio booleana
    public void setExpressioBooleana(String nom, String exp) throws Exception {
        boolean ee = cd.existsDocument(autor, titol);
        if (!ee) ce.setExpressioBooleana(nom, exp); // PRE: no existeix ExpressioBooleana
        else {

        }
    }

    // Destructora d'expressio booleana
    public void deleteExpressioBooleana(String nom) {
        ce.deleteExpressioBooleana(nom);
    }
}
