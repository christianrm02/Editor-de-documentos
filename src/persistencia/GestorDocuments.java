package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class GestorDocuments {
    
    //Retorna autor, titol i contingut del document carregat i desa el contingut a l'espai de disc del programa
    public String[] CarregaDocument(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String autor = reader.readLine();
            String titol = reader.readLine();
            String line = reader.readLine();
            String contingut = new String();
            while(line != null) {
                contingut.concat(line);
                line = reader.readLine();
            }
            reader.close();

            DesaDocument(autor, titol, contingut);
            return new String[]{autor, titol, contingut};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ExportaDocument(String autor, String titol, String path) {
        try {
            String dirPath = "./appdata/docs/";
            Files.createDirectories(Paths.get(dirPath));
            String fileName = Integer.toString(Objects.hash(autor, titol));
            //Obtenim el fitxer de disc local
            FileInputStream fileInputStream = new FileInputStream(dirPath.concat(fileName));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            String contingut = (String) objectInputStream.readObject();
            objectInputStream.close();

            //Exportem el fitxer
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(autor);
            bufferedWriter.newLine();
            bufferedWriter.write(titol);
            bufferedWriter.newLine();
            bufferedWriter.write(contingut);

            bufferedWriter.close();
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String ObrirDocument(String autor, String titol) {
        try {
            String dirPath = "./appdata/docs/";
            Files.createDirectories(Paths.get(dirPath));
            String fileName = Integer.toString(Objects.hash(autor, titol));
            FileInputStream fileInputStream = new FileInputStream(dirPath.concat(fileName));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            String contingut = (String) objectInputStream.readObject();
            objectInputStream.close();
            return contingut;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Si no troba el fitxer simplement retorna null
        return null;
    }

    public void DesaDocument(String autor, String titol, String contingut) {
        try {
            String dirPath = "./appdata/docs/";
            Files.createDirectories(Paths.get(dirPath));
            String fileName = Integer.toString(Objects.hash(autor, titol));
            FileOutputStream fileOutputStream = new FileOutputStream(dirPath.concat(fileName));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(contingut);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
