package datatypes;

public class PairAutorTitol {
    private String autor;
    private String titol;

    public PairAutorTitol(String autor, String titol) {
        this.autor = autor;
        this.titol = titol;
    }

    public PairAutorTitol() {}

    public String getAutor() {
        return autor;
    }

    public String getTitol() {
        return titol;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }
}