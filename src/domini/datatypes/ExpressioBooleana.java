package domini.datatypes;
import excepcions.ExpBoolNoValidaException;

import java.util.*;

/**
* ExpressioBooleana: S'implementa la classe Expressió Booleana.
* @author Pol Fradera
*/
public class ExpressioBooleana {

    /**
     * Nom de l'expressió booleana.
     */
    private String nom;

    /**
     * Expressió de l'expressió booleana.
     */
    private String exp;

    /**
     * Expressió en forma d'arbre de l'expressió booleana.
     */
    private Tree expA;

    /**
     * Constructora de l'expressió booleana amb només l'expressió.
     */
    public ExpressioBooleana(String exp) throws ExpBoolNoValidaException {
        esCorrecte(exp);

        this.exp = exp;
        List<String> llistaExp = crearLlista();
        /*
        for (String s:llistaExp) System.out.print(s + ", ");
        System.out.println();
        */

        this.expA = new Tree(llistaExp);
    }

    /**
     * Constructora de l'expressió booleana amb el nom i l'expressió.
     */
    public ExpressioBooleana(String nom, String exp) throws ExpBoolNoValidaException {
        esCorrecte(exp);

        this.nom = nom;
        this.exp = exp;
        List<String> llistaExp = crearLlista();
        this.expA = new Tree(llistaExp);
    }

    /**
     * Mètode que llença una excepció si la part de l'expressió on hi ha cometes o claus no és correcte.
     * @param index: int: indica a partir de quin caràcter s'ha de comprovar l'expressió.
     * @param exp: String: expressió booleana per a comprovar.
     * @param c_final: char: caràcter que indica fins a on s'ha de fer la comprovació.
     * @return int: indica l'índex següent del caràcter c_final.
     * @exception ExpBoolNoValidaException: exp no és vàlida.
     */
    private int comprovacio_cometes_i_clau(int index, String exp, char c_final) throws ExpBoolNoValidaException {
        ++index;
        boolean espai_disponible = false;
        boolean paraules = false; //true si hi ha més d'una paraula
        while (index < exp.length() && exp.charAt(index) != c_final) {
            if (es_operador(exp.charAt(index))) throw new ExpBoolNoValidaException();
            else if (exp.charAt(index) == ' ') {
                if (!espai_disponible) throw new ExpBoolNoValidaException();
                else espai_disponible = false;
                paraules = true;
            }
            else espai_disponible = true;
            ++index;
        }
        if (index >= exp.length() || !espai_disponible || !paraules) throw new ExpBoolNoValidaException();
        return index;
    }

    /**
     * Mètode que llença una excepció si l'expressió exp no és correcte.
     * @param exp: String: expressió booleana per a comprovar.
     * @exception ExpBoolNoValidaException: exp no és vàlida.
     */
    private void esCorrecte(String exp) throws ExpBoolNoValidaException {
        if (exp.length() == 0) throw new ExpBoolNoValidaException(); //expressió buida
        int i = 0;
        boolean espai = false;
        boolean espai_necessari = false;
        int parentesis_oberts = 0;
        while (i < exp.length()) {
            if (exp.charAt(i) == ' ') {
                if (espai) throw new ExpBoolNoValidaException(); //dos espais seguits
                else espai = true;
                if (espai_necessari) espai_necessari = false;
            }
            else {
                if (espai_necessari) throw new ExpBoolNoValidaException(); //operadors mal col·locats
                else if (exp.charAt(i) == '(') ++parentesis_oberts;
                else if (exp.charAt(i) == ')') {
                    if (parentesis_oberts == 0) throw new ExpBoolNoValidaException(); //mal parentitzat
                    else --parentesis_oberts;
                }
                else if (exp.charAt(i) == '&' || exp.charAt(i) == '|') {
                    if (!espai) throw new ExpBoolNoValidaException(); //operadors mal col·locats
                    espai_necessari = true;
                }
                else if (exp.charAt(i) == '"') i = comprovacio_cometes_i_clau(i, exp, '"');
                else if (exp.charAt(i) == '{') i = comprovacio_cometes_i_clau(i, exp, '}');
                else if (exp.charAt(i) == '}') throw new ExpBoolNoValidaException(); //claus incorrectes
                espai = false;
            }
            ++i;
        }
        if (parentesis_oberts > 0) throw new ExpBoolNoValidaException(); //mal parentitzat
        if (exp.charAt(i-1) == '!') throw new ExpBoolNoValidaException(); //! incorrecte
    }

    /**
     * Mètode per saber si un char és un operador lògic.
     * @param c: char: char qualsevol.
     * @return boolean: Indica si el char c és un operador lògic, un parèntesi o una clau d'obertura.
     */
    private boolean es_operador (char c) {
        if (c == '&' || c == '|' || c == '!' || c == '(' || c == '{' || c == ')') return true;
        return false;
    }

    /**
     * Mètode que converteix en una llista la part de l'expressió que està entre cometes.
     * @param llista: List<String>: llista on s'afegeixen els strings corresponents.
     * @param index: int: indica a partir de quin caràcter s'ha de convertir l'expressió a una llista.
     * @return int: indica l'índex següent del caràcter cometes de tancament.
     */
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

    /**
     * Mètode que converteix en una llista la part de l'expressió que està entre claus.
     * @param llista: List<String>: llista on s'afegeixen els strings corresponents.
     * @param index: int: indica a partir de quin caràcter s'ha de convertir l'expressió a una llista.
     * @return int: indica l'índex següent del caràcter clau de tancament.
     */
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

    /**
     * Mètode que converteix l'expressió booleana a una llista de strings.
     */
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
            else if (exp.charAt(i) == '!') {
                if (paraula) {
                    llista.add(s1);
                    paraula = false;
                }
                llista.add("!");
                s1 = "";
            }
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

    /**
     * Getter del nom de l'expressió booleana.
     * @return String: Es retorna el nom de l'expressió booleana.
     */
    public String getNom() { return nom; }

    /**
     * Getter de l'expressió de l'expressió booleana.
     * @return String: Es retorna l'expressió de l'expressió booleana.
     */
    public String getExp() { return exp; }

    /**
     * Getter de l'arbre de l'expressió booleana.
     * @return Tree: Es retorna l'arbre de l'expressió booleana.
     */
    public Tree getExpA() { return expA; }

    //Setters
    //public void setNom(String nom) { this.nom = nom; }
/*
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
    }*/
}