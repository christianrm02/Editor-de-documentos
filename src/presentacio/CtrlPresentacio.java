package presentacio;

import controladores.CtrlDomini;
import excepcions.*;
import transversal.Pair;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CtrlPresentacio {
    private ViewPrincipal Main;
    private ViewGestioExpBool ViewExps;
    private CtrlDomini cd;

    public CtrlPresentacio() {}

    public void mostraViewPrincipal(){
        if(Main == null) {
            Main = new ViewPrincipal("Documentator", this); //si paso instancia este metodo tiene q no puede ser static pero como el main es static, y esta func es llama desde ahi, esta también tiene q ser static, por lo q no puedo pasar this
            cd = new CtrlDomini();
            List<Pair<String, String>> docs = cd.getTitolsAutors();
            Main.initDocs(docs);
        }
        else Main.setVisible(true);
    }

    public void ocultaViewPrincipal() {
        Main.setVisible(false);
    }

    public void mostraVistaGestioExpBool(JTable documents){
        ViewExps = new ViewGestioExpBool(documents);
        List<Pair<String, String>> expsList = getAllExpressionsBooleanes();

        ViewExps.initExp(expsList);
    }

    public void mostraViewMostrarCont(String titol, String autor){
        String cont = getContingut(autor, titol);
        new ViewMostrarCont(titol, autor, cont);
    }

    public void mostraViewEditar(String titol, String autor){
        String cont = obrirDocument(autor, titol);
        new ViewEditar(titol, autor, cont);
        actualitzaTitol("Test");
        actualitzaAutor("Autor");
    }

    public void tancarAplicacio() {
        try {
            System.exit(0); //dudas
            cd.tancar();
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, "No s'ha pogut tancar l'aplicació.",
                    "Error tancar", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualitzaTitol(String newT) { //se tiene q comprobarantes si se puede crear
        String titol = Main.getTitolDocObert();
        String autor = Main.getAutorDocObert();
        boolean valid = modificarTitol(titol, autor, newT);

        if(valid) Main.actualitzaTitol(newT); // no hace falta else con pop up error, ya se lanza en la otra
    }

    public void actualitzaAutor(String newA) { //se tiene q comprobarantes si se puede crear
        String titol = Main.getTitolDocObert();
        String autor = Main.getAutorDocObert();
        boolean valid = modificarTitol(titol, autor, newA);

        if(valid) Main.actualitzaAutor(newA); // no hace falta else con pop up error, ya se lanza en la otra
    }

    /*Crides a domini*/
    public List<String> getAutors() {
        return cd.getAutors();
    }

    /*public List<Pair<String, String>> getTitolsAutors() { //BORRAR??

    }*/

    public String getContingut(String autor, String titol) {
        String cont = null;
        try {
            cont = cd.getContingut(autor, titol);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No s'ha pogut obtenir el contingut del document.",
                    "Error obtenció contingut", JOptionPane.ERROR_MESSAGE);
        }
        return cont;
    }

    public String obrirDocument(String autor, String titol) { //QUE STRING DEVUELVE ESTA FUNC? FALTA PONER Q SE LLAME CUANDO SE ABRA DOC
        String cont = null;
        try {
            cont = cd.obrirDocument(autor, titol);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No s'ha pogut obrir el document.",
                    "Error obrir document", JOptionPane.ERROR_MESSAGE);
        }
        return cont;
        //return cd.obrirDocument(autor, titol);
        //return "HOLA";
    }

    public boolean crearDocument(String autor, String titol){
        boolean valid = true;
        try {
            cd.crearDocument(autor, titol);
        }
        catch (EDocumentException | IOException e){
            if(e instanceof EDocumentException) {
                JOptionPane.showMessageDialog(null, "Ya existeix un document amb aquell títol i autor.",
                        "Error creació document", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Hi ha hagut un error en la creació del document.",
                        "Error creació document", JOptionPane.ERROR_MESSAGE);
            }
            valid = false;
        }
        return valid;
    }

    public void desarDocument() {
        try {
            cd.desarDocument();
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(null, "Hi ha hagut un error al desar el document.",
                    "Error desar document", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Pair<String, String>> importaDocuments(List<String> path) { //path = path+nom+.format
        List<Pair<String, String>> docsImp = null;
        try {
            docsImp = cd.importarDocuments(path);
        }
        catch (EDocumentException | IOException e){
            if(e instanceof EDocumentException) {
                JOptionPane.showMessageDialog(null, "Ya existeix un document amb aquell títol i autor.",
                        "Error importar document", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Hi ha hagut un error al importar el document.",
                        "Error importar document", JOptionPane.ERROR_MESSAGE);
            }
        }
        return docsImp;
    }

    public void exportaDocument(String autor, String titol, String path) { //seria mejor bool y q sea true si todo ok o false si ya existe ese doc?
        try {
            cd.exportarDocument(autor, titol, path);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Hi ha hagut un error, no s'ha pogut exportar.",
                    "Error exportació", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean esborrarDocument(String autor, String titol) {
        boolean esborrat = true;
        try {
            cd.esborrarDocument(autor, titol);
        }
        catch(DeleteDocumentException | IOException e) { //MIRAR SI SON ESTAS LAS Q LANZA
            if(e instanceof DeleteDocumentException) {
                JOptionPane.showMessageDialog(null, "No s'ha pogut esborrar el document.",
                        "Error esborrar document", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "No s'ha pogut esborrar el document.",
                        "Error esborrar document", JOptionPane.ERROR_MESSAGE);
            }
            esborrat = false;
        }
        return esborrat;
    }

    public boolean modificarTitol(String autor, String titol, String newT) {
        boolean valid = true;
        try {
            cd.modificarTitol(autor, titol, newT);
            Main.actualitzaTitol(autor, titol, newT); //??
        }
        catch (EDocumentException e){
            JOptionPane.showMessageDialog(null, "Ja existeix un document amb el nou títol i l'autor.",
                    "Error modificació títol", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        return valid;
    }

    public boolean modificarAutor(String autor, String titol, String newA) {
        boolean valid = true;
        try {
            cd.modificarAutor(autor, titol, newA);
            Main.actualitzaTitol(autor, titol, newA); //??
        }
        catch (EDocumentException e){
            JOptionPane.showMessageDialog(null, "Ja existeix un document amb el títol i el nou autor.",
                    "Error modificació autor", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        return valid;
    }

    public void modificarContingut(String cont) { //vieweditar
        cd.modificarContingut(cont);
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

    public List<Pair<String, String>> cercarPerRellevancia(String paraules, int K, boolean estrategia) {
        return cd.cercarPerRellevancia(paraules, K, estrategia);
    }

    public List<Pair<String, String>> getAllExpressionsBooleanes() {
        //return cd.getAllExpressionsBooleanes();
        List<Pair<String, String>> exps = new ArrayList<>();
        return exps;
    }

    public boolean creaExpressioBooleana(String nom, String exp) {
        boolean valida = true;
        try {
            cd.setExpressioBooleana(nom, exp);
        }
        catch (EExpBoolException | ExpBoolNoValidaException e) {
            if(e instanceof EExpBoolException) {
                JOptionPane.showMessageDialog(null, "Ja existeix una expressió booleana amb aquell nom.",
                        "Error creació expressió", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "La expressió booleana introduïda no és vàlida.",
                        "Error creació expressió", JOptionPane.ERROR_MESSAGE);
            }
            valida = false;
        }
        return valida;
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        List<Pair<String, String>> docs = new ArrayList<>();
        try {
            docs = cd.cercarExpressioBooleana(exp);
        }
        catch(ExpBoolNoValidaException e) {
            JOptionPane.showMessageDialog(null, "Hi ha hagut un error, no s'ha pogut exportar.",
                    "Error exportació", JOptionPane.ERROR_MESSAGE);
        }
        return docs;
    }

    public void modExpressioBooleana(String nom, String nExp) {
        try {
            cd.modExpressioBooleana(nom, nExp);
        }
        catch(ExpBoolNoValidaException e) {
            JOptionPane.showMessageDialog(null, "L'expressió booleana introduïda no és vàlida.",
                    "Error modificar expressió", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteExpressioBooleana(String nom) {
        cd.deleteExpressioBooleana(nom);
    }

    public static void main(String[] args) {
        CtrlPresentacio cp = new CtrlPresentacio();
        cp.mostraViewPrincipal();
    }
}
