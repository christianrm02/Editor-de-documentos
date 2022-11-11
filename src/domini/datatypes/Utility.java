package datatypes;

//import java.text.Normalizer;

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
}
