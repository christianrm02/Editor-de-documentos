package indexs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import datatypes.PairAutorTitol;


//Aquest index indica a quin document es troba cada frase (molt util per buscar per expressio booleana un cop es tenen les frases que compleixen l'expressio)
public class IndexExpBooleana {
    
    private HashMap<String, List<Boolean>> indexParaulaFrase;   //Per cada paraula indica a quines frases apareix
    private List<PairAutorTitol> indexFraseDocument;            //Per cada frase(index) indica a quin document pertany   

    public IndexExpBooleana() {
        indexParaulaFrase = new HashMap<String, List<Boolean>>();
        indexFraseDocument = new ArrayList<PairAutorTitol>();
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
    
}
