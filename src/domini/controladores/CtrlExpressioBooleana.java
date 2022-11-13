package controladores;
import datatypes.*;
import transversal.*;

import java.util.*;

import static datatypes.Utility.ParseFrase;

/**
 * @author Pol Fradera
 */
public class CtrlExpressioBooleana {

    private Map<String, ExpressioBooleana> expressions;

    public CtrlExpressioBooleana() {
        expressions = new HashMap<>();
    }

    public boolean existsExpressioBooleana(String nom) {
        return expressions.containsKey(nom);
    }

    private Set<Integer> not(Set<Integer> set, CtrlIndex ci) {
        int n = ci.GetNumFrases();
        Set<Integer> complementary = new HashSet<>();
        for (int i = 0; i < n; ++i) {
            if (!set.contains(i)) complementary.add(i);
        }
        return complementary;
    }

    //Per fer or
    private Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        set1.addAll(set2);
        return set1;
    }

    //Per fer and
    private Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        set1.retainAll(set2);
        return set1;
    }

    private boolean isOperator(String s) {
        return s.length() == 1 && (s.equals("&") || s.equals("|") || s.equals("!"));
    }

    private Set<Integer> cercaExpBol(TreeNode node, CtrlIndex ci) {
        if (!isOperator(node.data)) {
            String[] words = ParseFrase(node.data); //per la seqüècia
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

    public List<Pair<String, String>> cercarExpressioBooleana(String exp, CtrlIndex ci) {
        ExpressioBooleana expB = new ExpressioBooleana(exp);
        Tree expTree = expB.getExpA();
        Set<Integer> frases = cercaExpBol(expTree.root, ci);
        return ci.GetDocuments(frases);
    }

    //Getter
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

    //Setter
    public void setExpressioBooleana(String nom, String exp) {
        ExpressioBooleana expB = new ExpressioBooleana(nom, exp);
        expressions.put(nom, expB);
    }

    //Destructora
    public void deleteExpressioBooleana(String nom) {
        expressions.remove(nom);
    }
}