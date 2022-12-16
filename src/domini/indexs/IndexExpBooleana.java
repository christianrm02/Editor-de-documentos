package domini.indexs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import transversal.Pair;
import domini.datatypes.Utility;


/**
 * IndexExpBooleana: Index per les cerques per expressió booleana
 * @author Èric Ryhr
 */
public class IndexExpBooleana implements Serializable{
    
    private HashMap<String, List<Pair<Boolean, String>>> indexParaulaFrase;   //Per cada paraula indica a quines frases apareix i la paraula que te a continuacio
    private List<Pair<String, String>> indexFraseDocument;      //Per cada frase(index) indica a quin document pertany 

    public IndexExpBooleana() {
        indexParaulaFrase = new HashMap<>();
        indexFraseDocument = new ArrayList<Pair<String, String>>();
    }

    public void AfegirDoc(String autor, String titol, List<String> contingut) {
        Pair<String, String> autorTitol = new Pair<String, String>(autor, titol);

        for (String frase : contingut) {
            indexFraseDocument.add(autorTitol);
            int n = indexFraseDocument.size();
            addPosition();
            String[] paraules = Utility.ParseFrase(frase);

            for(int i = 0; i < paraules.length; i++) {
                String paraula = paraules[i];
                //Si la paraula no era al index afegim una altra entrada
                if(!indexParaulaFrase.containsKey(paraula)) {
                    List<Pair<Boolean, String>> infoParaula = new ArrayList<Pair<Boolean, String>>();
                    fillList(infoParaula);      //Omplim la llista amb false

                    Pair<Boolean, String> infoParaulaFrase = infoParaula.get(n-1);
                    infoParaulaFrase.x = true;          //L'ultim element sera true perque es la frase que acabem de insertar
                    if(i != paraules.length-1) infoParaulaFrase.y = paraules[i+1]; //Ens guardem la seguent paraula
                    infoParaula.set(n-1, infoParaulaFrase);
                    indexParaulaFrase.put(paraula, infoParaula);//Inserim la fila a l'index
                }
                //Si la paraula ja existeix al index simplement posem a true la casella corresponent i guardem la paraula seguent 
                else {
                    List<Pair<Boolean, String>> infoParaula = indexParaulaFrase.get(paraula);
                    Pair<Boolean, String> infoParaulaFrase = infoParaula.get(n-1);
                    infoParaulaFrase.x = true;          //L'ultim element sera true perque es la frase que acabem de insertar
                    if(i != paraules.length-1) infoParaulaFrase.y = paraules[i+1]; //Ens guardem la seguent paraula
                    infoParaula.set(n-1, infoParaulaFrase);
                    indexParaulaFrase.put(paraula, infoParaula);//Inserim la fila a l'index
                }
            }
        }
    }

    //PRE: El document existeix
    public void EsborrarDoc(String autor, String titol) {
        int startingIndexEliminar = -1;
        int numFrasesEliminar = 0;

        //Obtenim totes les frases que pertanyen al document que esborrarem
        //OPTIMITZACIO: Es fa un break quan ha trobat tots els autorTitol iguals seguits
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

            //Del indexParaulaFrase eliminem les posicions de les frases eliminades
            for (List<Pair<Boolean, String>> infoParaula : indexParaulaFrase.values()) {
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
        List<Pair<Boolean, String>> infoParaula = indexParaulaFrase.get(paraula);

        //Si la paraula no es a cap frase returnem un set buit
        if(infoParaula == null) return frases;

        for(int i = 0; i < infoParaula.size(); i++) {
            if(infoParaula.get(i).x) frases.add(i);
        }

        return frases;
    }

    public int GetNumFrases(){
        return indexFraseDocument.size();
    }

    //Comprova si existeix una sequencia de paraules seguides a una frase
    //Candidats conte les frases que tenen totes les paraules de la sequencia (potser no en ordre)
    public Set<Integer> GetSequencia(String sequencia, Set<Integer> candidats){
        Set<Integer> res = new HashSet<Integer>();
        String[] paraules = Utility.ParseFrase(sequencia);

        for (Integer c : candidats) {
            boolean valid = true;
            for (int i = 0; i < paraules.length; i++) {
                String paraula = paraules[i];
                if(i != paraules.length-1) {
                    String nextParaulaSeq = paraules[i+1];
                    List<Pair<Boolean, String>> infoParaula = indexParaulaFrase.get(paraula);
                    if(infoParaula == null) {
                        valid = false;
                        break;
                    }
                    Pair<Boolean, String> infoParaulaFrase = infoParaula.get(c);
                    String nextParaulaReal = infoParaulaFrase.y;
                    if(!nextParaulaSeq.equals(nextParaulaReal))  {
                        valid = false;
                        break;
                    }
                }
            }
            if(valid) res.add(c); //Si arribem aqui totes les paraules de la sequencia estan en ordre a la frase c
        }
        return res;
    }

    //Retorna els documents que contenen les frases indexs
    public List<Pair<String, String>> GetDocuments(Set<Integer> indexs) {
        Set<Pair<String, String>> docs = new HashSet<Pair<String, String>>();

        for (int index : indexs) {
            docs.add(indexFraseDocument.get(index));
        }

        return new ArrayList<Pair<String, String>>(docs);
    }

    //Omplira les primeres posicions de la llista amb false
    private void fillList(List<Pair<Boolean, String>> infoWord) {
        for(int i = 0; i < indexFraseDocument.size(); i++) infoWord.add(new Pair<Boolean, String>(false, null));
    }
    
    //Crea una nova columna de false al index per la frase que inserirem a continuacio
    private void addPosition() {
        for (List<Pair<Boolean, String>> infoParaula : indexParaulaFrase.values()) {
            infoParaula.add(new Pair<Boolean, String>(false, null));
        }
    }

    //Netegem el index treient les paraules que no apareixen a cap frase
    private void cleanIndex() {
        Set<String> toRemove = new HashSet<String>();

        //Busquem les paraules que no tenen entrades
        for (String paraula : indexParaulaFrase.keySet()) {
            List<Pair<Boolean, String>> infoParaula = indexParaulaFrase.get(paraula);
            boolean found = false;
            for (Pair<Boolean, String> b : infoParaula) {
                if(b.x) {
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
