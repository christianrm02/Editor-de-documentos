import java.util.List;

import datatypes.Pair;
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

    public void AfegirDoc(String autor, String titol, List<String> contingut) {
        indexAutorPrefix.InsertAutor(autor);
        indexExpBooleana.AfegirDoc(autor, titol, contingut);
        indexParaulaTFIDF.AfegirDoc(autor, titol, contingut);
    }

    public void EsborrarDoc(String autor, String titol) {
        indexExpBooleana.EsborrarDoc(autor, titol);
        indexParaulaTFIDF.EsborrarDoc(autor, titol);
    }

    public void EsborrarAutors(List<String> autors) {
        for (String autor : autors) {
            indexAutorPrefix.DeleteAutor(autor);
        }
    }

    public void ActualitzarTitol(String autor, String titol, String newTitol) {
        indexExpBooleana.ActualitzarTitol(autor, titol, newTitol);
        indexParaulaTFIDF.ActualitzarTitol(autor, titol, newTitol);
    }

    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        indexExpBooleana.ActualitzarAutor(autor, titol, newAutor);
        indexParaulaTFIDF.ActualitzarAutor(autor, titol, newAutor);
    }

    public void ActualitzarContingut(String autor, String titol, List<String> contingut) {
        indexExpBooleana.ActualitzarContingut(autor, titol, contingut);
        indexParaulaTFIDF.ActualitzarContingut(autor, titol, contingut);
    }

    public List<String> GetAutorsPrefix(String prefix) {
        return indexAutorPrefix.GetAutorsPrefix(prefix);
    }

    public List<Pair<String, String>> GetKDocsSimilarS (String autor, String titol, int K) {
        return indexParaulaTFIDF.GetKDocsSimilarS(new Pair<String, String>(autor, titol), K);
    }

    //Retorna els indexs de les frases que contenen paraula
    public List<Integer> GetFrases(String paraula) {
        return indexExpBooleana.GetFrases(paraula);
    }

    //Retorna els documents que contenen les frases indexs
    public List<Pair<String, String>> GetDocuments(List<Integer> indexs) {
        return indexExpBooleana.GetDocuments(indexs);
    }

}