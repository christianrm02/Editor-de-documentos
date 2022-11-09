package drivers;
import controladores.*;
import datatypes.*;
import java.util.*;

public class DriverCtrlDomini {
    CtrlDomini cd;
    Scanner entrada;
    Boolean dObert;

    Integer nDocuments;

    DriverCtrlDomini() {
        cd = null;
        entrada = new Scanner(System.in);
        dObert = null;
        nDocuments = 0;
    }

    public void tCrearCtrlDomini() {
        cd = new CtrlDomini();
    }

    public void tCrearDocument() throws Exception {
        System.out.println("Escriu el titol del document que vols crear: ");
        String titol = entrada.nextLine();

        System.out.println("Escriu l'autor del document que vols crear: ");
        String autor = entrada.nextLine();
        System.out.println("Has introduit el titol " + titol + "i l'autor " + autor + ".");
        cd.crearDocument(autor, titol);
    }

    public TreeMap<Integer, Pair<String, String>> escriuClaus() {
        List<Pair<String, String>> titaut = cd.getTitolsAutors();
        TreeMap<Integer, Pair<String, String>> m = new TreeMap<Integer, Pair<String, String>>();
        Integer i = 0;
        for (Pair<String, String> ta : titaut) {
            m.put(i, ta);
            System.out.println(Integer.toString(i) + ". Titol: " + ta.y + " Autor: " + ta.x);
            ++i;
        }
        return m;
    }

    public void tEsborrarDocuments() {
        System.out.println("A continuacio sortiran els documents existents, com a molt pots seleccionar-ne 10:");
        TreeMap<Integer, Pair<String, String>> m = escriuClaus();
        //s'ha d'acabar
    }

    public void tGetTitols() {
        System.out.println("A continuacio tenim els titols de tots els documents existents:");
        List<String> titols = cd.getTitols();
        for (String t : titols) {
            System.out.println(t);
        }
    }

    public void tGetAutors() {
        System.out.println("A continuacio tenim els autors de tots els documents existents:");
        List<String> autors = cd.getAutors();
        for (String a : autors) {
            System.out.println(a);
        }
    }

    public void tGetTitolsAutors() {
        System.out.println("A continuacio tenim tots els documents (titol i autor) existents:");
        List<Pair<String, String>> titaut = cd.getTitolsAutors();
        for (Pair<String, String> ta : titaut) {
            System.out.println("Titol: " + ta.y + " Autor: " + ta.x);
        }
    }

    public void tObrirDocument() {
        System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
        TreeMap<Integer, Pair<String, String>> m = escriuClaus();
        String docSel = entrada.nextLine();
        Pair<String, String> p = m.get(Integer.valueOf(docSel));
        cd.obrirDocument(p.x, p.y);
    }

    public void tModificarTitol() throws Exception {
        System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
        TreeMap<Integer, Pair<String, String>> m = escriuClaus();
        String docSel = entrada.nextLine();
        System.out.println("Introdueix el nou titol:");
        String newT = entrada.nextLine();
        Pair<String, String> p = m.get(Integer.valueOf(docSel));
        cd.modificarTitol(p.x, p.y, newT);
    }

    public void tModificarAutor() throws Exception {
        System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
        TreeMap<Integer, Pair<String, String>> m = escriuClaus();
        String docSel = entrada.nextLine();
        System.out.println("Introdueix el nou autor:");
        String newA = entrada.nextLine();
        Pair<String, String> p = m.get(Integer.valueOf(docSel));
        cd.modificarAutor(p.x, p.y, newA);
    }

    public void tModificarContingut() { // no tinc lar com fer-la
        System.out.println("Introdueix el nou contingut:");
        String cont = entrada.nextLine();
        //cd.modificarContingut(cont);
    }

    public static void main (String [] args) throws Exception {
        DriverCtrlDomini dcd = new DriverCtrlDomini();
        opcions_disponibles();
        String ent = dcd.entrada.nextLine();
        while (!ent.equals("0")) { // Si posem 0, surt
            switch (ent) {
                case "1": { // Constructora de CtrlDomini la 1a vegada / Constructora de document i obre el document
                    if (dcd.cd == null) dcd.tCrearCtrlDomini();
                    dcd.tCrearDocument();
                    break;
                }
                // Si no es crea cap document, la resta d'operacions no funcionen
                case "2": { // Destructora de documents
                    dcd.tEsborrarDocuments();
                    break;
                }
                case "3": { // Getter dels titols de tots els documents
                    dcd.tGetTitols();
                    break;
                }
                case "4": { // Getter dels autors de tots els documents
                    dcd.tGetAutors();
                    break;
                }
                case "5": { // Getter de les claus (autor + titol) de tots els documents
                    dcd.tGetTitolsAutors();
                    break;
                }
                case "6": { // "Obre" el document que s'indiqui entre els existents
                    dcd.tObrirDocument();
                    break;
                }
                case "7": { // Modificadora de titol d'un document
                    dcd.tModificarTitol();
                    break;
                }
                case "8": { // Modificadora d'autor d'un document
                    dcd.tModificarAutor();
                    break;
                }
                case "9": { // Modificadora de contingut del document obert, nomes es pot utilitzar si s'ha fet abans un obrir document
                    dcd.tModificarContingut();
                    break;
                }
/*                case "10": { // Llista els titols d'un autor existent escollit per l'usuari
                    dcd.tLlistarTitolsdAutors();
                    break;
                }
                case "11": { // Lista els autors que compleixen el prefix indicat per l'usuari
                    dcd.tLlistarAutorsPrefix();
                    break;
                }
                case "12": { // Llista els K documents mes semblants a l'indicat
                    dcd.tLlistarKDocumentsS();
                    break;
                }
                case "13": { // Cerca segons una expressio booleana
                    dcd.tCercarExpressioBooleana();
                    break;
                }
                case "14": { // Getter d'expressions booleanes
                    dcd.tGetExpressioBooleana();
                    break;
                }
                case "15": { // Setter d'expressio booleana
                    dcd.tSetExpressioBooleana();
                    break;
                }
                case "16"; { // Modificadora d'expressio booleana
                    dcd.tModExpressioBooleana();
                    break;
                }
                case "17"; { // Destructora d'expressio booleana
                    dcd.tDeleteExpressioBooleana();
                    break;
                }*/
                default:
                    break;
            }
            opcions_disponibles();
            ent = dcd.entrada.nextLine();
        }
    }

    private static void opcions_disponibles() {
        System.out.println("Benvingut al driver de ctrlDomini, per probar un metode ingressa per teclat el seu numero");
        System.out.println("1-  Constructora de document");
        System.out.println("Abans d'utilitzar les seguents, com a minim ha d'haver un document.");
        System.out.println("2-  Destructora de document");
        System.out.println("3-  getTitols");
        System.out.println("4-  getAutors");
        System.out.println("5-  getTitolsAutors");
        System.out.println("6-  Obrir document");
        System.out.println("7-  Modificar titol d'un document");
        System.out.println("8-  Modificar autor d'un document");
        System.out.println("9-  Modificar contingut d'un document (primer s'ha d'obrir un document)");
        System.out.println("10-  Llistar titols d'un autor");
        System.out.println("11-  Llistar autors per prefix");
        System.out.println("12-  Llistar K documents semblants a un document D");
        System.out.println("13-  Cerca amb una expressio booleana");
        System.out.println("14-  getExpressioBooleana");
        System.out.println("15-  Crea expressio booleana");
        System.out.println("16-  Modififica expressio booleana");
        System.out.println("17-  Elimina expressio booleana");
        System.out.println("0-  cancel·lar");
        System.out.println("Introdueix un numero: ");
    }
}