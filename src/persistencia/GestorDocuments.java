package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import transversal.FileFormat;

public class GestorDocuments {
    
    //Retorna autor, titol i contingut del document carregat i desa el contingut a l'espai de disc del programa
    public String[] ImportaDocument(String path) throws IOException {
        String[] doc = new String[3];
        String format = getFormat(path);

        if(format == "txt") doc = loadTXT(path);
        else if(format == "xml") doc = loadXML(path);

        //Desem el contingut a disc local
        DesaContingut(doc[0], doc[1], doc[2]);
        return doc;
    }

    public void ExportaDocument(String autor, String titol, String path, FileFormat f) throws IOException {
        //Obtenim el contingut de disc local
        String contingut = GetContingut(autor, titol);
        if(contingut == null) return;
        
        //Exportem el fitxer
        if(f == FileFormat.txt) writeTXT(autor, titol, contingut, path);
        else if(f == FileFormat.xml) writeXML(autor, titol, contingut, path);
    }

    public String GetContingut(String autor, String titol) throws IOException {
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

    public void DesaContingut(String autor, String titol, String contingut) throws IOException {
        String dirPath = "./appdata/docs/";
        Files.createDirectories(Paths.get(dirPath));
        String fileName = Integer.toString(Objects.hash(autor, titol));
        FileOutputStream fileOutputStream = new FileOutputStream(dirPath.concat(fileName).concat(".prop"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(contingut);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    private String[] loadTXT(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String autor = reader.readLine();
        String titol = reader.readLine();
        String line = reader.readLine();
        StringBuilder aux = new StringBuilder();
        while(line != null) {
            aux.append(line);
            aux.append('\n');
            line = reader.readLine();
        }
        reader.close();
        String contingut = aux.toString();
        return new String[]{autor, titol, contingut};
    }

    private void writeTXT(String autor, String titol, String contingut, String path) throws IOException {
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

    private String[] loadXML(String path) throws IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Element root = db.parse(new File(path)).getDocumentElement();
            String autor = root.getElementsByTagName("autor").item(0).getTextContent();
            String titol = root.getElementsByTagName("titol").item(0).getTextContent();
            String contingut = root.getElementsByTagName("contingut").item(0).getTextContent();
            return new String[]{autor, titol, contingut};
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void writeXML(String autor, String titol, String contingut, String path) throws IOException {
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

    private String getFormat(String path) {
        String[] folders = path.split("\\");
        String[] file = folders[folders.length-1].split(".");
        String format = file[file.length-1];
        return format;
    }
}
