package excepcions;

public class IDInvalid extends Exception {
    public IDInvalid() {
        super("L'id indicat conté \"_\" o és més llarg de 50 caràcters.");
    }

    public String toString() { return "L'id indicat no pot contenir ni \"_\" ni ser més llarg de 50 caràcters.";}
}
