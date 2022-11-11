package controladores;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.lang.String;

import datatypes.*;

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

    // Interaccio amb capa de persistencia
    public void carregaFitxers(List<String> locs) {

    }

    public void exportarFitxer(String autor, String titol, String loc, int format) {

    }

    // Getters de de document
    public List<String> getTitols() {
        Set<String> s = cd.getTitols();
        List<String> tit = new ArrayList<String>(s);
        return tit;
    }

    public List<String> getAutors() {
        Set<String> s = cd.getAutors();
        List<String> aut = new ArrayList<String>(s);
        return aut;
    }

    public List<Pair<String, String>> getTitolsAutors() {
        return cd.getClaus();
    }

    public List<String> obrirDocument(String autor, String titol) {
        cd.obreDocument(autor, titol);
        return cd.getContingut();
    }

    // Creacio de document
    public boolean crearDocument(String autor, String titol/*, string format*/) {
        boolean ed = cd.existsDocument(autor, titol);
        if (!ed) {
            cd.crearDocument(autor, titol/*, format*/); // PRE: no existeix Document
            ci.AfegirDoc(autor, titol, new ArrayList<String>());
        }
        else return false;
        return true;
    }

    // Destruccio de documents
    public void esborrarDocuments(List<Pair<String, String>> docs) {
        Set<String> a = new TreeSet<String>();
        for (Pair<String, String> p : docs) {
            if (cd.esborrarDocument(p.x, p.y)) a.add(p.x);
            ci.EsborrarDoc(p.x, p.y);
        }
        List<String> aut = new ArrayList<String>(a);
        ci.EsborrarAutors(aut);
    }

    // Modificadores de document
    public boolean modificarTitol(String autor, String titol, String newT) {
        boolean ed = cd.existsDocument(autor, newT);
        if (!ed) {
            cd.modificarTitol(autor, titol, newT);
            ci.ActualitzarTitol(autor, titol, newT);
        }
        else return false;
        return true;
    }

    public boolean modificarAutor(String autor, String titol, String newA) {
        boolean ed = cd.existsDocument(newA, titol);
        if (!ed) {
            boolean asegueix = cd.modificarAutor(autor, titol, newA);
            ci.ActualitzarAutor(autor, titol, newA);
            List<String> a = new ArrayList<String>(); a.add(autor);
            if (!asegueix) ci.EsborrarAutors(a);
        }
        else return false;
        return true;
    }

    public void modificarContingut(String autor, String titol, String cont) {
        cd.modificarContingut(cont);
        List<String> c = cd.getContingut();
        ci.ActualitzarContingut(autor, titol, c);
    }

    // Cerques
    public List<String> llistarTitolsdAutors(String autor) {
        Set<String> a = cd.getTitolsAutor(autor);
        List<String> aut = new ArrayList<String>(a);
        return aut;
    }

    public List<String> llistarAutorsPrefix(String prefix) {
        return ci.GetAutorsPrefix(prefix);
    }

    public List<Pair<String, String>> llistarKDocumentsS(String autor, String titol, int K) {
        if (K < 1) return null;
        return ci.GetKDocsSimilarS(autor, titol, K);
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        return ce.cercarExpressioBooleana(exp, ci);
    }

/*    // OPCIONAL
    public List<Pair<String, String>> cercarPerRellevancia(List<String> paraules, int K) {
        return null;
    }*/

    // Getter d'expressio booleana
    public String getExpressioBooleana(String nom) {
        return ce.getExpressioBooleana(nom);
    }

    // Creadora d'expressio booleana
    public boolean setExpressioBooleana(String nom, String exp) {
        boolean ee = ce.existsExpressioBooleana(nom);
        if (!ee) ce.setExpressioBooleana(nom, exp); // PRE: no existeix ExpressioBooleana
        else return false;
        return true;
    }

    public void modExpressioBooleana(String nom, String nExp) {
        ce.setExpressioBooleana(nom, nExp);
    }

    // Destructora d'expressio booleana
    public void deleteExpressioBooleana(String nom) {
        ce.deleteExpressioBooleana(nom);
    }
}