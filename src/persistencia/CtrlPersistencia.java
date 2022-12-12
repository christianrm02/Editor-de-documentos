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

    public void obrirDocument(String autor, String titol) {
        gD.ObrirDocument(autor, titol);
    }

    public void desaDocument(String autor, String titol, String contingut) {
        gD.DesaDocument(autor, titol, contingut);
    }

    public byte[] ImportarIndexs() {
        return gI.ImportarIndexs();
    }

    public void ExportarIndexs(byte[] info) {
        gI.ExportarIndexs(info);
    }

    public byte[] CarregarExpB() {
        return gEB.CarregarExpB();
    }

    public void GuardarExpB(byte[] info) {
        gEB.GuardarExpB(info);
    }

}
