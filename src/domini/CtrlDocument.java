import java.util.List;
//import java.util.HashSet; //para los sets?
//import java.util.HashMap;
import java.util.*;
import java.util.concurrent.*;

import datatypes.Pair;
import datatypes.Format;
import datatypes.Document;
import datatypes.Contingut;
public class CtrlDocument {
    private Document docAct; //NO ENTIENDO CUANDO MODIFICAR
    private TreeMap<String, TreeMap<String, Document>> documents;


    /*CONTRUCTORA*/
    public CtrlDocument() {
        documents = new TreeMap<String, TreeMap<String, Document>>();
    }

    /*GETTERS*/
    public Document getDocument(String autor, String titol) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol), tiene q comprobar la facade q exista, porq sinó existe devuelve null
        Document d = new Document();
        d = documents.get(autor).get(titol);
        /*try {
            d = documents.get(autor).get(titol);
        }
        catch (Exception e){
            System.out.println("No existeix el document");
        }*/
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

    public List<Pair> getClaus() {
        List<Pair> claus = new ArrayList<Pair>();
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
                Pair clau = new Pair();
                clau.x = autor;
                clau.y = titol;
                claus.add(clau);
            }
        }
        return claus;
    }

    public Contingut getContingut(String autor, String titol) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol)
        Contingut c = new Contingut();
        try {
            c = documents.get(autor).get(titol).getContingut();
        }
        catch (Exception e){
            System.out.println("No existeix el document");
        }
        return c;
    }

    /*SETTERS*/
    public void crearDocument(String autor, String titol) { //EXCEPCIÓ JA EXISTEIX EL DOCUMENT (autor, titol), SE ENCARGA FACADE
        TreeMap<String, Document> titols = new TreeMap<String, Document>();
        Document d = new Document(autor, titol, Format.txt); ////////////////FALTA EL FORMATO
        if (documents.containsKey(autor)){ //existe el autor
            titols = documents.get(autor);
        }
        titols.put(titol, d);
        documents.put(autor, titols);
    }

    public void esborrarDocuments(List<Pair> docs) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol)
        for(Pair doc : docs) {
            if (documents.get(doc.x).size() == 1) { //si l'autor només té un titol, s'esborra l'autor
                documents.remove(doc.x);
            }
            else {
                TreeMap<String, Document> titols = new TreeMap<String, Document>();
                titols = documents.get(doc.x); //estas 3 lineas podrian ser documents.get(doc.getAutor()).erase(doc.getTitol()) ???
                titols.remove(doc.y);
                documents.put(doc.x, titols);
            }
        }
    }

    public void modificarAutor(String autor, String titol, String newA) { //EXCEPCIÓ YA EXISTEIX EL DOCUMENT (newA, titol)
        try {
            if(existsDocument(newA, titol)) throw new Exception();
        }
        catch (Exception e){
            System.out.println("Ja existeix un document identificat amb (newA, titol)");
        }
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
            titols = new TreeMap<String, Document>();
            titols.put(titol, d);
            documents.put(newA, titols);
        }
    }

    public void modificarTitol(String autor, String titol, String newT) { //EXCEPCIÓ YA EXISTEIX EL DOCUMENT (autor, newT)
        try {
            if(existsDocument(autor, newT)) throw new Exception();
        }
        catch (Exception e){
            System.out.println("Ja existeix un document identificat amb (autor, newT)");
        }
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