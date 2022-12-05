package presentacio;

import controladores.CtrlDomini;
import transversal.Pair;

import javax.swing.*;
import java.util.List;

public class CtrlPresentacio {
    private JFrame Main;
    private static CtrlDomini cd = new CtrlDomini();


    /*Crides a domini*/
    public List<String> getAutors() {
        return cd.getAutors();
    }

    public List<Pair<String, String>> getTitolsAutors() {
        return cd.getTitolsAutors();
    }

    public String getContingut(String autor, String titol) {
        return cd.getContingut(autor, titol);
    }

    public String obrirDocument(String autor, String titol) {
        return cd.obrirDocument(autor, titol);
    }

    public boolean crearDocument(String autor, String titol, String cont) {
        return cd.crearDocument(autor, titol, cont);
    }

    public void importaDocument(String path) {

    }

    public void esborrarDocuments(List<Pair<String, String>> docs) {
        cd.esborrarDocuments(docs);
    }

    public boolean modificarTitol(String autor, String titol, String newT) {
        return cd.modificarTitol(autor, titol, newT);
    }

    public boolean modificarAutor(String autor, String titol, String newA) {
        return cd.modificarTitol(autor, titol, newA);
    }

    public void modificarContingut(String autor, String titol, String cont) { //s'ha de mirar q mes ha de fer
        cd.modificarContingut(autor, titol, cont);
    }

    public List<String> llistarTitolsdAutors(String autor) {
        return cd.llistarTitolsdAutors(autor);
    }

    public List<String> llistarAutorsPrefix(String prefix) {
        return cd.llistarAutorsPrefix(prefix);
    }

    public List<Pair<String, String>> llistarKDocumentsS(String autor, String titol, int K, boolean estrategia) {
        return cd.llistarKDocumentsS(autor, titol, K, estrategia);
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        return cd.cercarExpressioBooleana(exp);
    }

    public List<Pair<String, String>> cercarPerRellevancia(String paraules, int K, boolean estrategia) {
        return cd.cercarPerRellevancia(paraules, K, estrategia);
    }

    public List<Pair<String, String>> getAllExpressionsBooleanes() {
        return cd.getAllExpressionsBooleanes();
    }

    public boolean setExpressioBooleana(String nom, String exp) {
        return cd.setExpressioBooleana(nom, exp);
    }

    public void modExpressioBooleana(String nom, String nExp) {
        cd.modExpressioBooleana(nom, nExp);
    }

    public void deleteExpressioBooleana(String nom) {
        cd.deleteExpressioBooleana(nom);
    }



}
