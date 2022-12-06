package presentacio;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class ViewEditar extends JFrame {
    private JPanel panel1;
    protected JButton desarButton;
    protected JButton exportarButton;
    private JButton sortirButton;
    private JLabel titol;
    private JLabel autor;
    protected JTextPane textPane1;

    private int desarAbansDeTancar(CtrlPresentacio cp, String t, String a, String contNou, boolean sortir) {
        String frase = "No has desat el document. El vols desar abans de ";
        if (sortir) frase += "tornar a la pantalla d'inici?";
        else frase += "tancar el programa?";
        int opt, opt2 = 1;
        do {
            opt = JOptionPane.showConfirmDialog(null, frase, "Desar document", JOptionPane.YES_NO_OPTION);
            if (opt == 0) cp.modificarContingut(a, t, contNou);
            else if (opt == 1) {
                opt2 = JOptionPane.showConfirmDialog(null, "Estàs segur que no vols desar el document?", "Desar document", JOptionPane.YES_NO_OPTION);
            }
        } while(opt == 1 && opt2 == 1);
        return opt;
    }

    private void setCont(String cont, String contNou) {
        cont = contNou;
    }

    public ViewEditar(CtrlPresentacio cp, String t, String a, String cont) {
        setContentPane(panel1);
        setMinimumSize(new Dimension(400, 200));
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
                    desarAbansDeTancar(cp, a, t, contNou, true);
                }
                //cp.tancarDocument();
            }
        });

        desarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contNou = textPane1.getText();
                if (!cont.equals(contNou)) {
                    cp.modificarContingut(t, a, contNou);
                    setCont(cont, contNou); // cont = contNou, no deixa fer-ho sino
                    JOptionPane.showMessageDialog(null, "El document s'ha desat correctament.");
                }
            }
        });

        exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String doc;
                do {
                    doc = JOptionPane.showInputDialog("Escriu el nom que li vols posar al document:");
                } while (doc.equals(""));
                if (doc != null) {
                    String[] tox = {"txt", "xml"};
                    int opt = JOptionPane.showOptionDialog(null, "Escull el format que vols pel document " + doc + ".", "Escollir format", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tox, tox[0]);
                    if (opt == 0 || opt == 1) {
                        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        chooser.setDialogTitle("Selecciona on vols desar el fitxer.");
                        //chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data/dades"));
                        int returnValue = chooser.showOpenDialog(null);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            String contNou = textPane1.getText();
                            if (!cont.equals(contNou)) cp.modificarContingut(a, t, contNou);
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
            @Override
            public void windowClosing(WindowEvent e) {
                String contNou = textPane1.getText();
                int opt = 0;
                if (!cont.equals(contNou)) opt = desarAbansDeTancar(cp, a, t, contNou, false);
                if (opt == 0 || opt == 1) {
                    //afegir tot el tractament per guardar tot a persistencia
                    System.exit(0);
                }
            }
        });

        titol.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newT;
                do {
                    newT = JOptionPane.showInputDialog("Escriu el nou títol:");
                } while (newT == "");
                if(newT != null) { // diria q no pot passar a no ser q tanquis
                    String au = autor.getText(), ti = titol.getText();
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar el títol del document " + ti + " " + au + " de " + ti + " a " + newT + " ?", "Modificar títol", JOptionPane.YES_NO_OPTION);
                    if (opt == 0) {
                        cp.modificarTitol(au, ti, newT);
                        titol.setText(newT);
                        JOptionPane.showMessageDialog(null, "S'ha modificat el títol a " + newT + ".");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No s'ha modificat el títol.");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No s'ha modificat el títol.");
                }
            }
        });

        autor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newA;
                do {
                    newA = JOptionPane.showInputDialog("Escriu el nou autor:");
                } while (newA == "");
                if(newA != null) {
                    String au = autor.getText(), ti = titol.getText();
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar l'autor del document " + ti + " " + au + " de " + au + " a " + newA + " ?", "Modificar autor", JOptionPane.YES_NO_OPTION);
                    if (opt == 0) {
                        cp.modificarAutor(au, ti, newA);
                        autor.setText(newA);
                        JOptionPane.showMessageDialog(null, "S'ha modificat l'autor a " + newA + ".");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No s'ha modificat l'autor.");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No s'ha modificat l'autor.");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame view = new ViewEditar(new CtrlPresentacio(), "Hola q tal", "Paco", "Hola q tal, com estàs?");
    }
}

