import java.util.List;

import datatypes.Contingut;
import datatypes.Document;
import datatypes.PairAutorTitol;
import indexs.*;


class CtrlIndex {

    private IndexAutorPrefix indexAutorPrefix;
    private IndexExpBooleana indexExpBooleana;
    private IndexParaulaTFIDF indexParaulaTFIDF;
    

    public CtrlIndex() {
        indexAutorPrefix = new IndexAutorPrefix();
        indexExpBooleana = new IndexExpBooleana();
        indexParaulaTFIDF = new IndexParaulaTFIDF();
    }

    public void AfegirDoc(Document doc) {
        
    }

    public void EsborrarDoc(String autor, String titol) {

    }

    public void ActualitzarTitol(String autor, String titol, String newTitol) {

    }

    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        
    }

    public void ActualitzarContingut(String autor, String titol, Contingut oldContingut, Contingut contingut) {

    }

    public List<String> GetTitolsAutor(String autor) {
        return null;
    }

    public List<String> GetAutorsPrefix(String prefix) {
        return indexAutorPrefix.GetAutorsPrefix(prefix);
    }

    public List<PairAutorTitol> GetKDocsSimilarS (String autor, String titol, int K) {
        return null;
    }

    //Retorna els indexs de les frases que contenen paraula
    public List<Integer> GetFrases(String paraula) {
        return null;
    }

    //Retorna els documents que contenen les frases indexs
    public List<PairAutorTitol> GetDocuments(List<Integer> indexs) {
        return null;
    }

}