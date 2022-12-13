package persistencia;

import controladores.CtrlExpressioBooleana;
import controladores.CtrlIndex;
import transversal.FileFormat;

public class CtrlPersistencia {
    private GestorDocuments gD;
    private GestorIndexs gI;
    private GestorExpBooleanes gEB;

    // Constructora
    public CtrlPersistencia() {

    }
    public String[] carregaDocument(String path, FileFormat f) {
        return gD.CarregaDocument(path, f);
    }

    public void exportaDocument(String autor, String titol, String path, FileFormat f) {
        gD.ExportaDocument(autor, titol, path, f);
    }

    public void getContingut(String autor, String titol) {
        gD.GetContingut(autor, titol);
    }

    public void desaContingut(String autor, String titol, String contingut) {
        gD.DesaContingut(autor, titol, contingut);
    }

    public byte[] ImportarIndexs() {
        return gI.ImportarIndexs();
    }

    public void ExportarIndexs(byte[] info) {
        gI.ExportarIndexs(info);
    }

    public String[] CarregarExpB(String path) {
        return gEB.CarregarExpB(path);
    }

    public void GuardarExpB(String nom, String exp) {
        gEB.GuardarExpB(nom, exp);
    }

}
