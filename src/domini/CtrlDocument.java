//import java.util.List;
//import java.util.HashSet; //para los sets?
//import java.util.HashMap;

import datatypes.Document;
import datatypes.Format;
import datatypes.Pair;

import java.util.*;
public class CtrlDocument {
    public Document docAct; //la pongo publica para el test, aunque igual vale la pena dejarla así
    private TreeMap<String, TreeMap<String, Document>> documents;


    /*CONTRUCTORA*/
    public CtrlDocument() {
        documents = new TreeMap<>();
    }

    /*GETTERS*/
    public Document getDocument(String autor, String titol) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol), tiene q comprobar la facade q exista, porq sinó existe devuelve null
        return documents.get(autor).get(titol);
    }

    public Boolean existsDocument(String autor, String titol) {
        return (documents.containsKey(autor) && documents.get(autor).containsKey(titol));
    }

    public List<Document> getAll() {
        List<Document> docs = new ArrayList<>();
        for(Map<String, Document> titols : documents.values()) {
            docs.addAll(titols.values());
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
        Set<String> ttls = new TreeSet<>();
        for(String autor : documents.keySet()) {
            Map<String,Document> titols = documents.get(autor);
            ttls.addAll(titols.keySet());
        }
        return ttls;
    }

    public List<Pair<String, String>> getClaus() {
        List<Pair<String, String>> claus = new ArrayList<>();
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
                Pair<String, String> clau = new Pair<>();
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
        TreeMap<String, Document> titols = new TreeMap<>();
        Document d = new Document(autor, titol, Format.txt); ////////////////FALTA EL FORMATO
        if (documents.containsKey(autor)){ //existe el autor
            titols = documents.get(autor);
        }
        titols.put(titol, d);
        documents.put(autor, titols);
        obreDocument(autor, titol);
    }

    public void obreDocument(String autor, String titol) {
        docAct = getDocument(autor, titol);
    }

    public Boolean esborrarDocument(String autor, String titol) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol)
        Boolean autorContinua = true;
        if (documents.get(autor).size() == 1) { //si l'autor només té un titol, s'esborra l'autor
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

    public Boolean modificarAutor(String autor, String titol, String newA) { //EXCEPCIÓ YA EXISTEIX EL DOCUMENT (newA, titol)
        /*try {
            if(existsDocument(newA, titol)) throw new Exception();
        }
        catch (Exception e){
            System.out.println("Ja existeix un document identificat amb (newA, titol)");
        }*/
        TreeMap<String, Document> titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setAutor(newA);
        Boolean autorContinua = true;
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
        return autorContinua;
    }

    public void modificarTitol(String autor, String titol, String newT) { //EXCEPCIÓ YA EXISTEIX EL DOCUMENT (autor, newT)
        TreeMap<String, Document> titols = documents.get(autor);
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