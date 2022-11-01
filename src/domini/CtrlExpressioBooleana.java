import java.util.*;

public class CtrlExpressioBooleana {

    private HashMap<String, ExpressioBooleana> expressions;
    public CtrlExpressioBooleana() {
        expressions = new HashMap<String, ExpressioBooleana>();
    }

    public ExpressioBooleana getExpressioBooleana(String nom) {

    }

    public Boolean existsExpressioBooleana(String nom) {

    }

    public List<ExpressioBooleana> getAll() {
        List<ExpressioBooleana> exps = new ArrayList<ExpressioBooleana>();

        return exps;
    }

    //Setter
    public void setExpressioBooleana(String nom, String exp) {
        ExpressioBooleana expB = new ExpressioBooleana(exp);
        expressions.put(nom, expB);
    }

    //Destructora
    public void deleteExpressioBooleana(String nom) {
        expressions.remove(nom);
    }

}