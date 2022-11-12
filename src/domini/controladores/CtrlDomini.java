package controladores;

import transversal.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.lang.String;

/**
 * @author Marc Roman
 */
public class CtrlDomini {
    private CtrlDocument cd;
    private CtrlIndex ci;
    private CtrlExpressioBooleana ce;

    // Constructora
    public CtrlDomini() {
        cd = new CtrlDocument();
        ci = new CtrlIndex();
        ce = new CtrlExpressioBooleana();
    }

/*    public void carregaFitxers(List<String> locs) {

    }

    public void exportarFitxer(String autor, String titol, String loc, int format) {

    }*/

    // Getters de de document
    public List<String> getTitols() {
        Set<String> s = cd.getTitols();
        return new ArrayList<>(s);
    }

    public List<String> getAutors() {
        Set<String> s = cd.getAutors();
        return new ArrayList<>(s);
    }

    public List<Pair<String, String>> getTitolsAutors() {
        return cd.getClaus();
    }

    public List<String> getContingut(String autor, String titol) {
        return cd.getContingut(autor, titol);
    }

    public List<String> obrirDocument(String autor, String titol) {
        cd.obreDocument(autor, titol);
        return cd.getContingutObert();
    }

    // Creacio de document
    public boolean crearDocument(String autor, String titol) {
        boolean ed = cd.existsDocument(autor, titol);
        if (!ed) {
            cd.crearDocument(autor, titol); // PRE: no existeix Document
            ci.AfegirDoc(autor, titol, new ArrayList<>());
        }
        return !ed;
    }

    // Destruccio de documents
    public void esborrarDocuments(List<Pair<String, String>> docs) {
        Set<String> a = new TreeSet<>();
        for (Pair<String, String> p : docs) {
            if (cd.esborrarDocument(p.x, p.y)) a.add(p.x);
            ci.EsborrarDoc(p.x, p.y);
        }
        ci.EsborrarAutors(new ArrayList<>(a));
    }

    // Modificadores de document
    public boolean modificarTitol(String autor, String titol, String newT) {
        boolean ed = cd.existsDocument(autor, newT);
        if (!ed) {
            cd.modificarTitol(autor, titol, newT);
            ci.ActualitzarTitol(autor, titol, newT);
        }
        return !ed;
    }

    public boolean modificarAutor(String autor, String titol, String newA) {
        boolean ed = cd.existsDocument(newA, titol);
        if (!ed) {
            boolean asegueix = cd.modificarAutor(autor, titol, newA);
            ci.ActualitzarAutor(autor, titol, newA);
            List<String> a = new ArrayList<>(); a.add(autor);
            if (!asegueix) ci.EsborrarAutors(a);
        }
        return !ed;
    }

    public void modificarContingut(String autor, String titol, String cont) {
        cd.modificarContingut(cont);
        List<String> c = cd.getContingutObert();
        ci.ActualitzarContingut(autor, titol, c);
    }

    // Cerques
    public List<String> llistarTitolsdAutors(String autor) {
        Set<String> a = cd.getTitolsAutor(autor);
        return new ArrayList<>(a);
    }

    public List<String> llistarAutorsPrefix(String prefix) {
        return ci.GetAutorsPrefix(prefix);
    }

    public List<Pair<String, String>> llistarKDocumentsS(String autor, String titol, int K, boolean strategy) {
        if (K < 1) return null;
        return ci.GetKDocsSimilarS(autor, titol, K, strategy);
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        return ce.cercarExpressioBooleana(exp, ci);
    }

/*    // OPCIONAL
    public List<Pair<String, String>> cercarPerRellevancia(List<String> paraules, int K) {
        return null;
    }*/

    // Getter d'expressions booleanes
    public List<Pair<String, String>> getAllExpressionsBooleanes() {
        return ce.getAll();
    }

    // Creadora d'expressio booleana
    public boolean setExpressioBooleana(String nom, String exp) {
        boolean ee = ce.existsExpressioBooleana(nom);
        if (!ee) ce.setExpressioBooleana(nom, exp); // PRE: no existeix ExpressioBooleana
        return !ee;
    }

    public void modExpressioBooleana(String nom, String nExp) {
        ce.setExpressioBooleana(nom, nExp);
    }

    // Destructora d'expressio booleana
    public void deleteExpressioBooleana(String nom) {
        ce.deleteExpressioBooleana(nom);
    }
}