package presentacio;

import controladores.CtrlDomini;
import excepcions.*;
import transversal.Pair;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CtrlPresentacio {
    private ViewPrincipal viewPrincipal;
    private ViewGestioExpBool ViewExps;
    private CtrlDomini cd;

    public CtrlPresentacio() {}

    public void mostraViewPrincipal(){
        if(viewPrincipal == null) {
            viewPrincipal = new ViewPrincipal("Documentator", this); //si paso instancia este metodo tiene q no puede ser static pero como el viewPrincipal es static, y esta func es llama desde ahi, esta también tiene q ser static, por lo q no puedo pasar this
            cd = new CtrlDomini();
            List<Pair<String, String>> docs = cd.getTitolsAutors();
            viewPrincipal.initDocs(docs);
        }
        else viewPrincipal.setVisible(true);
    }

    public void ocultaViewPrincipal() {
        viewPrincipal.setVisible(false);
    }

    public void mostraVistaGestioExpBool(JTable documents){
        ViewExps = new ViewGestioExpBool(documents, this);
        List<Pair<String, String>> expsList = getAllExpressionsBooleanes();

        ViewExps.initExp(expsList);
    }

    public void mostraViewMostrarCont(String titol, String autor){ //Conseguir el cont con el getContingut, y q este llame a esta y así la main view como q no conoce las otras views?
        String cont = getContingut(autor, titol);
        new ViewMostrarCont(this, titol, autor, cont);
    }

    public void mostraViewEditar(String titol, String autor, String cont){
        new ViewEditar(this, titol, autor, cont);
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
        String titol = viewPrincipal.getTitolDocObert();
        String autor = viewPrincipal.getAutorDocObert();
        boolean valid = modificarTitol(titol, autor, newT);

        if(valid) viewPrincipal.actualitzaTitol(newT); // no hace falta else con pop up error, ya se lanza en la otra
    }

    public void actualitzaAutor(String newA) { //se tiene q comprobarantes si se puede crear
        String titol = viewPrincipal.getTitolDocObert();
        String autor = viewPrincipal.getAutorDocObert();
        boolean valid = modificarAutor(titol, autor, newA);

        if(valid) viewPrincipal.actualitzaAutor(newA); // no hace falta else con pop up error, ya se lanza en la otra
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

    public String obrirDocument(String autor, String titol) { //DEVUELVE EL CONTENIDO, SE TIENE Q LLAMAR DESDE LA MAIN VIEW
        String cont = null;
        try {
            cont = cd.obrirDocument(autor, titol);
            mostraViewEditar(titol, autor, cont);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No s'ha pogut obrir el document.",
                    "Error obrir document", JOptionPane.ERROR_MESSAGE);
        }
        return cont;
    }

    public boolean crearDocument(String autor, String titol){
        boolean valid = true;
        try {
            cd.crearDocument(autor, titol);
        }
        catch (EDocumentException | IOException e){
            if(e instanceof EDocumentException) {
                JOptionPane.showMessageDialog(null, "Ja existeix un document amb aquell títol i autor.",
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

    public Pair<String, String> importaDocument(String path) { //path = path+nom+.format
        Pair<String, String> docImp = new Pair<>();
        try {
            docImp = cd.importarDocument(path);
        }
        catch (EDocumentException | IOException e){
            if(e instanceof EDocumentException) {
                JOptionPane.showMessageDialog(null, "Ja existeix un document amb aquell títol i autor.",
                        "Error importar document", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Hi ha hagut un error al importar el document.",
                        "Error importar document", JOptionPane.ERROR_MESSAGE);
            }
            docImp = null;
        }
        return docImp;
    }

    public boolean exportaDocument(String autor, String titol, String path) { //seria mejor bool y q sea true si todo ok o false si ya existe ese doc?
        boolean expOk = true;
        try {
            cd.exportarDocument(autor, titol, path);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Hi ha hagut un error, no s'ha pogut exportar.",
                    "Error exportació", JOptionPane.ERROR_MESSAGE);
            expOk = false;
        }
        return expOk;
    }

    public boolean esborrarDocument(String autor, String titol) {
        boolean esborrat = true;
        try {
            cd.esborrarDocument(autor, titol);
        }
        catch(DeleteDocumentException | IOException e) {
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
            //viewPrincipal.actualitzaTitol(newT); //??
        }
        catch (EDocumentException | IOException e){
            if(e instanceof EDocumentException) {
                JOptionPane.showMessageDialog(null, "Ja existeix un document amb el nou títol i l'autor.",
                        "Error modificació títol", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "No s'ha pogut modificar el títol.",
                        "Error modificació títol", JOptionPane.ERROR_MESSAGE);
            }
            valid = false;
        }
        return valid;
    }

    public boolean modificarAutor(String autor, String titol, String newA) {
        boolean valid = true;
        try {
            cd.modificarAutor(autor, titol, newA);
            //viewPrincipal.actualitzaTitol(newA); //??
        }
        catch (EDocumentException | IOException e){
            if(e instanceof EDocumentException) {
                JOptionPane.showMessageDialog(null, "Ja existeix un document amb el títol i el nou autor.",
                        "Error modificació autor", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "No s'ha pogut modificar l'autor.",
                        "Error modificació autor", JOptionPane.ERROR_MESSAGE);
            }
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
        return cd.getAllExpressionsBooleanes();
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

    public boolean modExpressioBooleana(String nom, String nExp) {
        boolean valida = true;
        try {
            cd.modExpressioBooleana(nom, nExp);
        }
        catch(ExpBoolNoValidaException e) {
            JOptionPane.showMessageDialog(null, "L'expressió booleana introduïda no és vàlida.",
                    "Error modificar expressió", JOptionPane.ERROR_MESSAGE);
            valida = false;
        }
        return valida;
    }

    public void deleteExpressioBooleana(String nom) {
        cd.deleteExpressioBooleana(nom);
    }

    public static void main(String[] args) {
        CtrlPresentacio cp = new CtrlPresentacio();
        cp.mostraViewPrincipal();
    }
}
