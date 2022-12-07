package presentacio;

import transversal.Pair;

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
    private JButton esborrarDocsButton;
    int anteriorColumn;


    public Main(String title) {
        anteriorColumn = -1;
        setContentPane(panel1);
        setTitle("Editor de textos");
        setSize(1000, 300);
        setMinimumSize(new Dimension(200, 200));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

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
        //documents.setSelectionMode(ListSelectionModel.);

        JTableHeader header = documents.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int column = documents.columnAtPoint(point);
                sortKeys.clear();
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
                sorter.setSortKeys(sortKeys);
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
                String newT = JOptionPane.showInputDialog(null, "Escriu el nou títol:", "");
                if(newT != null && !newT.equals("")) {
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar el títol del document " + documents.getValueAt(documents.getSelectedRow(), 0) + " de " + documents.getValueAt(documents.getSelectedRow(), 1) + " a " + newT + " ?", "Modificar títol", JOptionPane.YES_NO_OPTION);
                    if(opt == 0) {
                        boolean modificat = CtrlPresentacio.modificarTitol((String) documents.getValueAt(documents.getSelectedRow(), 1), (String) documents.getValueAt(documents.getSelectedRow(), 0), newT); //autor, titol, newT
                        if(modificat) {
                            tableModel.addRow(new Object[]{newT, documents.getValueAt(documents.getSelectedRow(), 1), documents.getValueAt(documents.getSelectedRow(), 2)});
                            tableModel.removeRow(documents.getSelectedRow());
                            JOptionPane.showMessageDialog(null, "S'ha modificat l'autor a " + newT + ".");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "El document amb títol " + newT + " i autor " + (String) documents.getValueAt(documents.getSelectedRow(), 1) + " ja existeix.");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No s'ha modificat el títol.");
                    }
                }
                else if(newT != null && newT.equals("")) {
                    JOptionPane.showMessageDialog(null, "Introdueix un títol vàlid.");
                }
                /*else {
                    JOptionPane.showMessageDialog(null, "No s'ha modificat el títol.");
                }*/
            }
        });

        modA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newA = JOptionPane.showInputDialog(null, "Escriu el nou autor:", "");
                if(newA != null && !newA.equals("")){
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar l'autor del document " + documents.getValueAt(documents.getSelectedRow(), 0) + " de " + documents.getValueAt(documents.getSelectedRow(), 1) + " a " + newA + " ?", "Modificar autor", JOptionPane.YES_NO_OPTION);
                    if (opt == 0) {
                        //documents.setValueAt(newA, documents.getSelectedRow(), 1);
                        //como el cambiar de valor no lo ordenaba correctamente he decidido borrarlo y volverlo a crear
                        boolean modificat = CtrlPresentacio.modificarAutor((String) documents.getValueAt(documents.getSelectedRow(), 1), (String) documents.getValueAt(documents.getSelectedRow(), 0), newA); //autor, titol, newA
                        if(modificat) {
                            tableModel.addRow(new Object[]{documents.getValueAt(documents.getSelectedRow(), 0), newA, documents.getValueAt(documents.getSelectedRow(), 2)});
                            tableModel.removeRow(documents.getSelectedRow());
                            JOptionPane.showMessageDialog(null, "S'ha modificat l'autor a " + newA + ".");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "El document amb títol " + (String) documents.getValueAt(documents.getSelectedRow(), 0) + " i autor " + newA + " ja existeix.");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No s'ha modificat l'autor.");
                    }
                }
                else if(newA != null && newA.equals("")) {
                    JOptionPane.showMessageDialog(null, "Introdueix un autor vàlid.");
                }
                /*else {
                    JOptionPane.showMessageDialog(null, "No s'ha modificat l'autor.");
                }*/
            }
        });

        borrarD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(null, "Segur que vols borrar el document " + documents.getValueAt(documents.getSelectedRow(), 0) + " de " + documents.getValueAt(documents.getSelectedRow(), 1) + " permanentment?", "Esborrar document", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    Object[][] documentBorrar = {{documents.getValueAt(documents.getSelectedRow(), 0), documents.getValueAt(documents.getSelectedRow(), 1)}};
                    borrarDocs(documentBorrar);
                    tableModel.removeRow(documents.getSelectedRow());
                    /*if (documents.getRowCount() == 0) {
                        documents.setVisible(false); ////////no va, el sentido que le veo es que se oculte la tabla y se muestre una label q ponga q: No hi ha cap document, es pot crear o importar. Y q clicando a esas palabras q estaruian subrayadas se hiciera el mismo efecto q con sus botones
                        documents.getTableHeader().setVisible(false);
                        CapDocLabel.setVisible(true);
                    }*/
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
                JTextField newT = new JTextField("",20);
                JPanel insertTitol = new JPanel();
                insertTitol.add(new JLabel("Escriu el titol:"));
                insertTitol.add(newT);

                JTextField newA = new JTextField("", 20);
                JPanel insertAutor = new JPanel();
                insertAutor.add(new JLabel("Escriu l'autor:"));
                insertAutor.add(newA);

                panelCreacio.setLayout(new BorderLayout());
                panelCreacio.add(insertTitol, BorderLayout.NORTH);
                panelCreacio.add(insertAutor, BorderLayout.SOUTH);

                JOptionPane.showMessageDialog(null, panelCreacio, "Creació", JOptionPane.QUESTION_MESSAGE);

                if(newT.getText() != null && newA.getText() != null && !newT.getText().equals("") && !newA.getText().equals("")) {
                    boolean creat = CtrlPresentacio.crearDocument(newA.getText(), newT.getText(), "");
                    if(creat) {
                        tableModel.addRow(new Object[]{newT.getText(), newA.getText(), LocalDate.now()
                                + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)});
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "El document amb títol " + newT.getText() + " i autor " + newA.getText() + " ja existeix.");
                    }
                }
                /*else if(newA.getText() != null && newT.getText() != null && (newT.getText().equals("") || newA.getText().equals(""))){ //FALTA PONER UN MENSAJE EN CASO DE Q SE DEJEN LOS 2 EN BLANCO Y SE CLIQ ACEPTAR
                    JOptionPane.showMessageDialog(null, "Introdueix un autor i un títol vàlid");
                }*/
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
                        JOptionPane.showMessageDialog(null,
                                "Has seleccionat més arxius del màxim, s'importaran només els 10 primers.");
                    }
                    int min = 10;
                    if(arxius.length < 10) min = arxius.length;
                    for(int i = 0; i < min; ++i) {
                        //System.out.println(arxius[i].getAbsolutePath());
                        List<String> docImp = CtrlPresentacio.importaDocument(arxius[i].getAbsolutePath());
                        if(docImp.get(2).equals("null")) { //ya existe
                            JOptionPane.showMessageDialog(null,
                                    "El document amb path " + arxius[i].getAbsolutePath() +
                                            " no es pot importar perqué ya existeix un document amb títol " +
                                            docImp.get(1) + " i autor " + docImp.get(0) + ".");
                        }
                        //System.out.println(docImp.get(1) + docImp.get(0) + docImp.get(2));
                    }
                }
            }
        });

        gestioExpBoolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaGestioExpBool();
            } //este le tendrá q decir al ctrlpres q la muestre, él no
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

        /*Llistar titols d'autor*/
        llistarTdeA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String[] auts = cp.getAutors().toArray(new String[0]);
                String[] auts = {"Ana", "Carlos", "Joan", "Pepe"};
                JComboBox jca = new JComboBox(auts);
                jca.setEditable(false);

                Object[] options = new Object[]{"Tria un autor: ", jca};

                int opt = JOptionPane.showOptionDialog(null, options, "Llistar títols d'autor",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (opt == 0) {
                    //System.out.println((String)jca.getSelectedItem());

                    String autorSelec = (String)jca.getSelectedItem();
                    List<String> titols = CtrlPresentacio.llistarTitolsdAutors(autorSelec);
                    Object[][] titolsdAutor = new Object[titols.size()][1];
                    for(int i = 0; i < titols.size(); ++i) {
                        Object[] titol = {titols.get(i)};
                        titolsdAutor[i] = titol;
                    }
                    String[] cols = {"Títols"};
                    DefaultTableModel tm = new DefaultTableModel(titolsdAutor, cols) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    JTable tits = new JTable(tm);

                    tits.setAutoCreateRowSorter(true);
                    tits.getTableHeader().setReorderingAllowed(false);
                    tits.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    tits.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
                    JPanel panelTits = new JPanel();
                    panelTits.setLayout(new BorderLayout());
                    JScrollPane tableAutsScroll = new JScrollPane(tits);
                    panelTits.add(tableAutsScroll);
                    //panelTits.setBounds(200, 200, 50, 50);

                    JOptionPane.showMessageDialog(null, panelTits, "Títols de l'autor " + autorSelec, JOptionPane.DEFAULT_OPTION);
                }
            }
        });

        llistarAperP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pref = JOptionPane.showInputDialog(null,
                        "Escriu el prefix dels autors que vols llistar:",
                        "Llistar per prefix",
                        JOptionPane.DEFAULT_OPTION);
                if (pref != null) {
                    //String[] tda = cp.llistarAutorsPrefix("A").toArray(new String[0]); //ns com, pero agafar la opcio del jcombo q s'hagi escollit
                    List<String> autors = CtrlPresentacio.llistarAutorsPrefix(pref);
                    Object[][] autorsObj = new Object[autors.size()][1];
                    for(int i = 0; i < autors.size(); ++i) {
                        Object[] autor = {autors.get(i)};
                        autorsObj[i] = autor;
                    }
                    String[] columns = {"Autors"};
                    DefaultTableModel tm = new DefaultTableModel(autorsObj, columns) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };

                    JTable auts = new JTable(tm);
                    auts.setAutoCreateRowSorter(true);
                    auts.getTableHeader().setReorderingAllowed(false);
                    auts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    auts.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
                    JPanel panelAuts = new JPanel();
                    panelAuts.setLayout(new BorderLayout());
                    JScrollPane tableAutsScroll = new JScrollPane(auts);
                    panelAuts.add(tableAutsScroll);

                    JOptionPane.showMessageDialog(null, panelAuts, "Autors amb el prefix " + pref, JOptionPane.DEFAULT_OPTION);
                }
            }
        });

        esborrarDocsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Creamos la tabla*/
                if (documents.getSelectedRowCount() > 0) {
                    Object[][] documentsBorrar = new Object[documents.getSelectedRowCount()][2];
                    Object[] columnsBorrar = {"Títols", "Autors"};

                    int index = 0;
                    int selectedRow[] = documents.getSelectedRows();
                    for (int i : selectedRow) {
                        Object[] docBorrar = {tableModel.getValueAt(i,0), tableModel.getValueAt(i,1)};
                        documentsBorrar[index] = docBorrar;
                        ++index;
                        //System.out.println(selectedRow[i]);
                    }

                    DefaultTableModel tm = new DefaultTableModel(documentsBorrar, columnsBorrar) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    JTable docsBorrar = new JTable(tm);
                    docsBorrar.setAutoCreateRowSorter(true);
                    docsBorrar.getTableHeader().setReorderingAllowed(false);
                    docsBorrar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    docsBorrar.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
                    docsBorrar.getColumnModel().getColumn(1).setCellRenderer(new GestioCell("text"));
                    JPanel panelBorrar = new JPanel();
                    panelBorrar.setLayout(new BorderLayout());
                    JScrollPane tableAutsScroll = new JScrollPane(docsBorrar);
                    panelBorrar.add(new JLabel("S'esborraran els següents documents:"), BorderLayout.NORTH);
                    panelBorrar.add(tableAutsScroll, BorderLayout.CENTER);
                    panelBorrar.add(new JLabel("Estàs d'acord?"), BorderLayout.SOUTH);
                    //docsBorrar.setFillsViewportHeight(true);

                    /*Mostramos tabla*/
                    int opt = JOptionPane.showOptionDialog(null, panelBorrar, "Esborrar documents seleccionats",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
                    if(opt == 0) { //s'esborren + missatge
                        borrarDocs(documentsBorrar);
                        for(int i = selectedRow.length; i > 0; --i) {
                            tableModel.removeRow(documents.getSelectedRow());
                        }
                        JOptionPane.showMessageDialog(null, "S'han esborrat correctament els documents", "Esborrar documents seleccionats", JOptionPane.DEFAULT_OPTION);
                    }
                    else { //misstage no s'han borrat
                        JOptionPane.showMessageDialog(null, "No s'han esborrat cap document", "Esborrar documents seleccionats", JOptionPane.DEFAULT_OPTION);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No hi ha cap document seleccionat", "Esborrar documents seleccionats", JOptionPane.DEFAULT_OPTION);
                }
            }
        });

        cercaR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String paraules;
                //do {
                    paraules = JOptionPane.showInputDialog(null, "Escriu les paraules que vols cercar per rellevància:", "Llistar documents rellevants", -1);
                //} while (paraules.equals(""));

                JPanel message = new JPanel();
                SpinnerModel value = new SpinnerNumberModel(1, 1, documents.getRowCount(), 1);
                JSpinner num = new JSpinner(value);
                JFormattedTextField tf = ((JSpinner.DefaultEditor)num.getEditor()).getTextField();
                tf.setEditable(false);
                message.setLayout(new BorderLayout());
                message.add(new Label("Escriu el nombre de documents que vols llistar:"), BorderLayout.NORTH);
                message.add(num);

                int opt1 = JOptionPane.showOptionDialog(null, message, "Llistar documents semblants",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
                if(opt1 == 0) {
                    String[] tox = {"TF-IDF", "TF"};
                    int opt2 = JOptionPane.showOptionDialog(null, "Escull l'estratègia amb la que vols cercar:", "Escollir estratègia", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tox, tox[0]);
                    if (opt2 == 0 || opt2 == 1) {
                        boolean estrategia = false;
                        if(opt2 == 1) estrategia = true;
                        List<Pair<String, String>> docsCondicio = CtrlPresentacio.cercarPerRellevancia(paraules, (int) num.getValue(), estrategia);

                        Object[][] docsCondicioObj = new Object[docsCondicio.size()][2];
                        for(int i = 0; i < docsCondicio.size(); ++i) {
                            Object[] docCondicioObj = {docsCondicio.get(i).y, docsCondicio.get(i).x};
                            System.out.println(docsCondicio.get(i).y + docsCondicio.get(i).x);
                            docsCondicioObj[i] = docCondicioObj;
                        }
                        String[] columns = {"Títols", "Autors"};
                        DefaultTableModel tm = new DefaultTableModel(docsCondicioObj, columns) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };

                        JTable docs = new JTable(tm);
                        docs.setAutoCreateRowSorter(true);
                        docs.getTableHeader().setReorderingAllowed(false);
                        docs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        docs.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
                        docs.getColumnModel().getColumn(1).setCellRenderer(new GestioCell("text"));
                        JPanel panelDocs = new JPanel();
                        panelDocs.setLayout(new BorderLayout());
                        JScrollPane tableAutsScroll = new JScrollPane(docs);
                        panelDocs.add(tableAutsScroll, BorderLayout.CENTER);

                        String estrat = "TF-IDF";
                        if(estrategia) estrat = "TF";
                        JLabel label = new JLabel( "Aquests són els " + num.getValue() +
                                " documents més rellevants segons les paraules " + paraules +
                                " amb l'estratègia " + estrat + ".");
                        panelDocs.add(label, BorderLayout.SOUTH);
                        JOptionPane.showMessageDialog(null, panelDocs, "Documents segons la cerca per rellevància", JOptionPane.DEFAULT_OPTION);
                    }
                }
            }
        });

        llistarSemblants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //de alguna manera hacer el get de la fila
                JPanel message = new JPanel();
                SpinnerModel value = new SpinnerNumberModel(1, 1, documents.getRowCount(), 1);
                JSpinner num = new JSpinner(value);
                JFormattedTextField tf = ((JSpinner.DefaultEditor)num.getEditor()).getTextField();
                tf.setEditable(false);
                message.setLayout(new BorderLayout());
                message.add(new Label("Escriu el nombre de documents que vols llistar:"), BorderLayout.NORTH);
                message.add(num);

                int opt1 = JOptionPane.showOptionDialog(null, message, "Llistar documents semblants",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
                if(opt1 == 0) {
                    System.out.println(num.getValue());
                    String[] tox = {"TF-IDF", "TF"};
                    int opt2 = JOptionPane.showOptionDialog(null, "Escull l'estratègia amb la que vols cercar:", "Llistar documents semblants", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tox, tox[0]);
                    if (opt2 == 0 || opt2 == 1) {
                        //List<Pair<String, String>> rell = cp.llistarKDocumentsS("Jose", "Hola", Integer.parseInt(k), opt != 0);
                        //mostrar
                    }
                }
            }
        });


        validate();
    }

    private void borrarDocs(Object[][] documentsBorrar) {
        List<Pair<String, String>> docsBorrarList = new ArrayList<>();
        Pair<String,String> p = new Pair();
        for(int i = 0; i < documentsBorrar.length; ++i) {
            p.x = (String) documentsBorrar[i][1];
            p.y = (String) documentsBorrar[i][0];
            //System.out.println(p.x + p.y);
            docsBorrarList.add(p);
        }
        CtrlPresentacio.esborrarDocuments(docsBorrarList);
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

