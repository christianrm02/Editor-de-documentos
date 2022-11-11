package datatypes;
import java.text.Normalizer;
import java.util.*;

import static datatypes.Utility.UTF8toASCII;

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
        String s = UTF8toASCII(exp);
        List<String> llista = new ArrayList<>();
        int i = 0;
        String s1 = "";
        while (i < s.length()) {
            if (s.charAt(i) == '{') {
                llista.add("(");
                ++i;
                String s2 = "";
                while (s.charAt(i) != '}') {
                    if (s.charAt(i) == ' ') {
                        llista.add(s2);
                        llista.add("&");
                        s2 = "";
                    }
                    else s2+=s.charAt(i);
                    ++i;
                }
                llista.add(s2);
                llista.add(")");
                ++i;
            }
            else if (s.charAt(i) == '&') {
                llista.add("&");
                ++i;
            }
            else if (s.charAt(i) == '|') {
                llista.add("|");
                ++i;
            }
            else if (s.charAt(i) == '!') llista.add("!");
            else if (s.charAt(i) == '(') llista.add("(");
            else if (s.charAt(i) == ')') {
                llista.add(s1);
                llista.add(")");
                ++i;
                s1 = "";
            }
            else if (s.charAt(i) == '\"') {
                ++i;
                String s2 = "";
                while (s.charAt(i) != '\"') {
                    s2 += s.charAt(i);
                    ++i;
                }
                llista.add(s2);
                ++i;
            }
            else if (s.charAt(i) == ' ') {
                llista.add(s1);
                s1 = "";
            }
            else s1 += s.charAt(i);
            ++i;
        }
        if (s1.length() > 0) llista.add(s1);
        this.expA = new Tree(llista);
    }

    //Getters
    public String getNom() { return nom; }

    public String getExp() { return exp; }

    public Tree getExpA() { return expA; }

   // public List<String> getLlista() { return llista; }


    //Setters
    public void setNom(String nom) { this.nom = nom; }

    public void setExp(String exp) {
        this.exp = exp;
        //crearArbre();
    }
}