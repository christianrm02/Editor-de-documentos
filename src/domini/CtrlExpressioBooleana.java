import java.util.*;

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
/*
    public List<ExpressioBooleana> getAll() {
        List<ExpressioBooleana> exps = new ArrayList<ExpressioBooleana>();
        for ()
        return exps;
    }*/

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