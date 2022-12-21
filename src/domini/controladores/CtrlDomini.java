package domini.controladores;

import persistencia.CtrlPersistencia;
import transversal.*;
import excepcions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static domini.datatypes.Utility.converteix_a_frases;

/**
 * Aquesta classe és l’encarregada d’interactuar amb les altres capes. També és l’encarregada d’instanciar la resta de controladors de la capa de domini i el de persistència.
 * @author Marc Roman
 */
public class CtrlDomini {
    /**
     * Controlador d'index.
     */
    private CtrlIndex ci;
    /**
     * Controlador d'expressio booleana.
     */
    private CtrlExpressioBooleana ce;
    /**
     * Controlador de persistencia.
     */
    private CtrlPersistencia cp;
    /**
     * Paràmetres del document obert actual. Si no hi ha cap document obert tots són igual a "".
     */
    private String titolAct, autorAct, contAct;

    /**
     * Constructora del controlador de domini, que inicialitza els controladors d'index, expressio booleana i persistencia.
     */
    public CtrlDomini() {
        ci = new CtrlIndex();
        ce = new CtrlExpressioBooleana();
        cp = new CtrlPersistencia();
    }

    /**
     * Mètode per inicialitzar els indexs i expressions booleanes a la capa de domini.
     *
     * @return List<Pair < String, String>>: Es retorna la llista de tots els documents (auto+titol) existents.
     */
    public List<Pair<Pair<String, String>, String>> init() throws IOException, ExpBoolNoValidaException {
        ci.ImportarIndexs(cp.importarIndexs());
        List<Pair<String, String>> ebs = cp.carregarExpB();
        for (Pair<String, String> eb : ebs) ce.setExpressioBooleana(eb.x, eb.y);
        return getTitolsAutorsData();
    }

    /**
     * Mètode per guardar els indexs i expressions booleanes a la capa de persistència.
     */
    public void tancar() throws IOException {
        cp.exportarIndexs(ci.ExportarIndexs());
        cp.guardarExpB(ce.getAll());
    }

    /**
     * Mètode per importar el document de la localització loc al sistema.
     * @param loc: String: localització del document a importar.
     * @param data: String: data de l'instant on s'ha demanat importar.
     * @return Pair<String,String>: Es retorna l'autor i el titol del document importat.
     * @exception EDocumentException: El document ja existeix al sistema.
     * @exception IOException: Hi ha hagut algun problema en accedir a disc.
     */
    public Pair<String,String> importarDocument(String loc, String data) throws EDocumentException, IOException, FormatInvalid {
            String[] doc = cp.importaDocument(loc);
            if (ci.FindDoc(doc[0], doc[1])) throw new EDocumentException();
            ci.AfegirDoc(doc[0], doc[1], data, converteix_a_frases(doc[2]));
        return new Pair<>(doc[0], doc[1]);
    }

    /**
     * Mètode per exportar el document (autor+titol) a la localització loc.
     * @param autor: String: autor del document a exportar.
     * @param titol: String: titol del document a exportar.
     * @param loc: String: localització on es vol guardar el document a exportar.
     * @exception IOException: Hi ha hagut algun problema en accedir a disc.
     */
    public void exportarDocument(String autor, String titol, String loc) throws IOException, FormatInvalid {
        cp.exportaDocument(autor, titol, loc);
    }

    /**
     * Getter d'autors.
     * @return List<String>: Es retornen tots els autors existents al sistema.
     */
    public List<String> getAutors() {
        return new ArrayList<>(ci.GetAutorsPrefix(""));
    }

    /**
     * Getter de documents.
     * @return List<Pair<Pair<String, String>,String>>: Es retornen ots els identificadors de documents (autor+titol) texistents al sistema.
     */
    public List<Pair<Pair<String, String>,String>> getTitolsAutorsData() {
        return new ArrayList<>(ci.GetKeys());
    }

    /**
     * Mètode per obtenir el contingut del document (autor+titol).
     * @param autor: String: autor del document.
     * @param titol: String: titol del document.
     * @return String: Es retorna el contingut del document (autor+titol).
     * @exception IOException: Hi ha hagut algun problema en accedir a disc.
     */
    public String getContingut(String autor, String titol) throws IOException {
        return cp.getContingut(autor, titol);
    }

    /**
     * Mètode per obrir el document (autor+titol). titolAct, autorAct i contAct prenen el valor actual corresponent del document que s'ha obert.
     * @param autor: String: autor del document.
     * @param titol: String: titol del document.
     * @return String: Es retorna el contingut del document (autor+titol).
     * @exception IOException: Hi ha hagut algun problema en accedir a disc.
     */
    public String obrirDocument(String autor, String titol) throws IOException {
        titolAct = titol;
        autorAct = autor;
        contAct = cp.getContingut(autor, titol);
        return contAct;
    }

    /**
     * Mètode per crear un document amb autor autor, títol titol i contingut en blanc.
     * @param autor: String: autor del document a crear.
     * @param titol: String: titol del document a crear.
     * @param data: String: data de l'instant on s'ha demanat crear el document.
     * @exception EDocumentException: El document ja existeix al sistema.
     * @exception IOException: Hi ha hagut algun problema en accedir a disc.
     */
    public void crearDocument(String autor, String titol, String data) throws EDocumentException, IOException {
        if (ci.FindDoc(autor, titol)) throw new EDocumentException();
        ci.AfegirDoc(autor, titol, data, new ArrayList<String>());
        cp.desaContingut(autor, titol, "");
    }

    /**
     * Mètode per esborrar el document amb autor autor i títol titol.
     * @param autor: String: autor del document a esborrar.
     * @param titol: String: titol del document a esborrar.
     * @exception IOException: Hi ha hagut algun problema en accedir a disc.
     * @exception DeleteDocumentException: Hi ha hagut algun problema intentant esborrar el document de disc.
     */
    public void esborrarDocument(String autor, String titol) throws IOException, DeleteDocumentException {
        ci.EsborrarDoc(autor, titol);
        cp.esborrarDocument(autor, titol);
    }

    /**
     * Mètode per modificar el títol del document amb clau (autor+titol).
     * @param autor: String: autor del document.
     * @param titol: String: titol del document.
     * @param newT: String: nou titol que se li vol posar al document.
     * @exception EDocumentException: El document (autor+newT) ja existeix al sistema.
     * @exception IOException: Hi ha hagut algun problema en accedir a disc.
     */
    public void modificarTitol(String autor, String titol, String newT) throws EDocumentException, IOException {
        if (ci.FindDoc(autor, newT)) throw new EDocumentException();
        ci.ActualitzarTitol(autor, titol, newT);
        cp.actualitzarTitol(autor, titol, newT);
    }

    /**
     * Mètode per modificar l'autor del document amb clau (autor+titol).
     * @param autor: String: autor del document.
     * @param titol: String: titol del document.
     * @param newA: String: nou autor que se li vol posar al document.
     * @exception EDocumentException: El document (newA+titol) ja existeix al sistema.
     * @exception IOException: Hi ha hagut algun problema en accedir a disc.
     */
    public void modificarAutor(String autor, String titol, String newA) throws EDocumentException, IOException {
        if (ci.FindDoc(newA, titol)) throw new EDocumentException();
        ci.ActualitzarAutor(autor, titol, newA);
        cp.actualitzarAutor(autor, titol, newA);
    }

    /**
     * Mètode per modificar el contingut (contAct) del document obert actualment (autorAct+titolAct) i actualitzar la seva data.
     * @param cont: String: nou contingut del document (autorAct+titolAct).
     * @param data: String: data de l'instant on s'ha desat el contingut.
     */
    public void modificarContingut(String cont, String data) {
        contAct = cont;
        ci.SetData(autorAct, titolAct, data);
        ci.ActualitzarContingut(autorAct, titolAct, converteix_a_frases(cont));
    }

    /**
     * Mètode per desar el contingut (contAct) del document obert actualment (autorAct+titolAct) a la capa de persistència.
     * @exception IOException: Hi ha hagut algun problema en accedir a disc.
     */
    public void desarDocument() throws IOException {
        cp.desaContingut(autorAct, titolAct, contAct);
    }

    /**
     * Mètode per simular a capa de domini que es tanca el document obert posant autorAct, titolAct i contAct amb valor "".
     */
    public void tancarDocument() {
        autorAct = "";
        titolAct = "";
        contAct = "";
    }

    /**
     * Mètode que dona tots els títols de l'autor autor.
     * @param autor: String: autor del que es volen tots els seus títols.
     * @return List<String>: Es retorna una llista amb tots els títols de l'autor autor.
     */
    public List<String> llistarTitolsdAutors(String autor) {
        Set<String> a = ci.GetTitolsAutor(autor);
        return new ArrayList<>(a);
    }

    /**
     * Mètode que dona tots els autors amb prefix prefix.
     * @param prefix: String: prefix dels autors a cercar.
     * @return List<String>: Es retorna una llista amb tots els autors que tenen com a prefix prefix.
     */
    public List<String> llistarAutorsPrefix(String prefix) {
        return new ArrayList<>(ci.GetAutorsPrefix(prefix));
    }

    /**
     * Mètode que dona les com a molt K claus dels documents més semblants al document (autor+titol) amb l'estratègia estrategia.
     * @param autor: String: autor del document.
     * @param titol: String: titol del document.
     * @param K: int: nombre de documents a llistar.
     * @param estrategia: boolean: estrategia per fer la cerca.
     * @return List<Pair<String, String>>: Es retorna una llista amb com a molt K claus dels documents més semblants al document (autor+titol) amb l'estratègia estrategia.
     */
    public List<Pair<String, String>> llistarKDocumentsS(String autor, String titol, int K, boolean estrategia) {
        return ci.GetKDocsSimilarS(autor, titol, K, estrategia);
    }

    /**
     * Mètode que dona les claus dels documents que cumpleixen l'expressió booleana exp.
     * @param exp: String: expressió booleana per fer la cerca.
     * @return List<Pair<String, String>>: Es retorna una llista amb les claus dels documents que compleixen l'expressió booleana exp.
     * @exception ExpBoolNoValidaException: exp no és vàlida.
     */
    public List<Pair<String, String>> cercarExpressioBooleana(String exp) throws ExpBoolNoValidaException {
        return ce.cercarExpressioBooleana(exp, ci);
    }

    /**
     * Mètode que dona les claus dels documents que cumpleixen l'expressió booleana amb nom nom.
     * @param nom: String: nom de l'expressió booleana per fer la cerca.
     * @return List<Pair<String, String>>: Es retorna una llista amb les claus dels documents que compleixen l'expressió booleana amb nom nom.
     */
    public List<Pair<String, String>> cercarExpressioBooleanaNom(String nom) {
        return ce.cercarExpressioBooleanaExistent(nom, ci);
    }

    /**
     * Opcional: Mètode que dona les com a molt K claus dels documents més rellevants segons les paraules paraules amb l'estratègia estrategia.
     * @param paraules: String: paraules rellevants a cercar.
     * @param K: int: nombre de documents a llistar.
     * @param estrategia: boolean: estrategia per fer la cerca.
     * @return List<Pair<String, String>>: Es retorna una llista amb com a molt K claus dels documents més rellevants segons les paraules paraules amb l'estratègia estrategia.
     */
    public List<Pair<String, String>> cercarPerRellevancia(String paraules, int K, boolean estrategia) {
        return ci.CercaPerRellevancia(paraules, K, estrategia);
    }

    /**
     * Getter que dona totes les expressions booleanes (nom + exp).
     * @return List<Pair<String, String>>: Es retorna una llista amb totes les expressions booleanes (nom+exp).
     */
    public List<Pair<String, String>> getAllExpressionsBooleanes() {
        return ce.getAll();
    }

    /**
     * Mètode per crear una expressió booleana amb nom nom i expressió exp.
     * @param nom: String: nom de l'expressió booleana a crear.
     * @param exp: String: expressió de l'expressió booleana a crear.
     * @exception EExpBoolException: L’expressió booleana amb nom nom existeix.
     * @exception ExpBoolNoValidaException: exp no és vàlida.
     */
    public void setExpressioBooleana(String nom, String exp) throws EExpBoolException, ExpBoolNoValidaException {
        if (ce.existsExpressioBooleana(nom)) throw new EExpBoolException();
        ce.setExpressioBooleana(nom, exp);
    }

    /**
     * Mètode per modificar l'expressió de l'expressió booleana amb nom nom a nExp.
     * @param nom: String: nom de l'expressió booleana a modificar.
     * @param nExp: String: nova expressió per a l'expressió booleana.
     * @exception ExpBoolNoValidaException: nExp no és vàlida.
     */
    public void modExpressioBooleana(String nom, String nExp) throws ExpBoolNoValidaException {
        ce.setExpressioBooleana(nom, nExp);
    }

    /**
     * Mètode per esborrar l'expressió booleana amb nom nom.
     * @param nom: String: nom de l'expressió booleana a esborrar.
     */
    public void deleteExpressioBooleana(String nom) {
        ce.deleteExpressioBooleana(nom);
    }
}