package controladores;

import datatypes.Document;
import transversal.Pair;

import java.util.*;

/**
 * @author Christian Rivero
 */
public class CtrlDocument {
    public Document docAct; //la pongo publica para el test, aunque igual vale la pena dejarla así
    private TreeMap<String, TreeMap<String, Document>> documents; //TreeMap amb clau "autor" de TreeMaps amb clau "titol" de Documents


    /*CONTRUCTORA*/
    /**
     * Creadora d'un Document buit.
     */
    public CtrlDocument() {
        documents = new TreeMap<>();
    }

    /*GETTERS*/
    /**
     * Operació que retorna el document identificat per autor+titol.
     * @param autor: String: Autor del document.
     * @param titol: String: Títol del document.
     * @return Document: Es retorna el document que es buscaba, o null si no existeix.
     */
    public Document getDocument(String autor, String titol) {
        if(documents.get(autor) != null) {
            return documents.get(autor).get(titol);
        }
        else {
            return null;
        }
    }

    /**
     * Consultora que retorna si existeix un document identificat per autor+titol al sistema.
     * @param autor: String: Autor del document.
     * @param titol: String: Títol del document.
     * @return boolean: Si existeix retorna true, sinó retorna false.
     */
    public boolean existsDocument(String autor, String titol) {
        return (documents.containsKey(autor) && documents.get(autor).containsKey(titol));
    }

    /**
     * Operació que retorna tots els documents del sistema, sense repetits.
     * @return List<Document>: Retorna una llista amb tots els documents del sistema, aquesta estarà buida si no
     * n'hi ha cap document.
     */
    public List<Document> getAll() {
        List<Document> docs = new ArrayList<>();
        for(Map<String, Document> titols : documents.values()) {
            docs.addAll(titols.values());
        }
        return docs;
    }

    /**
     * Operació que retorna tots els autors del sistema, sense repetits.
     * @return Set<String>: Retorna un set amb tots els autors del sistema, aquest estarà buit si no hi ha cap document.
     */
    public Set<String> getAutors() {
        return documents.keySet();
    }

    /**
     * Operació que retorna tots els títols del sistema, sense repetits.
     * @return Set<String>: Retorna un set amb tots els títols del sistema, aquest estarà buit si no hi ha cap document.
     */
    public Set<String> getTitols() {
        Set<String> ttls = new TreeSet<>();
        for(String autor : documents.keySet()) {
            Map<String,Document> titols = documents.get(autor);
            ttls.addAll(titols.keySet());
        }
        return ttls;
    }

    /**
     * Operació que retorna totes les claus (autor+titol) dels documents del sistema.
     * @return List<Pair<String, String>>: Retorna una llista amb totes les claus del sistema, aquesta estarà buida
     * si no hi ha cap document.
     */
    public List<Pair<String, String>> getClaus() {
        List<Pair<String, String>> claus = new ArrayList<>();
        for(String autor : documents.keySet()) {
            Map<String,Document> titols = documents.get(autor);
            for(String titol : titols.keySet()) {
                Pair<String, String> clau = new Pair<>(autor, titol);
                claus.add(clau);
            }
        }
        return claus;
    }

    /**
     * Operació que retorna el contingut del document actual.
     * @return List<String>: Retorna una llista de les frases del document actual.
     */
    public List<String> getContingutObert() {
        return docAct.getContingut();
    }

    /**
     * Operació que retorna el contingut del document identificat per (autor+titol).
     * @return List<String>: Retorna una llista de les frases del document actual.
     */
    public List<String> getContingut(String autor, String titol) {
        return documents.get(autor).get(titol).getContingut();
    }

    /**
     * Operació que retorna els títols dels documents d'un autor.
     * @param autor: String: Autor sobre el que es vol fer la búsqueda.
     * @return Set<String>: Retorna un set format pels títols de l'autor. El set estarà buit si l'autor no té cap
     * document al sistema.
     */
    public Set<String> getTitolsAutor(String autor) {
        return documents.get(autor).keySet();
    }

    /*SETTERS*/
    /**
     * Operació que afegeix un nou document al sistema. Si no existia l'autor, s'afegeix al sistema amb un únic títol,
     * i sinó, només s'afegeix el títol al map de títols de l'autor. No existia un document amb claus (autor+titol).
     * @param autor: String: Autor del nou document.
     * @param titol: String: Títol del nou document.
     */
    public void crearDocument(String autor, String titol) {
        TreeMap<String, Document> titols = new TreeMap<>();
        Document d = new Document(autor, titol); //Format.txt, no el tenim en compte per aquesta primera entrega
        if (documents.containsKey(autor)){ //existe el autor
            titols = documents.get(autor);
        }
        titols.put(titol, d);
        documents.put(autor, titols);
        obreDocument(autor, titol);
    }

    /**
     * Operació que actualitza l'atribut docAct, passa a ser el document identificat per (autor+titol). Existeix
     * al sistema.
     * @param autor: String: Autor del document.
     * @param titol: String: Títol del document.
     */
    public void obreDocument(String autor, String titol) {
        docAct = getDocument(autor, titol);
    }

    /**
     * Operació que elimina el document identificat per (autor+titol) del sistema. El document existia. Si s'esborra el
     * document actual, aquest passa a null.
     * @param autor: String: Autor del document.
     * @param titol: String: Títol del document.
     * @return boolean: Retorna false si l'autor s'elimina del sistema, sinó retorna true.
     */
    public boolean esborrarDocument(String autor, String titol) {
        boolean autorContinua = true;
        if(docAct != null && docAct.equals(getDocument(autor, titol))) docAct = null; //si estava obert, ja no /////////////////////////
        if (documents.get(autor).size() == 1) { //si l'autor només té un títol, s'esborra l'autor
            documents.remove(autor);
            autorContinua = false;
        }
        else {
            TreeMap<String, Document> titols = documents.get(autor); //estas 3 lineas podrian ser documents.get(doc.getAutor()).erase(doc.getTitol()) ???
            titols.remove(titol);
            documents.put(autor, titols);
        }
        return autorContinua;
    }

    /**
     * Operació que canvia l'autor del document identificat per (autor+titol). El document existia.
     * @param autor: String: Antic autor del document.
     * @param titol: String: Títol del document.
     * @param newA: String: Nou autor del document.
     * @return boolean: Retorna false si l'autor s'elimina del sistema, sinó retorna true.
     */
    public boolean modificarAutor(String autor, String titol, String newA) {
        boolean actualitzaAct = false;
        if(docAct != null && docAct.equals(getDocument(autor, titol))) actualitzaAct = true;
        TreeMap<String, Document> titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setAutor(newA);
        boolean autorContinua = true;
        if (titols.size() == 1) { //si l'autor només té un document, s'esborra l'autor
            documents.remove(autor);
            autorContinua = false;
        }
        else { //si en té més, s'esborra aquell titol
            titols.remove(titol);
            documents.put(autor, titols);
        }
        if (documents.containsKey(newA)) { //si ja existeix newA, s'afegeix a la seva llista
            titols = documents.get(newA);
            titols.put(titol, d);
            documents.put(newA, titols);
        }
        else { //si no existia newA es crea amb un titol
            titols = new TreeMap<>();
            titols.put(titol, d);
            documents.put(newA, titols);
        }
        if(actualitzaAct) obreDocument(newA, titol);
        return autorContinua;
    }

    /**
     * Operació que canvia el títol del document identificat per (autor+titol). El document existia.
     * @param autor: String: Autor del document.
     * @param titol: String: Antic títol del document.
     * @param newT: String: Nou títol del document.
     */
    public void modificarTitol(String autor, String titol, String newT) {
        boolean actualitzaAct = false;
        if(docAct != null && docAct.equals(getDocument(autor, titol))) actualitzaAct = true;
        TreeMap<String, Document> titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setTitol(newT);
        titols.remove(titol);
        titols.put(newT, d);
        documents.put(autor, titols);
        if(actualitzaAct) obreDocument(autor, newT);
    }

    /**
     * Operació que canvia el contingut del document obert, és a dir, docAct. El document existia.
     * @param newC: String: Nou contingut del document.
     */
    public void modificarContingut(String newC) {
        docAct.setContingut(newC);
    }


    /*public void modificarContingut(String autor, String titol, String newC) {
        TreeMap<String, Document> titols = new TreeMap<String, Document>();
        titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setContingut(newC);
        titols.put(titol, d);
        documents.put(autor, titols);
    }*/
}