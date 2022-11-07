import datatypes.Tree;
import java.util.*;

public class ExpressioBooleana {

    private String nom;
    private String exp;
    private Tree expA;

    private ArrayList<String> llista;

    //Constructora
    public ExpressioBooleana (String nom, String exp) {
        this.nom = nom;
        this.exp = exp;
        //crearArbre();
    }

    public ExpressioBooleana (String exp) {
        this.exp = exp;
        //crearArbre();
    }

    private void crearArbre() {

    }
/*
    private boolean operador(char c) {
        if (c <= 32 || c >= 35 && )
    }*/

    private void crearLlista(String s) throws Exception {
        List<String> paraules = new ArrayList<String>();
        boolean clau_oberta = false;
        boolean espai = true;
        int parent_obert = 0;
        String s1 = "";
        for (int i = 0; i < s.length(); i++) {
            if (espai && s.charAt(i) == ' ') throw new Exception();
            if (clau_oberta) {
                if (s.charAt(i) != ' ' && s.charAt(i) != '}') {
                    espai = false;
                    s1+=s.charAt(i);
                }
                else if (s.charAt(i) == '}') {
                    llista.add(s1);
                    clau_oberta = false;
                }
                else {
                    espai = true;
                    llista.add(s1);
                    llista.add("&");
                    s1 = "";
                }
            }
            else if (s.charAt(i) == '{') clau_oberta = true;
        }
    }

    //Getters
    public String getNom() { return nom; }

    public String getExp() { return exp; }

    public Tree getExpA() { return expA; }


    //Setters
    public void setNom(String nom) { this.nom = nom; }

    public void setExp(String exp) {
        this.exp = exp;
        //crearArbre();
    }
}