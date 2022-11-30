package controladores;

import java.util.List;
import java.util.Set;

import transversal.Pair;
import datatypes.Trie;
import indexs.*;


public class CtrlIndex {

    private Trie indexDocuments;
    private IndexExpBooleana indexExpBooleana;
    private IndexParaulaTFIDF indexParaulaTFIDF;
    

    public CtrlIndex() {
        indexDocuments = new Trie();
        indexExpBooleana = new IndexExpBooleana();
        indexParaulaTFIDF = new IndexParaulaTFIDF();
    }

    public void AfegirDoc(String autor, String titol, List<String> contingut) {
        if(indexDocuments.FindDoc(autor, titol)) return;

        indexDocuments.AfegirDoc(autor, titol);
        indexExpBooleana.AfegirDoc(autor, titol, contingut);
        indexParaulaTFIDF.AfegirDoc(autor, titol, contingut);
    }

    public void EsborrarDoc(String autor, String titol) {
        if(!indexDocuments.FindDoc(autor, titol)) return;

        indexDocuments.EsborrarDoc(autor, titol);
        indexExpBooleana.EsborrarDoc(autor, titol);
        indexParaulaTFIDF.EsborrarDoc(autor, titol);
    }

    public void ActualitzarTitol(String autor, String titol, String newTitol) {
        if(!indexDocuments.FindDoc(autor, titol)) return;

        indexDocuments.ActualitzarTitol(autor, titol, newTitol);
        indexExpBooleana.ActualitzarTitol(autor, titol, newTitol);
        indexParaulaTFIDF.ActualitzarTitol(autor, titol, newTitol);
    }

    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        if(!indexDocuments.FindDoc(autor, titol)) return;

        indexDocuments.ActualitzarAutor(autor, titol, newAutor);
        indexExpBooleana.ActualitzarAutor(autor, titol, newAutor);
        indexParaulaTFIDF.ActualitzarAutor(autor, titol, newAutor);
    }

    public void ActualitzarContingut(String autor, String titol, List<String> contingut) {
        if(!indexDocuments.FindDoc(autor, titol)) return;

        indexExpBooleana.ActualitzarContingut(autor, titol, contingut);
        indexParaulaTFIDF.ActualitzarContingut(autor, titol, contingut);
    }

    public Set<String> GetTitolsAutor(String autor) {
        return indexDocuments.GetTitolsAutor(autor);
    }

    public List<String> GetAutorsPrefix(String prefix) {
        return indexDocuments.SearchWordsPrefix(prefix);
    }

    public List<Pair<String, String>> GetKDocsSimilarS (String autor, String titol, int K, boolean estrategia) {
        return indexParaulaTFIDF.GetKDocsSimilarS(new Pair<String, String>(autor, titol), K, estrategia);
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

}