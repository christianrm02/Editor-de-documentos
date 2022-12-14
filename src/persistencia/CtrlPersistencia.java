package persistencia;

import controladores.CtrlExpressioBooleana;
import controladores.CtrlIndex;
import datatypes.ExpressioBooleana;
import transversal.FileFormat;
import transversal.Pair;

import java.io.IOException;
import java.util.List;

public class CtrlPersistencia {

    // Constructora
    public CtrlPersistencia() {

    }
    public String[] importaDocument(String path) throws IOException {
        return GestorDocuments.ImportaDocument(path);
    }

    public void exportaDocument(String autor, String titol, String path) throws IOException {
        GestorDocuments.ExportaDocument(autor, titol, path);
    }

    public String getContingut(String autor, String titol) throws IOException {
        return GestorDocuments.GetContingut(autor, titol);
    }

    public void desaContingut(String autor, String titol, String contingut) throws IOException {
        GestorDocuments.DesaContingut(autor, titol, contingut);
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