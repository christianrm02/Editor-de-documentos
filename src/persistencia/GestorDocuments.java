package persistencia;

import excepcions.DeleteDocumentException;
import excepcions.FormatInvalid;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * GestorDocuments: Funcions estàtiques per gestionar l'emmagatzematge dels documents a disc
 * @author Èric Ryhr
 */
public class GestorDocuments {
    
    /** 
     * Mètode per importar un document de disc i desar-lo a la carpeta del sistema
     * @param path Ruta del document a importar a disc
     * @return Array que conté l'autor, el títol i el contingut del document en aquest ordre
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     * @throws FormatInvalid L'extensió del fitxer importat no era correcta
     */
    public static String[] ImportaDocument(String path) throws IOException, FormatInvalid {
        String[] doc = new String[3];
        String format = getFormat(path);

        if(format.equals("txt") || format.equals("TXT")) {
            doc = loadTXT(path);
        } else if(format.equals("xml") || format.equals("XML")) {
            doc = loadXML(path);
        } else throw new FormatInvalid();
        //Desem el contingut a disc local
        DesaContingut(doc[0], doc[1], doc[2]);
        return doc;
    }

    
    /** 
     * Mètode per exportar un document del sistema a disc a la localització del path
     * @param autor Autor del document a desar
     * @param titol Títol del document a desar
     * @param path Localització on desar el document
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     * @throws FormatInvalid L'extensió del fitxer importat no era correcta
     */
    public static void ExportaDocument(String autor, String titol, String path) throws IOException, FormatInvalid {
        //Obtenim el contingut de disc local
        String contingut = GetContingut(autor, titol);
        if(contingut == null) return;

        String format = getFormat(path);
        
        //Exportem el fitxer
        if(format.equals("txt")) writeTXT(autor, titol, contingut, path);
        else if(format.equals("xml")) writeXML(autor, titol, contingut, path);
        else throw new FormatInvalid();
    }

    
    /** 
     * Mètode per obtenir el contingut d'un document desat a la carpeta del sistema
     * @param autor Autor del document
     * @param titol Autor del document
     * @return Contingut del document
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     */
    public static String GetContingut(String autor, String titol) throws IOException {
        try {
            String dirPath = "./appdata/docs/";
            Files.createDirectories(Paths.get(dirPath));
            String fileName = Integer.toString(Objects.hash(autor, titol));
            FileInputStream fileInputStream = new FileInputStream(dirPath.concat(fileName).concat(".prop"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            String contingut = (String) objectInputStream.readObject();
            objectInputStream.close();
            return contingut;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Si no troba el fitxer simplement retorna null
        return null;
    }

    
    /** 
     * Mètode per desar el contingut d'un document a la carpeta del sistema
     * @param autor Autor del document
     * @param titol Títol del document
     * @param contingut Contingut del document
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     */
    public static void DesaContingut(String autor, String titol, String contingut) throws IOException {
        String dirPath = "./appdata/docs/";
        Files.createDirectories(Paths.get(dirPath));
        String fileName = Integer.toString(Objects.hash(autor, titol));
        FileOutputStream fileOutputStream = new FileOutputStream(dirPath.concat(fileName).concat(".prop"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(contingut);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    
    /** 
     * Mètode per esborrar un document de la carpeta del sistema
     * @param autor Autor del document
     * @param titol Títol del document
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     * @throws DeleteDocumentException Hi h ahagut algun problema al esborrar el document
     */
    public static void EsborrarDoc(String autor, String titol) throws IOException, DeleteDocumentException {
        String dirPath = "./appdata/docs/";
        Files.createDirectories(Paths.get(dirPath));
        String fileName = Integer.toString(Objects.hash(autor, titol));
        File fileToDelete = new File(dirPath.concat(fileName).concat(".prop"));
        if(!fileToDelete.delete()) throw new DeleteDocumentException();
    }

    
    /** 
     * Mètode per actualitzar l'autor d'un document de la carpeta del sistema
     * @param autor Autor del document
     * @param titol Títol del document
     * @param newAutor Nou autor del document
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     */
    public static void ActualitzarAutor(String autor, String titol, String newAutor) throws IOException {
        String dirPath = "./appdata/docs/";
        Files.createDirectories(Paths.get(dirPath));
        String oldFileName = Integer.toString(Objects.hash(autor, titol));
        String newFileName = Integer.toString(Objects.hash(newAutor, titol));
        Path oldPath = Paths.get(dirPath.concat(oldFileName).concat(".prop"));
        Path newPath = Paths.get(dirPath.concat(newFileName).concat(".prop"));

        Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
    }

    
    /** 
     * Mètode per actualitzar el títol d'un document de la carpeta del sistema
     * @param autor Autor del document
     * @param titol Títol del document
     * @param newTitol Nou títol del document
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     */
    public static void ActualitzarTitol(String autor, String titol, String newTitol) throws IOException {
        String dirPath = "./appdata/docs/";
        Files.createDirectories(Paths.get(dirPath));
        String oldFileName = Integer.toString(Objects.hash(autor, titol));
        String newFileName = Integer.toString(Objects.hash(autor, newTitol));
        Path oldPath = Paths.get(dirPath.concat(oldFileName).concat(".prop"));
        Path newPath = Paths.get(dirPath.concat(newFileName).concat(".prop"));

        Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
    }

    
    /** 
     * Mètode per importar un document en format TXT de la localització indicada
     * @param path Ruta del document a importar a disc
     * @return Array que conté l'autor, el títol i el contingut del document en aquest ordre
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     */
    private static String[] loadTXT(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String autor = reader.readLine();
        String titol = reader.readLine();
        if(autor == null || titol == null) {
            reader.close();
            throw new IOException();
        }
        String line = reader.readLine();
        StringBuilder aux = new StringBuilder();
        while(line != null) {
            aux.append(line);
            line = reader.readLine();
            if(line != null) aux.append('\n');
        }
        reader.close();
        String contingut = aux.toString();
        return new String[]{autor, titol, contingut};
    }

    
    /** 
     * Mètode per exportar un document en format TXT
     * @param autor Autor del document a desar
     * @param titol Títol del document a desar
     * @param contingut Contingut del document
     * @param path Localització on desar el document
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     */
    private static void writeTXT(String autor, String titol, String contingut, String path) throws IOException {
        //Exportem el fitxer
        FileOutputStream fos = new FileOutputStream(path);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(autor);
        bw.newLine();
        bw.write(titol);
        bw.newLine();
        bw.write(contingut);
        bw.close();
    }

    
    /** 
     * Mètode per importar un document en format XML de la localització indicada
     * @param path Ruta del document a importar a disc
     * @return Array que conté l'autor, el títol i el contingut del document en aquest ordre
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     */
    private static String[] loadXML(String path) throws IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Element root = db.parse(new File(path)).getDocumentElement();
            Node autorNode = root.getElementsByTagName("autor").item(0);
            Node titolNode = root.getElementsByTagName("titol").item(0);
            String autor = autorNode.getTextContent();
            String titol = titolNode.getTextContent();
            String contingut = root.getElementsByTagName("contingut").item(0).getTextContent();
            return new String[]{autor, titol, contingut};
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    
    /** 
     * Mètode per exportar un document en format XML
     * @param autor Autor del document a desar
     * @param titol Títol del document a desar
     * @param contingut Contingut del document
     * @param path Localització on desar el document
     * @throws IOException Hi ha hagut algun problema al accedir a disc
     */
    private static void writeXML(String autor, String titol, String contingut, String path) throws IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element root = doc.createElement("document");
            doc.appendChild(root);

            Element autorE = doc.createElement("autor");
            root.appendChild(autorE);
            autorE.setTextContent(autor);

            Element titolE = doc.createElement("titol");
            root.appendChild(titolE);
            titolE.setTextContent(titol);

            Element contingutE = doc.createElement("contingut");
            root.appendChild(contingutE);
            contingutE.setTextContent(contingut);

            FileOutputStream fos = new FileOutputStream(path);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult res = new StreamResult(fos);

            t.transform(source, res);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Mètode per obtenir el format del fitxer al que apunta un path
     * @param path Ruta del fitxer
     * @return Extensió del fitxer
     */
    private static String getFormat(String path) {
        int index = path.lastIndexOf('.');
        if(index > 0) {
            return path.substring(index + 1);
        }
        return null;
    }
}
