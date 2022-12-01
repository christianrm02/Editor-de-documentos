package datatypes;

//import java.text.Normalizer;

import java.util.*;

import transversal.Pair;

/**
 * Utility: Funcions estàtiques per processar text
 * @author Èric Ryhr
 */
public class Utility {
    
    /*static public String UTF8toASCII(String frase) {
        String res = Normalizer.normalize(frase, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
        return res.replaceAll("·", "");
    }*/

    static public String[] ParseFrase(String frase) {
        frase = frase.replaceAll("[(),;:.!?]", "");
        frase = frase.replaceAll("\n", " ");
        String[] paraules = frase.split(" ");
        return paraules;
    }

    static public List<String> converteix_a_frases(String fr) {
        List<String> frases = new ArrayList<>();
        if (fr == "") return frases;
        boolean salt_final = fr.charAt(fr.length() - 1) == '\n'; // si l'ultim caracter es '\n', salt_final es true, altrament fals
        List<String> aux = List.of(fr.split("\n"));

        for (int j = 0; j < aux.size(); ++j) {
            String f = aux.get(j);
            if (f.equals("")) frases.add("\n");
            else {
                for (int i = 0; i < f.length(); ++i) {
                    String s = "";
                    while (i < f.length() && f.charAt(i) != '.' && f.charAt(i) != '!' &&
                            f.charAt(i) != '?') {
                        s += f.charAt(i);
                        ++i;
                    }
                    if (i < f.length()) s += f.charAt(i);
                    if (i >= f.length() - 1 && (salt_final || j != aux.size() - 1)) s += '\n';
                    frases.add(s);
                }
            }
        }
        return frases;
    }

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
        for (int i = 0; i < l.size(); ++i) {
            Pair<String, String> p = l.get(i);
            s.add(new Pair<>(p.y, p.x));
        }
        List<Pair<String, String>> l2 = new ArrayList<>(s);
        for (int i = 0; i < l2.size(); ++i) {
            Pair<String, String> p = swap(l2.remove(i));
            l.add(i, p);
        }
        return l2;
    }

    static public List<Pair<String, String>> ordena_alfab_desc_y(List<Pair<String, String>> l) {
        List<Pair<String, String>> l2 = ordena_alfab_asc_y(l);
        Collections.reverse(l2);
        return l2;
    }

    static public Pair<String, String> swap(Pair<String, String> p) {
        String aux = p.x;
        p.x = p.y;
        p.y = aux;
        return p;
    }
}
