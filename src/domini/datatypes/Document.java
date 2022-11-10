package datatypes;

import java.util.ArrayList;
import java.util.List;

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
        this.contingut = new Contingut();
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

    public Format getFormat() {
        return format;
    }

    /*SETTERS*/
    public void setAutor(String newAutor) {
        autor = newAutor;
    }

    public void setTitol(String newTitol) {
        titol = newTitol;
    }

    public void setContingut(String newContingut) {
        contingut.actualitzaContingut(newContingut);
    }
}

class DocumentStub extends Document {
    @Override
    public List<String> getContingut(){
        List<String> contingut = new ArrayList<>();
        contingut.add("En un lugar de la Mancha, de cuyo nombre no quiero acordarme.");
        return contingut;
    }
}