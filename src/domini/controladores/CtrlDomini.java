package controladores;

import persistencia.CtrlPersistencia;
import transversal.*;

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
        //ce.carregarExpB(cp.CarregarExpB);
        return getTitolsAutors();
    }

    public void tancar() {
        cp.ExportarIndexs(ci.ExportarIndexs());
        //cp.guardarExpB(cp.GuardarExpB);
    }

    public List<Pair<String,String>> carregaFitxers(List<String> locs) {
        List<Pair<String,String>> l = new ArrayList<>();
        for (String loc: locs) {
            String[] doc = cp.carregaDocument(loc, FileFormat.txt);
            ci.AfegirDoc(doc[0], doc[1], converteix_a_frases(doc[2]));
            l.add(new Pair<>(doc[0], doc[1]));
        }
        return l;
    }

    public boolean exportarDocument(String autor, String titol, String loc, FileFormat format) {
        cp.exportaDocument(autor, titol, loc, format);
        return false;
    }

    // Getters de document
    public List<String> getAutors() {
        return new ArrayList<>(ci.GetAutorsPrefix(""));
    }

    public List<Pair<String, String>> getTitolsAutors() {
        return new ArrayList<>(ci.GetKeys());
    }

    public String getContingut(String autor, String titol) {
        return cp.getContingut(autor, titol);
    }

    public String obrirDocument(String autor, String titol) {
        titolAct = titol;
        autorAct = autor;
        contAct = cp.getContingut(autor, titol);
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

    public void modificarContingut(String cont) {
        contAct = cont;
        ci.ActualitzarContingut(autorAct, titolAct, converteix_a_frases(cont));
    }

    public void desarDocument() {
        cp.desaDocument(autorAct, titolAct, contAct);
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
        return ci.GetKDocsSimilarS(autor, titol, K, estrategia);
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp) throws Exception {

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
    public boolean setExpressioBooleana(String nom, String exp) throws Exception {
        boolean ee = ce.existsExpressioBooleana(nom);
        if (!ee) ce.setExpressioBooleana(nom, exp); // PRE: no existeix ExpressioBooleana
        return !ee;
    }

    public void modExpressioBooleana(String nom, String nExp) throws Exception {
        ce.setExpressioBooleana(nom, nExp);
    }

    // Destructora d'expressio booleana
    public void deleteExpressioBooleana(String nom) {
        ce.deleteExpressioBooleana(nom);
    }
}