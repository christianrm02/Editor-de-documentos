package presentacio;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Main extends JFrame {
    private JPanel panel1;
    private JButton creaButton;
    private JButton importaButton;
    private JButton ajudaButton;
    private JButton xButton;
    private JPanel tablePanel;
    private JButton gestióExpressionsBooleanesButton;
    private JButton busquedaButton;
    private JLabel CapDocLabel;


    public Main(String title) {
        setContentPane(panel1);
        setTitle("Editor de textos");
        setSize(1000, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        /*list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = list1.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Object o = list1.getModel().getElementAt(index);
                        System.out.println("Double-clicked on: " + o.toString());
                    }
                }
            }
        });*/


        /*JLabel moreOptions = new JLabel();
        ImageIcon iconMoreOptions = new ImageIcon(getClass().getResource("/icons/moreOptions.png"));
        moreOptions.setIcon(iconMoreOptions);*/


        String[] colums = {"Titols", "Autors", "Darrera modificació", " "};
        Object[][] titols = {
                //{"Dia", "Pep", LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)},
                {"Historia de una escalera", "Ana", LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)},
                {"La venganza de Don Tenorio", "Ana", LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)},
                {"La divina comedia", "Carlos", LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)},
                {"La divina comedia", "Joan", LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)},
                {"La ceguera", "Pepe", LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)},
                {"Lo que el viento se llevó", "Pepe", LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)}};/*




                {"Day", "Pepe", LocalDate.now() + " " + LocalTime.now()},
                {"Rio", "Romero", LocalDate.now() + " " + LocalTime.now()},
                {"Camino", "Alex", LocalDate.now() + " " + LocalTime.now()},
                {"Dia", "Zacarias", LocalDate.now() + " " + LocalTime.now()},
                {"SD", "Juan", LocalDate.now() + " " + LocalTime.now()},
                {"La hostia", "JAGGER", LocalDate.now() + " " + LocalTime.now()},
                {"Illo no veas", "Illojuan", LocalDate.now() + " " + LocalTime.now()},
                {"Siempre tipo fuego", "Oak", LocalDate.now() + " " + LocalTime.now()},
                {"Adelante Pikachu", "Ash", LocalDate.now() + " " + LocalTime.now()},
                {"Tornado de fuego", "Axel", LocalDate.now() + " " + LocalTime.now()},
                {"Mano mágica", "Marc", LocalDate.now() + " " + LocalTime.now()},
                {"Lo tengo!", "Layton", LocalDate.now() + " " + LocalTime.now()},
                {"Ay ay ay", "Luke", LocalDate.now() + " " + LocalTime.now()},
                {"Roooar", "Luxray", LocalDate.now() + " " + LocalTime.now()},
                {"Yo q sé", "Yo", LocalDate.now() + " " + LocalTime.now()},*/
                //{"Lápiz", "Antes", LocalDate.of(1972, 2, 23) + " " + LocalTime.of(20, 01, 15, 0003).truncatedTo(ChronoUnit.SECONDS)},
                //{"Vida", "Después", LocalDate.of(1972, 2, 23) + " " + LocalTime.of(20, 03, 15, 0003).truncatedTo(ChronoUnit.SECONDS)}};
        DefaultTableModel tableModel = new DefaultTableModel(titols, colums);
        JTable documents = new JTable(tableModel);
        //documents.setAutoResizeMode(5);
        documents.setAutoCreateRowSorter(true);
        tablePanel.setLayout(new BorderLayout());
        JScrollPane tableScroll = new JScrollPane(documents);
        //documents.setFillsViewportHeight(true);
        tablePanel.add(tableScroll, BorderLayout.CENTER);

        documents.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
        documents.getColumnModel().getColumn(1).setCellRenderer(new GestioCell("text"));
        documents.getColumnModel().getColumn(2).setCellRenderer(new GestioCell("int"));
        documents.getColumnModel().getColumn(3).setCellRenderer(new GestioCell("icon"));
        documents.setRowHeight(25);
        documents.getColumnModel().getColumn(0).setPreferredWidth(100);
        documents.getColumnModel().getColumn(1).setPreferredWidth(100);
        documents.getColumnModel().getColumn(2).setPreferredWidth(200);
        documents.getColumnModel().getColumn(3).setPreferredWidth(10);
        documents.getTableHeader().setReorderingAllowed(false);

        /* popup busqueda */
        //JMenu menuBusqueda;
        //JMenuBar mb = new JMenuBar();
        //menuBusqueda = new JMenu("Búsqueda");
        JMenuItem llistarTdeA=new JMenuItem("Llistar títols autor");
        JMenuItem llistarAperP=new JMenuItem("Llistar autors prefix");
        JMenuItem cercaR=new JMenuItem("Cerca per rellevància");
        //menuBusqueda.add(iB1); menuBusqueda.add(iB2); menuBusqueda.add(iB3);
        //mb.add(menuBusqueda);
        JPopupMenu popBusqueda = new JPopupMenu();
        popBusqueda.add(llistarTdeA); popBusqueda.add(llistarAperP); popBusqueda.add(cercaR);

        /* popup opciones docs */
        JMenuItem exportarD=new JMenuItem("Exportar document");
        JMenuItem llistarSemblants=new JMenuItem("Llistar documents semblants");
        JMenuItem modT=new JMenuItem("Modificar titol");
        JMenuItem modA=new JMenuItem("Modificar autor");
        JMenuItem mostrarD=new JMenuItem("Mostrar document");
        JMenuItem borrarD=new JMenuItem("Borrar document");
        JPopupMenu popOptDoc = new JPopupMenu();
        popOptDoc.add(exportarD); popOptDoc.add(llistarSemblants); popOptDoc.add(modT); popOptDoc.add(modA); popOptDoc.add(mostrarD); popOptDoc.add(borrarD);

        busquedaButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                popBusqueda.show(e.getComponent(), busquedaButton.getX()+busquedaButton.getWidth(), e.getY()-30);
            }
        });

        documents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = documents.rowAtPoint(e.getPoint());
                int columna = documents.columnAtPoint(e.getPoint());
                if (columna==3) {
                    popOptDoc.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        modT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newT = JOptionPane.showInputDialog("Escriu el nou títol:");
                if(newT != null) {
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar el títol del document " + documents.getValueAt(documents.getSelectedRow(), 0) + " de " + documents.getValueAt(documents.getSelectedRow(), 1) + " a " + newT + " ?", "Modificar títol", JOptionPane.YES_NO_OPTION);
                    if (opt == 0) {
                        documents.setValueAt(newT, documents.getSelectedRow(), 0);
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

        modA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newA = JOptionPane.showInputDialog("Escriu el nou autor:");
                if(newA != null) {
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar l'autor del document " + documents.getValueAt(documents.getSelectedRow(), 0) + " de " + documents.getValueAt(documents.getSelectedRow(), 1) + " a " + newA + " ?", "Modificar autor", JOptionPane.YES_NO_OPTION);
                    if (opt == 0) {
                        documents.setValueAt(newA, documents.getSelectedRow(), 1);
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

        borrarD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(null, "Segur que vols borrar el document " + documents.getValueAt(documents.getSelectedRow(), 0) + " de " + documents.getValueAt(documents.getSelectedRow(), 1) + " permanentment?", "Esborrar document", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    tableModel.removeRow(documents.getSelectedRow());
                    if (documents.getRowCount() == 0) {
                        documents.setVisible(false); ////////no va, el sentido que le veo es que se oculte la tabla y se muestre una label q ponga q: No hi ha cap document, es pot crear o importar. Y q clicando a esas palabras q estaruian subrayadas se hiciera el mismo efecto q con sus botones
                        documents.getTableHeader().setVisible(false);
                        CapDocLabel.setVisible(true);
                    }
                    JOptionPane.showMessageDialog(null, "S'ha esborrat el document correctament");
                } else {
                    JOptionPane.showMessageDialog(null, "No s'ha esborrat el document");
                }
            }
        });

        validate();

        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panelCreacio = new JPanel();
                JTextField newT = new JTextField(20);
                JPanel insertTitol = new JPanel();
                insertTitol.add(new JLabel("Escriu el titol:"));
                insertTitol.add(newT);

                JTextField newA = new JTextField(20);
                JPanel insertAutor = new JPanel();
                insertAutor.add(new JLabel("Escriu l'autor:"));
                insertAutor.add(newA);

                panelCreacio.setLayout(new BorderLayout());
                panelCreacio.add(insertTitol, BorderLayout.NORTH);
                panelCreacio.add(insertAutor, BorderLayout.SOUTH);

                JOptionPane.showMessageDialog(null, panelCreacio, "Creació", JOptionPane.QUESTION_MESSAGE);

                tableModel.addRow(new Object[]{newT.getText(), newA.getText(), LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)});
                //System.out.println(newT.getText());
                //System.out.println(newA.getText());

            }
        });

        importaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.setFileFilter(new FileNameExtensionFilter("Compatibles amb l'aplicació", "txt", "xml"));
                chooser.setDialogTitle("Selecciona fitxer");
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
            }
        });
    }

    public static void main(String[] args) {
        JFrame mainView = new Main("Editor de textos");

        /* keyboard listeners */
        /*mainView.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_CLEAR) {
                    System.out.println("Up SUPR is pressed!");
                } else if (keyCode == KeyEvent.VK_DELETE) {
                    System.out.println("Up BORRAR is pressed!");
                } else if (keyCode == KeyEvent.VK_A) {
                    System.out.println("Up A is pressed!");
                }
            }
        });*/
    }
}

/*
String[] colums = {"Titols", "Autors", "Darrera modificació"};
        Object[][] titols = {
                {"Dia", "Pep", LocalDateTime.now()},
                {"Day", "Pepe"},
                {"Rio", "Romero"},
                {"Camino", "Alex"},
                {"Dia", "Zacarias"},
                {"SD", "Juan"},
                {"La hostia", "JAGGER"},
                {"Illo no veas", "Illojuan"},
                {"Siempre tipo fuego", "Oak"},
                {"Adelante Pikachu", "Ash"},
                {"Tornado de fuego", "Axel"},
                {"Mano mágica", "Marc"},
                {"Lo tengo!", "Layton"},
                {"Ay ay ay", "Luke"},
                {"Roooar", "Luxray"},
                {"Yo q sé", "Yo"},
                {"Lápiz", "Boli"},
                {"Vida", "Alex"}};
*/

