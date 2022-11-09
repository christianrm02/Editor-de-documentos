import datatypes.TreeNode;

import java.text.Normalizer;
import java.util.*;

public class ExpressioBooleana {

    private String nom;
    private String exp;
    private TreeNode expA;

   // private ArrayList<String> llista;

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
    }

    private String UTF8toASCII(String frase) {
        String res = Normalizer.normalize(frase, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
        return res.replaceAll("·", "");
    }

    private int preced(String s) {
        if (s.equals("|")) {
            return 1;              //Precedence of | 1
        }
        else if (s.equals("&")) {
            return 2;            //Precedence of & is 2
        }
        else if (s.equals("!")) {
            return 3;            //Precedence of ! is 3
        }
        else if (s.equals("(")) {
            return 0;
        }
        return -1;
    }

    private boolean isOperator(String s) {
        return s.length() == 1 && (s.equals("&") || s.equals("|") || s.equals("!"));
    }

    public List<String> inToPost(List<String> infix) {
        Stack<String> stk = new Stack<>();
        stk.push("#"); //add some extra character to avoid underflow

        List<String> postfix = new ArrayList<>();

        for (String s : infix) {
            if (!isOperator(s)) {
                postfix.add(s);
            }
            else if (s.equals("(")) {
                stk.push(s);
            }
            else if (s.equals(")")) {
                while (!stk.peek().equals("#") && !
            }


        }

        while(!stk.peek().equals("#")) {
            postfix += stk.top();        //store and pop until stack is not empty.
            stk.pop();
        }

        return postfix;
    }


    //Getters
    public String getNom() { return nom; }

    public String getExp() { return exp; }

    public TreeNode getExpA() { return expA; }

   // public List<String> getLlista() { return llista; }


    //Setters
    public void setNom(String nom) { this.nom = nom; }

    public void setExp(String exp) {
        this.exp = exp;
        //crearArbre();
    }

    /*
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

            else if (exp.charAt(i) == '{') {
                if (!espai_disponible)
                clau_oberta = true;
            }

        if (parent_obert != 0 || clau_oberta || cometes_obertes) throw new Exception();
                }*/
}