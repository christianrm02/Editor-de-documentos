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
    private CtrlPresentacio cp;

    private int desarAbansDeTancar(String t, String a, String contNou, boolean sortir) {
        String frase = "No has desat el document. El vols desar abans de ";
        if (sortir) frase += "tornar a la pantalla d'inici?";
        else frase += "tancar el programa?";
        int opt, opt2 = 1;
        do {
            opt = JOptionPane.showConfirmDialog(null, frase, "Desar document", JOptionPane.YES_NO_OPTION);
            if (opt == 0) {
                cp.modificarContingut(contNou);
            }
            else if (opt == 1) {
                opt2 = JOptionPane.showConfirmDialog(null, "Estàs segur que no vols desar el document?", "Desar document", JOptionPane.YES_NO_OPTION);
            }
        } while(opt == 1 && opt2 == 1);
        return opt;
    }

    public ViewEditar(CtrlPresentacio ctrlp, String t, String a, String c) {
        setContentPane(panel1);
        setMinimumSize(new Dimension(400, 200));
        setTitle("Editor de textos " + t + ", " + a);
        setSize(1000, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        cp = ctrlp;
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
                cp.desarDocument();
                cp.mostraViewPrincipal();
                dispose();
            }
        });

        desarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contNou = textPane1.getText();
                if (!cont.equals(contNou)) {
                    cp.modificarContingut(contNou);
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
                            JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, opts, opts[0]);

                    if (opt == 0 && newNom.getText() != null && !newNom.getText().equals("") && !((String) tipus.getSelectedItem()).equals("")) {
                        String path = chooser.getSelectedFile().getAbsolutePath();
                        String au = autor.getText(), ti = titol.getText();
                        String loc = path + "\\" + newNom.getText() + "." + (String)tipus.getSelectedItem(); //COMPROBAR Q SE HACE BIEN EL PATH
                        cp.exportaDocument(au, ti, loc);
                        //System.out.println(titol + " " + autor + " " + newNom.getText() + " " + chooser.getSelectedFile().getAbsolutePath());
                    } else if (opt == 0 && (newNom.getText().equals("") || ((String) tipus.getSelectedItem()).equals(""))) {
                        JOptionPane.showMessageDialog(null, "Indica un nom i un format vàlids, no deixis camps buits.",
                                "Error exportació", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No s'ha exportat el document.");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Alguna cosa ha sortit malament, torna a intentar-ho.");
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String contNou = textPane1.getText();
                int opt = 0;
                String au = autor.getText(), ti = titol.getText();
                if (!cont.equals(contNou)) opt = desarAbansDeTancar(au, ti, contNou, false);
                if (opt == 0 || opt == 1) {
                    cp.desarDocument();
                    cp.tancarAplicacio();
                }
            }
        });

        titol.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newT;
                do {
                    newT = JOptionPane.showInputDialog(null, "Escriu el nou títol:", "Modificar títol", JOptionPane.DEFAULT_OPTION);
                } while (newT.equals(""));
                if(newT != null) { // diria q no pot passar a no ser q tanquis
                    String au = autor.getText(), ti = titol.getText();
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar el títol del document " + ti + " " + au + " de " + ti + " a " + newT + " ?", "Modificar títol", JOptionPane.YES_NO_OPTION);
                    if (opt == 0) {
                        cp.actualitzaTitol(newT);
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
                    newA = JOptionPane.showInputDialog(null, "Escriu el nou autor:", "Modificar autor", JOptionPane.DEFAULT_OPTION);
                } while (newA.equals(""));
                if(newA != null) {
                    String au = autor.getText(), ti = titol.getText();
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar l'autor del document " + ti + " " + au + " de " + au + " a " + newA + " ?", "Modificar autor", JOptionPane.YES_NO_OPTION);
                    if (opt == 0) {
                        cp.actualitzaAutor(newA);
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

/*    public static void main(String[] args) {
        JFrame view = new ViewEditar(new CtrlPresentacio(), "Hola q tal", "Paco", "Hola q tal, com estàs?");
    }*/
}

