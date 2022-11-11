package controladores;
import datatypes.*;

import java.text.Normalizer;
import java.util.*;

public class CtrlExpressioBooleana {

    private Map<String, ExpressioBooleana> expressions;
    private CtrlIndex ci;

    public CtrlExpressioBooleana() {
        expressions = new HashMap<String, ExpressioBooleana>();
    }

    public String getExpressioBooleana(String nom) {
        return expressions.get(nom).getExp();
    }

    public Boolean existsExpressioBooleana(String nom) {
        return expressions.containsKey(nom);
    }

    private Set<Integer> not(Set<Integer> set) {
        int n = ci.GetNumFrases();
        Set<Integer> complementary = new HashSet<>();
        for (int i = 0; i < n - set.size(); ++i) {
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

    private Set<Integer> cercaExpBol(TreeNode node) {
        //List<Integer> frases = new ArrayList<Integer>();
        if (!isOperator(node.data)) {
            String[] words = node.data.split("\\W+"); //per la sequencia
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
            if (node.data.equals("&")) return intersection(cercaExpBol(node.leftNode), cercaExpBol(node.rightNode));
            else if (node.data.equals("|")) return union(cercaExpBol(node.leftNode), cercaExpBol(node.rightNode));
            else return not(cercaExpBol(node.leftNode));
        }
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        ExpressioBooleana expB = new ExpressioBooleana(exp);
        Tree expTree = expB.getExpA();
        Set<Integer> frases = new HashSet<>();
        frases = cercaExpBol(expTree.root);
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

    private static String UTF8toASCII(String frase) {
        String res = Normalizer.normalize(frase, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
        return res.replaceAll("·", "");
    }

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        String s = leer.nextLine();
        s = UTF8toASCII(s);
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
                    } else s2 += s.charAt(i);
                    ++i;
                }
                llista.add(s2);
                llista.add(")");
                ++i;
            } else if (s.charAt(i) == '&') {
                llista.add("&");
                ++i;
            } else if (s.charAt(i) == '|') {
                llista.add("|");
                ++i;
            } else if (s.charAt(i) == '!') llista.add("!");
            else if (s.charAt(i) == '(') llista.add("(");
            else if (s.charAt(i) == ')') {
                llista.add(s1);
                llista.add(")");
                ++i;
                s1 = "";
            } else if (s.charAt(i) == '\"') {
                ++i;
                String s2 = "";
                while (s.charAt(i) != '\"') {
                    s2 += s.charAt(i);
                    ++i;
                }
                llista.add(s2);
                ++i;
            } else if (s.charAt(i) == ' ') {
                llista.add(s1);
                s1 = "";
            } else s1 += s.charAt(i);
            ++i;
        }
        if (s1.length() > 0) llista.add(s1);


        for (String h : llista) {
            System.out.print(h + ",");
        }

    }
}