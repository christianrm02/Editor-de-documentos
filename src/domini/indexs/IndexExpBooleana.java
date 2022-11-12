package indexs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import transversal.Pair;
import datatypes.Utility;


/**
 * IndexExpBooleana: Index per les cerques per expressió booleana
 * @author Èric Ryhr
 */
public class IndexExpBooleana {
    
    private HashMap<String, List<Boolean>> indexParaulaFrase;   //Per cada paraula indica a quines frases apareix
    private List<Pair<String, String>> indexFraseDocument;      //Per cada frase(index) indica a quin document pertany 
    private List<String> indexFrases;                           //Ens guardem les frases en ordre per comprovar si existeixen sequencies de paraules

    public IndexExpBooleana() {
        indexParaulaFrase = new HashMap<String, List<Boolean>>();
        indexFraseDocument = new ArrayList<Pair<String, String>>();
        indexFrases = new ArrayList<String>();
    }

    public void AfegirDoc(String autor, String titol, List<String> contingut) {
        Pair<String, String> autorTitol = new Pair<String, String>(autor, titol);

        for (String frase : contingut) {
            indexFraseDocument.add(autorTitol);
            indexFrases.add(frase);
            addPosition();
            String[] paraules = Utility.ParseFrase(frase);

            for (String paraula : paraules) {
                //Si la paraula no era al index afegim una altra entrada
                if(!indexParaulaFrase.containsKey(paraula)) {
                    List<Boolean> infoParaula = new ArrayList<Boolean>();
                    fillList(infoParaula);                  //Omplim la llista amb false
                    infoParaula.set(indexFrases.size()-1, true);  //L'ultim element sera true perque es la paraula que acabem de insertar
                    indexParaulaFrase.put(paraula, infoParaula);
                }
                //Si la paraula ja existeix al index simplement posem a true la casella corresponent 
                else {
                    List<Boolean> infoParaula = indexParaulaFrase.get(paraula);
                    infoParaula.set(indexFrases.size()-1, true);
                    indexParaulaFrase.put(paraula, infoParaula);
                }
            }
        }
    }

    //PRE: El document existeix
    public void EsborrarDoc(String autor, String titol) {
        int startingIndexEliminar = -1;
        int numFrasesEliminar = 0;

        //Obtenim totes les frases que pertanyen al document que esborrarem
        //OPTIMITZACIO: Es pot fer un break quan ha trobat tots els autorTitol iguals seguits
        for (int i = 0; i < indexFraseDocument.size(); i++) {
            Pair<String, String> autorTitol = indexFraseDocument.get(i);
            if(autor == autorTitol.x && titol == autorTitol.y) {
                if(startingIndexEliminar == -1) startingIndexEliminar = i;
                numFrasesEliminar++;
            } else if (startingIndexEliminar != -1) break;
        }

        //Eliminem les frases
        for (int i = startingIndexEliminar; i < startingIndexEliminar+numFrasesEliminar; i++) {
            //int s = indexFraseDocument.size();
            indexFraseDocument.remove(startingIndexEliminar);
            indexFrases.remove(startingIndexEliminar);

            //Del indexParaulaFrase eliminem les posicions de les frases eliminades
            for (List<Boolean> infoParaula : indexParaulaFrase.values()) {
                infoParaula.remove(startingIndexEliminar);
            }
        }

        //Si alguna paraula no apareix a cap frase l'eliminem de l'index
        cleanIndex();
    }

    //PRE: El document existeix
    public void ActualitzarTitol(String autor, String titol, String newTitol) {
        Pair<String, String> oldAutorTitol = new Pair<String, String>(autor, titol);
        Pair<String, String> newAutorTitol = new Pair<String, String>(autor, newTitol);
        
        int startingIndexCanviar = -1;
        int numDocsCanviar = 0;
        for (int i = 0; i < indexFraseDocument.size(); i++) {
            if(indexFraseDocument.get(i).equals(oldAutorTitol)) {
                if(startingIndexCanviar == -1) startingIndexCanviar = i;
                numDocsCanviar++;
            } else if (startingIndexCanviar != -1) break;
        }

        for (int i = startingIndexCanviar; i < startingIndexCanviar+numDocsCanviar; i++) {
            indexFraseDocument.set(i, newAutorTitol);
        }
    }

    //PRE: El document existeix
    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        Pair<String, String> oldAutorTitol = new Pair<String, String>(autor, titol);
        Pair<String, String> newAutorTitol = new Pair<String, String>(newAutor, titol);
        
        int startingIndexCanviar = -1;
        int numDocsCanviar = 0;
        for (int i = 0; i < indexFraseDocument.size(); i++) {
            if(indexFraseDocument.get(i).equals(oldAutorTitol)) {
                if(startingIndexCanviar == -1) startingIndexCanviar = i;
                numDocsCanviar++;
            } else if (startingIndexCanviar != -1) break;
        }

        for (int i = startingIndexCanviar; i < startingIndexCanviar+numDocsCanviar; i++) {
            indexFraseDocument.set(i, newAutorTitol);
        }
    }

    public void ActualitzarContingut(String autor, String titol, List<String> contingut) {
        EsborrarDoc(autor, titol);
        AfegirDoc(autor, titol, contingut);
    }

    //Retorna els indexs de les frases que contenen paraula
    public Set<Integer> GetFrases(String paraula) {
        Set<Integer> frases = new HashSet<Integer>();
        List<Boolean> infoParaula = indexParaulaFrase.get(paraula);

        //Si la paraula no es a cap frase returnem una llista buida
        if(infoParaula == null) return frases;

        for(int i = 0; i < infoParaula.size(); i++) {
            if(infoParaula.get(i)) frases.add(i);
        }

        return frases;
    }

    public int GetNumFrases(){
        return indexFrases.size();
    }

    //Comprova si existeix una sequencia de paraules seguides a una frase
    public Set<Integer> GetSequencia(String sequencia, Set<Integer> candidats){
        Set<Integer> res = new HashSet<Integer>();

        for (Integer i : candidats) {
            if(indexFrases.get(i).contains(sequencia)) res.add(i);
        }

        return res;
    }

    //Retorna els documents que contenen les frases indexs
    public List<Pair<String, String>> GetDocuments(Set<Integer> indexs) {
        List<Pair<String, String>> docs = new ArrayList<Pair<String, String>>();

        for (int index : indexs) {
            docs.add(indexFraseDocument.get(index));
        }

        return docs;
    }

    //Omplira les primeres posicions de la llista amb false
    private void fillList(List<Boolean> infoWord) {
        for(int i = 0; i < indexFrases.size(); i++) infoWord.add(false);
    }
    
    //Crea una nova columna de false al index per la frase que inserirem a continuacio
    private void addPosition() {
        for (List<Boolean> infoParaula : indexParaulaFrase.values()) {
            infoParaula.add(false);
        }
    }

    //Netegem el index treient les paraules que no apareixen
    private void cleanIndex() {
        Set<String> toRemove = new HashSet<String>();

        //Busquem les paraules que no tenen entrades
        for (String paraula : indexParaulaFrase.keySet()) {
            List<Boolean> infoParaula = indexParaulaFrase.get(paraula);
            boolean found = false;
            for (boolean b : infoParaula) {
                if(b) {
                    found = true;
                    break;
                }
            }
            if(!found) toRemove.add(paraula);
        }
        //Borrem les paraules que no tenen entrades
        for (String s : toRemove) indexParaulaFrase.remove(s);
    }
}
