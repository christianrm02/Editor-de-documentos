package persistencia;

import transversal.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GestorExpBooleanes {
    public static List<Pair<String, String>> CarregarExpB() throws IOException {
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
            return exps;
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

   public static void GuardarExpB(List<Pair<String, String>> exps) throws IOException {
        String dirPath = "./appdata/expressions/";
        Files.createDirectories(Paths.get(dirPath));
        for (int i = 0; i < exps.size(); ++i) {
            FileOutputStream fileOutputStream = new FileOutputStream(dirPath.concat(exps.get(i).x));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(exps.get(i).y);
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

