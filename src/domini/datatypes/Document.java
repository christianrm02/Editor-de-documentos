package datatypes;
import datatypes.Format;
import datatypes.Contingut;

import java.util.*;

public class Document {
    private String autor;
    private String titol;
    private Contingut contingut;
    private Format format;

    /*CONTRUCTORA*/
    public Document() {}
    public Document(String autor, String titol, Format format) {
        this.format = format;
        this.autor = autor;
        this.titol = titol;
    }

    /*GETTERS*/
    public String getAutor() {
        return autor;
    }

    public String getTitol() {
        return titol;
    }

    public List<String> getContingut() {
        return contingut.getFrases();
    }

    /*SETTERS*/
    public void setAutor(String newAutor) {
        autor = newAutor;
    }

    public void setTitol(String newTitol) {
        titol = newTitol;
    }

    public void setContingut(String newContingut) {
        Contingut c = new Contingut(newContingut);
        contingut = c;
    }
}