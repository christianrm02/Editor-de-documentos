package drivers;
import domini.CtrlDocument;
import java.util.Scanner;

public class DriverCtrlDocument {
    private DriverCtrlDocument dcd = new DriverCtrlDocument.getInstance();
    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opc = sc.nextInt();
        Boolean sortir = false;
        while(!sortir) {
            switch(opc) {
                case 1: {
                    System.out.println("HAS TRIAT 1");
                }
                case 2: {
                    System.out.println("HAS TRIAT 2");
                }
                case 0: {
                    sortir = true;
                }
                default: {
                    break;
                }
            }
        }
        System.out.println("ESPEREM QUE THAGUIM AJUDAT");
    }

    private static void opcions_disponibles() {
        System.out.println("Benvingut al driver de ctrlDocument, per probar un metode ingressa per teclat el seu numero");
        System.out.println("**Constructors**");
        System.out.println("1-  Constructora");
        System.out.println("**Getters**");
        System.out.println("2-  getDocument");
        System.out.println("3-  existsDocument");
        System.out.println("4-  getAll");
        System.out.println("5-  getAutors");
        System.out.println("6-  getTitols");
        System.out.println("7-  getClaus");
        System.out.println("8-  getContingut");
        System.out.println("9-  getTitolsAutor");
        System.out.println("**Setters**");
        System.out.println("10-  crearDocument");
        System.out.println("11-  obreDocument");
        System.out.println("12-  modificarAutor");
        System.out.println("13-  modificarTitol");
        System.out.println("14-  modificarContingut");
        System.out.println("**Sortir**");
        System.out.println("0-  cancelar");
        System.out.println("Introdueix un numero: ");
    }
}