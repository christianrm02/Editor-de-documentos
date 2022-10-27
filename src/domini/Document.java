
public class Document {
    private Format format;
    private Frase títol;
    private Frase autor;
    private Contingut contingut;

    /*CONTRUCTORA*/
    public Document(Format format, Frase autor, Frase títol) {
        this.format = format;
        this.autor = autor;
        this.títol = títol;
    }

    /*GETTERS*/
    public Frase getTítol() {
        return títol;
    }

    public Frase getAutor() {
        return autor;
    }

    public Contingut getContingut() {
        return contingut;
    }

    /*SETTERS*/
    public void setTítol(Frase newTítol) {
        títol = newTítol;
    }

    public void setAutor(Frase newAutor) {
        autor = newAutor;
    }

    public void setContingut(Contingut newContingut) {
        contingut = newContingut;
    }
}