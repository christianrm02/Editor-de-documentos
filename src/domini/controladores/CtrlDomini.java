package controladores;

import persistencia.CtrlPersistencia;
import transversal.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static datatypes.Utility.converteix_a_frases;

/**
 *
 * @author Marc Roman
 */
public class CtrlDomini {
    private CtrlIndex ci;
    private CtrlExpressioBooleana ce;
    private CtrlPersistencia cp;
    private String titolAct, autorAct, contAct;

    // Constructora
    public CtrlDomini() {
        ci = new CtrlIndex();
        ce = new CtrlExpressioBooleana();
        cp = new CtrlPersistencia();
    }

    public List<Pair<String, String>> init() {
        ci.ImportarIndexs(cp.ImportarIndexs());
        return getTitolsAutors();
    }

    public void tancar() {
        cp.ExportarIndexs(ci.ExportarIndexs());
    }

/*    public void carregaFitxers(List<String> locs) {

    }

    public void exportarFitxer(String autor, String titol, String loc, Format format) {

    }*/

    // Getters de document
    public List<String> getAutors() {
        return new ArrayList<>(ci.GetAutorsPrefix(""));
    }

    public List<Pair<String, String>> getTitolsAutors() {
        return new ArrayList<>(ci.GetKeys());
    }

    public String getContingut(String autor, String titol) {
        return null;
    }

    public String obrirDocument(String autor, String titol) { // s'haura de mirar
        titolAct = titol;
        autorAct = autor;
        contAct = null;
        return contAct;
    }

    // Creacio de document
    public boolean crearDocument(String autor, String titol, String cont) {
        boolean ed = ci.FindDoc(autor, titol);
        if (!ed) {
            ci.AfegirDoc(autor, titol, converteix_a_frases(cont));
        }
        return !ed;
    }

    // Destruccio de documents
    public void esborrarDocuments(List<Pair<String, String>> docs) {
        for (Pair<String, String> p : docs) {
            ci.EsborrarDoc(p.x, p.y);
        }
    }

    // Modificadores de document
    public boolean modificarTitol(String autor, String titol, String newT) {
        boolean ed = ci.FindDoc(autor, newT);
        if (!ed) {
            ci.ActualitzarTitol(autor, titol, newT);
        }
        return !ed;
    }

    public boolean modificarAutor(String autor, String titol, String newA) {
        boolean ed = ci.FindDoc(newA, titol);
        if (!ed) {
            ci.ActualitzarAutor(autor, titol, newA);
        }
        return !ed;
    }

    public void modificarContingut(String autor, String titol, String cont) { //s'ha de mirar q mes ha de fer
        ci.ActualitzarContingut(autor, titol, converteix_a_frases(cont));
    }

    // Cerques
    public List<String> llistarTitolsdAutors(String autor) {
        Set<String> a = ci.GetTitolsAutor(autor);
        return new ArrayList<>(a);
    }

    public List<String> llistarAutorsPrefix(String prefix) {
        return new ArrayList<>(ci.GetAutorsPrefix(prefix));
    }

    public List<Pair<String, String>> llistarKDocumentsS(String autor, String titol, int K, boolean estrategia) {
        if (K < 1) return null;
        return ci.GetKDocsSimilarS(autor, titol, K, estrategia);
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        return ce.cercarExpressioBooleana(exp, ci);
    }

    // OPCIONAL
    public List<Pair<String, String>> cercarPerRellevancia(String paraules, int K, boolean estrategia) {
        return ci.CercaPerRellevancia(paraules, K, estrategia);
    }

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