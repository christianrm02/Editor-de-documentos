package indexs;

import java.util.PriorityQueue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datatypes.Pair;
import datatypes.Utility;

//Aquest index desa els documents en forma de vector de TF-IDFs
//Insercions tenen cost linear amb el nombre de paraules
//Cerques tenen cost linear amb el nombre de documents
public class IndexParaulaTFIDF {

    static Set<String> stopWords;

    static {
        //Add stop words
        Path caPath = Path.of("./empty-ca-utf8.txt");
        Path spPath = Path.of("./empty-sp-utf8.txt");
        Path engPath = Path.of("./empty-eng-utf8.txt");

        try {
            String ca = Files.readString(caPath);
            String sp = Files.readString(spPath);
            String eng = Files.readString(engPath);

            String caASCII = Utility.UTF8toASCII(ca);
            String spASCII = Utility.UTF8toASCII(sp);
            String engASCII = Utility.UTF8toASCII(eng);

            String[] caStopWords = caASCII.split("\n");
            String[] spStopWords = spASCII.split("\n");
            String[] engStopWords = engASCII.split("\n");

            for (String caStopWord : caStopWords) stopWords.add(caStopWord);
            for (String spStopWord : spStopWords) stopWords.add(spStopWord);
            for (String engStopWord : engStopWords) stopWords.add(engStopWord);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    //Index TFIDFs per document. x es TF i y es TFIDF
    private HashMap<Pair<String, String>, HashMap<String, Pair<Double, Double>>> indexTFIDF;
    //Index IDF per paraula (ordenades de la mateixa manera que les columnes del indexTFIDF)
    private HashMap<String, Integer> indexGlobalTF;

    public IndexParaulaTFIDF() {
        indexTFIDF = new HashMap<Pair<String, String>, HashMap<String, Pair<Double, Double>>>();
        indexGlobalTF = new HashMap<String, Integer>();
    }

    public void AfegirDoc(String autor, String titol, List<String> contingut) {
        //Juntem totes les paraules del document en una llista
        List<String> paraules = getAllWords(contingut);
        int numWordsDoc = paraules.size();
        Pair<String, String> autorTitol = new Pair<String, String>(autor, titol);
        HashMap<String, Pair<Double, Double>> infoDoc = new HashMap<>();
        //Creem una nova fila pel nou document
        addCurrentWords(infoDoc);

        calcularTFs(autorTitol, paraules, numWordsDoc);
        calcularGlobalTFs(paraules);
        calcularTFIDFs();
    }

    public void EsborrarDoc(String autor, String titol) {
        Pair<String, String> autorTitol = new Pair<String,String>(autor, titol);

        var infoDoc = indexTFIDF.get(autorTitol);
        for(Map.Entry<String,Pair<Double, Double>> infoWord : infoDoc.entrySet()) {
            if(infoWord.getValue().x == 0.0) continue;
            String paraula = infoWord.getKey();
            int newGlobalTF = indexGlobalTF.get(paraula) - 1;
            indexGlobalTF.put(paraula, newGlobalTF);
        }

        indexTFIDF.remove(autorTitol);
        
        calcularTFIDFs();
    }

    public void ActualitzarTitol(String autor, String titol, String newTitol) {
        Pair<String, String> oldAutorTitol = new Pair<String, String>(autor, titol);
        Pair<String, String> newAutorTitol = new Pair<String, String>(autor, newTitol);

        var infoDoc = indexTFIDF.remove(oldAutorTitol);
        indexTFIDF.put(newAutorTitol, infoDoc);
    }

    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        Pair<String, String> oldAutorTitol = new Pair<String, String>(autor, titol);
        Pair<String, String> newAutorTitol = new Pair<String, String>(newAutor, titol);

        var infoDoc = indexTFIDF.remove(oldAutorTitol);
        indexTFIDF.put(newAutorTitol, infoDoc);
    }

    public void ActualitzarContingut(String autor, String titol, List<String> contingut) {
        EsborrarDoc(autor, titol);
        AfegirDoc(autor, titol, contingut);
    }

    public List<Pair<String, String>> GetKDocsSimilarS(Pair<String, String> autorTitol, int K) {
        //Obtenim la llista de TF-IDF's de S
        List<Pair<Double, Double>> qTFIDF = new ArrayList<>(indexTFIDF.get(autorTitol).values());
        
        //Creem un comparador que ordeni elements de gran a petit
        Comparator<Pair<Double, Pair<String, String>>> customComparator = new Comparator<Pair<Double, Pair<String, String>>>() {
            @Override
            public int compare(Pair<Double, Pair<String, String>> s1, Pair<Double, Pair<String, String>> s2) {
                return Double.compare(s2.x, s1.x);
            }
        };
        //Aqui colocarem els documents en ordre de similaritat
        PriorityQueue<Pair<Double, Pair<String, String>>> docsSemblants = new PriorityQueue<Pair<Double, Pair<String, String>>>(customComparator);

        for (Pair<String, String> doc : indexTFIDF.keySet()) {
            if(doc == autorTitol) continue;

            List<Pair<Double, Double>> docTFIDF = new ArrayList<>(indexTFIDF.get(doc).values());
            double metric = cosinusMetric(qTFIDF, docTFIDF);

            docsSemblants.add(new Pair<Double, Pair<String, String>>(metric, doc));
        }
        
        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();

        //Retornem els K primers resultats
        for(int i = 0; i < K; i++) result.add(docsSemblants.poll().y);
        
        return result;
    }

    static private Double cosinusMetric(List<Pair<Double, Double>> query, List<Pair<Double, Double>> document){
        double dot = 0.0;
        double queryNorm = 0.0;
        double documentNorm = 0.0;

        for(int i = 0; i < query.size(); i++) {
            double q = query.get(i).y;
            double d = document.get(i).y;

            dot += q*d;
            queryNorm += q*q;
            documentNorm += d*d;
        }

        queryNorm = Math.sqrt(queryNorm);
        documentNorm = Math.sqrt(documentNorm);

        return dot/(queryNorm*documentNorm);
    }

    static private List<String> getAllWords(List<String> frases){
        List<String> paraules = new ArrayList<String>();
        for (String frase : frases) {
            paraules.addAll(Arrays.asList(Utility.parseFrase(frase)));
        }
        return paraules;
    }

    private void addCurrentWords(HashMap<String, Pair<Double, Double>> infoDoc){
        for (String word : indexGlobalTF.keySet()) {
            Pair<Double, Double> infoWord = new Pair<Double,Double>(0.0, 0.0);
            infoDoc.put(word, infoWord);
        }
    }

    private void addColumn(String word) {
        indexGlobalTF.put(word, 0);
        for (HashMap<String, Pair<Double, Double>> infoDoc : indexTFIDF.values()) {
            Pair<Double, Double> infoWord = new Pair<Double,Double>(0.0, 0.0);
            infoDoc.put(word, infoWord);
        }
    }

    private double idf(String word) {
        return Math.log((1+indexTFIDF.size())/(1+indexGlobalTF.get(word))) + 1;
    }

    private void calcularTFs(Pair<String, String> autorTitol, List<String> paraules, int numWordsDoc){
        //Sumem les paraules que apareixen al document
        for(String paraula : paraules) {
            if(stopWords.contains(paraula)) continue;
            //Si la paraula no existia afegim una nova columna als 2 indexs
            if(!indexGlobalTF.containsKey(paraula)) {
                addColumn(paraula);
            }
            //Sumem 1 a TF
            indexTFIDF.get(autorTitol).get(paraula).x++;
        }
        //Dividim pel nombre de paraules del document per obtenir TF
        for (Pair<Double, Double> infoWord : indexTFIDF.get(autorTitol).values()) {
            infoWord.x = infoWord.x/numWordsDoc;
        }
    }

    private void calcularGlobalTFs(List<String> paraules) {
        //Per cada paraula recorrem la seva columna del index i comptem els cops que el seu TF > 0
        for (String paraula : paraules) {
            int count = 0; 
            for (HashMap<String, Pair<Double, Double>> infoDoc : indexTFIDF.values()) {
                if(infoDoc.get(paraula).x > 0.0) count++;
            }
            indexGlobalTF.put(paraula, count);
        }
    }

    private void calcularTFIDFs() {
        for (HashMap<String, Pair<Double, Double>> infoDoc : indexTFIDF.values()) {
            for(Map.Entry<String,Pair<Double, Double>> infoWord : infoDoc.entrySet()) {
                String paraula = infoWord.getKey();
                infoWord.getValue().y = infoWord.getValue().x * idf(paraula);
            }
        }
    }
}
