package datatypes;

import java.text.Normalizer;

public class Utility {
    
    static public String UTF8toASCII(String frase) {
        String res = Normalizer.normalize(frase, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
        return res.replaceAll("·", "");
    }

    static public String[] parseFrase(String frase) {
        frase.replaceAll("[,;:.!?\n]", "");
        String[] paraules = frase.split(" ");
        return paraules;
    }
}
