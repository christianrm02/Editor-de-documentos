package datatypes;

import java.util.List;
import java.util.ArrayList;
import java.lang.String;

public class Contingut {
    private List<String> frases;

    // Metode per convertir un string en una llista de frases
    private void converteix_a_frases(String fr) {
        frases = new ArrayList<String>();
        String s;
        for (int i = 0; i < fr.length(); ++i) {
            s = new String();
            while (i < fr.length() && fr.charAt(i) != '.' && fr.charAt(i) != '!' &&
                    fr.charAt(i) != '?' && (i == 0 || fr.charAt(i) != '\n')) {
                s += fr.charAt(i);
                ++i;
            }
            if (i < fr.length()) {
                s += fr.charAt(i);
                frases.add(s);
                ++i;
            }
            else { //cas que acabi el string sense ., ! o ?
                frases.add(s);
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