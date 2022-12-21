package persistencia;
import excepcions.DeleteDocumentException;
import excepcions.FormatInvalid;
import transversal.Pair;

import java.io.IOException;
import java.util.List;

/**
 * Aquesta classe és l’encarregada d’interactuar amb les classes de persistència.
 * @author Pol Fradera
 */
public class CtrlPersistencia {

    /**
     * Constructora del controlador de persistència.
     */
    public CtrlPersistencia() {

    }

    /**
     * Mètode per importar un document de disc i desar-lo a la carpeta del sistema.
     * @param path: String: Ruta del document a importar a disc.
     * @return String[]: Array que conté l'autor, el títol i el contingut del document en aquest ordre.
     * @exception IOException: Hi ha hagut algun problema en accedir al disc.
     * @exception FormatInvalid: L'extensió del fitxer importat no era correcta.
     */
    public String[] importaDocument(String path) throws IOException, FormatInvalid {
        return GestorDocuments.ImportaDocument(path);
    }

    /**
     * Mètode per exportar un document del sistema a disc a la localització del path.
     * @param autor: String: Autor del document a desar.
     * @param titol: String: Títol del document a desar.
     * @param path: String: Localització on desar el document.
     * @exception IOException: Hi ha hagut algun problema en accedir al disc.
     * @exception FormatInvalid: L'extensió del fitxer importat no era correcta.
     */
    public void exportaDocument(String autor, String titol, String path) throws IOException, FormatInvalid {
        GestorDocuments.ExportaDocument(autor, titol, path);
    }

    /**
     * Mètode per obtenir el contingut d'un document desat a la carpeta del sistema.
     * @param autor: String: Autor del document.
     * @param titol: String: Títol del document.
     * @return String: Contingut del document.
     * @exception IOException: Hi ha hagut algun problema en accedir al disc.
     */
    public String getContingut(String autor, String titol) throws IOException {
        return GestorDocuments.GetContingut(autor, titol);
    }

    /**
     * Mètode per desar el contingut d'un document a la carpeta del sistema.
     * @param autor: String: Autor del document.
     * @param titol: String: Títol del document.
     * @param contingut: String: Contingut del document.
     * @exception IOException: Hi ha hagut algun problema en accedir al disc.
     */
    public void desaContingut(String autor, String titol, String contingut) throws IOException {
        GestorDocuments.DesaContingut(autor, titol, contingut);
    }

    /**
     * Mètode per esborrar un document de la carpeta del sistema.
     * @param autor: String: Autor del document.
     * @param titol: String: Títol del document.
     * @exception IOException: Hi ha hagut algun problema en accedir al disc.
     * @exception DeleteDocumentException: Hi ha hagut algun problema en esborrar el document.
     */
    public void esborrarDocument(String autor, String titol) throws IOException, DeleteDocumentException {
        GestorDocuments.EsborrarDoc(autor, titol);
    }

    /**
     * Mètode per actualitzar l'autor d'un document de la carpeta del sistema.
     * @param autor: String: Autor del document.
     * @param titol: String: Títol del document.
     * @param newAutor: String: Nou autor del document.
     * @exception IOException: Hi ha hagut algun problema en accedir al disc.
     */
    public void actualitzarAutor(String autor, String titol, String newAutor) throws IOException {
        GestorDocuments.ActualitzarAutor(autor, titol, newAutor);
    }

    /**
     * Mètode per actualitzar el títol d'un document de la carpeta del sistema.
     * @param autor: String: Autor del document.
     * @param titol: String: Títol del document.
     * @param newTitol: String: Nou títol del document.
     * @exception IOException: Hi ha hagut algun problema en accedir al disc.
     */
    public void actualitzarTitol(String autor, String titol, String newTitol) throws IOException {
        GestorDocuments.ActualitzarTitol(autor, titol, newTitol);
    }

    /**
     * Mètode per importar els índexs de disc.
     * @return byte[]: Import en forma d'array de bytes.
     * @exception IOException: Hi ha hagut algun problema en accedir al disc.
     */
    public byte[] importarIndexs() throws IOException {
        return GestorIndexs.ImportarIndexs();
    }

    /**
     * Mètode per exportar els índexs a disc.
     * @param info: byte[]: Índexs en forma d'array de bytes.
     * @exception IOException: Hi ha hagut algun problema en accedir al disc.
     */
    public void exportarIndexs(byte[] info) throws IOException {
        GestorIndexs.ExportarIndexs(info);
    }

    /**
     * Mètode per càrregar les expressions booleanes de disc.
     * @return List<Pair<String, String>>: Es retornen totes les expressions booleanes existents al disc (per cada una, el seu nom i l'expressió).
     * @exception IOException Hi ha hagut algun problema en accedir al disc.
     */
    public List<Pair<String, String>> carregarExpB() throws IOException {
        return GestorExpBooleanes.CarregarExpB();
    }

    /**
     * Mètode per guardar les expressions booleanes a disc.
     * @param exps: List<Pair<String, String>>: Totes les expressions booleanes que es volen guardar a disc.
     * @exception IOException Hi ha hagut algun problema en accedir al disc.
     */
    public void guardarExpB(List<Pair<String, String>> exps) throws IOException {
        GestorExpBooleanes.GuardarExpB(exps);
    }
}