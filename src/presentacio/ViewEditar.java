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
    private String cont;

    private int desarAbansDeTancar(String t, String a, String contNou, boolean sortir) {
        String frase = "No has desat el document. El vols desar abans de ";
        if (sortir) frase += "tornar a la pantalla d'inici?";
        else frase += "tancar el programa?";
        int opt, opt2 = 1;
        do {
            opt = JOptionPane.showConfirmDialog(null, frase, "Desar document", JOptionPane.YES_NO_OPTION);
            if (opt == 0) {
                String au = autor.getText(), ti = titol.getText();
                CtrlPresentacio.modificarContingut(au, ti, contNou);
                //CtrlPresentacio.desarContingut();
            }
            else if (opt == 1) {
                opt2 = JOptionPane.showConfirmDialog(null, "Estàs segur que no vols desar el document?", "Desar document", JOptionPane.YES_NO_OPTION);
            }
        } while(opt == 1 && opt2 == 1);
        return opt;
    }

    private void setCont(String cont, String contNou) {
        cont = contNou;
    }

    public ViewEditar(String t, String a, String c) {
        setContentPane(panel1);
        setMinimumSize(new Dimension(400, 200));
        setTitle("Editor de textos " + t + ", " + a);
        setSize(1000, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        titol.setText(t);
        autor.setText(a);
        textPane1.setText(c);
        cont = c;

        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contNou = textPane1.getText();
                if (!cont.equals(contNou)) {
                    String au = autor.getText(), ti = titol.getText();
                    desarAbansDeTancar(au, ti, contNou, true);
                }
                //CtrlPresentacio.tancarDocument();
                CtrlPresentacio.mostraViewPrincipal();
                dispose();
            }
        });

        desarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contNou = textPane1.getText();
                if (!cont.equals(contNou)) {
                    CtrlPresentacio.modificarContingut(t, a, contNou);
                    cont = contNou;
                    JOptionPane.showMessageDialog(null, "El document s'ha desat correctament.");
                }
            }
        });

        exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("Selecciona una carpeta on guardar el document");
                chooser.setMultiSelectionEnabled(false);
                //chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data/dades"));
                int result = chooser.showOpenDialog(null);
                //int result = chooser.showSaveDialog(this);
                if (result == chooser.APPROVE_OPTION) {
                    //System.out.println(chooser.getSelectedFile().getAbsolutePath());
                    JPanel panelExportacio = new JPanel();
                    JTextField newNom = new JTextField("", 20);
                    JPanel insertNom = new JPanel();
                    insertNom.add(new JLabel("Nom de l'arxiu: "));
                    insertNom.add(newNom);
                    JComboBox tipus = new JComboBox();
                    tipus.addItem("");
                    tipus.addItem("txt");
                    tipus.addItem("xml");
                    tipus.setSize(90, 20);
                    JPanel insertTipus = new JPanel();
                    insertTipus.setLayout(new BorderLayout());
                    insertTipus.add(new JLabel("Tria el format: "), BorderLayout.WEST);
                    insertTipus.add(tipus);

                    panelExportacio.setLayout(new BorderLayout());
                    panelExportacio.add(insertNom, BorderLayout.NORTH);
                    panelExportacio.add(insertTipus, BorderLayout.SOUTH);
                    //JPanel insertTitol = new JPanel();
                    //insertTitol.add(new JLabel("Escriu el titol:"));
                    //insertTitol.add(newT);

                    String[] opts = {"Sí", "Cancel·la"};
                    int opt = JOptionPane.showOptionDialog(null, panelExportacio, "Exportació document",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);

                    if (opt == 0 && newNom.getText() != null && !newNom.getText().equals("") && !((String) tipus.getSelectedItem()).equals("")) {
                        String path = chooser.getSelectedFile().getAbsolutePath();
                        boolean totOk = CtrlPresentacio.exportaDocument(t, a, newNom.getText(), path);
                        if (!totOk) {
                            JOptionPane.showMessageDialog(null, "El document no s'ha pogut exportar", "Error exportació", JOptionPane.ERROR_MESSAGE);
                        }
                        //System.out.println(titol + " " + autor + " " + newNom.getText() + " " + chooser.getSelectedFile().getAbsolutePath());
                    } else if (opt == 0 && (newNom.getText().equals("") || ((String) tipus.getSelectedItem()).equals(""))) {
                        JOptionPane.showMessageDialog(null, "Indica un nom i un format vàlids, no deixis camps buits.",
                                "Error exportació", JOptionPane.ERROR_MESSAGE);
                    }
                }
                /*else { //MIRAR SI ESTE ELSE VA BIEN AHÍ
                    JOptionPane.showMessageDialog(null, "Alguna cosa ha sortit malament, torna a intentar-ho.");
                }*/
            }
                /*String doc;
                do {
                    doc = JOptionPane.showInputDialog(null, "Escriu el nom que li vols posar al document:", "Exportar document", -1);
                } while (doc != null && doc.equals(""));
                if (doc != null) {
                    String[] tox = {"txt", "xml", "Cancel"};
                    int opt = JOptionPane.showOptionDialog(null, "Escull el format que vols pel document " + doc + ".", "Escollir format", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, tox, tox[0]);
                    if (opt == 0 || opt == 1) {
                        //JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        chooser.setDialogTitle("Selecciona on vols desar el fitxer.");
                        //chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data/dades"));
                        int returnValue = chooser.showOpenDialog(null);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            String contNou = textPane1.getText();
                            String au = autor.getText(), ti = titol.getText();
                            if (!cont.equals(contNou)) CtrlPresentacio.modificarContingut(au, ti, contNou);
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
                        System.out.println(new String(bytes));


                            System.out.println(arxiu.getAbsolutePath());
                        }
                        else JOptionPane.showMessageDialog(null, "No s'ha exportat el document.");
                    } else JOptionPane.showMessageDialog(null, "No s'ha exportat el document.");
                } else JOptionPane.showMessageDialog(null, "No s'ha exportat el document.");
            }*/
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String contNou = textPane1.getText();
                int opt = 0;
                String au = autor.getText(), ti = titol.getText();
                if (!cont.equals(contNou)) opt = desarAbansDeTancar(au, ti, contNou, false);
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
                    newT = JOptionPane.showInputDialog(null, "Escriu el nou títol:", "Modificar títol", JOptionPane.DEFAULT_OPTION);
                } while (newT == "");
                if(newT != null) { // diria q no pot passar a no ser q tanquis
                    String au = autor.getText(), ti = titol.getText();
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar el títol del document " + ti + " " + au + " de " + ti + " a " + newT + " ?", "Modificar títol", JOptionPane.YES_NO_OPTION);
                    if (opt == 0) {
                        CtrlPresentacio.actualitzaTitol(newT);
                        CtrlPresentacio.modificarTitol(au, ti, newT);
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
                    newA = JOptionPane.showInputDialog(null, "Escriu el nou autor:", "Modificar autor", JOptionPane.DEFAULT_OPTION);

                } while (newA == "");
                if(newA != null) {
                    String au = autor.getText(), ti = titol.getText();
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar l'autor del document " + ti + " " + au + " de " + au + " a " + newA + " ?", "Modificar autor", JOptionPane.YES_NO_OPTION);
                    if (opt == 0) {
                        CtrlPresentacio.actualitzaAutor(newA);
                        CtrlPresentacio.modificarAutor(au, ti, newA);
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
        JFrame view = new ViewEditar("Hola q tal", "Paco", "Hola q tal, com estàs?");
    }
}

