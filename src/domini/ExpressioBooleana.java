import datatypes.Tree;

public class ExpressioBooleana {

    private String nom;
    private String exp;
    private Tree expA;

    //Constructora
    public ExpressioBooleana (String nom, String exp) {
        this.nom = nom;
        this.exp = exp;
        //crearArbre();
    }

    private void crearArbre() {

    }

    //Getters
    public String getNom() { return nom; }

    public String getExp() { return exp; }

    public Tree getExpA() { return expA; }


    //Setters
    public void setNom(String nom) { this.nom = nom }

    public void setExp(String exp) {
        this.exp = exp;
        //crearArbre();
    }
}