package persistencia;
import excepcions.DeleteDocumentException;
import excepcions.FormatInvalid;
import transversal.Pair;

import java.io.IOException;
import java.util.List;

public class CtrlPersistencia {

    // Constructora
    public CtrlPersistencia() {

    }
    public String[] importaDocument(String path) throws IOException, FormatInvalid {
        return GestorDocuments.ImportaDocument(path);
    }

    public void exportaDocument(String autor, String titol, String path) throws IOException, FormatInvalid {
        GestorDocuments.ExportaDocument(autor, titol, path);
    }

    public String getContingut(String autor, String titol) throws IOException {
        return GestorDocuments.GetContingut(autor, titol);
    }

    public void desaContingut(String autor, String titol, String contingut) throws IOException {
        GestorDocuments.DesaContingut(autor, titol, contingut);
    }

    public void esborrarDocument(String autor, String titol) throws IOException, DeleteDocumentException {
        GestorDocuments.EsborrarDoc(autor, titol);
    }

    public void actualitzarAutor(String autor, String titol, String newAutor) throws IOException {
        GestorDocuments.ActualitzarAutor(autor, titol, newAutor);
    }

    public void actualitzarTitol(String autor, String titol, String newTitol) throws IOException {
        GestorDocuments.ActualitzarTitol(autor, titol, newTitol);
    }

    public byte[] importarIndexs() throws IOException {
        return GestorIndexs.ImportarIndexs();
    }

    public void exportarIndexs(byte[] info) throws IOException {
        GestorIndexs.ExportarIndexs(info);
    }

    public List<Pair<String, String>> carregarExpB() throws IOException {
        return GestorExpBooleanes.CarregarExpB();
    }

    public void guardarExpB(List<Pair<String, String>> exps) throws IOException {
        GestorExpBooleanes.GuardarExpB(exps);
    }
}