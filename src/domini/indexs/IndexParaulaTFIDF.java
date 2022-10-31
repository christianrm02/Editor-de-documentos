package indexs;

import java.util.HashMap;
import java.util.List;

//Aquest index realitza el TFIDF pero ho fa a nivell de frase i no de document
public class IndexParaulaTFIDF {
    
    HashMap<String, DataTFIDF> indexTFIDF;  //Index
    Integer N;                              //Nombre de frases

    public IndexParaulaTFIDF() {
        indexTFIDF = new HashMap<String, DataTFIDF>();
    }

    //Retorna la frequencia de la paraula word
    public List<Double> GetTFs(String word) {
        return indexTFIDF.get(word).TFs;
    }
    
}

class DataTFIDF {
    List<Double> TFs;
    Double IDF;
    List<Double> TFIDFs;
}
