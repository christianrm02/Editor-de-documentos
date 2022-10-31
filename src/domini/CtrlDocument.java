import java.util.List;
import java.util.HashSet; //para los sets?

import domini.PairAutorTitol;

public class CtrlDocument {
    private Document docAct; //NO ENTIENDO CUANDO MODIFICAR
    private Map<String, Map<String, Document>> documents;


    /*CONTRUCTORA*/
    public CtrlDocument() {
        documents = new TreeMap<String, Map<String, Document>>();
    }

    /*GETTERS*/
    public Document getDocument(String autor, String titol) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol)
        Document d = documents.get(autor).get(titol);
        return d;
    }

    public boolean existsDocument(String autor, String titol) {
        return (documents.containsKey(autor) || documents.get(autor).containsKey(titol));
    }

    public List<Document> getAll() {
        List<Document> docs = new ArrayList<Document>();
        for(Entry<String, Map<String, Document>> titols : documents.entrySet()) {
            for(Entry<String, Document> document : titols.entrySet()) {
                docs.add(document);
            }
        }
        return docs;
    }

    public Vector<String> getAutors() {
        List<String> autors = new ArrayList<String>();
        for(Entry<String, Map<String, Document>> titols : documents.entrySet()) {
            autors.add(titols.getKey());
        }
        return autors;
    }

    public Set<String> getTitols() {
        Set<String> titols = new TreeSet<String>();
        for(Entry<String, Map<String, Document>> titols : documents.entrySet()) {
            for (Entry<String, Document> document : titols.entrySet()) {
                titols.add(document.getKey());
            }
        }
        return titols;
    }

    public List<PairAutorTitol> getClaus() {
        List<PairAutorTitol> claus = new ArrayList<PairAutorTitol>();
        String autor, titol;
        clau = new PairAutorTitol();
        for(Entry<String, Map<String, Document>> titols : documents.entrySet()) {
            autor = titols.getKey();
            for (Entry<String, Document> document : titols.entrySet()) {
                titol = document.getKey();
                clau.setAutor(autor); clau.setTitol(titol);
                claus.add(clau);
            }
        }
        return claus;
    }

    public String getContingut(String autor, String titol) { //EXCEPCIÓ NO EXISTEIX EL DOCUMENT (autor, titol)
        return documents.get(autor).get(titol).getContingut();
    }

    /*SETTERS*/
    public crearDocument(String autor, String titol) { //EXCEPCIÓ JA EXISTEIX EL DOCUMENT (autor, titol)
        Map<String, Document> titols = new TreeMap<String, Document>();
        Document d = new Document(autor, titol); ////////////////FALTA EL FORMATO
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
                Map<String, Document> titols = new TreeMap<String, Document>();
                titols = documents.get(doc.getAutor()); //estas 3 lineas podrian ser documents.get(doc.getAutor()).erase(doc.getTitol()) ???
                titols.erase(doc.getTitol());
                documents.put(doc.getAutor(), titols);
            }
        }
    }

    public void modificarAutor(String autor, String titol, String newA) { //EXCEPCIÓ YA EXISTEIX EL DOCUMENT (newA, titol)
        Map<String, Document> titols = new TreeMap<String, Document>();
        titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setAutor(newA);
        if (titols.size() == 1) { //si l'autor només té un document, s'esborra l'autor
            documents.remove(autor);
        }
        else { //si en té més, s'esborra aquell titol
            titols.erase(titol);
            documents.put(autor, titols);
        }
        if (documents.containsKey(newA)) { //si ja existeix newA, s'afegeix a la seva llista
            titols = documents.get(newA);
        }
        else { //si no existia newA es crea amb un titol
            titols.clear();
            titols.put(titol, d);
            documents.put(newA, titols);
        }
    }

    public void modificarTitol(String autor, String titol, String newT) { //EXCEPCIÓ YA EXISTEIX EL DOCUMENT (autor, newT)
        Map<String, Document> titols = new TreeMap<String, Document>();
        titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setTitol(newT);
        titols.erase(titol);
        titols.put(newT, d);
        documents.put(autor, titols);
    }

    public void modificarContingut(String autor, String titol, String newC) {
        Map<String, Document> titols = new TreeMap<String, Document>();
        titols = documents.get(autor);
        Document d = titols.get(titol);
        d.setContingut(newC);
        titols.put(titol, d);
        documents.put(autor, titols);
    }
}