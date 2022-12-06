package presentacio;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private JPanel panel1;
    private JButton creaButton;
    private JButton importaButton;
    private JButton ajudaButton;
    private JButton xButton;
    private JPanel tablePanel;
    private JButton gestioExpBoolButton;
    private JButton busquedaButton;
    private JLabel CapDocLabel;
    int anteriorColumn;


    public Main(String title) {
        anteriorColumn = -1;
        setContentPane(panel1);
        setTitle("Editor de textos");
        setSize(1000, 300);
        setMinimumSize(new Dimension(200, 200));
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
                {"La ceguera", "Pepe", LocalDate.of(1973, 2, 23) + " " + LocalTime.of(20, 01, 15, 0003).truncatedTo(ChronoUnit.SECONDS)},
                {"Lo que el viento se llevó", "Pepe", LocalDate.of(1972, 2, 23) + " " + LocalTime.of(20, 03, 15, 0003).truncatedTo(ChronoUnit.SECONDS)}};/*


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
        DefaultTableModel tableModel = new DefaultTableModel(titols, colums){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable documents = new JTable(tableModel);
        //documents.setAutoResizeMode(5);

        documents.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(documents.getModel());
        documents.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sorter.setSortKeys(sortKeys);
        documents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //documents.setEnabled(false);///arreglar, necesito bloquear edicion tabla

        JTableHeader header = documents.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int column = documents.columnAtPoint(point);
                sortKeys.clear();
                //System.out.println(anteriorColumn);
                if(anteriorColumn != 1 && column == 1) {
                    sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
                    sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
                    anteriorColumn = column;
                } else if(anteriorColumn != -0 && column == 0) {
                    sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
                    sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
                    anteriorColumn = column;
                } else if(anteriorColumn == 1 && column == 1) {
                    sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
                    sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING)); //ASCENDING
                    anteriorColumn = -1;
                } else if(anteriorColumn == 0 && column == 0) {
                    sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
                    sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING)); //ASCENDING
                    anteriorColumn = -1;
                }
                //if(anteriorColumn != -1) anteriorColumn = column;
                sorter.setSortKeys(sortKeys);

                //System.out.println(anteriorColumn);
                //System.out.println(column);
            }
        });

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

        /*Tres puntos opciones doc*/
        documents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columna = documents.columnAtPoint(e.getPoint());
                if (columna==3) {
                    popOptDoc.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        /*Boton derecho opciones doc ABORTO LA IDEA PORQ PARA Q FUNCIONE TENDRIA Q HACER ANTES UN CLICK, Y NO SE COMO HACERLO
        documents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isRightMouseButton(e)) {
                    click();
                    popOptDoc.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });*/

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
                        //documents.setValueAt(newT, documents.getSelectedRow(), 0);
                        tableModel.addRow(new Object[]{newT, documents.getValueAt(documents.getSelectedRow(), 1), documents.getValueAt(documents.getSelectedRow(), 2)});
                        tableModel.removeRow(documents.getSelectedRow());
                        JOptionPane.showMessageDialog(null, "S'ha modificat el títol a " + newT + " .");
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
                        //documents.setValueAt(newA, documents.getSelectedRow(), 1);
                        //como el cambiar de valor no lo ordenaba correctamente he decidido borrarlo y volverlo a crear
                        tableModel.addRow(new Object[]{documents.getValueAt(documents.getSelectedRow(), 0), newA, documents.getValueAt(documents.getSelectedRow(), 2)});
                        tableModel.removeRow(documents.getSelectedRow());
                        JOptionPane.showMessageDialog(null, "S'ha modificat l'autor a " + newA + " .");
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
                chooser.setFileFilter(new FileNameExtensionFilter("Compatibles amb l'aplicació, màxim 10", "txt", "xml"));
                chooser.setDialogTitle("Selecciona fitxers, màxim 10");
                chooser.setMultiSelectionEnabled(true);
                //chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data/dades"));
                int returnValue = chooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] arxius = chooser.getSelectedFiles();
                    if (arxius.length > 10) {
                        JOptionPane.showMessageDialog(null, "Has seleccionat més arxius del màxim, s'importaran només els 10 primers.");
                        File[] arxius_maxim = new File[10];
                        for(int i = 0; i < 10; ++i) {
                            arxius_maxim[i] = arxius[i];
                        }
                        //pasas al ctrlpres los paths
                        //for(int i = 0; i < 10; ++i) {
                            //System.out.println(arxius_maxim[i].getAbsolutePath());
                        //}
                    }
                    else {
                        ////pasas al ctrlpres los paths
                    }
                }
            }
        });

        gestioExpBoolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaGestioExpBool();
            }
        });

        ajudaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = 0; //0 primera vez, -1 se cierra ventana
                int opt = JOptionPane.showOptionDialog(null,"Amb el botó Crea es crea un nou document amb contingut buit.", "Panell d'ajuda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Següent"}, "Següent");
                //while(opt == -2);
                String message = "";
                while(count < 6 && opt != -1) {
                    if(opt == 0 && count != 0) --count;
                    else ++count;
                    if(count == 0) message = "Amb el botó Crea es crea un nou document amb contingut buit.";
                    else if(count == 1) message = "Amb el botó Importa es crea un nou document amb el contingut del document seleccionat. Només es pot importar documents de tipus .txt o .xml";
                    else if(count == 2) message = "Amb el botó Gestió expressions booleanes s'obre una vista per gestionar les expressions guardades.";
                    else if(count == 3) message = "Amb el botó de Cerca es pot filtrar els documents a mostrar";
                    else if(count == 4) message = "Amb els 3 punts verticals de cada document, o clicant a sobre de la fila amb el botó dret, s'obre més opcions per aquell document";
                    else if(count == 5) message = "Es pot tancar el programa en qualsevol moment clicant al botó amb la X de la cantonada superior dreta";
                    if(count == 0) opt = JOptionPane.showOptionDialog(null,message, "Panell d'ajuda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Següent"}, "Següent");
                    else if(count < 5) opt = JOptionPane.showOptionDialog(null,message, "Panell d'ajuda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Anterior", "Següent"}, "Següent");
                    else opt = JOptionPane.showOptionDialog(null,message, "Panell d'ajuda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Anterior", "Acaba"}, "Acaba");
                }
            }
        });

        validate();
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

