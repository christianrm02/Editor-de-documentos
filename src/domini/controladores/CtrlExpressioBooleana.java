package controladores;
import datatypes.*;
import transversal.*;

import java.text.Normalizer;
import java.util.*;

import static datatypes.Utility.ParseFrase;

public class CtrlExpressioBooleana {

    private Map<String, ExpressioBooleana> expressions;

    public CtrlExpressioBooleana() {
        expressions = new HashMap<String, ExpressioBooleana>();
    }

    public String getExpressioBooleana(String nom) {
        return expressions.get(nom).getExp();
    }

    public Boolean existsExpressioBooleana(String nom) {
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
        //List<Integer> frases = new ArrayList<Integer>();
        if (!isOperator(node.data)) {
            String[] words = ParseFrase(node.data); //per la sequencia
            Set<Integer> frases = new HashSet<>();
            frases = ci.GetFrases(words[0]);
            if (words.length == 1) return frases;
            else { //es una sequencia
                int i = 1;
                while (i < words.length) {
                    Set<Integer> frases2 = new HashSet<>();
                    frases2 = ci.GetFrases(words[i]);
                    frases.retainAll(frases2);
                    ++i;
                }
                Set<Integer> frases3 = new HashSet<>();
                frases3 = (ci.GetSequencia(node.data, frases));
                return frases3;
            }
        } else {
            if (node.data.equals("&")) return intersection(cercaExpBol(node.leftNode, ci), cercaExpBol(node.rightNode, ci));
            else if (node.data.equals("|")) return union(cercaExpBol(node.leftNode, ci), cercaExpBol(node.rightNode, ci));
            else return not(cercaExpBol(node.leftNode, ci), ci);
        }
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp, CtrlIndex ci) {
        ExpressioBooleana expB = new ExpressioBooleana(exp);
        Tree expTree = expB.getExpA();
        Set<Integer> frases = new HashSet<>();
        frases = cercaExpBol(expTree.root, ci);
        //List<Pair> documents = new ArrayList<>();
        return ci.GetDocuments(frases);
    }

    public List<Pair> getAll() {
        List<Pair> exps = new ArrayList<Pair>();
        for (String clau : expressions.keySet()) {
            Pair p = new Pair();
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

    public static void main(String[] args) {
        Set<Integer> s1 = new HashSet<>();
        s1.add(1); s1.add(3); s1.add(4); s1.add(8);
        int n = 10;
        Set<Integer> complementary = new HashSet<>();
        for (int i = 0; i < n; ++i) {
            if (!s1.contains(i)) complementary.add(i);
        }
        for (Integer i : complementary) System.out.print(i +", ");
    }
}