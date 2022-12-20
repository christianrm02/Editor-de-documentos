package presentacio;

import domini.controladores.CtrlDomini;
import excepcions.*;
import transversal.Pair;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CtrlPresentació, classe que comunica la capa de domini amb les vistes.
 * @author Christian Rivero
 */
public class CtrlPresentacio {
    /**
     * Instància de la ViewPrincipal
     */
    private ViewPrincipal viewPrincipal;
    /**
     * Instància de la ViewGestioExpBool
     */
    private ViewGestioExpBool viewExps;
    /**
     * Instància de la ViewEditar
     */
    private ViewEditar viewEditar;
    /**
     * Instància del controlador de domini
     */
    private CtrlDomini cd;

    /**
     * Creadora bàsica
     */
    public CtrlPresentacio() {}

    /**
     * Mètode que mostra la vista principal, tant si és la primera vegada des que s’inicia el programa,
     * com si no, actualitzada amb tots els documents.
     */
    public void mostraViewPrincipal(){
        if(viewPrincipal == null) {
            viewPrincipal = new ViewPrincipal(this);
            cd = new CtrlDomini();
            List<Pair<String, String>> docs = new ArrayList<>();
            try {
                docs = cd.init();
            }
            catch (IOException e) {
                //JOptionPane.showMessageDialog(null, "No s'ha pogut obrir l'aplicació.", "Error obrir", JOptionPane.ERROR_MESSAGE);
                popupException(e.toString(), "Error obrir programa");
                docs = null;
            }
            catch (ExpBoolNoValidaException e) { //PORQUE THROWEA ESTA EXCEPCIÓN?
                popupException(e.toString(), "Error obrir programa");
                docs = null;
            }
            if(docs != null) viewPrincipal.initDocs(docs);
        }
        else viewPrincipal.setVisible(true);
    }

    /**
     * Mètode que oculta la vista principal.
     */
    public void ocultaViewPrincipal() {
        viewPrincipal.setVisible(false);
    }

    /**
     * Mètode que mostra la vista de gestió d’expressions booleanes.
     */
    public void mostraVistaGestioExpBool(JTable documents){
        viewExps = new ViewGestioExpBool(documents, this);
        List<Pair<String, String>> expsList = getAllExpressionsBooleanes();

        viewExps.initExp(expsList);
    }

    /**
     * Mètode que mostra la vista de mostrar el contingut d’un document (autor+titol) que existeix al sistema.
     */
    public void mostraViewMostrarCont(String titol, String autor){ //Conseguir el cont con el getContingut, y q este llame a esta y así la main view como q no conoce las otras views?
        String cont = getContingut(autor, titol);
        new ViewMostrarCont(this, titol, autor, cont);
    }

    /**
     * Mètode que mostra la vista d'editar el contingut d’un document (autor+titol) que existeix al sistema.
     */
    public void mostraViewEditar(String titol, String autor, String cont){
        if(viewEditar != null) viewEditar.dispose();
        viewEditar = new ViewEditar(this, titol, autor, cont);
    }

    /**
     * Mètode per guardar els indexs i expressions booleanes a la capa de persistència.
     */
    public void tancarAplicacio() {
        try {
            //System.out.println("Tancant aplicació...Guardant documents i expressions...");
            cd.tancar();
            System.exit(0); //dudas
        }
        catch(IOException e) {
            //JOptionPane.showMessageDialog(null, "No s'ha pogut tancar l'aplicació.", "Error tancar", JOptionPane.ERROR_MESSAGE);
            popupException(e.toString(), "Error tancar programa");
        }
    }

    /**
     * Mètode que canvia el títol d'un document obert, si es pot, el canvia en les vistes.
     * @param newT: String: Nou títol del document obert.
     * @return boolean: Indica si es pot modificar o no.
     */
    public boolean actualitzaTitol(String newT) { //se tiene q comprobarantes si se puede crear
        String titol = viewPrincipal.getTitolDocObert();
        String autor = viewPrincipal.getAutorDocObert();
        boolean valid = modificarTitol(autor, titol, newT);

        if(valid) viewPrincipal.actualitzaTitol(newT); // no hace falta else con pop up error, ya se lanza en la otra

        return valid;
    }

    /**
     * Mètode que canvia l'autor d'un document obert, si es pot, el canvia en les vistes.
     * @param newA: String: Nou autor del document obert.
     * @return boolean: Indica si es pot modificar o no.
     */
    public boolean actualitzaAutor(String newA) { //se tiene q comprobarantes si se puede crear
        String titol = viewPrincipal.getTitolDocObert();
        String autor = viewPrincipal.getAutorDocObert();
        boolean valid = modificarAutor(autor, titol, newA);

        if(valid) viewPrincipal.actualitzaAutor(newA); // no hace falta else con pop up error, ya se lanza en la otra

        return valid;
    }

    public void actualitzaDarreraModificacio(String date) {
        viewPrincipal.actualitzaDarreraModificacio(date);
    }

    /*Crides a domini*/
    /**
     * Getter d'autors.
     * @return List<String>: Es retornen tots els autors existents al sistema.
     */
    public List<String> getAutors() {
        return cd.getAutors();
    }

    /*public List<Pair<String, String>> getTitolsAutors() { //BORRAR??

    }*/

    /**
     * Mètode per obtenir el contingut del document (autor+titol).
     * @param autor: String: autor del document.
     * @param titol: String: titol del document.
     * @return String: Es retorna el contingut del document (autor+titol).
     */
    public String getContingut(String autor, String titol) {
        String cont = null;
        try {
            cont = cd.getContingut(autor, titol);
        }
        catch (IOException e) {
            //JOptionPane.showMessageDialog(null, "No s'ha pogut obtenir el contingut del document.", "Error obtenció contingut", JOptionPane.ERROR_MESSAGE);
            popupException(e.toString(), "Error obtenir contingut");
        }
        return cont;
    }

    /**
     * Mètode per obrir el document (autor+titol). titolAct, autorAct i contAct prenen el valor actual corresponent del document que s'ha obert.
     * @param autor: String: autor del document.
     * @param titol: String: titol del document.
     * @return String: Es retorna el contingut del document (autor+titol).
     */
    public String obrirDocument(String autor, String titol) {
        String cont = null;
        try {
            cont = cd.obrirDocument(autor, titol);
            mostraViewEditar(titol, autor, cont);
        }
        catch (IOException e) {
            //JOptionPane.showMessageDialog(null, "No s'ha pogut obrir el document.", "Error obrir document", JOptionPane.ERROR_MESSAGE);
            popupException(e.toString(), "Error obrir document");
        }
        return cont;
    }

    /**
     * Mètode per simular a la capa de domini que es tanca el document obert.
     */
    public void tancarDocument() {
        cd.tancarDocument();
    }

    /**
     * Mètode per crear un document amb autor autor, títol titol i contingut buit.
     * @param autor: String: autor del document a crear.
     * @param titol: String: titol del document a crear.
     * @return boolean: Indica si s'ha creat el document introduït.
     */
    public boolean crearDocument(String autor, String titol){
        boolean valid = true;
        try {
            cd.crearDocument(autor, titol);
        }
        catch (EDocumentException e){
            //JOptionPane.showMessageDialog(null, "Ja existeix un document amb aquell títol i autor.", "Error creació document", JOptionPane.ERROR_MESSAGE);
            popupException(e.toString(), "Error crear document");
            valid = false;
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Hi ha hagut un error en la creació del document.", "Error creació document", JOptionPane.ERROR_MESSAGE);
            popupException(e.toString(), "Error crear document");
            valid = false;
        }
        return valid;
    }

    /**
     * Mètode per desar el contingut del document obert actualment a la capa de persistència.
     */
    public void desarDocument() {
        try {
            cd.desarDocument();
        }
        catch (IOException e){
            //JOptionPane.showMessageDialog(null, "Hi ha hagut un error al desar el document.", "Error desar document", JOptionPane.ERROR_MESSAGE);
            popupException(e.toString(), "Error desar document");
        }
    }

    /**
     * Mètode per importar el document de la localització path al sistema.
     * @param path: String: localització del document a importar.
     * @return Pair<String,String>: Es retorna l'autor i el titol del document importat.
     *          Retorna null si no s'ha pogut importar.
     */
    public Pair<String, String> importaDocument(String path) { //path = path+nom+.format
        Pair<String, String> docImp = new Pair<>();
        try {
            docImp = cd.importarDocument(path);
        }
        catch (EDocumentException e){
            //JOptionPane.showMessageDialog(null, "Ja existeix un document amb aquell títol i autor.", "Error importar document", JOptionPane.ERROR_MESSAGE);
            popupException(e.toString(), "Error importar document");
            docImp = null;
        }
        catch(IOException e) {
            //JOptionPane.showMessageDialog(null, "Hi ha hagut un error al importar el document.","Error importar document", JOptionPane.ERROR_MESSAGE);
            popupException(e.toString(), "Error importar document");
            docImp = null;
        }
        catch(FormatInvalid e) {
            //JOptionPane.showMessageDialog(null, "S'ha seleccionat un format que no és vàlid, només txt i xml.", "Error importar document", JOptionPane.ERROR_MESSAGE);
            popupException(e.toString(), "Error importar document");
            docImp = null;
        }
        return docImp;
    }

    /**
     * Mètode per exportar el document (autor+titol) a la localització path.
     * @param autor: String: autor del document a exportar.
     * @param titol: String: titol del document a exportar.
     * @param path: String: localització on es vol guardar el document a exportar.
     * @return boolean: Indica si el document s'ha pogut exportar correctament o no.
     */
    public boolean exportaDocument(String autor, String titol, String path) {
        boolean expOk = true;
        try {
            cd.exportarDocument(autor, titol, path);
        }
        catch (IOException e) {
            //JOptionPane.showMessageDialog(null, "Hi ha hagut un error, no s'ha pogut exportar.","Error exportació", JOptionPane.ERROR_MESSAGE);
            expOk = false;
            popupException(e.toString(), "Error exportar document");
        }
        catch (FormatInvalid e) { //ESTA CREO Q NO DEBERIA DE DAR ESTA EXCEPCION
            //JOptionPane.showMessageDialog(null, "Hi ha hagut un error, no s'ha pogut exportar.","Error exportació", JOptionPane.ERROR_MESSAGE);
            expOk = false;
            popupException(e.toString(), "Error exportar document");
        }
        return expOk;
    }

    /**
     * Mètode per esborrar el document amb autor autor i títol titol.
     * @param autor: String: autor del document a esborrar.
     * @param titol: String: titol del document a esborrar.
     * @return boolean: Indica si el document s'ha pogut esborrar correctament o no.
     */
    public boolean esborrarDocument(String autor, String titol) {
        boolean esborrat = true;
        try {
            cd.esborrarDocument(autor, titol);
        }
        catch(DeleteDocumentException e) {
            //JOptionPane.showMessageDialog(null, "No s'ha pogut esborrar el document.", "Error esborrar document", JOptionPane.ERROR_MESSAGE);
            esborrat = false;
            popupException(e.toString(), "Error esborrar document");
        }
        catch(IOException e) {
            //JOptionPane.showMessageDialog(null, "No s'ha pogut esborrar el document.","Error esborrar document", JOptionPane.ERROR_MESSAGE);
            esborrat = false;
            popupException(e.toString(), "Error esborrar document");
        }
        return esborrat;
    }

    /**
     * Mètode per modificar el títol del document amb clau (autor+titol) per newT.
     * @param autor: String: autor del document.
     * @param titol: String: titol del document.
     * @param newT: String: nou titol que se li vol posar al document.
     * @return boolean: Indica si s'ha pogut modificar el títol o no.
     */
    public boolean modificarTitol(String autor, String titol, String newT) {
        boolean valid = true;
        try {
            cd.modificarTitol(autor, titol, newT);
            //viewPrincipal.actualitzaTitol(newT); //??
        }
        catch (EDocumentException  e){
            //JOptionPane.showMessageDialog(null, "Ja existeix un document amb el nou títol i l'autor.","Error modificació títol", JOptionPane.ERROR_MESSAGE);
            valid = false;
            popupException(e.toString(), "Error modificar títol");
        }
        catch(IOException e) {
            //JOptionPane.showMessageDialog(null, "No s'ha pogut modificar el títol.","Error modificació títol", JOptionPane.ERROR_MESSAGE);
            valid = false;
            popupException(e.toString(), "Error modificar títol");
        }
        return valid;
    }

    /**
     * Mètode per modificar el títol del document amb clau (autor+titol).
     * @param autor: String: autor del document.
     * @param titol: String: titol del document.
     * @param newA: String: nou autor que se li vol posar al document.
     * @return boolean: Indica si s'ha pogut modificar l'autor o no.
     */
    public boolean modificarAutor(String autor, String titol, String newA) {
        boolean valid = true;
        try {
            cd.modificarAutor(autor, titol, newA);
            //viewPrincipal.actualitzaTitol(newA); //??
        }
        catch (EDocumentException e){
            //JOptionPane.showMessageDialog(null, "Ja existeix un document amb el títol i el nou autor.", "Error modificació autor", JOptionPane.ERROR_MESSAGE);
            valid = false;
            popupException(e.toString(), "Error modificar autor");
        }
        catch(IOException e) {
            //JOptionPane.showMessageDialog(null, "No s'ha pogut modificar l'autor.","Error modificació autor", JOptionPane.ERROR_MESSAGE);
            valid = false;
            popupException(e.toString(), "Error modificar autor");
        }
        return valid;
    }

    /**
     * Mètode per modificar el contingut del document obert actualment.
     * @param cont: String: nou contingut del document (autorAct+titolAct).
     */
    public void modificarContingut(String cont) { //vieweditar
        cd.modificarContingut(cont);
    }

    /**
     * Mètode que dona tots els títols de l'autor autor.
     * @param autor: String: autor del que es volen tots els seus títols.
     * @return List<String>: Es retorna una llista amb tots els títols de l'autor autor.
     */
    public List<String> llistarTitolsdAutors(String autor) {
        return cd.llistarTitolsdAutors(autor);
    }

    /**
     * Mètode que dona tots els autors amb prefix prefix.
     * @param prefix: String: prefix dels autors a cercar.
     * @return List<String>: Es retorna una llista amb tots els autors que tenen com a prefix prefix.
     */
    public List<String> llistarAutorsPrefix(String prefix) {
        return cd.llistarAutorsPrefix(prefix);
    }

    /**
     * Mètode que dona les com a molt K claus dels documents més semblants al document (autor+titol) amb l'estratègia estrategia.
     * @param autor: String: autor del document.
     * @param titol: String: titol del document.
     * @param K: int: nombre de documents a llistar, 1 <= K <= nombreDocumentsTotal -1.
     * @param estrategia: boolean: estrategia per fer la cerca.
     * @return List<Pair<String, String>>: Es retorna una llista amb com a molt K claus dels documents més semblants al document (autor+titol)
     *          amb l'estratègia estrategia.
     */
    public List<Pair<String, String>> llistarKDocumentsS(String autor, String titol, int K, boolean estrategia) {
        return cd.llistarKDocumentsS(autor, titol, K, estrategia);
    }

    /**
     * Opcional: Mètode que dona les com a molt K claus dels documents més rellevants segons les paraules paraules amb l'estratègia estrategia.
     * @param paraules: String: paraules rellevants a cercar.
     * @param K: int: nombre de documents a llistar, 1 <= K <= nombreDocumentsTotal.
     * @param estrategia: boolean: estrategia per fer la cerca.
     * @return List<Pair<String, String>>: Es retorna una llista amb com a molt K claus dels documents més rellevants segons les paraules
     *          paraules amb l'estratègia estrategia.
     */
    public List<Pair<String, String>> cercarPerRellevancia(String paraules, int K, boolean estrategia) {
        return cd.cercarPerRellevancia(paraules, K, estrategia);
    }

    /**
     * Getter que dona totes les expressions booleanes (nom + exp).
     * @return List<Pair<String, String>>: Es retorna una llista de pairs amb totes les expressions booleanes (nom+exp).
     */
    public List<Pair<String, String>> getAllExpressionsBooleanes() {
        return cd.getAllExpressionsBooleanes();
    }

    /**
     * Mètode per crear una expressió booleana amb nom nom i expressió exp.
     * @param nom: String: nom de l'expressió booleana a crear.
     * @param exp: String: expressió de l'expressió booleana a crear.
     * @return boolean: Indica si l'expressió booleana ha sigut creada o no.
     */
    public boolean creaExpressioBooleana(String nom, String exp) {
        boolean valida = true;
        try {
            cd.setExpressioBooleana(nom, exp);
        }
        catch (EExpBoolException e) {
            //JOptionPane.showMessageDialog(null, "Ja existeix una expressió booleana amb aquell nom.", "Error creació expressió", JOptionPane.ERROR_MESSAGE);
            valida = false;
            popupException(e.toString(), "Error crear expressió");
        }
        catch(ExpBoolNoValidaException e) {
            //JOptionPane.showMessageDialog(null, "La expressió booleana introduïda no és vàlida.","Error creació expressió", JOptionPane.ERROR_MESSAGE);
            valida = false;
            popupException(e.toString(), "Error crear expressió");
        }
        return valida;
    }

    /**
     * Mètode que dona les claus dels documents que cumpleixen l'expressió booleana exp.
     * @param exp: String: expressió booleana per fer la cerca.
     * @return List<Pair<String, String>>: Es retorna una llista amb les claus dels documents que compleixen l'expressió booleana exp,
     *          la llista = null si ha succeït algun error.
     */
    public List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        List<Pair<String, String>> docs = new ArrayList<>();
        try {
            docs = cd.cercarExpressioBooleana(exp);
        }
        catch(ExpBoolNoValidaException e) {
            //JOptionPane.showMessageDialog(null, e.toString(), "Error cerca", JOptionPane.ERROR_MESSAGE);
            docs = null;
            popupException(e.toString(), "Error cerca per expressió");
        }
        return docs;
    }

    /**
     * Mètode que dona les claus dels documents que cumpleixen l'expressió booleana amb nom nom.
     * @param nom: String: nom de l'expressió booleana per fer la cerca.
     * @return List<Pair<String, String>>: Es retorna una llista amb les claus dels documents que compleixen l'expressió booleana amb nom nom.
     */
    public List<Pair<String, String>> cercarExpressioBooleanaNom(String nom) {
        List<Pair<String, String>> docs = cd.cercarExpressioBooleanaNom(nom);
        return docs;
    }

    /**
     * Mètode per modificar l'expressió de l'expressió booleana amb nom nom a nExp.
     * @param nom: String: nom de l'expressió booleana a modificar.
     * @param nExp: String: nova expressió per a l'expressió booleana.
     * @return boolean: Indica si s'ha pogut fer la modificació o no.
     */
    public boolean modExpressioBooleana(String nom, String nExp) {
        boolean valida = true;
        try {
            cd.modExpressioBooleana(nom, nExp);
        }
        catch(ExpBoolNoValidaException e) {
            //JOptionPane.showMessageDialog(null, "L'expressió booleana introduïda no és vàlida.", "Error modificar expressió", JOptionPane.ERROR_MESSAGE);
            valida = false;
            popupException(e.toString(), "Error modificar expressió");
        }
        return valida;
    }

    /**
     * Mètode per esborrar l'expressió booleana amb nom nom.
     * @param nom: String: nom de l'expressió booleana a esborrar.
     */
    public void deleteExpressioBooleana(String nom) {
        cd.deleteExpressioBooleana(nom);
    }

    /**
     * Mètode main de l'aplicació, comença mostrant la viewPincipal.
     */
    public static void main(String[] args) {
        CtrlPresentacio cp = new CtrlPresentacio();
        cp.mostraViewPrincipal();
    }

    private void popupException(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
