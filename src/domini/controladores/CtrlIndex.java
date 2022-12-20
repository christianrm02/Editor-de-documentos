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

/**
 * CtrlIndex: Controlador de domini pels índexs
 * @author Èric Ryhr
 */
public class CtrlIndex {

    /**
     * Índex per desar les claus dels documents
     */
    private Trie indexDocuments;

    /**
     * Índex per assistir les queries per expressió booleana
     */
    private IndexExpBooleana indexExpBooleana;

    /**
     * Índex per realitzar comparacions entre documents
     */
    private IndexParaulaTFIDF indexParaulaTFIDF;
    
    /**
     * Constructora de la classe CtrlIndex. Inicialitza els índexs
     */
    public CtrlIndex() {
        indexDocuments = new Trie();
        indexExpBooleana = new IndexExpBooleana();
        indexParaulaTFIDF = new IndexParaulaTFIDF();
    }

    
    /** 
     * Mètode per insertar documents als índexs
     * @param autor - Autor del document
     * @param titol - Títol del document
     * @param contingut - Contingut del document
     */
    public void AfegirDoc(String autor, String titol, List<String> contingut) {
        indexDocuments.AfegirDoc(autor, titol);
        indexExpBooleana.AfegirDoc(autor, titol, contingut);
        indexParaulaTFIDF.AfegirDoc(autor, titol, contingut);
    }

    
    /** 
     * Mètode per esborrar documents dels índexs
     * @param autor - Autor del document
     * @param titol - Títol del document
     */
    public void EsborrarDoc(String autor, String titol) {
        indexDocuments.EsborrarDoc(autor, titol);
        indexExpBooleana.EsborrarDoc(autor, titol);
        indexParaulaTFIDF.EsborrarDoc(autor, titol);
    }

    
    /** 
     * Mètode per comprovar si existeixen documents als índexs
     * @param autor - Autor del document a buscar
     * @param titol - Títol del document a buscar
     * @return boolean - True si el document es troba als índexs, False si no
     */
    public boolean FindDoc(String autor, String titol) {
        return indexDocuments.FindDoc(autor, titol);
    }

    
    /** 
     * Mètode per actualitzar el títol d'un document dels índexs
     * @param autor - Autor del document
     * @param titol - Títol del document
     * @param newTitol - Nou títol del document
     */
    public void ActualitzarTitol(String autor, String titol, String newTitol) {
        indexDocuments.ActualitzarTitol(autor, titol, newTitol);
        indexExpBooleana.ActualitzarTitol(autor, titol, newTitol);
        indexParaulaTFIDF.ActualitzarTitol(autor, titol, newTitol);
    }

    
    /** 
     * Mètode per actualitzar l'autor d'un document dels índexs
     * @param autor - Autor del document
     * @param titol - Títol del document
     * @param newAutor - Nou autor del document
     */
    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        indexDocuments.ActualitzarAutor(autor, titol, newAutor);
        indexExpBooleana.ActualitzarAutor(autor, titol, newAutor);
        indexParaulaTFIDF.ActualitzarAutor(autor, titol, newAutor);
    }

    
    /** 
     * Mètode per actualitzar el contingut d'un document dels índexs
     * @param autor - Autor del document
     * @param titol - Títol del document
     * @param contingut - Contingut del document
     */
    //PRE: EXISTEIX
    public void ActualitzarContingut(String autor, String titol, List<String> contingut) {
        indexExpBooleana.ActualitzarContingut(autor, titol, contingut);
        indexParaulaTFIDF.ActualitzarContingut(autor, titol, contingut);
    }

    
    /** 
     * Mètode per obtenir totes les claus dels documents dels índexs
     * @return Set<Pair<String, String>> - Set amb tots els parells (autor, títol) dels índexs
     */
    public Set<Pair<String, String>> GetKeys() {
        return indexDocuments.GetKeys();
    }

    
    /** 
     * Mètode per obtenir tots els títols d'un autor
     * @param autor - Autor del que s'obtenen els títols
     * @return Set<String> - Set amb els títols de l'autor. Buit si no existeix
     */
    public Set<String> GetTitolsAutor(String autor) {
        return indexDocuments.GetTitolsAutor(autor);
    }

    
    /** 
     * Mètode per obtenir els autors que comencen per prefix
     * @param prefix - Prefix pel que han de començar els noms dels autors
     * @return Set<String> - Set amb els autors obtinguts
     */
    public Set<String> GetAutorsPrefix(String prefix) {
        return indexDocuments.SearchWordsPrefix(prefix);
    }

    
    /** 
     * Mètode per obtenir els documents més semblants al passat per paràmetre
     * @param autor - Autor del document a comparar
     * @param titol - Títol del document a comparar
     * @param K - Nombre de respostes a retornar
     * @param estrategia - True si la comparació es fa amb TF, False si amb TF-IDF
     * @return List<Pair<String, String>> - Llista dels documents més semblants ordenats amb el més semblant primer
     */
    public List<Pair<String, String>> GetKDocsSimilarS (String autor, String titol, int K, boolean estrategia) {
        return indexParaulaTFIDF.GetKDocsSimilarS(new Pair<String, String>(autor, titol), K, estrategia);
    }

    
    /** 
     * Mètode per obtenir els documents més semblants a l'entrada passada per paràmetre
     * @param entrada - Entrada a comparar
     * @param K - Nombre de respostes a retornar
     * @param estrategia - True si la comparació es fa amb TF, False si amb TF-IDF
     * @return List<Pair<String, String>> - Llista dels documents més semblants ordenats amb el més semblant primer
     */
    public List<Pair<String, String>> CercaPerRellevancia(String entrada, int K, boolean estrategia) {
        return indexParaulaTFIDF.CercaPerRellevancia(entrada, K, estrategia);
    }

    
    /** 
     * Mètode per obtenir els índexs de les frases on apareix una paraula
     * @param paraula - Paraula a buscar
     * @return Set<Integer> - Índex de les frases on apareix paraula
     */
    //Retorna els indexs de les frases que contenen paraula
    public Set<Integer> GetFrases(String paraula) {
        return indexExpBooleana.GetFrases(paraula);
    }

    
    /** 
     * Mètode per obtenir el nombre de frases emmagatzemades
     * @return int - Nombre de frases emmagatzemades
     */
    public int GetNumFrases() {
        return indexExpBooleana.GetNumFrases();
    }

    
    /** 
     * Mètode per obtenir els índexs de les frases on apareix una seqüència de paraules
     * @param sequencia - Seqüència a buscar
     * @param candidats - Índex de les frases que contenen totes les paraules de la seqüència (potser no en ordre)
     * @return Set<Integer> - Índex de les frases on apareix seqüència
     */
    public Set<Integer> GetSequencia(String sequencia, Set<Integer> candidats) {
        return indexExpBooleana.GetSequencia(sequencia, candidats);
    }

    
    /** 
     * Mètode per, donat un conjunt d'índexs, retornar els documents on apareixen
     * @param indexs - Conjunt d'índexs a buscar
     * @return List<Pair<String, String>> - Claus dels documents obtinguts
     */
    public List<Pair<String, String>> GetDocuments(Set<Integer> indexs) {
        return indexExpBooleana.GetDocuments(indexs);
    }

    
    /** 
     * Mètode per importar els indexs de disc i sobreescriure els actuals
     * @param info - Índexs en forma d'array de bytes
     * @throws IOException Hi ha hagut algun problema amb els streams
     */
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

    
    /** 
     * Mètode per exportar els indexs a capa persistència
     * @return byte[] - Els 3 índexs serialitzats en forma de byte array
     * @throws IOException Hi ha hagut algun problema amb els streams
     */
    public byte[] ExportarIndexs() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        Object[] indexs = new Object[] {indexDocuments, indexExpBooleana, indexParaulaTFIDF};
        oos.writeObject(indexs);
        oos.close();
        return baos.toByteArray();
    }

}