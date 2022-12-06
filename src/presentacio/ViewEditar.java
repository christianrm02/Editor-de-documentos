package presentacio;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class ViewEditar extends JFrame {
    private JPanel panel1;
    protected JButton desarButton;
    protected JButton exportarButton;
    private JButton sortirButton;
    private JLabel titol;
    private JLabel autor;
    protected JTextPane textPane1;

    private int desarAbansDeTancar(CtrlPresentacio cp, String t, String a, String contNou) {
        int opt = JOptionPane.showConfirmDialog(null, "No has desat el document. El vols desar abans de tancar?", "Desar document", JOptionPane.YES_NO_OPTION);
        if (opt == 0) cp.modificarContingut(a, t, contNou);
        return opt;
    }

    public ViewEditar(CtrlPresentacio cp, String t, String a, String cont) {
        setContentPane(panel1);
        setTitle("Editor de textos " + t + ", " + a);
        setSize(1000, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        titol.setText(t);
        autor.setText(a);
        textPane1.setText(cont);

        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contNou = textPane1.getText();
                if (!cont.equals(contNou)) {
                    desarAbansDeTancar(cp, a, t, contNou);
                }
                //cp.tancaDocument();
            }
        });

        desarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contNou = textPane1.getText();
                if (!cont.equals(contNou)) {
                    cp.modificarContingut(t, a, contNou);
                    JOptionPane.showMessageDialog(null, "El document s'ha desat correctament.");
                }
            }
        });

        exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String doc = JOptionPane.showInputDialog("Escriu el nom que li vols posar al document:");
                if (doc != null && !doc.equals("")) {
                    String[] tox = {"txt", "xml"};
                    int opt = JOptionPane.showOptionDialog(null, "Escull el format que vols pel document " + doc + ".", "Escollir format", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tox, tox[0]);
                    if (opt == 0 || opt == 1) {
                        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        chooser.setDialogTitle("Selecciona on vols desar el fitxer.");
                        //chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data/dades"));
                        int returnValue = chooser.showOpenDialog(null);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            File arxiu = chooser.getSelectedFile();

                            //CtrlPresentacio.importaDocument(arxiu.getAbsolutePath());
                        /*byte[] bytes = new byte[0]; //mover esto al ctrl presentacion, a una uncion nueva
                        try {
                            bytes = Files.readAllBytes(Path.of(arxiu.getAbsolutePath()));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        // Reading the file to String List
                        try {
                            @SuppressWarnings("unused")

                            // Creating a List class object of string type
                            // as data in file to be read is words
                            List allLines = new List();
                            allLines = (List) Files.readAllLines(Path.of(arxiu.getAbsolutePath()), StandardCharsets.UTF_8);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        System.out.println(new String(bytes));*/


                            System.out.println(arxiu.getAbsolutePath());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No s'ha exportat el document.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No s'ha exportat el document.");
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                String contNou = textPane1.getText();
                int opt = 0;
                if (!cont.equals(contNou)) opt = desarAbansDeTancar(cp, a, t, contNou);
                if (opt == 0 || opt == 1) {
                    //afegir tot el tractament per guardar tot a persistencia
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame view = new ViewEditar(new CtrlPresentacio(), "Hola q tal", "Paco", "Hola q tal, com estàs?");
    }
}

