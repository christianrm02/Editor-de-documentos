package persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * GestorIndexs: Funcions estàtiques per desar els índexs del sistema a disc
 * @author Èric Ryhr
 */
public class GestorIndexs {
    
    /** 
     * Mètode per importar els indexs de disc
     * @return byte[] - Import en forma d'array de bytes
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     */
    public static byte[] ImportarIndexs() throws IOException {
        try {
            //System.out.println("Working Directory = " + System.getProperty("user.dir"));
            FileInputStream fileInputStream = new FileInputStream("./appdata/indexs.idx");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            byte[] info = (byte[]) objectInputStream.readObject();
            objectInputStream.close();
            return info;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            //Si no troba el fitxer simplement retorna null
            return null;
        }
        return null;
    }

    
    /** 
     * Mètode per exportar els indexs a disc
     * @param info - Indexs en forma d'array de bytes
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     */
    public static void ExportarIndexs(byte[] info) throws IOException {
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Files.createDirectories(Paths.get("./appdata/"));
        FileOutputStream fileOutputStream = new FileOutputStream("./appdata/indexs.idx");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(info);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
}
