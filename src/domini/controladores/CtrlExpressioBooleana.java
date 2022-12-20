package domini.controladores;
import domini.datatypes.*;
import excepcions.ExpBoolNoValidaException;
import transversal.*;

import java.util.*;

import static domini.datatypes.Utility.ParseFrase;

/**
 * Aquesta classe s’encarrega de gestionar les expressions booleanes del sistema.
 * @author Pol Fradera
 */
public class CtrlExpressioBooleana {

    /**
     * Conjunt d'expressions booleanes guardades al sistema.
     */
    private Map<String, ExpressioBooleana> expressions;

    /**
     * Constructora del controlador d'expressió booleana.
     */
    public CtrlExpressioBooleana() {
        expressions = new HashMap<>();
    }

    /**
     * Mètode per saber si una expressió booleana ja existeix.
     * @param nom: String: nom de l'expressió booleana.
     * @return boolean: Indica si l'expressió booleana amb nom nom existeix.
     */
    public boolean existsExpressioBooleana(String nom) {
        return expressions.containsKey(nom);
    }

    /**
     * Mètode que dona els identificadors de les frases complementàries a les indicades per set.
     * @param set: Set<Integer>: conjunt de frases en què es vol aplicar l'operació not.
     * @param ci: CtrlIndex: controlador d'índex necessari per realitzar l'operació del complementari.
     * @return Set<Integer>: Es retorna un set amb els identificadors de les frases complementàries a les frases del set.
     */
    private Set<Integer> not(Set<Integer> set, CtrlIndex ci) {
        int n = ci.GetNumFrases();
        Set<Integer> complementary = new HashSet<>();
        for (int i = 0; i < n; ++i) {
            if (!set.contains(i)) complementary.add(i);
        }
        return complementary;
    }

    /**
     * Mètode que dona els identificadors de les frases resultants d'aplicar l'operació unió entre el set1 i el set2.
     * @param set1: Set<Integer>: primer conjunt de frases en què es vol aplicar l'operació unió.
     * @param set2: Set<Integer>: segon conjunt de frases en què es vol aplicar l'operació unió.
     * @return Set<Integer>: Es retorna un set amb els identificadors de les frases que es troben al set1 i també de les que es troben al set2.
     */
    private Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        set1.addAll(set2);
        return set1;
    }

    /**
     * Mètode que dona els identificadors de les frases resultants d'aplicar l'operació intersecció entre el set1 i el set2.
     * @param set1: Set<Integer>: primer conjunt de frases en què es vol aplicar l'operació intersecció.
     * @param set2: Set<Integer>: segon conjunt de frases en què es vol aplicar l'operació intersecció.
     * @return Set<Integer>: Es retorna un set només amb els identificadors de les frases que es troben en el set1 i el set2.
     */
    private Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        set1.retainAll(set2);
        return set1;
    }

    /**
     * Mètode per saber si un string és un operador lògic.
     * @param s: String: string qualsevol.
     * @return boolean: Indica si l'String s és un operador lògic.
     */
    private boolean isOperator(String s) {
        return s.length() == 1 && (s.equals("&") || s.equals("|") || s.equals("!"));
    }

    /**
     * Mètode que dona els identificadors de les frases de tots els documents que compleixen l'expressió booleana que té com a arrel el node node.
     * @param node: TreeNode: arrel d'un subarbre de l'expressió booleana.
     * @param ci: CtrlIndex: controlador d'índex necessari per realitzar la cerca.
     * @return Set<Integer>: Es retorna un set amb els identificadors de les frases que compleixen l'expressió booleana que té com a arrel el node node.
     */
    private Set<Integer> cercaExpBol(TreeNode node, CtrlIndex ci) {
        if (!isOperator(node.data)) {
            String[] words = ParseFrase(node.data); //per la seqüència
            Set<Integer> frases = ci.GetFrases(words[0]); //frases on apareix la primera paraula
            if (words.length == 1) return frases; //només és una paraula
            else { //es una sequencia
                int i = 1;
                while (i < words.length) {
                    Set<Integer> frases2 = ci.GetFrases(words[i]);
                    frases.retainAll(frases2);
                    ++i;
                }
                return ci.GetSequencia(node.data, frases); //frases on apareix la seqüència
            }
        } else {
            if (node.data.equals("&")) return intersection(cercaExpBol(node.leftNode, ci), cercaExpBol(node.rightNode, ci));
            else if (node.data.equals("|")) return union(cercaExpBol(node.leftNode, ci), cercaExpBol(node.rightNode, ci));
            else return not(cercaExpBol(node.leftNode, ci), ci);  //operador !
        }
    }

    /**
     * Mètode que dona les claus dels documents que cumpleixen l'expressió booleana amb nom nom_exp.
     * @param nom_exp: String: nom de l'expressió booleana existent per fer la cerca.
     * @param ci: CtrlIndex: controlador d'índex necessari per realitzar la cerca.
     * @return List<Pair<String, String>>: Es retorna una llista amb les claus dels documents que compleixen l'expressió booleana amb nom nom_exp.
     */
    public List<Pair<String, String>> cercarExpressioBooleanaExistent(String nom_exp, CtrlIndex ci) {
        ExpressioBooleana expB = expressions.get(nom_exp);
        Tree expTree = expB.getExpA();
        Set<Integer> frases = cercaExpBol(expTree.root, ci);
        return ci.GetDocuments(frases);
    }

    /**
     * Mètode que dona les claus dels documents que cumpleixen l'expressió booleana exp.
     * @param exp: String: expressió booleana per fer la cerca.
     * @param ci: CtrlIndex: controlador d'índex necessari per realitzar la cerca.
     * @return List<Pair<String, String>>: Es retorna una llista amb les claus dels documents que compleixen l'expressió booleana exp.
     * @exception ExpBoolNoValidaException: exp no és vàlida.
     */
    public List<Pair<String, String>> cercarExpressioBooleana(String exp, CtrlIndex ci) throws ExpBoolNoValidaException {
        ExpressioBooleana expB = new ExpressioBooleana(exp);
        Tree expTree = expB.getExpA();
        Set<Integer> frases = cercaExpBol(expTree.root, ci);
        return ci.GetDocuments(frases);
    }

    /**
     * Getter d'expressions booleanes.
     * @return List<Pair<String, String>>: Es retornen totes les expressions booleanes existents al sistema (per cada una, el seu nom i l'expressió).
     */
    public List<Pair<String, String>> getAll() {
        List<Pair<String, String>> exps = new ArrayList<>();
        for (String clau : expressions.keySet()) {
            Pair<String, String> p = new Pair<>();
            p.x = expressions.get(clau).getNom();
            p.y = expressions.get(clau).getExp();
            exps.add(p);
        }
        return exps;
    }

    /**
     * Mètode per crear una expressió booleana amb nom nom i expressió exp.
     * @param nom: String: nom de l'expressió booleana a crear.
     * @param exp: String: expressió de l'expressió booleana a crear.
     * @exception ExpBoolNoValidaException: exp no és vàlida.
     */
    public void setExpressioBooleana(String nom, String exp) throws ExpBoolNoValidaException {
        ExpressioBooleana expB = new ExpressioBooleana(nom, exp);
        expressions.put(nom, expB);
    }

    /**
     * Mètode per esborrar l'expressió booleana amb nom nom.
     * @param nom: String: nom de l'expressió booleana a esborrar.
     */
    public void deleteExpressioBooleana(String nom) {
        expressions.remove(nom);
    }
}