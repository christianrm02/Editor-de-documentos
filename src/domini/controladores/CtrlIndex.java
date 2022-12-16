package domini.controladores;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Set;

import transversal.Pair;
import domini.datatypes.Trie;
import domini.indexs.*;


public class CtrlIndex {

    private Trie indexDocuments;
    private IndexExpBooleana indexExpBooleana;
    private IndexParaulaTFIDF indexParaulaTFIDF;
    

    public CtrlIndex() {
        indexDocuments = new Trie();
        indexExpBooleana = new IndexExpBooleana();
        indexParaulaTFIDF = new IndexParaulaTFIDF();
    }

    //PRE: NO EXISTEIX
    public void AfegirDoc(String autor, String titol, List<String> contingut) {
        indexDocuments.AfegirDoc(autor, titol);
        indexExpBooleana.AfegirDoc(autor, titol, contingut);
        indexParaulaTFIDF.AfegirDoc(autor, titol, contingut);
    }

    //PRE: EXISTEIX
    public void EsborrarDoc(String autor, String titol) {
        indexDocuments.EsborrarDoc(autor, titol);
        indexExpBooleana.EsborrarDoc(autor, titol);
        indexParaulaTFIDF.EsborrarDoc(autor, titol);
    }

    public boolean FindDoc(String autor, String titol) {
        return indexDocuments.FindDoc(autor, titol);
    }

    //PRE: EXISTEIX
    public void ActualitzarTitol(String autor, String titol, String newTitol) {
        indexDocuments.ActualitzarTitol(autor, titol, newTitol);
        indexExpBooleana.ActualitzarTitol(autor, titol, newTitol);
        indexParaulaTFIDF.ActualitzarTitol(autor, titol, newTitol);
    }

    //PRE: EXISTEIX
    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        indexDocuments.ActualitzarAutor(autor, titol, newAutor);
        indexExpBooleana.ActualitzarAutor(autor, titol, newAutor);
        indexParaulaTFIDF.ActualitzarAutor(autor, titol, newAutor);
    }

    //PRE: EXISTEIX
    public void ActualitzarContingut(String autor, String titol, List<String> contingut) {
        indexExpBooleana.ActualitzarContingut(autor, titol, contingut);
        indexParaulaTFIDF.ActualitzarContingut(autor, titol, contingut);
    }

    public Set<Pair<String, String>> GetKeys() {
        return indexDocuments.GetKeys();
    }

    public Set<String> GetTitolsAutor(String autor) {
        return indexDocuments.GetTitolsAutor(autor);
    }

    public Set<String> GetAutorsPrefix(String prefix) {
        return indexDocuments.SearchWordsPrefix(prefix);
    }

    public List<Pair<String, String>> GetKDocsSimilarS (String autor, String titol, int K, boolean estrategia) {
        return indexParaulaTFIDF.GetKDocsSimilarS(new Pair<String, String>(autor, titol), K, estrategia);
    }

    public List<Pair<String, String>> CercaPerRellevancia(String entrada, int K, boolean estrategia) {
        return indexParaulaTFIDF.CercaPerRellevancia(entrada, K, estrategia);
    }

    //Retorna els indexs de les frases que contenen paraula
    public Set<Integer> GetFrases(String paraula) {
        return indexExpBooleana.GetFrases(paraula);
    }

    public int GetNumFrases() {
        return indexExpBooleana.GetNumFrases();
    }

    public Set<Integer> GetSequencia(String sequencia, Set<Integer> candidats) {
        return indexExpBooleana.GetSequencia(sequencia, candidats);
    }

    //Retorna els documents que contenen les frases indexs
    public List<Pair<String, String>> GetDocuments(Set<Integer> indexs) {
        return indexExpBooleana.GetDocuments(indexs);
    }

    public void ImportarIndexs(byte[] info) throws IOException {
        try {
            //Si no existia el fitxer (programa obert per primer cop) no es fa res
            if(info == null) return;

            ByteArrayInputStream bais = new ByteArrayInputStream(info);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object[] indexs = (Object[]) ois.readObject();
            indexDocuments = (Trie) indexs[0];
            indexExpBooleana = (IndexExpBooleana) indexs[1];
            indexParaulaTFIDF = (IndexParaulaTFIDF) indexs[2];
            ois.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] ExportarIndexs() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        Object[] indexs = new Object[] {indexDocuments, indexExpBooleana, indexParaulaTFIDF};
        oos.writeObject(indexs);
        oos.close();
        return baos.toByteArray();
    }

}