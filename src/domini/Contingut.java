import java.util.List

public class Contingut {
    private List<String> frases;

    // Metode per convertir un string en una llista de frases
    private void converteix_a_frases(String fr) {
        frases = new List<String>();
        String s;
        for (int i = 0; i < fr.lenght(); ++i) {
            String s = new String();
            while (fr.charAt(i) != null && fr.charAt(i) != '.' && fr.charAt(i) != '!' &&
                    fr.charAt(i) != '?' && (i == 0 || fr.charAt(i) != 'n' || fr.charAt(i-1) != '/')) {
                s += fr.charAt(i);
                ++i;
            }
            if (fr.charAt(i) != null) {
                s += fr.charAt(i);
                frases.afegir(s);
                ++i;
            }
            else { //cas que acabi el string sense ., ! o ?
                frases.afegir(s);
            }
        }
    }

    // Constructores
    public Contingut() {
        frases = new List<String>();
    }

    public Contingut(String fr) {
        converteix_a_frases(fr);
    }

    // Getter
    public List<String> getFrases() {
        return frases;
    }
}