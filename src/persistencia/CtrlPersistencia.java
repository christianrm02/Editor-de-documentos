package persistencia;

import controladores.CtrlExpressioBooleana;
import controladores.CtrlIndex;
import transversal.FileFormat;

import java.io.IOException;

public class CtrlPersistencia {
    private GestorDocuments gD;
    private GestorIndexs gI;
    private GestorExpBooleanes gEB;

    // Constructora
    public CtrlPersistencia() {

    }
    public String[] importaDocument(String path) throws IOException {
        return gD.ImportaDocument(path);
    }

    public void exportaDocument(String autor, String titol, String path, FileFormat f) throws IOException {
        gD.ExportaDocument(autor, titol, path, f);
    }

    public void getContingut(String autor, String titol) throws IOException {
        gD.GetContingut(autor, titol);
    }

    public void desaContingut(String autor, String titol, String contingut) throws IOException {
        gD.DesaContingut(autor, titol, contingut);
    }

    public byte[] ImportarIndexs() throws IOException {
        return gI.ImportarIndexs();
    }

    public void ExportarIndexs(byte[] info) throws IOException {
        gI.ExportarIndexs(info);
    }

    public String[] CarregarExpB(String path) throws IOException {
        return gEB.CarregarExpB(path);
    }

    public void GuardarExpB(String nom, String exp) throws IOException {
        gEB.GuardarExpB(nom, exp);
    }

}
