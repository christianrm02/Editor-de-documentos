package presentacio;

import controladores.CtrlDomini;
import transversal.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CtrlPresentacio {
    private JFrame Main;
    private static CtrlDomini cd = new CtrlDomini();

    public static void mostraVistaGestioExpBool(){
        new VistaGestioExpBool();
    }

    public static void mostraViewMostrarCont(String titol, String autor){
        String cont = getContingut(autor, titol);
        new ViewMostrarCont(titol, autor, cont);
    }

    public static void mostraViewEditar(String titol, String autor){
        String cont = getContingut(autor, titol);
        new ViewEditar(titol, autor, cont);
    }

    /*Crides a domini*/
    public List<String> getAutors() {
        return cd.getAutors();
    }

    public List<Pair<String, String>> getTitolsAutors() {
        return cd.getTitolsAutors();
    }

    public static String getContingut(String autor, String titol) {
        //return cd.getContingut(autor, titol);
        return "Casa";
    }

    public String obrirDocument(String autor, String titol) {
        return cd.obrirDocument(autor, titol);
    }

    public static boolean crearDocument(String autor, String titol, String cont) {
        return true;
        //return cd.crearDocument(autor, titol, cont);
    }

    public static List<String> importaDocument(String path) {
        /*List<String> docImp = cd.importaDocument(path); //devuelve autor, titulo, contenido
          if(!cd.crearDocument(docImp[0], docImp[1], docImp[2])) docImp[2] = "null"; //para saber si ya existe el titulo + autor marco el contenido con esa key
          return docImp;
        */
        List<String> docImp = List.of(new String[]{"Pepe", "Dia", "null"});
        return docImp;
    }

    public static boolean exportaDocument(String titol, String autor, String nomDoc, String path) { //seria mejor bool y q sea true si todo ok o false si ya existe ese doc?
        //cd.exportaDocument(titol, autor, nomDoc, path); //el ctrl de persistencia tendria q conseguir el contenido del doc tambien
        return false;
    }

    public static void esborrarDocuments(List<Pair<String, String>> docs) {
        //cd.esborrarDocuments(docs);
    }

    public static boolean modificarTitol(String autor, String titol, String newT) {
        return true;
        //return cd.modificarTitol(autor, titol, newT);
    }

    public static boolean modificarAutor(String autor, String titol, String newA) {
        return false;
        //return cd.modificarTitol(autor, titol, newA);
    }

    public static void modificarContingut(String autor, String titol, String cont) { //vieweditar
        //cd.modificarContingut(autor, titol, cont);
    }

    public static List<String> llistarTitolsdAutors(String autor) {
        //return cd.llistarTitolsdAutors(autor);
        List<String> titols = List.of(new String[]{"Dia", "Nit"});
        return titols;
    }

    public static List<String> llistarAutorsPrefix(String prefix) {
        //return cd.llistarAutorsPrefix(prefix);
        List<String> autors = List.of(new String[]{"Pepe", "Laura"});
        return autors;
    }

    public static List<Pair<String, String>> llistarKDocumentsS(String autor, String titol, int K, boolean estrategia) {
        //return cd.llistarKDocumentsS(autor, titol, K, estrategia);
        List<Pair<String, String>> docs = new ArrayList<>();
        Pair p = new Pair();
        p.x = "Pepe";
        p.y = "Dia";
        docs.add(p);
        Pair p2 = new Pair();
        p2.x = "Laura";
        p2.y = "Escaleras";
        docs.add(p2);
        return docs;
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        return cd.cercarExpressioBooleana(exp);
    }

    public static List<Pair<String, String>> cercarPerRellevancia(String paraules, int K, boolean estrategia) {
        //return cd.cercarPerRellevancia(paraules, K, estrategia);
        List<Pair<String, String>> docs = new ArrayList<>();
        Pair p = new Pair();
        p.x = "Pepe";
        p.y = "Dia";
        docs.add(p);
        Pair p2 = new Pair();
        p2.x = "Laura";
        p2.y = "Escaleras";
        docs.add(p2);
        return docs;
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
