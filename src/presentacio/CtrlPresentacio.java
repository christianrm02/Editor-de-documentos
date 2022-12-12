package presentacio;

import controladores.CtrlDomini;
import transversal.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CtrlPresentacio {
    private static ViewPrincipal Main;
    private static CtrlDomini cd = new CtrlDomini();

    public static void mostraViewPrincipal(){
        if(Main == null) Main = new ViewPrincipal("Documentator");
        else Main.setVisible(true);
    }

    public static void ocultaViewPrincipal() {Main.setVisible(false);}

    public static void mostraVistaGestioExpBool(JTable documents){
        new VistaGestioExpBool(documents);
    }

    public static void mostraViewMostrarCont(String titol, String autor){
        String cont = getContingut(autor, titol);
        new ViewMostrarCont(titol, autor, cont);
    }

    public static void mostraViewEditar(String titol, String autor){
        String cont = obrirDocument(autor, titol);
        new ViewEditar(titol, autor, cont);
        actualitzaTitol("Test");
        actualitzaAutor("Autor");
    }

    public static void actualitzaTitol(String newT) { //se tiene q comprobarantes si se puede crear
        String titol = Main.getTitolDocObert();
        String autor = Main.getAutorDocObert();
        boolean valid = modificarTitol(titol, autor, newT);

        if(valid) Main.actualitzaTitol(newT);
        else {//pop up error;

        }

        //Main.dispose();
        //mostraViewPrincipal();
    }

    public static void actualitzaAutor(String newA) { //se tiene q comprobarantes si se puede crear
        String titol = Main.getTitolDocObert();
        String autor = Main.getAutorDocObert();
        boolean valid = modificarTitol(titol, autor, newA);

        if(valid) Main.actualitzaAutor(newA);
        else {//pop up error;

        }
    }

    /*Crides a domini*/
    public List<String> getAutors() {
        return cd.getAutors();
    }

    public static List<Pair<String, String>> getTitolsAutors() {
        //return cd.getTitolsAutors();
        Pair<String, String> p1 = new Pair<>();
        Pair<String, String> p2 = new Pair<>();
        Pair<String, String> p3 = new Pair<>();
        Pair<String, String> p4 = new Pair<>();
        Pair<String, String> p5 = new Pair<>();
        Pair<String, String> p6 = new Pair<>();
        p1.x = "Ana"; p1.y = "Historia de una escalera";
        p2.x = "Ana"; p2.y = "La venganza de Don Tenorio";
        p3.x = "Carlos"; p3.y = "La divina comedia";
        p4.x = "Joan"; p4.y = "La divina comedia";
        p5.x = "Pepe"; p5.y = "La ceguera";
        p6.x = "Pepe"; p6.y = "Lo que el viento se llevó";
        List<Pair<String, String>> docs = new ArrayList<>();
        docs.add(p1);
        docs.add(p2);
        docs.add(p3);
        docs.add(p4);
        docs.add(p5);
        docs.add(p6);

        return docs;
    }

    public static String getContingut(String autor, String titol) {
        //return cd.getContingut(autor, titol);
        return "Casa";
    }

    public static String obrirDocument(String autor, String titol) { //QUE STRING DEVUELVE ESTA FUNC? FALTA PONER Q SE LLAME CUANDO SE ABRA DOC
        //return cd.obrirDocument(autor, titol);
        return "HOLA";
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
        //Main.actualitzaTitol(autor, titol, newT);
        //return cd.modificarTitol(autor, titol, newT);
    }

    public static boolean modificarAutor(String autor, String titol, String newA) {
        boolean valid = true;
        return valid;
        //if(valid)
        //return cd.modificarTitol(autor, titol, newA);
    }

    public static void modificarContingut(String autor, String titol, String cont) { //vieweditar
        //cd.modificarContingut(autor, titol, cont);
    }

    public static List<String> llistarTitolsdAutors(String autor) {
        //return cd.llistarTitolsdAutors(autor);
        List<String> titols = List.of(new String[]{"Historia de una escalera", "Ana"});
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
        p.x = "Ana";
        p.y = "Historia de una escalera";
        docs.add(p);
        Pair p2 = new Pair();
        p2.x = "Laura";
        p2.y = "Escaleras";
        docs.add(p2);
        return docs;
    }

    public static List<Pair<String, String>> cercarPerRellevancia(String paraules, int K, boolean estrategia) {
        //return cd.cercarPerRellevancia(paraules, K, estrategia);
        List<Pair<String, String>> docs = new ArrayList<>();
        Pair p = new Pair();
        p.x = "Ana";
        p.y = "Historia de una escalera";
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

    public static boolean creaExpressioBooleana(String nom, String exp) {
        //return cd.setExpressioBooleana(nom, exp);
        return true;
    }

    public static List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        //return cd.cercarExpressioBooleana(exp);
        List<Pair<String, String>> docs = new ArrayList<>();
        Pair p = new Pair();
        p.x = "Ana";
        p.y = "Historia de una escalera";
        docs.add(p);
        Pair p2 = new Pair();
        p2.x = "Laura";
        p2.y = "Escaleras";
        docs.add(p2);
        return docs;
    }

    public static void modExpressioBooleana(String nom, String nExp) {
        //cd.modExpressioBooleana(nom, nExp);
    }

    public static void deleteExpressioBooleana(String nom) {
        //cd.deleteExpressioBooleana(nom);
    }



}
