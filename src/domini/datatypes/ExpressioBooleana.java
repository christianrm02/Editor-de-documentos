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
    public ExpressioBooleana(String exp) throws Exception {
        esCorrecte(exp);

        this.exp = exp;
        List<String> llistaExp = crearLlista();
        this.expA = new Tree(llistaExp);
    }

    public ExpressioBooleana(String nom, String exp) throws Exception {
        esCorrecte(exp);

        this.nom = nom;
        this.exp = exp;
        List<String> llistaExp = crearLlista();
        this.expA = new Tree(llistaExp);
    }

    void esCorrecte(String exp) throws Exception {
        if (exp.length() == 0) throw new Exception("expressió buida");
        int i = 0;
        boolean espai = false;
        boolean espai_necessari = false;
        int parentesis_oberts = 0;
        while (i < exp.length()) {
            if (exp.charAt(i) == ' ') {
                if (espai) throw new Exception("dos espais seguits");
                else espai = true;
                if (espai_necessari) espai_necessari = false;
            }
            else {
                if (espai_necessari) throw new Exception("operadors mal col·locats");
                else if (exp.charAt(i) == '(') ++parentesis_oberts;
                else if (exp.charAt(i) == ')') {
                    if (parentesis_oberts == 0) throw new Exception("mal parentitzat");
                    else --parentesis_oberts;
                }
                else if (exp.charAt(i) == '&' || exp.charAt(i) == '|') {
                    if (!espai) throw new Exception("operadors mal col·locats");
                    espai_necessari = true;
                }
                espai = false;
            }
            ++i;
        }
        if (parentesis_oberts > 0) throw new Exception("mal parentitzat");
    }

    private boolean es_operador (char c) {
        if (c == '&' || c == '|' || c == '!' || c == '(' || c == '{' || c == ')') return true;
        return false;
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

    public static void InOrder(TreeNode arrel) {
        if (arrel == null) return;
        else {
            InOrder(arrel.leftNode);
            System.out.print(arrel.data+", ");
            InOrder(arrel.rightNode);
        }
    }
    public static void main (String [] args) throws Exception {
        Scanner leer = new Scanner(System.in);
        String s = leer.nextLine();
        ExpressioBooleana exp = new ExpressioBooleana(s);
        InOrder(exp.getExpA().root);
    }
}