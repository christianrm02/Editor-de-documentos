package domini.datatypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility: Funcions estàtiques per processar text
 * @author Èric Ryhr
 */
public class Utility {

    /** 
     * Mètode per separar una frase en paraules i eliminar símbols
     * @param frase - Frase a tractar
     * @return String[] - Array amb les paraules de la frase
     */
    static public String[] ParseFrase(String frase) {
        frase = frase.replaceAll("[(),;:.!?]", "");
        frase = frase.replaceAll("\n", " ");
        String[] paraules = frase.split(" ");
        return paraules;
    }

    
    /** 
     * Mètode per separar un contingut en frases
     * @param fr - Contingut a tractar
     * @return List<String> - Llista amb les frases obtenides
     */
    static public List<String> converteix_a_frases(String fr) {
        List<String> frases = new ArrayList<>();
        if (fr.equals("")) return frases;
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
}
