import java.util.List

public class Contingut {
    private List<String> frases;

    // Metode per convertir un string en una llista de frases
    private void converteix_a_frases(String fr) {
        frases = new List<String>();
        String s;
        int lon;
        for (int i = 0; i < fr.lenght(); i += lon) {
            String s = new String();
            lon = 0;
            while (fr.charAt(i+lon) != null && fr.charAt(i+lon) != '.' && fr.charAt(i+lon) != '!' &&
                    fr.charAt(i+lon) != '?' && (i+lon == 0 || fr.charAt(i+lon) != 'n' || fr.charAt(i+lon-1) != '/')) {
                s += fr.charAt(i);
                ++lon;
            }
            if (fr.charAt(i+lon) != null) {
                s += fr.charAt(i+lon);
                frases.afegir(s);
                ++lon;
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