import java.util.List;
//import java.util.HashSet; //para los sets?
//import java.util.HashMap;
import java.util.*;
import java.util.concurrent.*;

import datatypes.PairAutorTitol;
import datatypes.Format;

public class CtrlDocument {
    private Document docAct; //NO ENTIENDO CUANDO MODIFICAR
    private TreeMap<String, TreeMap<String, Document>> documents;


    /*CONTRUCTORA*/
    public CtrlDocument() {
        documents = new TreeMap<String, TreeMap<String, Document>>();
    }

    /*GETTERS*/
    public Document getDocument(String autor, String titol) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol)
        Document d = documents.get(autor).get(titol);
        return d;
    }

    public boolean existsDocument(String autor, String titol) {
        return (documents.containsKey(autor) && documents.get(autor).containsKey(titol));
    }

    public List<Document> getAll() {
        List<Document> docs = new ArrayList<Document>();
        for(Map<String, Document> titols : documents.values()) {
            for(Document document : titols.values()) {
                docs.add(document);
            }
        }
        return docs;
    }

    public Set<String> getAutors() {
        /*List<String> autors = new ArrayList<String>();
        for(Map<String, Document> titols : documents.values()) {
            autors.add(titols.getKey());
        }*/
        return documents.keySet();
    }

    public Set<String> getTitols() {
        Set<String> ttls = new TreeSet<String>();
        for(String autor : documents.keySet()) {
            Map<String,Document> titols = documents.get(autor);
            for(String titol : titols.keySet()) {
                ttls.add(titol);
            }
        }
        return ttls;
    }

    public List<PairAutorTitol> getClaus() {
        List<PairAutorTitol> claus = new ArrayList<PairAutorTitol>();
        PairAutorTitol clau = new PairAutorTitol();
        /*for(Map<String, Document> titols : documents.values()) {
            autor = titols.getKey();
            for (Document document : titols.values()) {
                titol = document.getKey();
                clau.setAutor(autor); clau.setTitol(titol);
                claus.add(clau);
            }
        }*/
        for(String autor : documents.keySet()) {
            Map<String,Document> titols = documents.get(autor);
            for(String titol : titols.keySet()) {
                clau.setAutor(autor); clau.setTitol(titol);
                claus.add(clau);
            }
        }
        return claus;
    }

    public Contingut getContingut(String autor, String titol) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol)
        return documents.get(autor).get(titol).getContingut();
    }

    /*SETTERS*/
    public void crearDocument(String autor, String titol) { //EXCEPCIÓ JA EXISTEIX EL DOCUMENT (autor, titol)
        TreeMap<String, Document> titols = new TreeMap<String, Document>();
        Document d = new Document(autor, titol, Format.txt); ////////////////FALTA EL FORMATO
        if (documents.containsKey(autor)){ //existe el autor
            titols = documents.get(autor);
        }
        titols.put(titol, d);
        documents.put(autor, titols);
    }

    public void esborrarDocuments(List<PairAutorTitol> docs) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol), no seria mejor si el for lo hiciera la façana?
        for(PairAutorTitol doc : docs) {
            if (documents.get(doc.getAutor()).size() == 1) { //si l'autor només té un titol, s'esborra l'autor
                documents.remove(doc.getAutor());
            }
            else {
                TreeMap<String, Document> titols = new TreeMap<String, Document>();
                titols = documents.get(doc.getAutor()); //estas 3 lineas podrian ser documents.get(doc.getAutor()).erase(doc.getTitol()) ???
                titols.remove(doc.getTitol());
                documents.put(doc.getAutor(), titols);
            }
        }
    }

    public void modificarAutor(String autor, String titol, String newA) { //EXCEPCIÓ YA EXISTEIX EL DOCUMENT (newA, titol)
        TreeMap<String, Document> titols = new TreeMap<String, Document>();
        titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setAutor(newA);
        if (titols.size() == 1) { //si l'autor només té un document, s'esborra l'autor
            documents.remove(autor);
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
            TreeMap<String, Document> newtitols = new TreeMap<String, Document>();
            //titols.clear(); NO FUNCIONA BIEN EL CLEAR NO SÉ PORQ
            newtitols.put(titol, d);
            documents.put(newA, newtitols);
        }
    }

    public void modificarTitol(String autor, String titol, String newT) { //EXCEPCIÓ YA EXISTEIX EL DOCUMENT (autor, newT)
        TreeMap<String, Document> titols = new TreeMap<String, Document>();
        titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setTitol(newT);
        titols.remove(titol);
        titols.put(newT, d);
        documents.put(autor, titols);
    }

    public void modificarContingut(String autor, String titol, Contingut newC) {
        TreeMap<String, Document> titols = new TreeMap<String, Document>();
        titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setContingut(newC);
        titols.put(titol, d);
        documents.put(autor, titols);
    }
}