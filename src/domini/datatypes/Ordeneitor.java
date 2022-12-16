package domini.datatypes;

import transversal.Pair;

import java.util.*;

/**
 * Ordeneitor: S'implementen les ordenacions que no estan per defecte.
 * @author Marc Roman
 */
public class Ordeneitor {
    static public List<String> ordena_alfab_desc(List<String> l) {
        Collections.reverse(l);
        return l;
    }

    static public List<Pair<String, String>> ordena_alfab_desc_x(List<Pair<String, String>> l) {
        Collections.reverse(l);
        return l;
    }

    static public List<Pair<String, String>> ordena_alfab_asc_y(List<Pair<String, String>> l) {
        Set<Pair<String, String>> s = new TreeSet<>();
        for (Pair<String, String> p : l) {
            s.add(new Pair<>(p.y, p.x));
        }
        List<Pair<String, String>> l2 = new ArrayList<>(s);
        for (int i = 0; i < l2.size(); ++i) {
            Pair<String, String> p = swap(l2.remove(i));
            l2.add(i, p);
        }
        return l2;
    }

    static public List<Pair<String, String>> ordena_alfab_desc_y(List<Pair<String, String>> l) {
        Set<Pair<String, String>> s = new TreeSet<>();
        for (Pair<String, String> p : l) {
            s.add(new Pair<>(p.y, p.x));
        }
        List<Pair<String, String>> l2 = new ArrayList<>(s);
        int m = l2.size();
        for (int i = 0; i < m; ++i) {
            Pair<String, String> p = swap(l2.remove(i));
            l2.add(m - 2 - i, p);
        }
        return l2;
    }

    static public Pair<String, String> swap(Pair<String, String> p) {
        String aux = p.x;
        p.x = p.y;
        p.y = aux;
        return p;
    }
}
