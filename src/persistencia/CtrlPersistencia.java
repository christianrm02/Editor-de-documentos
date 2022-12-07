package persistencia;

public class CtrlPersistencia {
    private GestorDocuments gD;
    private GestorIndexs gI;
    private GestorExpBooleanes gEB;

    public void importaDocument(String path) {
        gD.CarregaDocument(path);
    }

    public void ExportaDocument(String path) {
        gD.ExportaDocument(path);
    }

    public void obrirDocument(String autor, String titol) {
        gD.ObrirDocument(autor, titol);
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
