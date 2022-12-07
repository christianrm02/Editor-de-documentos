package persistencia;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GestorExpBooleanes {
    public byte[] CarregarExpB() {
        try {
            FileInputStream fileInputStream = new FileInputStream("./appdata/expressions/expB");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            byte[] info = (byte[]) objectInputStream.readObject();
            objectInputStream.close();
            return info;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Si no troba el fitxer simplement retorna null
        return null;
    }

    public void GuardarExpB(byte[] info) {
        try {
            //System.out.println("Working Directory = " + System.getProperty("user.dir"));
            Files.createDirectories(Paths.get("./appdata/expressions/"));
            FileOutputStream fileOutputStream = new FileOutputStream("./appdata/expressions/expB");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(info);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
