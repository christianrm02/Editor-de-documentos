import datatypes.Tree;

import java.text.Normalizer;
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

    private String UTF8toASCII(String frase) {
        String res = Normalizer.normalize(frase, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
        return res.replaceAll("·", "");
    }
/*
    private boolean operador(char c) {
        if (c <= 32 || c >= 35 && )
    }*/

    private void crearLlista(String s) throws Exception {
        List<String> paraules = new ArrayList<String>();
        String exp = UTF8toASCII(s);
        boolean clau_oberta = false;
        boolean espai_disponible = false;
        boolean cometes_obertes = false;
        boolean negacio = false;
        int parent_obert = 0;
        String s1 = "";
        for (int i = 0; i < exp.length(); i++) {
            if (!espai_disponible && exp.charAt(i) == ' ') throw new Exception();
            if (exp.charAt(i) == '"') { //espai disponible = false
                if (cometes_obertes) {
                    cometes_obertes = false;
                    if (s1.isEmpty()) throw new Exception(); //no es mira de moment si es un sol operador (!,&,|);
                    llista.add(s1);
                    s1 = "";
                }
                else cometes_obertes = true;
            }
            else if (cometes_obertes) {
                espai_disponible = true;
                s1+=exp.charAt(i);
            }
            if (clau_oberta) {
                if (exp.charAt(i) == ' ') {
                    espai_disponible = false;
                    llista.add(s1); //no es mira de moment si es un sol operador (!,&,|);
                    llista.add("&");
                    s1 = "";
                }
                else if (exp.charAt(i) == '}') {
                    if (!espai_disponible) throw new Exception();
                    llista.add(s1); //no es mira de moment si es un sol operador (!,&,|);
                    s1 = "";
                    clau_oberta = false;
                }
                else {
                    espai_disponible = true;
                    s1 += exp.charAt(i);
                }
            }
            else if (espai_disponible) {
                if (exp.charAt(i) == '&' || exp.charAt(i) == '|') {
                    llista.add(String.valueOf(exp.charAt(i)));
                }
                else if (exp.charAt(i) == '!') {
                    llista.add(String.valueOf(exp.charAt(i)));
                    negacio = true;
                    espai_disponible = false;
                }
                else s1 += exp.charAt(i);
            }
            else if (exp.charAt(i) == ' ') {
                llista.add(s1); //no es mira de moment si es un sol operador (!,&,|);
                s1 = "";
                espai_disponible = false;
            }
            else if (negacio) {
                s1 += exp.charAt(i);
            }
/*
            else if (exp.charAt(i) == '{') {
                if (!espai_disponible)
                clau_oberta = true;
            }*/
        }
        if (parent_obert != 0 || clau_oberta || cometes_obertes) throw new Exception();
    }

    //Getters
    public String getNom() { return nom; }

    public String getExp() { return exp; }

    public Tree getExpA() { return expA; }

    public List<String> getLlista() { return llista; }


    //Setters
    public void setNom(String nom) { this.nom = nom; }

    public void setExp(String exp) {
        this.exp = exp;
        //crearArbre();
    }
}