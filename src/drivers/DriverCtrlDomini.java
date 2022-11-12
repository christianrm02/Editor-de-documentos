package drivers;
import controladores.*;
import datatypes.*;
import java.util.*;

/**
 * DriverCtrlDomini: Driver per provar totes les funcionalitats disponibles des de CtrlDomini.
 * @author Marc Roman
 */
public class DriverCtrlDomini {
    CtrlDomini cd;
    Scanner entrada;
    Boolean dObert;

    int nDocuments;
    int nExps;

    DriverCtrlDomini() {
        cd = null;
        entrada = new Scanner(System.in);
        dObert = false;
        nDocuments = 0;
        nExps = 0;
    }

    public void tCrearCtrlDomini() {
        cd = new CtrlDomini();
    }

    public void tCrearDocument() {
        System.out.println("Escriu el titol del document que vols crear: ");
        String titol = entrada.nextLine();

        System.out.println("Escriu l'autor del document que vols crear: ");
        String autor = entrada.nextLine();
        System.out.println("Has introduit el titol " + titol + "i l'autor " + autor + ".");
        if (cd.crearDocument(autor, titol)) {
            ++nDocuments;
            dObert = true;
            System.out.println("El document ha estat creat i obert correctament");
        }
        else System.out.println("El document " + titol + " + " + autor + " existeix");
    }

    public TreeMap<int, Pair<String, String>> escriuClaus() {
        List<Pair<String, String>> titaut = cd.getTitolsAutors();
        TreeMap<int, Pair<String, String>> m = new TreeMap<int, Pair<String, String>>();
        int i = 1;
        for (Pair<String, String> ta : titaut) {
            m.put(i, ta);
            System.out.println(Integer.toString(i) + ". Titol: " ta.y + " Autor: " + ta.x);
            ++i;
        }
        return m;
    }

    public TreeMap<int, Pair<String, String>> escriuExps() {
        List<Pair<String, String>> l = cd.getAllExpressionsBooleanes();
        TreeMap<int, Pair<String, String>> m = new TreeMap<int, Pair<String, String>>();
        int i = 1;
        for (Pair<String, String> eb : l) {
            m.put(i, eb);
            System.out.println(Integer.toString(i) + ". Nom: "ta.x + " Expressio: " + ta.y);
            ++i;
        }
        return m;
    }

    public void tEsborrarDocuments() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els documents existents, com a molt pots seleccionar-ne 10:");
            TreeMap<int, Pair<String, String>> m = escriuClaus();
            System.out.println("Posa els numeros dels documents. Si poses 0, es considera que no vols esborrar mes documents.");
            List<Pair<String, String>> docs = new ArrayList<Pair<String, String>>();
            String ent = entrada.nextLine();
            for (int i = 0; i < 10 && ent != "0"; ++i) {
                docs.add(m.get(Integer.valueOf(ent)));
            }
            if (docs.size() == 0) System.out.println("No has seleccionat cap document a esborrar.");
            else {
                System.out.println("Segur que vols esborrar els documents seleccionats?");
                System.out.println("0. Cancel·lar");
                System.out.println("1. Esborrar");
                String ec = entrada.nextLine();
                if (ec == "1") {
                    cd.esborrarDocuments(docs);
                    System.out.println("Els documents s'han esborrat corractament.");
                } else System.out.println("Has cancel·lat l'operacio.");
            }
        } else System.out.println("No hi ha documents a esborrar, crea'n un abans!");
    }

    public void tGetTitols() {
        if (nDocuments > 0) {
            System.out.println("A continuacio tenim els titols de tots els documents existents:");
            List<String> titols = cd.getTitols();
            for (String t : titols) System.out.println(t);
        } else System.out.println("No hi ha titols a mostrar, crea un document abans!");
    }

    public void tGetAutors() {
        if (nDocuments > 0) {
            System.out.println("A continuacio tenim els autors de tots els documents existents:");
            List<String> autors = cd.getAutors();
            for (String a : autors) System.out.println(a);
        } else System.out.println("No hi ha autors a llistar, crea un document abans!");
    }

    public void tGetTitolsAutors() {
        if (nDocuments > 0) {
            System.out.println("A continuacio tenim tots els documents (titol i autor) existents:");
            List<Pair<String, String>> titaut = cd.getTitolsAutors();
            for (Pair<String, String> ta : titaut) System.out.println("Titol: " + ta.y + " Autor: " + ta.x);
        } else System.out.println("No hi ha documents a llistar, crea'n un abans!");
    }

    public void tObrirDocument() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
            TreeMap<int, Pair<String, String>> m = escriuClaus();
            String docSel = entrada.nextLine();
            Pair<String, String> p = m.get(Integer.valueOf(docSel));
            cd.obrirDocument(p.x, p.y);
            System.out.println("El document " + p.x + " " + p.y + " s'ha obert.");
        } else System.out.println("No hi ha documents a obrir, crea'n un abans!");
    }

    public void tModificarTitol() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
            TreeMap<int, Pair<String, String>> m = escriuClaus();
            String docSel = entrada.nextLine();
            System.out.println("Introdueix el nou titol:");
            String newT = entrada.nextLine();
            Pair<String, String> p = m.get(Integer.valueOf(docSel));
            if (cd.modificarTitol(p.x, p.y, newT)) System.out.println("El titol ha estat modificat correctament.");
            else System.out.println("El document " + newT + " + " + autor + " existeix.");
        } else System.out.println("No hi ha documents a modificar, crea'n un abans!");
    }

    public void tModificarAutor() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
            TreeMap<int, Pair<String, String>> m = escriuClaus();
            String docSel = entrada.nextLine();
            System.out.println("Introdueix el nou autor:");
            String newA = entrada.nextLine();
            Pair<String, String> p = m.get(Integer.valueOf(docSel));
            if (cd.modificarAutor(p.x, p.y, newA)) System.out.println("L'autor ha estat modificat correctament.");
            else System.out.println("El document " + titol + " + " + newA + " existeix.");
        } else System.out.println("No hi ha documents a modificar, crea'n un abans!");
    }

    public void tModificarContingut() { // no tinc clar com fer-la
        if (dObert && nDocuments > 0) {
            System.out.println("Introdueix el nou contingut:");
            String cont = entrada.nextLine();
            cd.modificarContingut(titol, autor, cont);
        } else if (nDocuments < 1) System.out.println("No hi ha cap document, crea'n un abans!");
        else System.out.println("No hi ha cap document obert, obre'n un abans!");
    }

    public void tLlistarTitolsdAutors() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els autors existents, selecciona'n un:");
            List<String> aut = cd.getAutors();
            TreeMap<int, Pair<String, String>> m = new TreeMap<int, String>();
            int i = 0;
            for (String a : aut) {
                m.put(i, a);
                System.out.println(Integer.toString(i) + ". " + ta.x);
                ++i;
            }
            String au = entrada.nextLine();
            String autor = m.get(Integer.valueOf(au));
            List<String> titols = cd.llistarTitolsdAutors(autor);
            for (String t : titols) System.out.println(t);
        } else System.out.println("No hi ha cap document, crea'n un abans!");
    }

    public void tLlistarAutorsPrefix() {
        if (nDocuments > 0) {
            System.out.println("Introdueix un prefix per fer la cerca dels autors:");
            String prefix = entrada.nextLine();
            List<String> autors = cd.llistarAutorsPrefix();
            if (autors.size() == 0) System.out.println("No hi ha autors amb el prefix " + prefix + ".");
            else for (String a : autors) System.out.println(a);
        } else System.out.println("No hi ha cap autor a llistar, crea un document abans!");
    }

    public void tLlistarKDocumentsS() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
            TreeMap<int, Pair<String, String>> m = escriuClaus();
            String docSel = entrada.nextLine();
            System.out.println("Introdueix el nombre de documents que vols llistar:");
            String k = entrada.nextLine();
            List<String> docs = cd.llistarKDocumentsS();
            if (docs == null) System.out.println("K no es un nombre natural.");
            else if (docs.size() == 0) System.out.println("No hi ha documents semblants al document seleccionat.");
            else {
                System.out.println("Els documents semblants al document seleccionat son:");
                for (Pair<String,String> d : docs) System.out.println(d.y + ' ' + d.x);
            }
        } else System.out.println("No hi ha cap document, crea'n un abans!");
    }

    public void tCercarExpressioBooleana() {
        if (nDocuments > 0) {
            String q;
            String exp;
            if (nExps > 0) {
                System.out.println("Vols fer la cerca amb una expressio booleana guardada o nova?");
                System.out.println("1. Expressio booleana guardada");
                System.out.println("2. Expressio booleana nova");
                q = entrada.nextLine();
                if (q.equals("1")) {
                    System.out.println("Selecciona una expressio booleana guardada:");
                    TreeMap<int, Pair<String, String>> m = escriuExps();
                    String e = entrada.nextLine();
                    exp = m.get(Integer.valueOf(e)).y;
                }
            }
            if (nExps < 1 || q.equals("2")) {
                System.out.println("Escriu una expressio booleana:");
                exp = entrada.nextLine();
            }
            List<Pair<String, String>> docs = cd.cercarExpressioBooleana(exp);
            if (docs == null) System.out.println("L'expressio booleana no es correcta.");
            else if (docs.size() == 0) System.out.println("No s'ha trobat cap document que compleixi l'expressio booleana.");
            else {
                System.out.println("Els documents que compleixen l'expressio booleana son:");
                for (Pair<String,String> d : docs) System.out.println(d.y + ' ' + d.x);
            }
        } else System.out.println("No hi ha cap document, crea'n un abans!");
    }

    public void tGetAllExpressionsBooleanes() {
        if (nExps > 0) {
            System.out.println("A continuacio tenim totes les expressions booleanes guardades:");
            List<Pair<String, String>> l = cd.getAllExpressionsBooleanes();
            for (Pair<String, String> eb : l)
                System.out.println(Integer.toString(i) + ". Nom: "ta.x + " Expressio: " + ta.y);
        } System.out.println("No hi ha expressions booleanes guardades, crea'n una abans!");
    }

    public void tSetExpressioBooleana() {
        System.out.println("Escriu l'expressio booleana que vols guardar:");
        String exp = entrada.nextLine();

        System.out.println("Escriu el nom amb el que vols guardar l'expressio booleana:");
        String nom = entrada.nextLine();
        System.out.println("Has introduit el nom " + nom + "i l'expressio booleana " + exp + ".");
        if (cd.setExpressioBooleana(nom, exp)) {
            ++nExps;
            System.out.println("L'expressio booleana s'ha guardat correctament.");
        }
        else System.out.println("Existeix una expressio booleana guardada amb el nom " + nom  + ".");
    }

    public void tModExpressioBooleana() {
        if (nExps > 0) {
            System.out.println("Selecciona una expressio booleana guardada:");
            TreeMap<int, Pair<String, String>> m = escriuExps();
            String e = entrada.nextLine();
            System.out.println("Escriu la nova expressio booleana per a la seleccionada:");
            String nExp = entrada.nextLine();
            cd.setExpressioBooleana(m.get(Integer.valueOf(e)).x, nExp);
            System.out.println("L'expressio booleana s'ha modificat correctament.");
        } else System.out.println("No hi ha expressions booleanes guardades, crea'n una abans!");
    }

    public void tDeleteExpressioBooleana() {
        if (nExps > 0) {
            System.out.println("Selecciona una expressio booleana per esborrar-la:");
            TreeMap<int, Pair<String, String>> m = escriuExps();
            String e = entrada.nextLine();
            System.out.println("Segur que vols esborrar l'expressio booleana seleccionada?");
            System.out.println("0. Cancel·lar");
            System.out.println("1. Esborrar");
            String ec = entrada.nextLine();
            if (ec == "1") {
                cd.deleteExpressioBooleana(m.get(Integer.valueOf(e)).x);
                System.out.println("L'expressio booleana s'ha esborrat correctament.");
                --nExps;
            } else System.out.println("Has cancel·lat l'operacio.");
        } else System.out.println("No hi ha expressions booleanes guardades, crea'n una abans!");
    }

    public static void main (String [] args) {
        DriverCtrlDomini dcd = new DriverCtrlDomini();
        System.out.println("Donem")
        opcions_disponibles();
        String ent = dcd.entrada.nextLine();
        while (!ent.equals("0")) { // Si posem 0, surt
            switch (ent) {
                case "1": { // Constructora de CtrlDomini la 1a vegada / Constructora de document i obre el document
                    if (cd == null) dcd.tCrearCtrlDomini();
                    dcd.tCrearDocument();
                    break;
                }
                // Si no es crea cap document, la resta d'operacions no funcionen, excepte les de crear, eliminar, consultar i modificar expressions booleanes.
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
                case "10": { // Llista els titols d'un autor existent escollit per l'usuari
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
                case "14": { // Getter de totes les expressions booleanes, si no hi ha cap expressio booleana, no funciona.
                    dcd.tGetAllExpressionsBooleanes();
                    break;
                }
                case "15": { // Setter d'expressio booleana
                    dcd.tSetExpressioBooleana();
                    break;
                }
                case "16"; { // Modificadora d'expressio booleana, si no hi ha cap expressio booleana, no funciona.
                    dcd.tModExpressioBooleana();
                    break;
                }
                case "17"; { // Destructora d'expressio booleana, si no hi ha cap expressio booleana, no funciona.
                    dcd.tDeleteExpressioBooleana();
                    break;
                }
                default:
                    break;
            }
            opcions_disponibles();
            ent = dcd.entrada.nextLine();
        }
        dcd.entrada.close();
    }

    private static void opcions_disponibles() {
        System.out.println("Benvingut al driver de ctrlDomini, per provar un metode ingressa per teclat el seu numero");
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
        System.out.println("15-  getAllExpressionsBooleanes");
        System.out.println("16-  Crea expressio booleana");
        System.out.println("17-  Modififica expressio booleana");
        System.out.println("18-  Elimina expressio booleana");
        System.out.println("0-  cancel·lar");
        System.out.println("Introdueix un numero: ");
    }
}