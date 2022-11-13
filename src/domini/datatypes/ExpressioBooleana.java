package datatypes;
import java.util.*;


public class ExpressioBooleana {

    private String nom;
    private String exp;
    private Tree expA;

    //Constructora
    public ExpressioBooleana(String nom, String exp) {
        this.nom = nom;
        this.exp = exp;

        List<String> llistaExp = crearLlista();
        //for (String s : llistaExp) System.out.print(s + ", ");
        this.expA = new Tree(llistaExp);
    }

    public ExpressioBooleana(String exp) {
        this.exp = exp;

        List<String> llistaExp = crearLlista();
        this.expA = new Tree(llistaExp);
    }

    private int casCometes(List<String> llista, int index) {
        ++index;
        String s = "";
        while (exp.charAt(index) != '\"') {
            s += exp.charAt(index);
            ++index;
        }
        llista.add(s);
        return ++index;
    }

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
        return ++index;
    }

    private List<String> crearLlista() {
        List<String> llista = new ArrayList<>();
        int i = 0;
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
                llista.add(s1);
                llista.add(")");
                ++i;
                s1 = "";
            }
            else if (exp.charAt(i) == ' ') {
                llista.add(s1);
                s1 = "";
            }
            else s1 += exp.charAt(i);
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