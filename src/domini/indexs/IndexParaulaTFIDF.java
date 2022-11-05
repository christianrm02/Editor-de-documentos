package indexs;

import java.util.HashMap;
import java.util.List;

//Aquest index realitza el TFIDF pero ho fa a nivell de frase i no de document
public class IndexParaulaTFIDF {
    
    private HashMap<String, DataTFIDF> indexTFIDF;  //Index
    private int N;                                  //Nombre de documents

    public IndexParaulaTFIDF() {
        indexTFIDF = new HashMap<String, DataTFIDF>();
    }

    
    
}

class DataTFIDF {
    List<Double> TFs;       //Term frequencies per document
    Double IDF;             //IDF of a term between all documents
    List<Double> TFIDFs;    //TF-IDF of a term in a document
}
