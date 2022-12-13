package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GestorIndexs {
    
    public byte[] ImportarIndexs() throws IOException {
        try {
            //System.out.println("Working Directory = " + System.getProperty("user.dir"));
            FileInputStream fileInputStream = new FileInputStream("./appdata/indexs.idx");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            byte[] info = (byte[]) objectInputStream.readObject();
            objectInputStream.close();
            return info;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Si no troba el fitxer simplement retorna null
        return null;
    }

    public void ExportarIndexs(byte[] info) throws IOException {
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Files.createDirectories(Paths.get("./appdata/indexs/"));
        FileOutputStream fileOutputStream = new FileOutputStream("./appdata/indexs.idx");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(info);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
}
