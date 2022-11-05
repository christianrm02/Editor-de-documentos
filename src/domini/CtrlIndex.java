import java.util.List;

import datatypes.Contingut;
import datatypes.Document;
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

    public void AfegirDoc(Document doc) {
        String autor = doc.getAutor();
        String titol = doc.getTitol();
        Contingut cont = doc.getContingut();

        indexAutorPrefix.InsertAutor(autor);
        indexExpBooleana.AfegirDoc(autor, titol, cont);
    }

    public void EsborrarDoc(String autor, String titol) {
        //if(autor.count == 0) indexAutorPrefix.DeleteAutor(autor);
        indexExpBooleana.EsborrarDoc(autor, titol);
    }

    public void ActualitzarTitol(String autor, String titol, String newTitol) {
        indexExpBooleana.ActualitzarTitol(autor, titol, newTitol);
    }

    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        //Comprovar si el newAutor ja exsitia i si l'antic s'ha quedat sense copies
    }

    public void ActualitzarContingut(String autor, String titol, Contingut oldContingut, Contingut contingut) {
        indexExpBooleana.ActualitzarContingut(autor, titol, contingut);
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