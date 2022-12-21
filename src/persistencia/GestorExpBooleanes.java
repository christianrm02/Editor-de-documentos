package persistencia;

import transversal.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * GestorIndexs: Classe amb funcions estàtiques per gestionar l’emmagatzematge i la càrrega d’expressions booleanes al disc.
 * @author Pol Fradera
 */
public class GestorExpBooleanes {

    /**
     * Mètode per càrregar les expressions booleanes de disc.
     * @return List<Pair<String, String>>: Es retornen totes les expressions booleanes existents al disc (per cada una, el seu nom i l'expressió).
     * @exception IOException Hi ha hagut algun problema en accedir al disc.
     */
    public static List<Pair<String, String>> CarregarExpB() throws IOException {
        try {
            List<Pair<String, String>> exps = new ArrayList<>();
            String dirPath = "./appdata/expressions/";
            File carpeta = new File(dirPath);
            if (carpeta.exists()) {
                File[] llistaFitxers = carpeta.listFiles();
                if (llistaFitxers != null) {
                    for (File fitxer : llistaFitxers) {
                        String nom = fitxer.getName();
                        FileInputStream fileInputStream = new FileInputStream(dirPath.concat(nom));
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                        String exp = (String) objectInputStream.readObject();
                        objectInputStream.close();
                        Pair<String, String> p = new Pair<>();
                        p.x = nom;
                        p.y = exp;
                        exps.add(p);
                        fitxer.delete();
                    }
                }
            }
            return exps;
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mètode per guardar les expressions booleanes a disc.
     * @param exps: List<Pair<String, String>>: Totes les expressions booleanes que es volen guardar a disc.
     * @exception IOException Hi ha hagut algun problema en accedir al disc.
     */
   public static void GuardarExpB(List<Pair<String, String>> exps) throws IOException {
        String dirPath = "./appdata/expressions/";
        Files.createDirectories(Paths.get(dirPath));
       for (Pair<String, String> exp : exps) {
           FileOutputStream fileOutputStream = new FileOutputStream(dirPath.concat(exp.x));
           ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
           objectOutputStream.writeObject(exp.y);
           objectOutputStream.flush();
           objectOutputStream.close();
       }
    }
}
/*    public static void GuardarExpB(List<Pair<String, String>> exps) throws IOException {
        String dirPath = "./appdata/expressions/";
        Files.createDirectories(Paths.get(dirPath));
        for (int i = 0; i < exps.size(); ++i) {
            Files.writeString(Path.of(dirPath.concat(exps.get(i).x)), exps.get(i).y, StandardCharsets.UTF_8);
        }
    }*/

/*    public static List<Pair<String, String>> CarregarExpB() throws IOException {
        List<Pair<String, String>> exps = new ArrayList<>();

        String dirPath = "./appdata/expressions/";
        File carpeta = new File(dirPath);
        File[] llistaFitxers = carpeta.listFiles();
        for (int i = 0; i < llistaFitxers.length; ++i) {
            String nom = llistaFitxers[i].getName();
            BufferedReader reader = new BufferedReader(new FileReader(dirPath.concat(nom)));
            String exp = reader.readLine();
            reader.close();
            Pair<String, String> p = new Pair<>();
            p.x = nom;
            p.y = exp;
            exps.add(p);
        }
        return exps;
    }*/

/*public static List<Pair<String, String>> CarregarExpB() throws IOException {
        try {
            List<Pair<String, String>> exps = new ArrayList<>();

            String dirPath = "./appdata/expressions/";
            File carpeta = new File(dirPath);
            File[] llistaFitxers = carpeta.listFiles();
            for (int i = 0; i < llistaFitxers.length; ++i) {
                String nom = llistaFitxers[i].getName();
                FileInputStream fileInputStream = new FileInputStream(dirPath.concat(nom));
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                String exp = (String) objectInputStream.readObject();
                objectInputStream.close();
                Pair<String, String> p = new Pair<>();
                p.x = nom;
                p.y = exp;
                exps.add(p);
            }
            for (File fitxer: llistaFitxers) {
                fitxer.delete();
            }
            return exps;
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }*/
