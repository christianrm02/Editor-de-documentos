package indexs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import datatypes.Contingut;
import datatypes.Pair;

//Aquest index realitza el TFIDF pero ho fa a nivell de frase i no de document
public class IndexParaulaTFIDF {

    static Set<String> stopWords;

    static {
        //Add stop words
    }
    
    private HashMap<Pair<String, String>, Pair<List<Double>, List<Double>>> indexTFIDF; //Index TFIDFs per document
    private HashMap<String, Double> indexIDF;                                           //Index IDF per paraula

    public IndexParaulaTFIDF() {
        indexTFIDF = new HashMap<Pair<String, String>, Pair<List<Double>, List<Double>>>();
        indexIDF = new HashMap<String, Double>();
    }

    public void AfegirDoc(String autor, String titol, Contingut contingut) {
       
    }

    public void EsborrarDoc(String autor, String titol) {

    }

    public void ActualitzarTitol(String autor, String titol, String newTitol) {

    }

    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        
    }

    public void ActualitzarContingut(String autor, String titol, Contingut oldContingut, Contingut contingut) {

    }

    public List<Pair<String, String>> GetKDocsSimilarS(Pair<String, String> autorTitol, int K) {
        //Obtenim la llista de TF-IDF's de S
        List<Double> docTFIDF = indexTFIDF.get(autorTitol).TFIDFs;

        for (List<Double> TFIDFs : indexTFIDF.values().) {
            
        }
        
        
        
        return null;
    }
    
}
