package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GestorIndexs {
    
    public byte[] ImportarIndexs() {
        try {
            FileInputStream fileInputStream = new FileInputStream("./appdata/indexs/index");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            byte[] info = (byte[]) objectInputStream.readObject();
            objectInputStream.close();
            return info;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ExportarIndexs(byte[] info) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./appdata/indexs/index");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(info);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
