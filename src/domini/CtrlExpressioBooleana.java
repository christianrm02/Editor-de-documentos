import java.util.*;
import datatypes.Pair;

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

    public List<Pair> getAll() {
        List<Pair> exps = new ArrayList<Pair>();
        for (String clau:expressions.keySet()) {
            Pair p;
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