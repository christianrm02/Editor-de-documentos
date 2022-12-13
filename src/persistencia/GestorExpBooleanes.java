package persistencia;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GestorExpBooleanes {
    public String[] CarregarExpB(String path) throws IOException {
        String nom = path.substring(path.lastIndexOf("/")+1);
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String exp = reader.readLine();
        reader.close();
        return new String[]{nom, exp};
    }

    public void GuardarExpB(String nom, String exp) throws IOException {
        String dirPath = "./appdata/expressions/";
        Files.createDirectories(Paths.get(dirPath));
        FileOutputStream fileOutputStream = new FileOutputStream(dirPath.concat(nom));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(exp);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
}
