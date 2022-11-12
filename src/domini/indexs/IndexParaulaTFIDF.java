package indexs;

import java.util.PriorityQueue;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import transversal.Pair;
import datatypes.Utility;

/**
 * IndexParaulaTFIDF: Index de semblança entre documents
 * @author Èric Ryhr
 */
public class IndexParaulaTFIDF {

    static Set<String> stopWords;
    
    //Index TFIDFs per document i paraula. x es TF i y es TFIDF
    private TreeMap<Pair<String, String>, TreeMap<String, Pair<Double, Double>>> indexTFIDF;
    //Index nombre de documents on apareix la paraula
    private TreeMap<String, Integer> indexGlobalTF;

    public IndexParaulaTFIDF() {
        indexTFIDF = new TreeMap<Pair<String, String>, TreeMap<String, Pair<Double, Double>>>();
        indexGlobalTF = new TreeMap<String, Integer>();
        if(stopWords == null) readStopWords();
    }

    public void AfegirDoc(String autor, String titol, List<String> contingut) {
        //Juntem totes les paraules del document en una llista
        List<String> paraules = getAllWords(contingut);
        int numWordsDoc = paraules.size();
        Pair<String, String> autorTitol = new Pair<String, String>(autor, titol);
        //Creem una nova fila pel nou document
        TreeMap<String, Pair<Double, Double>> infoDoc = new TreeMap<>();
        calcularTFs(infoDoc, paraules, numWordsDoc);
        indexTFIDF.put(autorTitol, infoDoc);

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

    public boolean DocExists(Pair<String, String> autorTitol){
        return indexTFIDF.get(autorTitol) != null;
    }

    public List<Pair<String, String>> GetKDocsSimilarS(Pair<String, String> autorTitol, int K, boolean estrategia) {
        //Obtenim la llista de TF-IDF's de S
        TreeMap<String, Pair<Double, Double>> qTFIDF = indexTFIDF.get(autorTitol);
        
        //Creem un comparador que ordeni elements de gran a petit
        Comparator<Pair<Double, Pair<String, String>>> customComparator = new Comparator<Pair<Double, Pair<String, String>>>() {
            @Override
            public int compare(Pair<Double, Pair<String, String>> s1, Pair<Double, Pair<String, String>> s2) {
                return Double.compare(s2.x, s1.x);
            }
        };
        //Aqui colocarem els documents en ordre de similaritat
        PriorityQueue<Pair<Double, Pair<String, String>>> docsSemblants = new PriorityQueue<Pair<Double, Pair<String, String>>>(customComparator);

        //Comparem tots els documents amb query
        for (Pair<String, String> doc : indexTFIDF.keySet()) {
            if(doc.equals(autorTitol)) continue;

            TreeMap<String, Pair<Double, Double>> docTFIDF = indexTFIDF.get(doc);
            double metric = cosinusMetric(qTFIDF, docTFIDF, estrategia);

            docsSemblants.add(new Pair<Double, Pair<String, String>>(metric, doc));
        }
        
        //Retornem els K primers resultats
        List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
        for(int i = 0; i < K; i++) {
            if(docsSemblants.size() == 0) return result;
            result.add(docsSemblants.poll().y);
        }
        
        return result;
    }

    static private Double cosinusMetric(TreeMap<String, Pair<Double, Double>> query, TreeMap<String, Pair<Double, Double>> document, boolean estrategia){
        //Si algun dels documents es buit la metrica es nula
        if(query.size() == 0 || document.size() == 0) return 0.0;

        double dot = 0.0;
        double queryNorm = 0.0;
        double documentNorm = 0.0;

        Iterator<String> itQuery = query.keySet().iterator();
        Iterator<String> docQuery = document.keySet().iterator();
        String wordQ = (String) itQuery.next();
        String wordD = (String) docQuery.next();
        
        while(itQuery.hasNext() && docQuery.hasNext()) {
            double q, d;
            if(estrategia) {
                //Agafem TFs
                q = query.get(wordQ).x;
                d = document.get(wordD).x;
            } else {
                //Agafem TFIDFs
                q = query.get(wordQ).y;
                d = document.get(wordD).y;
            }
            //Si son iguals calculem el producte escalar i avancem els 2 iteradors
            if(wordQ.equals(wordD)) {
                dot += q*d;
                queryNorm += q*q;
                documentNorm += d*d;
                wordQ = (String) itQuery.next();
                wordD = (String) docQuery.next();
            //Si un dels dos es mes petit l'avancem fins que agafi a l'altre
            } else if (wordQ.compareTo(wordD) < 0) {
                queryNorm += q*q;
                wordQ = (String) itQuery.next();
            } else {
                documentNorm += d*d;
                wordD = (String) docQuery.next();
            }
        } 

        queryNorm = Math.sqrt(queryNorm);
        documentNorm = Math.sqrt(documentNorm);

        return dot/(queryNorm*documentNorm);
    }

    static private List<String> getAllWords(List<String> frases){
        List<String> paraules = new ArrayList<String>();
        for (String frase : frases) {
            paraules.addAll(Arrays.asList(Utility.ParseFrase(frase)));
        }
        return paraules;
    }

    private double idf(String word) {
        return Math.log((1+indexTFIDF.size())/(1+indexGlobalTF.get(word))) + 1;
    }

    private void calcularTFs(TreeMap<String, Pair<Double, Double>> infoDoc, List<String> paraules, int numWordsDoc){
        //Sumem les paraules que apareixen al document
        for(String paraula : paraules) {
            if(stopWords.contains(paraula)) continue;
            //Si la paraula no existia afegim una nova columna al index global de paraules
            if(!indexGlobalTF.containsKey(paraula)) {
                indexGlobalTF.put(paraula, 0);
            }
            //Sumem 1 a TF
            if(infoDoc.get(paraula) == null) infoDoc.put(paraula, new Pair<Double, Double>(1.0, 0.0));
            else {
                //Fem get i despres put perque no es pot fer get(paraula).x++
                Pair<Double, Double> aux = infoDoc.get(paraula);
                aux.x++;
                infoDoc.put(paraula, aux);
            }
        }
        //Dividim pel nombre de paraules del document per obtenir TF
        for (Pair<Double, Double> infoWord : infoDoc.values()) {
            infoWord.x = infoWord.x/numWordsDoc;
        }
    }

    private void calcularGlobalTFs(List<String> paraules) {
        //Per cada paraula recorrem la seva columna del index i comptem els cops que el seu TF > 0
        for (String paraula : paraules) {
            int count = 0; 
            for (TreeMap<String, Pair<Double, Double>> infoDoc : indexTFIDF.values()) {
                if(infoDoc.get(paraula) != null) 
                    if(infoDoc.get(paraula).x > 0.0) count++;
            }
            indexGlobalTF.put(paraula, count);
        }
    }

    private void calcularTFIDFs() {
        for (TreeMap<String, Pair<Double, Double>> infoDoc : indexTFIDF.values()) {
            for(Map.Entry<String,Pair<Double, Double>> infoWord : infoDoc.entrySet()) {
                String paraula = infoWord.getKey();
                infoWord.getValue().y = infoWord.getValue().x * idf(paraula);
            }
        }
    }

    private void readStopWords() {
        stopWords = new HashSet<>();
        
        //Add stop words
        try {
            Path caPath = Paths.get(getClass().getResource("empty-ca-utf8.txt").toURI());
            Path spPath = Paths.get(getClass().getResource("empty-sp-utf8.txt").toURI());
            Path engPath = Paths.get(getClass().getResource("empty-eng-utf8.txt").toURI());
            String ca = Files.readString(caPath);
            String sp = Files.readString(spPath);
            String eng = Files.readString(engPath);

            String[] caStopWords = ca.split("\n");
            String[] spStopWords = sp.split("\n");
            String[] engStopWords = eng.split("\n");

            for (String caStopWord : caStopWords) stopWords.add(caStopWord);
            for (String spStopWord : spStopWords) stopWords.add(spStopWord);
            for (String engStopWord : engStopWords) stopWords.add(engStopWord);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
