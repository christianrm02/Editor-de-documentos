package excepcions;

public class EExpBoolException extends Exception {
    public EExpBoolException() {
        super("El nom de l'expressió booleana existeix.");
    }

    public String toString() { return "Exception: El nom de l'expressió booleana no ha d'existir.";}
}
