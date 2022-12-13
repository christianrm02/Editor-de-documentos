package Excepcions;

public class baseAndExpEsZero extends Exception {

    public baseAndExpEsZero() {
        super("El valor exp y base no pueden ser 0");
    }

    public String toString() { return "Exception: El valor exp y base no pueden ser 0";}
}
