import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.lang.Exception;
import java.lang.String;

import datatypes.*;

public class CtrlFacade {
    private CtrlDocument cd;
    private CtrlIndex ci;
    private CtrlExpressioBooleana ce;

    static private List<String> SetAList(Set<String> s) {
        List<String> l = new ArrayList<String>();
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
    public void carregaFitxers(List<String> locs) {

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

    public List<Pair<String, String>> getTitolsAutors() {
        return cd.getClaus();
    }

    public List<String> obreDocument(String autor, String titol) {
        cd.obreDocument(autor, titol);
        return cd.getContingut();
    }

    // Creacio de document
    public void crearDocument(String autor, String titol/*, string format*/) throws Exception {
        boolean ed = cd.existsDocument(autor, titol);
        if (!ed) {
            cd.crearDocument(autor, titol/*, format*/); // PRE: no existeix Document
            ci.AfegirDoc(autor, titol, new ArrayList<String>());
        }
        else throw new Exception();
    }

    // Destruccio de documents
    public void esborrarDocuments(List<Pair<String, String>> docs) {
        Set<String> a = new TreeSet<String>();
        for (Pair<String, String> p : docs) {
            boolean asegueix = cd.esborrarDocument(p.x, p.y);
            ci.EsborrarDoc(p.x, p.y);
            if (!asegueix) a.add(p.x);
        }
        List<String> autors = SetAList(a);
        ci.EsborrarAutors(autors);
    }

    // Modificadores de document
    public void modificarTitol(String autor, String titol, String newT) throws Exception {
        boolean ed = cd.existsDocument(autor, newT);
        if (!ed) {
            cd.modificarTitol(autor, titol, newT);
            ci.ActualitzarTitol(autor, titol, newT);
        }
        else throw new Exception();
    }

    public void modificarAutor(String autor, String titol, String newA) throws Exception {
        boolean ed = cd.existsDocument(newA, titol);
        if (!ed) {
            boolean asegueix = cd.modificarAutor(autor, titol, newA);
            ci.ActualitzarAutor(autor, titol, newA);
            List<String> a = new ArrayList<String>(); a.add(autor);
            if (!asegueix) ci.EsborrarAutors(a);
        }
        else throw new Exception();
    }

    public void modificarContingut(String autor, String titol, String cont) {
        List<String> oldC = cd.getContingut();
        cd.modificarContingut(cont);
        List<String> c = cd.getContingut();
        ci.ActualitzarContingut(autor, titol, c);
    }

    // Cerques a indexos
    public List<String> llistarTitolsdAutors(String autor) {
        return cd.getTitolsAutor(autor);
    }

    public List<String> llistarAutorsPrefix(String prefix) {
        return ci.GetAutorsPrefix(prefix);
    }

    public List<Pair<String, String>> llistarKDocumentsS(String autor, String titol, int K) throws Exception {
        if (K < 1) throw new Exception();
        return ci.GetKDocsSimilarS(autor, titol, K);
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        return ce.cercarExpressioBooleana(exp);
    }

    // OPCIONAL
    public List<Pair<String, String>> cercarPerRellevancia(List<String> paraules, int K) {
        return null;
    }

    // Getter d'expressio booleana
    public String getExpressioBooleana(String nom) {
        return ce.getExpressioBooleana(nom); // aqui dependra de l'implementacio del ctrl
    }

    // Creadora d'expressio booleana
    public void setExpressioBooleana(String nom, String exp) throws Exception {
        boolean ee = ce.existsExpressioBooleana(nom);
        if (!ee) ce.setExpressioBooleana(nom, exp); // PRE: no existeix ExpressioBooleana
        else throw new Exception();
    }

    public void modExpressioBooleana(String nom, String nExp) {
        ce.setExpressioBooleana(nom, nExp);
    }

    // Destructora d'expressio booleana
    public void deleteExpressioBooleana(String nom) {
        ce.deleteExpressioBooleana(nom);
    }
}
