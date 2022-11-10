package datatypes;

import java.util.List;

/**
 * @author Christian Rivero
 */
public class Document {
    private String autor;
    private String titol;
    private Contingut contingut;
    private Format format;

    /*CONTRUCTORES*/
    /**
     * Creadora d'un Document buit
     */
    public Document() {}

    /**
     * Creadora d'un Document inicialitzat amb els paràmetres d'entrada i contingut buit
     * @param autor: String: Autor del document a crear.
     * @param titol: String: Títol del document a crear.
     * @param format: Format: Format del document a crear.
     */
    public Document(String autor, String titol, Format format) {
        this.format = format;
        this.autor = autor;
        this.titol = titol;
        this.contingut = new Contingut();
    }

    /*GETTERS*/
    /**
     * Consultora de l'autor del document
     * @return String: L'autor del document
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Consultora del títol del document
     * @return String: El titol del document
     */
    public String getTitol() {
        return titol;
    }

    /**
     * Consultora del contingut del document
     * @return List<String>: El contingut del document
     */
    public List<String> getContingut() {
        return contingut.getFrases();
    }

    /**
     * Consultora del format del document
     * @return Format: El format del document
     */
    public Format getFormat() {
        return format;
    }

    /*SETTERS*/
    /**
     * Modificadora de l'autor del document
     * @param newAutor: String: El nou autor del document
     */
    public void setAutor(String newAutor) {
        autor = newAutor;
    }

    /**
     * Modificadora del títol del document
     * @param newTitol: String: El nou titol del document
     */
    public void setTitol(String newTitol) {
        titol = newTitol;
    }

    /**
     * Modificadora del contingut del document
     * @param newContingut: String: El nou contingut del document
     */
    public void setContingut(String newContingut) {
        contingut.actualitzaContingut(newContingut);
    }

    /**
     * Modificadora del contingut del document, només utilitzada pel test DocumentTest
     * @param contingut: Contingut: El nou contingut del document
     */
    public void Contingut(Contingut contingut) {
        this.contingut = contingut;
    }
}

