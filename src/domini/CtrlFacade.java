import domini.CtrlDocument;
import domini.CtrlIndex;
import domini.CtrlExpressioBooleana;

import java.util.Vector;

public class CtrlFacade {
    private CtrlDocument cd;
    private CtrlIndex ci;
    private CtrlExpressioBooleana ce;

    public ctrlFacade() {
        cd = new CtrlDocument();
        ci = new CtrlIndex();
        ce = new CtrlExpressioBooleana();
    }

    public void carregaFitxers(Vector<String> locs) {

    }

    public Vector<String> getTitols() {
        return cd.getTitols();
    }

    public Vector<String> getAutors() {
        return cd.getAutors();
    }

    public Vector<Pair<String, String>> getTitolsAutors() {
        return cd.getClaus();
    }

    public String getContingut(String titol, String autor) {
        return cd.getContingut(titol, autor);
    }

    public void crearDocument(String titol, String autor) throws Exception {
        cd.crearDocument(titol, autor);
        ci.afegirDoc(titol, autor);
    }

    public void exportarFitxer(String titol, String autor, String loc, int format) {

    }

    public void esborrarDocuments(Vector<Pair<String, String>> docs) {
        cd.esborrarDocuments(docs);
        ci.esborrarDocs(docs);
    }

    public void modificarTitol(String titol, String autor, String newT) throws Exception {
        cd.modificarTitol(titol, autor, newT);
        ci.actualitzarTitol(titol, autor, newT);
    }

    public void modificarAutor(String titol, String autor, String newA) throws Exception {
        cd.modificarAutor(titol, autor, newA);
        ci.actualitzarAutor(titol, autor, newA);
    }

    public void modificarContingut(String titol, String autor, String cont) {
        cd.modificarContingut(titol, autor, cont);
        ci.actualitzarContingut(titol, autor, cont);
    }

    public Vector<String> llistarTitolsdAutors(String autor) {
        return ci.getTitols(autor);
    }

    public Vector<String> llistarAutorsPrefix(String prefix) {
        return ci.getAutorsPrefix(prefix);
    }

    public Vector<Pair<String,String>> llistarKDocumentsS(String titol, String autor, int K) {
        return ci.KDocsS(titol, autor, K);
    }

    public Vector<Pair<String,String>> cercarExpressioBooleana(String exp) {
        return ci.cercarExpressioBooleana(exp); // com fem per passar aqui de string a arbre?
    }

    public Vector<Pair<String,String>> cercarPerRellevancia(Vector<String> paraules, int K) {

    }

    public String getExpressioBooleana(String nom) {
        return ce.getExpressioBooleana(nom).exp(); // aqui dependra de l'implementacio del ctrl
    }

    public void setExpressioBooleana(String nom, String exp) throws Exception {
        ce.setExpressioBooleana(nom, exp);
    }

    public void deleteExpressioBooleana(String nom) {
        ce.deleteExpressioBooleana(nom);
    }
}
