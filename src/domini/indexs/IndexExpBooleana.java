package indexs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import datatypes.Contingut;
import datatypes.Pair;


//Aquest index desa per cada paraula les frases a la que apareix i per cada frase el document on apareix
public class IndexExpBooleana {
    
    private HashMap<String, List<Boolean>> indexParaulaFrase;   //Per cada paraula indica a quines frases apareix
    private List<Pair<String, String>> indexFraseDocument;      //Per cada frase(index) indica a quin document pertany 
    private int N;                                              //Nombre de frases  

    public IndexExpBooleana() {
        indexParaulaFrase = new HashMap<String, List<Boolean>>();
        indexFraseDocument = new ArrayList<Pair<String, String>>();
        N = 0;
    }

    public void AfegirDoc(String autor, String titol, Contingut contingut) {
        List<String> frases = contingut.getFrases();
        Pair<String, String> autorTitol = new Pair<String, String>(autor, titol);

        for (String frase : frases) {
            indexFraseDocument.add(autorTitol);
            addPosition();
            String[] paraules = parseFrase(frase);

            for (String paraula : paraules) {
                //Si la paraula no era al index afegim una altra entrada
                if(!indexParaulaFrase.containsKey(paraula)) {
                    List<Boolean> infoParaula = new ArrayList<Boolean>();
                    fillList(infoParaula);                  //Omplim la llista amb false
                    infoParaula.set(N-1, true);    //Lultim element sera true perque es la paraula que acabem de insertar
                    indexParaulaFrase.put(paraula, infoParaula);
                }
                //Si la paraula ja existeix al index simplement posem a true la casella corresponent 
                else {
                    List<Boolean> aux = indexParaulaFrase.get(paraula);
                    aux.set(N-1, true);
                    indexParaulaFrase.put(paraula, aux);
                }
            }
        }
    }

    public void EsborrarDoc(String autor, String titol) {
        List<Integer> frasesAEliminar = new ArrayList<Integer>();

        //Obtenim totes les frases que pertanyen al document que esborrarem
        //OPTIMITZACIO: Es pot fer un break quan ha trobat tots els autorTitol iguals seguits
        boolean found = false;
        for (int i = 0; i < indexFraseDocument.size(); i++) {
            Pair<String, String> autorTitol = indexFraseDocument.get(i);
            if(autor == autorTitol.x && titol == autorTitol.y) {
                frasesAEliminar.add(i);
                found = true;
            } else if (found) break;
        }

        //Eliminem les frases
        for (int fraseIndex : frasesAEliminar) {
            indexFraseDocument.remove(fraseIndex);

            //Del indexParaulaFrase eliminem les posicions de les frases eliminades
            for (List<Boolean> infoParaula : indexParaulaFrase.values()) {
                infoParaula.remove(fraseIndex);
            }
        }
        //Si alguna paraula no apareix a cap frase l'eliminem de l'index
        cleanIndex();
    }

    public void ActualitzarTitol(String autor, String titol, String newTitol) {
        Pair<String, String> oldAutorTitol = new Pair<String, String>(autor, titol);
        Pair<String, String> newAutorTitol = new Pair<String, String>(autor, newTitol);
        
        boolean found = false;
        for (Pair<String, String> Pair : indexFraseDocument) {
            if(Pair == oldAutorTitol) {
                Pair = newAutorTitol;
                found = true;
            }
            else if (found) break;
        }
    }

    public void ActualitzarAutor(String autor, String titol, String newAutor) {
        Pair<String, String> oldAutorTitol = new Pair<String, String>(autor, titol);
        Pair<String, String> newAutorTitol = new Pair<String, String>(newAutor, titol);
        
        boolean found = false;
        for (Pair<String, String> Pair : indexFraseDocument) {
            if(Pair == oldAutorTitol) {
                Pair = newAutorTitol;
                found = true;
            }
            else if (found) break;
        }
    }

    public void ActualitzarContingut(String autor, String titol, Contingut contingut) {
        EsborrarDoc(autor, titol);
        AfegirDoc(autor, titol, contingut);
    }

    //Retorna els indexs de les frases que contenen paraula
    public List<Integer> GetFrases(String paraula) {
        List<Integer> frases = new ArrayList<Integer>();
        List<Boolean> infoParaula = indexParaulaFrase.get(paraula);

        for(int i = 0; i < infoParaula.size(); i++) {
            if(infoParaula.get(i)) frases.add(i);
        }

        return frases;
    }

    //Retorna els documents que contenen les frases indexs
    public List<Pair<String, String>> GetDocuments(List<Integer> indexs) {
        List<Pair<String, String>> docs = new ArrayList<Pair<String, String>>();

        for (int index : indexs) {
            docs.add(indexFraseDocument.get(index));
        }

        return docs;
    }

    static private String[] parseFrase(String frase) {
        String[] paraules = frase.split(" ");
        return paraules;
    }

    //Omplira les N primeres posicions de la llista amb false
    static private void fillList(List<Boolean> list) {
        for(int i = 0; i < list.size(); i++) list.add(false);
    }
    
    //Crea una nova columna de false al index per la frase que inserirem a continuacio
    private void addPosition() {
        for (List<Boolean> infoParaula : indexParaulaFrase.values()) {
            infoParaula.add(false);
        }
        N++;
    }

    //Netegem el index treient les paraules que no apareixen
    private void cleanIndex() {
        for (String paraula : indexParaulaFrase.keySet()) {
            List<Boolean> infoParaula = indexParaulaFrase.get(paraula);
            Boolean found = false;
            for (Boolean b : infoParaula) {
                if(b) {
                    found = true;
                    break;
                }
            }
            if(!found) indexParaulaFrase.remove(paraula);
        }
    }
}
