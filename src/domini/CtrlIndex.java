import java.util.List;

import datatypes.PairAutorTitol;
import indexs.*;


class CtrlIndex {

    //IndexAutorTitols indexAutorTitols;
    IndexAutorPrefix indexAutorPrefix;
    IndexParaulaTFIDF indexParaulaTFIDF;
    

    public CtrlIndex() {
        indexAutorPrefix = new IndexAutorPrefix();
        indexParaulaTFIDF = new IndexParaulaTFIDF();
    }

    public void AfegirDoc(String autor, String titol) {

    }

    public void EsborrarDoc(String autor, String titol) {

    }

    public void ActualitzarTitol(String autor, String titol, String newTitol) {

    }

    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        
    }

    public void ActualitzarContingut(String autor, String titol, Contingut contingut) {

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

}