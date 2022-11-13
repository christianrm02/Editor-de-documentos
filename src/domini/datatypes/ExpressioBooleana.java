package datatypes;
import java.util.*;

/**
* ExpressioBooleana: S'implementa la classe Expressió Booleana.
* @author Pol Fradera
*/
public class ExpressioBooleana {

    private String nom;
    private String exp;
    private Tree expA;

    //Constructores
    public ExpressioBooleana(String exp) {
        this.exp = exp;

        List<String> llistaExp = crearLlista();
        this.expA = new Tree(llistaExp);
    }

    public ExpressioBooleana(String nom, String exp) {
        this.nom = nom;
        this.exp = exp;

        List<String> llistaExp = crearLlista();
        this.expA = new Tree(llistaExp);
    }

    //Es crida en el cas que a la transformació de string a llista hi hi hagin cometes
    private int casCometes(List<String> llista, int index) {
        ++index;
        String s = "";
        while (exp.charAt(index) != '\"') {
            s += exp.charAt(index);
            ++index;
        }
        llista.add(s);
        return index;
    }

    //Es crida en el cas que a la transformació de string a llista hi hi hagin claus
    private int casClau(List<String> llista, int index) {
        llista.add("(");
        ++index;
        String s = "";
        while (exp.charAt(index) != '}') {
            if (exp.charAt(index) == ' ') {
                llista.add(s);
                llista.add("&");
                s = "";
            }
            else s += exp.charAt(index);
            ++index;
        }
        llista.add(s);
        llista.add(")");
        return index;
    }

    //Converteix l'expressió booleana a una llista de strings
    private List<String> crearLlista() {
        List<String> llista = new ArrayList<>();
        int i = 0;
        boolean paraula = false;
        String s1 = "";
        while (i < exp.length()) {
            if (exp.charAt(i) == '{') i = casClau(llista, i);
            else if (exp.charAt(i) == '\"') i = casCometes(llista, i);
            else if (exp.charAt(i) == '&') {
                llista.add("&");
                ++i;
            }
            else if (exp.charAt(i) == '|') {
                llista.add("|");
                ++i;
            }
            else if (exp.charAt(i) == '!') llista.add("!");
            else if (exp.charAt(i) == '(') llista.add("(");
            else if (exp.charAt(i) == ')') {
                if (paraula) {
                    llista.add(s1);
                    paraula = false;
                }
                llista.add(")");
                s1 = "";
            }
            else if (paraula && exp.charAt(i) == ' ') { //si abans de l'espai hi ha una paraula
                llista.add(s1);
                s1 = "";
                paraula = false;
            }
            else if (exp.charAt(i) != ' ') {  //si hi ha un caràcter d'una paraula
                s1 += exp.charAt(i);
                paraula = true;
            }
            ++i;
        }
        if (s1.length() > 0) llista.add(s1);
        return llista;
    }

    //Getters
    public String getNom() { return nom; }

    public String getExp() { return exp; }

    public Tree getExpA() { return expA; }

    //Setters
    //public void setNom(String nom) { this.nom = nom; }
}