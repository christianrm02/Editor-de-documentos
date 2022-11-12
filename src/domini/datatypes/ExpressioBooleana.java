package datatypes;
import java.util.*;


public class ExpressioBooleana {

    private String nom;
    private String exp;
    private Tree expA;

    //Constructora
    public ExpressioBooleana (String nom, String exp) {
        this.nom = nom;
        this.exp = exp;
        crearLlistaiArbre();
    }

    public ExpressioBooleana (String exp) {
        this.exp = exp;
        crearLlistaiArbre();
    }

    private void crearLlistaiArbre() {
        List<String> llista = new ArrayList<>();
        int i = 0;
        String s1 = "";
        while (i < exp.length()) {
            if (exp.charAt(i) == '{') {
                llista.add("(");
                ++i;
                String s2 = "";
                while (exp.charAt(i) != '}') {
                    if (exp.charAt(i) == ' ') {
                        llista.add(s2);
                        llista.add("&");
                        s2 = "";
                    }
                    else s2 += exp.charAt(i);
                    ++i;
                }
                llista.add(s2);
                llista.add(")");
                ++i;
            }
            else if (exp.charAt(i) == '\"') {
                ++i;
                String s2 = "";
                while (exp.charAt(i) != '\"') {
                    s2 += exp.charAt(i);
                    ++i;
                }
                llista.add(s2);
                ++i;
            }
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
        this.expA = new Tree(llista);
    }

    //Getters
    public String getNom() { return nom; }

    public String getExp() { return exp; }

    public Tree getExpA() { return expA; }

    //Setters
    //public void setNom(String nom) { this.nom = nom; }

}