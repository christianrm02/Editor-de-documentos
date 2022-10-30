import domini.Document;
import domini.PairAutorTitol;
import java.util.HashMap;

public class CtrlDocument {
    private Document docAct;
    private Map<String, Map<String, Document>> documents;


    /*CONTRUCTORA*/
    public CtrlDocument() {
        documents = new TreeMap<String, Map<String, Document>>();
    }

    /*GETTERS*/
    public Document getDocument(String autor, String titol) {
        Document d = documents.get(autor).get(titol);
        return d;
    }

    public boolean existsDocument(String autor, String titol) {
        boolean exists = documents.containsKey(autor).containsKey(titol);
        return exists;
    }

    public vector<Document> getAll() {
        vector<Document> docs = new Vector();
        for(Entry<String, Map<String, Document>> titols : documents.entrySet()) {
            for(Entry<String, Document> document : titols.entrySet()) {
                docs.add(document);
            }
        }
        return docs;
    }

    public Vector<String> getTitols() {
        vector<String> titols = new Vector();
        for(Entry<String, Map<String, Document>> titols : documents.entrySet()) {
            for (Entry<String, Document> document : titols.entrySet()) {
                docs.add(document.getKey());
            }
        }
        return titols;
    }

        public Vector<String> getAutors() {
            vector<String> autors = new Vector();
            for(Entry<String, Map<String, Document>> titols : documents.entrySet()) {
                docs.add(titols.getKey());
            }
            return titols;
        }

    public Vector<PairAutorTitol> getClaus() {
        Vector<PairAutorTitol> claus = new Vector();
        String autor, titol;
        for(Entry<String, Map<String, Document>> titols : documents.entrySet()) {
            autor = titols.getKey();
            for (Entry<String, Document> document : titols.entrySet()) {
                titol = document.getKey();
                c = new PairAutorTitol(autor, titol)
                claus.add(c);
            }
        }
        return claus;
    }

    public String getContingut(String autor, String titol) {
        return documents.get(autor).get(titol).getContingut();
    }

    /*SETTERS*/
    public crearDocument(String autor, String titol) { ////////////////////
        if(documents.containsKey(autor).containsKey(titol)) {
            ----LANZAR EXC YA EXISTE
        }
        else if (documents.containsKey(autor)){ //si no existe el autor, si existe el autor pero no el titulo
            documents.get(autor)
        }
    }

    public void esborrarDocuments(Vector<PairAutorTitol> docs) { /////////////
        for(PairAutorTitol doc : docs) {
            documents.remove(doc.getAutor()).remove(doc.getTitol());
        }
    }

    public void modificarTitol(String autor, String titol, String newT) {

    }

    public void modificarAutor(String autor, String titol, String newA) {

    }

    public void modificarContingut(String autor, String titol, String newC) {

    }


}