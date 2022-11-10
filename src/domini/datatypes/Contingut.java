package datatypes;

import java.util.List;
import java.util.ArrayList;
import java.lang.String;

public class Contingut {
    private List<String> frases;

    // Metode per convertir un string en una llista de frases
    private void converteix_a_frases(String fr) {
        frases = new ArrayList<String>();
        if (fr == "") return;
        boolean salt_final = fr.charAt(fr.length() - 1) == '\n';
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
                    if (i < f.length()) {
                        s += f.charAt(i);
                        ++i;
                    }
                    if (i == f.length() && (salt_final || j != aux.size() - 1)) s += '\n';
                    frases.add(s);
                }
            }
        }
    }

    // Constructores
    public Contingut() {
        frases = new ArrayList<String>();
    }

    public Contingut(String fr) {
        converteix_a_frases(fr);
    }

    // Getter
    public List<String> getFrases() {
        return frases;
    }

    // Setter
    public void actualitzaContingut(String cont) {
        converteix_a_frases(cont);
    }
}