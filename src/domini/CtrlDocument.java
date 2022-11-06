//import java.util.List;
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
        return documents.get(autor).get(titol);
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

    public List<Pair<String, String>> getClaus() {
        List<Pair<String, String>> claus = new ArrayList<Pair<String, String>>();
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
                Pair<String, String> clau = new Pair<String, String>();
                clau.x = autor;
                clau.y = titol;
                claus.add(clau);
            }
        }
        return claus;
    }

    public List<String> getContingut() { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol)
        return docAct.getContingut();
    }

    /*public List<String> getContingut(String autor, String titol) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol)
        return documents.get(autor).get(titol).getContingut();
    }*/
    
    public Set<String> getTitolsAutor(String autor) {
        return documents.get(autor).keySet();
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

    public void obreDocument(String autor, String titol) {
        docAct = documents.get(autor).get(titol);
    }

    public boolean esborrarDocuments(String autor, String titol) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol)
        boolean seliminaAutor = false;
        if (documents.get(autor).size() == 1) { //si l'autor només té un titol, s'esborra l'autor
            documents.remove(autor);
            seliminaAutor = true;
        }
        else {
            TreeMap<String, Document> titols = new TreeMap<String, Document>();
            titols = documents.get(autor); //estas 3 lineas podrian ser documents.get(doc.getAutor()).erase(doc.getTitol()) ???
            titols.remove(titol);
            documents.put(autor, titols);
        }
        return seliminaAutor;
    }

    public boolean modificarAutor(String autor, String titol, String newA) { //EXCEPCIÓ YA EXISTEIX EL DOCUMENT (newA, titol)
        /*try {
            if(existsDocument(newA, titol)) throw new Exception();
        }
        catch (Exception e){
            System.out.println("Ja existeix un document identificat amb (newA, titol)");
        }*/
        TreeMap<String, Document> titols = new TreeMap<String, Document>();
        titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setAutor(newA);
        boolean seliminaAutor = false;
        if (titols.size() == 1) { //si l'autor només té un document, s'esborra l'autor
            documents.remove(autor);
            seliminaAutor = true;
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
        return seliminaAutor;
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