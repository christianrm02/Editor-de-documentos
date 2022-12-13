package Excepcions;

public class nEsNegatiu extends Exception {

    public nEsNegatiu() {
        super("El valor n no puede ser negativo");
    }

    public String toString() { return "Exception: el valor n no puede ser negativo";}
}