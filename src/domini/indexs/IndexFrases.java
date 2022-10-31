package indexs;

import java.util.ArrayList;
import java.util.List;

import datatypes.PairAutorTitol;


//Aquest index indica a quin document es troba cada frase (molt util per buscar per expressio booleana un cop es tenen les frases que compleixen l'expressio)
public class IndexFrases {
    
    private List<PairAutorTitol> index;

    public IndexFrases() {
        index = new ArrayList<PairAutorTitol>();
    }

    public void AddDocument(PairAutorTitol document) {
        index.add(document);
    }

    public PairAutorTitol GetDocument(int IndexFrase) {
        return index.get(IndexFrase);
    }
    
}
