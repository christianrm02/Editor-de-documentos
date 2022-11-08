import datatypes.Tree;
import java.util.*;
import datatypes.Pair;
import datatypes.TreeNode;

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
        int n = ci.GetNumFrasesTotals();
        Set<Integer> complementary = new HashSet<>();
        for (int i = 0; i < n-set.size(); ++i) {
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
            Set<Integer> frases = new HashSet<>();
            frases.addAll(ci.GetFrases(node.data));
            return frases;
        }
        else {
            if (node.data.equals("&")) return intersection(cercaExpBol(node.leftNode), cercaExpBol(node.rightNode));
            else if (node.data.equals("|")) return union(cercaExpBol(node.leftNode), cercaExpBol(node.rightNode));
            else return not(cercaExpBol(node.leftNode));
        }
    }

    public List<Pair<String, String>> cercarExpressioBooleana(String exp) {
        ExpressioBooleana expB = new ExpressioBooleana(exp);
        Tree expTree = expB.getExpA();
        List<Integer> frases = new ArrayList<>();
        frases.addAll(cercaExpBol(expTree.getRoot()));
        //List<Pair> documents = new ArrayList<>();
        return ci.GetDocuments(frases);
    }

/*
    public List<Pair> cercarExpressioBooleana(String exp) {
        ExpressioBooleana expB = new ExpressioBooleana(exp);
        List<String> llistaExp;
        llistaExp = expB.getLlista();
        List<Integer> frases = new ArrayList<Integer>();
        boolean and = false; boolean or = false; boolean negacio = false; //un map?
        for (int i = 0; i < llistaExp.size(); i++) {
            String s = llistaExp.get(i);
            if (s.length() == 1 && (s == "&" || s == "|" || s == "!")) {
                if (s == "&") and = true;
                else if (s == "|") or = true;
                else negacio = true;
            }
            String paraules[] = s.split(" ");
            if (paraules.length > 1) {
                //sequencia de paraules
            }
            else frases = ci.GetFrases(s);
        }

    }*/

    public List<Pair> getAll() {
        List<Pair> exps = new ArrayList<Pair>();
        for (String clau:expressions.keySet()) {
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

}


/*    private List<Integer> intersection(List<Integer> llista1, List<Integer> llista2) {
        List<Integer> llista = new ArrayList<>();
        llista.addAll(llista1);
        llista.retainAll(llista2);
        return llista;
    }


    //Per fer or
    private List<Integer> union(List<Integer> llista1, List<Integer> llista2) {
        Set<Integer> set = new HashSet<>();
        set.addAll(llista1);
        set.addAll(llista2);
        return new ArrayList<>(set);
    }

            private List<Integer> not(List<Integer> llista) {
        int n = ci.GetNumFrasesTotals();
        List<Integer> complementari = new ArrayList<>();
        for (int i = 0; i < n-llista.size(); ++i) {
            if (!llista.contains(i)) complementari.add(i);
        }
        return complementari;
    }






 */