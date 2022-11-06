import java.util.*;
import datatypes.Pair;

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

    public List<Integer> union(List<Integer> llista1, HashSet<Integer> llista2) {
        Set<Integer> set = new HashSet<Integer>();
        set.addAll(llista1);
        set.addAll(llista2);
        return new ArrayList<Integer>(set);
    }
    public List<Integer> intersection(List<Integer> llista1, HashSet<Integer> llista2) {
        List<Integer> llista = new ArrayList<Integer>();
        llista.addAll(llista1);
        llista.retainAll(llista2);
        return llista;
    }

    public List<Pair> cercarExpressioBooleana(String exp) {
        ExpressioBooleana expB = new ExpressioBooleana(exp);
        List<Integer> frases = new ArrayList<Integer>();
        frases = ci.GetFrases(paraula);
    }

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