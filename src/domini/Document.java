import datatypes.Format;

public class Document {
    private Format format;
    private String titol;
    private String autor;
    private Contingut contingut;

    /*CONTRUCTORA*/
    public Document(String autor, String titol, Format format) {
        this.format = format;
        this.autor = autor;
        this.titol = titol;
    }

    /*GETTERS*/
    public String getTitol() {
        return titol;
    }

    public String getAutor() {
        return autor;
    }

    public Contingut getContingut() {
        return contingut;
    }

    /*SETTERS*/
    public void setTitol(String newTitol) {
        titol = newTitol;
    }

    public void setAutor(String newAutor) {
        autor = newAutor;
    }

    public void setContingut(Contingut newContingut) {
        contingut = newContingut;
    }
}