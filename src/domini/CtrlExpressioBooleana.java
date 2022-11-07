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

    //Per fer or
    public List<Integer> union(List<Integer> llista1, HashSet<Integer> llista2) {
        Set<Integer> set = new HashSet<Integer>();
        set.addAll(llista1);
        set.addAll(llista2);
        return new ArrayList<Integer>(set);
    }
    //Per fer and
    public List<Integer> intersection(List<Integer> llista1, HashSet<Integer> llista2) {
        List<Integer> llista = new ArrayList<Integer>();
        llista.addAll(llista1);
        llista.retainAll(llista2);
        return llista;
    }

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