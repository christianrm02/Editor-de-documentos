package drivers;

import controladores.*;
import java.util.*;
import transversal.*;
import static java.lang.Math.min;

/**
 * DriverCtrlDomini: Driver per provar totes les funcionalitats disponibles des de CtrlDomini.
 * @author Marc Roman
 */
public class DriverCtrlDomini {
    CtrlDomini cd;
    Scanner entrada;
    Boolean dObert;
    int nDocuments, nExps;
    String titolO, autorO;

    DriverCtrlDomini() {
        cd = new CtrlDomini();
        entrada = new Scanner(System.in);
        dObert = false;
        nDocuments = 0;
        nExps = 0;
    }

    public void tCrearDocument() {
        System.out.println("Escriu el titol del document que vols crear: ");
        String titol = entrada.nextLine();

        System.out.println("Escriu l'autor del document que vols crear: ");
        String autor = entrada.nextLine();
        System.out.println("Has introduit el titol " + titol + " i l'autor " + autor + ".");
        if (cd.crearDocument(autor, titol)) {
            ++nDocuments;
            dObert = true;
            titolO = titol; autorO = autor;
            System.out.println("El document ha estat creat i obert correctament.");
        }
        else System.out.println("El document " + titol + " + " + autor + " existeix");
    }

    public TreeMap<Integer, Pair<String, String>> escriuClaus() {
        List<Pair<String, String>> titaut = cd.getTitolsAutors();
        TreeMap<Integer, Pair<String, String>> m = new TreeMap<>();
        int i = 1;
        for (Pair<String, String> ta : titaut) {
            m.put(i, ta);
            System.out.println(i + ". Titol: " + ta.y + " Autor: " + ta.x);
            ++i;
        }
        return m;
    }

    public TreeMap<Integer, Pair<String, String>> escriuExps() {
        List<Pair<String, String>> l = cd.getAllExpressionsBooleanes();
        TreeMap<Integer, Pair<String, String>> m = new TreeMap<>();
        int i = 1;
        for (Pair<String, String> eb : l) {
            m.put(i, eb);
            System.out.println(i + ". Nom: " + eb.x + " Expressio: " + eb.y);
            ++i;
        }
        return m;
    }

    public void tEsborrarDocuments() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els documents existents, com a molt pots seleccionar-ne 10:");
            TreeMap<Integer, Pair<String, String>> m = escriuClaus();
            System.out.println("Posa els numeros dels documents. Si poses 0, es considera que no vols esborrar mes documents.");
            List<Pair<String, String>> docs = new ArrayList<>();
            String ent = entrada.nextLine();
            for (int i = 0; i < min(10, nDocuments) && !ent.equals("0"); ++i) {
                docs.add(m.get(Integer.valueOf(ent)));
            }
            if (docs.size() == 0) System.out.println("No has seleccionat cap document a esborrar.");
            else {
                System.out.println("Segur que vols esborrar els documents seleccionats?");
                System.out.println("0. Cancel·lar");
                System.out.println("1. Esborrar");
                String ec = entrada.nextLine();
                if (ec.equals("1")) {
                    for (Pair<String, String> d : docs)
                        if (d.x.equals(autorO) && d.y.equals(titolO)) {
                            dObert = false;
                            break;
                        }
                    cd.esborrarDocuments(docs);
                    System.out.println("Els documents s'han esborrat correctament.");
                    nDocuments -= docs.size();
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
            TreeMap<Integer, Pair<String, String>> m = escriuClaus();
            String docSel = entrada.nextLine();
            Pair<String, String> p = m.get(Integer.valueOf(docSel));
            cd.obrirDocument(p.x, p.y);
            System.out.println("El document " + p.x + " " + p.y + " s'ha obert.");
            dObert = true; autorO = p.x; titolO = p.y;
        } else System.out.println("No hi ha documents a obrir, crea'n un abans!");
    }

    public void tModificarTitol() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
            TreeMap<Integer, Pair<String, String>> m = escriuClaus();
            String docSel = entrada.nextLine();
            System.out.println("Introdueix el nou titol:");
            String newT = entrada.nextLine();
            Pair<String, String> p = m.get(Integer.valueOf(docSel));
            if (cd.modificarTitol(p.x, p.y, newT)) System.out.println("El titol ha estat modificat correctament.");
            else System.out.println("El document " + newT + " + " + p.x + " existeix.");
        } else System.out.println("No hi ha documents a modificar, crea'n un abans!");
    }

    public void tModificarAutor() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
            TreeMap<Integer, Pair<String, String>> m = escriuClaus();
            String docSel = entrada.nextLine();
            System.out.println("Introdueix el nou autor:");
            String newA = entrada.nextLine();
            Pair<String, String> p = m.get(Integer.valueOf(docSel));
            if (cd.modificarAutor(p.x, p.y, newA)) System.out.println("L'autor ha estat modificat correctament.");
            else System.out.println("El document " + p.y + " + " + newA + " existeix.");
        } else System.out.println("No hi ha documents a modificar, crea'n un abans!");
    }

    public void tModificarContingut() { // Quan posem un contingut nou, si fem diversos salts de linia seguits o espais després de la ultima paraula de la linia, no es guarden be per les limitacions del .nextline()
        if (dObert && nDocuments > 0) {
            System.out.println("Introdueix el nou contingut, quan ja l'hagis acabat, escriu '!fi' a una ultima linia:");
            String cont = "";
            String ent = entrada.nextLine();
            while (!ent.equals("!fi")) {
                cont += ent + '\n';
                ent = entrada.nextLine();
            }
            cd.modificarContingut(autorO, titolO, cont);
        } else if (nDocuments < 1) System.out.println("No hi ha cap document, crea'n un abans!");
        else System.out.println("No hi ha cap document obert, obre'n un abans!");
    }

    public void tLlistarTitolsdAutors() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els autors existents, selecciona'n un:");
            List<String> aut = cd.getAutors();
            TreeMap<Integer, String> m = new TreeMap<>();
            int i = 1;
            for (String a : aut) {
                m.put(i, a);
                System.out.println(i + ". " + a);
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
            List<String> autors = cd.llistarAutorsPrefix(prefix);
            if (autors.size() == 0) System.out.println("No hi ha autors amb el prefix " + prefix + ".");
            else for (String a : autors) System.out.println(a);
        } else System.out.println("No hi ha cap autor a llistar, crea un document abans!");
    }

    public void tMostrarDocument() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
            TreeMap<Integer, Pair<String, String>> m = escriuClaus();
            String docSel = entrada.nextLine();
            Pair<String, String> p = m.get(Integer.valueOf(docSel));
            List<String> cont = cd.getContingut(p.x, p.y);
            for (String c : cont) {
                if (c.charAt(c.length()-1) != '\n') System.out.print(c + ' ');
                else System.out.print(c);
            }
            System.out.print("\n");
        } else System.out.println("No hi ha cap document, crea'n un abans!");
    }

    public void tLlistarKDocumentsS() {
        if (nDocuments > 0) {
            System.out.println("A continuacio sortiran els documents existents, selecciona'n un:");
            TreeMap<Integer, Pair<String, String>> m = escriuClaus();
            String docSel = entrada.nextLine();
            System.out.println("Introdueix el nombre de documents que vols llistar:");
            String k = entrada.nextLine();
            Pair<String, String> doc = m.get(Integer.valueOf(docSel));
            List<Pair<String, String>> docs = cd.llistarKDocumentsS(doc.x, doc.y, Integer.parseInt(k));
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
            String exp;
            if (nExps > 0) {
                System.out.println("Vols fer la cerca amb una expressio booleana guardada o nova?");
                System.out.println("1. Expressio booleana guardada");
                System.out.println("2. Expressio booleana nova");
                String q = entrada.nextLine();
                if (q.equals("1")) {
                    System.out.println("Selecciona una expressio booleana guardada:");
                    TreeMap<Integer, Pair<String, String>> m = escriuExps();
                    String e = entrada.nextLine();
                    exp = m.get(Integer.valueOf(e)).y;
                }
                else {
                    System.out.println("Escriu una expressio booleana:");
                    exp = entrada.nextLine();
                }
            }
            else {
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
                System.out.println("Nom: " + eb.x + " Expressio: " + eb.y);
        } else System.out.println("No hi ha expressions booleanes guardades, crea'n una abans!");
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
            TreeMap<Integer, Pair<String, String>> m = escriuExps();
            String e = entrada.nextLine();
            System.out.println("Escriu la nova expressio booleana per a la seleccionada:");
            String nExp = entrada.nextLine();
            cd.modExpressioBooleana(m.get(Integer.valueOf(e)).x, nExp);
            System.out.println("L'expressio booleana s'ha modificat correctament.");
        } else System.out.println("No hi ha expressions booleanes guardades, crea'n una abans!");
    }

    public void tDeleteExpressioBooleana() {
        if (nExps > 0) {
            System.out.println("Selecciona una expressio booleana per esborrar-la:");
            TreeMap<Integer, Pair<String, String>> m = escriuExps();
            String e = entrada.nextLine();
            System.out.println("Segur que vols esborrar l'expressio booleana seleccionada?");
            System.out.println("0. Cancel·lar");
            System.out.println("1. Esborrar");
            String ec = entrada.nextLine();
            if (ec.equals("1")) {
                cd.deleteExpressioBooleana(m.get(Integer.valueOf(e)).x);
                System.out.println("L'expressio booleana s'ha esborrat correctament.");
                --nExps;
            } else System.out.println("Has cancel·lat l'operacio.");
        } else System.out.println("No hi ha expressions booleanes guardades, crea'n una abans!");
    }

    public static void main (String [] args) {
        DriverCtrlDomini dcd = new DriverCtrlDomini();
        System.out.println("Benvingut al driver de ctrlDomini. Aquest driver esta pensat per utilitzar-lo correctament.");
        opcions_disponibles();
        String ent = dcd.entrada.nextLine();
        while (!ent.equals("0")) { // Si posem 0, surt
            switch (ent) {
                case "1": { // Constructora de CtrlDomini la 1a vegada / Constructora de document i obre el document
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
                case "12": { // Retorna el contingut del document indicat i el mostra
                    dcd.tMostrarDocument();
                    break;
                }
                case "13": { // Llista els K documents mes semblants a l'indicat
                    dcd.tLlistarKDocumentsS();
                    break;
                }
                case "14": { // Cerca segons una expressio booleana
                    dcd.tCercarExpressioBooleana();
                    break;
                }
                case "15": { // Getter de totes les expressions booleanes, si no hi ha cap expressio booleana, no funciona.
                    dcd.tGetAllExpressionsBooleanes();
                    break;
                }
                case "16": { // Setter d'expressio booleana
                    dcd.tSetExpressioBooleana();
                    break;
                }
                case "17": { // Modificadora d'expressio booleana, si no hi ha cap expressio booleana, no funciona.
                    dcd.tModExpressioBooleana();
                    break;
                }
                case "18": { // Destructora d'expressio booleana, si no hi ha cap expressio booleana, no funciona.
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
        System.out.println("Per provar un metode ingressa per teclat el seu numero");
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
        System.out.println("10- Llistar titols d'un autor");
        System.out.println("11- Llistar autors per prefix");
        System.out.println("12- Mostrar document");
        System.out.println("13- Llistar K documents semblants a un document D");
        System.out.println("14- Cerca amb una expressio booleana");
        System.out.println("15- getAllExpressionsBooleanes");
        System.out.println("16- Crea expressio booleana");
        System.out.println("17- Modififica expressio booleana");
        System.out.println("18- Elimina expressio booleana");
        System.out.println("0-  Cancel·lar");
        System.out.println("Introdueix un numero:");
    }
}