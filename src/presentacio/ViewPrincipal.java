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

import static javax.swing.SwingUtilities.isRightMouseButton;

public class ViewPrincipal extends JFrame {
    private JPanel panel1;
    private JButton creaButton;
    private JButton importaButton;
    private JButton ajudaButton;
    private JButton xButton;
    private JPanel tablePanel;
    private JButton gestioExpBoolButton;
    private JButton busquedaButton;
    private JButton esborrarDocsButton;
    int columnRepetida; //els seguents 5 atributs son necessaris per poder modificar el titol i autor i es mantenguin en ordre
    int anteriorColumnPuls;
    int numRows;
    TableRowSorter<TableModel> sorter;
    List<RowSorter.SortKey> sortKeys;
    DefaultTableModel tableModel;
    JTable documents;


    public ViewPrincipal(String title) {
        columnRepetida = -1;
        anteriorColumnPuls = -1;
        setContentPane(panel1);
        setTitle("Editor de textos");
        setSize(1000, 500);
        setMinimumSize(new Dimension(500, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        String[] colums = {"Titols", "Autors", "Darrera modificació", " "};
        List<Pair<String, String>> documentsList = new ArrayList<>();
        documentsList = CtrlPresentacio.getTitolsAutors();
        Object[][] documentsObj = new Object[documentsList.size()][2];
        for(int i = 0; i < documentsList.size(); ++i) {
            Object[] document = {documentsList.get(i).y, documentsList.get(i).x};
            documentsObj[i] = document;
        }

        tableModel = new DefaultTableModel(documentsObj, colums) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //JTable Jdocuments = new JTable(tableModel);
        documents = new JTable(tableModel);

        //documents.setAutoResizeMode(5);

        documents.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(documents.getModel());
        documents.setRowSorter(sorter);
        sortKeys = new ArrayList<>();
        sorter.setSortKeys(sortKeys);
        //documents.setSelectionMode(ListSelectionModel.);

        JTableHeader header = documents.getTableHeader();
        header.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int column = documents.columnAtPoint(point);
                anteriorColumnPuls = column;
                newOrder(anteriorColumnPuls);
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

        numRows = documents.getRowCount();

        /* popup busqueda */
        JMenuItem llistarTdeA = new JMenuItem("Llistar títols autor");
        JMenuItem llistarAperP = new JMenuItem("Llistar autors prefix");
        JMenuItem cercaR = new JMenuItem("Cerca per rellevància");
        JMenuItem cercaExp = new JMenuItem("Cerca per expressió booleana");
        JPopupMenu popBusqueda = new JPopupMenu();
        popBusqueda.add(llistarTdeA);
        popBusqueda.add(llistarAperP);
        popBusqueda.add(cercaR);
        popBusqueda.add(cercaExp);

        /* popup opciones docs */
        JMenuItem exportarD = new JMenuItem("Exportar document");
        JMenuItem llistarSemblants = new JMenuItem("Llistar documents semblants");
        JMenuItem modT = new JMenuItem("Modificar titol");
        JMenuItem modA = new JMenuItem("Modificar autor");
        JMenuItem mostrarD = new JMenuItem("Mostrar document");
        JMenuItem borrarD = new JMenuItem("Borrar document");
        JPopupMenu popOptDoc = new JPopupMenu();
        popOptDoc.add(exportarD);
        popOptDoc.add(llistarSemblants);
        popOptDoc.add(modT);
        popOptDoc.add(modA);
        popOptDoc.add(mostrarD);
        popOptDoc.add(borrarD);

        /*Desplegable cerca*/
        busquedaButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                popBusqueda.show(e.getComponent(), busquedaButton.getX() + busquedaButton.getWidth() - 7, e.getY() - 30);
            }
        });

        /*Tres puntos opciones doc*/
        documents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columna = documents.columnAtPoint(e.getPoint());
                if (columna == 3) {
                    popOptDoc.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        JMenuItem borrarDocsSeleccionats = new JMenuItem("Esborrar documents seleccionats");
        JPopupMenu popBorrarDocs = new JPopupMenu();
        popBorrarDocs.add(borrarDocsSeleccionats);

        documents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isRightMouseButton(e)) {
                    int row = documents.rowAtPoint(e.getPoint());
                    boolean botoDretSeleccionat = false;
                    int[] selection = documents.getSelectedRows();
                    for (int i = 0; i < selection.length && !botoDretSeleccionat; ++i) { ////MEJORABLE? SE PUEDE METER EN UN SET?
                        if (selection[i] == row) botoDretSeleccionat = true;
                    }
                    if (botoDretSeleccionat) { //si está seleccionada previamente -> borrarDocs
                        popBorrarDocs.show(e.getComponent(), e.getX(), e.getY());
                    } else { //abrir popups opciones en un doc
                        documents.clearSelection();
                        documents.addRowSelectionInterval(row, row);
                        popOptDoc.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        /* Se puede borrar desde el boton o con boton derecho sobre selección */
        ActionListener esborrarDocsSeleccionatsAction = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                /*Creamos la tabla*/
                if (documents.getSelectedRowCount() > 0) {
                    Object[][] documentsBorrar = new Object[documents.getSelectedRowCount()][2];
                    Object[] columnsBorrar = {"Títols", "Autors"};

                    int index = 0;
                    int selectedRow[] = documents.getSelectedRows();
                    for (int i : selectedRow) {
                        Object[] docBorrar = {tableModel.getValueAt(i, 0), tableModel.getValueAt(i, 1)};
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
                    /*JTable docsBorrar = new JTable(tm);
                    docsBorrar.setAutoCreateRowSorter(true);
                    docsBorrar.getTableHeader().setReorderingAllowed(false);
                    docsBorrar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    docsBorrar.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
                    docsBorrar.getColumnModel().getColumn(1).setCellRenderer(new GestioCell("text"));
                    JPanel panelBorrar = new JPanel();
                    panelBorrar.setLayout(new BorderLayout());
                    JScrollPane tableAutsScroll = new JScrollPane(docsBorrar);*/
                    JPanel panelBorrar = new showingDocsTable(tm, documents);
                    panelBorrar.add(new JLabel("S'esborraran els següents documents:"), BorderLayout.NORTH);
                    //panelBorrar.add(tableAutsScroll, BorderLayout.CENTER);
                    panelBorrar.add(new JLabel("Estàs d'acord?"), BorderLayout.SOUTH);
                    //docsBorrar.setFillsViewportHeight(true);

                    /*Mostramos tabla*/
                    int opt = JOptionPane.showOptionDialog(null, panelBorrar, "Esborrar documents seleccionats",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
                    if (opt == 0) { //s'esborren + missatge
                        borrarDocs(documentsBorrar);
                        for (int i = selectedRow.length; i > 0; --i) {
                            tableModel.removeRow(documents.getSelectedRow());
                        }
                        JOptionPane.showMessageDialog(null, "S'han esborrat correctament els documents", "Esborrar documents seleccionats", JOptionPane.DEFAULT_OPTION);
                        numRows = documents.getRowCount();
                    } else { //misstage no s'han borrat
                        JOptionPane.showMessageDialog(null, "No s'han esborrat cap document", "Esborrar documents seleccionats", JOptionPane.DEFAULT_OPTION);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No hi ha cap document seleccionat", "Esborrar documents seleccionats", JOptionPane.DEFAULT_OPTION);
                }
            }
        };

        borrarDocsSeleccionats.addActionListener(esborrarDocsSeleccionatsAction);
        esborrarDocsButton.addActionListener(esborrarDocsSeleccionatsAction);

        modT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newT = JOptionPane.showInputDialog(null, "Escriu el nou títol:", "");
                if(newT != null && !newT.equals("")) {
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar el títol del document " + documents.getValueAt(documents.getSelectedRow(), 0) + " de " + documents.getValueAt(documents.getSelectedRow(), 1) + " a " + newT + " ?", "Modificar títol", JOptionPane.YES_NO_OPTION);
                    if(opt == 0) {
                        boolean modificat = CtrlPresentacio.modificarTitol((String) documents.getValueAt(documents.getSelectedRow(), 1), (String) documents.getValueAt(documents.getSelectedRow(), 0), newT); //autor, titol, newT
                        if(modificat) {
                            //System.out.println(documents.getSelectedRow());
                            String autor = (String) documents.getValueAt(documents.getSelectedRow(), 1);
                            //tableModel.removeRow(documents.getSelectedRow());
                            //tableModel.addRow(new Object[]{newT, autor, "nop"});
                            documents.setValueAt(newT, documents.getSelectedRow(), 0);
                            newOrder(anteriorColumnPuls);
                            newOrder(anteriorColumnPuls);
                            JOptionPane.showMessageDialog(null, "S'ha modificat el títol a " + newT + ".");
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
                            String titol = (String) documents.getValueAt(documents.getSelectedRow(), 0);
                            //tableModel.removeRow(documents.getSelectedRow());
                            //tableModel.addRow(new Object[]{titol, newA, "nop"});
                            documents.setValueAt(newA, documents.getSelectedRow(), 1);
                            newOrder(anteriorColumnPuls);
                            newOrder(anteriorColumnPuls);
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
                    JOptionPane.showMessageDialog(null, "S'ha esborrat el document correctament");
                    numRows = documents.getRowCount();
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
                        numRows = documents.getRowCount();
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
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
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
                        else{
                            numRows = documents.getRowCount();
                        }
                        //System.out.println(docImp.get(1) + docImp.get(0) + docImp.get(2));
                    }
                }
                /*else { //MIRAR SI ESTE ELSE VA BIEN AHÍ
                    JOptionPane.showMessageDialog(null, "Alguna cosa ha sortit malament, torna a intentar-ho.");
                }*/
            }
        });

        exportarD.addActionListener(new ActionListener() {
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
                    JTextField newNom = new JTextField("",20);
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

                    if(opt == 0 && newNom.getText() != null && !newNom.getText().equals("") && !((String)tipus.getSelectedItem()).equals("")) {
                        String titol = (String)tableModel.getValueAt(documents.getSelectedRow(), 0);
                        String autor = (String)tableModel.getValueAt(documents.getSelectedRow(), 1);
                        String path = chooser.getSelectedFile().getAbsolutePath();
                        boolean totOk = CtrlPresentacio.exportaDocument(titol, autor, newNom.getText(), path);
                        if(!totOk) {
                            JOptionPane.showMessageDialog(null, "El document no s'ha pogut exportar", "Error exportació", JOptionPane.ERROR_MESSAGE);
                        }
                        //System.out.println(titol + " " + autor + " " + newNom.getText() + " " + chooser.getSelectedFile().getAbsolutePath());
                    }
                    else if(opt == 0 && (newNom.getText().equals("") || ((String)tipus.getSelectedItem()).equals(""))) {
                        JOptionPane.showMessageDialog(null, "Indica un nom i un format vàlids, no deixis camps buits.",
                                "Error exportació", JOptionPane.ERROR_MESSAGE);
                    }
                }
                /*else { //MIRAR SI ESTE ELSE VA BIEN AHÍ
                    JOptionPane.showMessageDialog(null, "Alguna cosa ha sortit malament, torna a intentar-ho.");
                }*/
            }
        });

        gestioExpBoolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.mostraVistaGestioExpBool(documents);
            }
        });

        mostrarD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacio.mostraViewMostrarCont((String)documents.getValueAt(documents.getSelectedRow(), 0), (String)documents.getValueAt(documents.getSelectedRow(), 1));
            }
        });

        documents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columna = documents.columnAtPoint(e.getPoint());
                if (e.getClickCount() == 2 && columna!=3) {
                    String titol = (String)documents.getValueAt(documents.getSelectedRow(), 1);
                    String autor = (String)documents.getValueAt(documents.getSelectedRow(), 0);
                    CtrlPresentacio.mostraViewEditar(titol, autor);
                    //CtrlPresentacio.ocultaViewPrincipal();
                }
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
                    else if(count == 1) message = "Amb el botó Importa es crea un nou document amb el contingut del document seleccionat. " +
                            "\nNomés es pot importar documents de tipus .txt o .xml, amb màxim 10 documents a l'hora. Si es seleccionen més " +
                            "\nnomés s'importaran els 10 primers en ordre alfabètic.";
                    else if(count == 2) message = "Amb el botó Esborrar documents seleccionats s'esborren els documents que estiguin seleccionats a la taula. " +
                            "\nEs pot seleccionar arrastrant amb el ratolí o clicant amb Ctrl+Clic.";
                    else if(count == 3) message = "Amb el botó Gestió expressions booleanes s'obre una vista per gestionar les expressions guardades.";
                    else if(count == 4) message = "El botó de Cerca mostra un desplegable amb 3 opcions de filtratge dels documents.";
                    else if(count == 5) message = "Amb els 3 punts verticals de cada document s'obre un panell d'opcions pel document.";
                    else if(count == 6) message = "També hi ha les funcionalitats de poder fer clic dret sobre un document per mostrar les seves opcions " +
                            "\n(equival als 3 punts verticals). I la funcionalitat de fer clic dret sobre un dels documents seleccionats i es mostrarà " +
                            "\nun botó per borrar tots els documents seleccionats.";
                    else if(count == 7) message = "Es pot tancar el programa en qualsevol moment clicant al botó amb la X de la cantonada superior dreta";
                    if(count == 0) opt = JOptionPane.showOptionDialog(null,message, "Panell d'ajuda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Següent"}, "Següent");
                    else if(count < 7) opt = JOptionPane.showOptionDialog(null,message, "Panell d'ajuda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Anterior", "Següent"}, "Següent");
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
                    /*JTable tits = new JTable(tm);

                    tits.setAutoCreateRowSorter(true);
                    tits.getTableHeader().setReorderingAllowed(false);
                    tits.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    tits.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
                    JPanel panelTits = new JPanel();
                    panelTits.setLayout(new BorderLayout());
                    JScrollPane tableAutsScroll = new JScrollPane(tits);
                    panelTits.add(tableAutsScroll);*/
                    //panelTits.setBounds(200, 200, 50, 50);

                    JPanel panelTits = new showingDocsTable(tm, documents);

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

                    /*JTable auts = new JTable(tm);
                    auts.setAutoCreateRowSorter(true);
                    auts.getTableHeader().setReorderingAllowed(false);
                    auts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    auts.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
                    JPanel panelAuts = new JPanel();
                    panelAuts.setLayout(new BorderLayout());
                    JScrollPane tableAutsScroll = new JScrollPane(auts);
                    panelAuts.add(tableAutsScroll);*/

                    JPanel panelAuts = new showingDocsTable(tm, documents);

                    JOptionPane.showMessageDialog(null, panelAuts, "Autors amb el prefix " + pref, JOptionPane.DEFAULT_OPTION);
                }
            }
        });

        /*esborrarDocsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /Creamos la tabla/
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

                    /Mostramos tabla/
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
        });*/

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

                        /*JTable docs = new JTable(tm);
                        docs.setAutoCreateRowSorter(true);
                        docs.getTableHeader().setReorderingAllowed(false);
                        docs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        docs.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
                        docs.getColumnModel().getColumn(1).setCellRenderer(new GestioCell("text"));
                        JPanel panelDocs = new JPanel();
                        panelDocs.setLayout(new BorderLayout());
                        JScrollPane tableAutsScroll = new JScrollPane(docs);
                        panelDocs.add(tableAutsScroll, BorderLayout.CENTER);*/

                        JPanel panelDocs = new showingDocsTable(tm, documents);

                        String estrat = "TF-IDF";
                        if(estrategia) estrat = "TF";
                        JLabel label = new JLabel( "Aquests són els " + num.getValue() +
                                " documents més rellevants segons les paraules escollides" +
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
                String autor = (String) documents.getValueAt(documents.getSelectedRow(), 1);
                String titol = (String) documents.getValueAt(documents.getSelectedRow(), 0);
                //System.out.println(titol + autor);
                JPanel message = new JPanel();
                SpinnerModel value = new SpinnerNumberModel(1, 1, documents.getRowCount(), 1);
                JSpinner num = new JSpinner(value);
                JFormattedTextField tf = ((JSpinner.DefaultEditor)num.getEditor()).getTextField(); //para evitar modificar por texto
                tf.setEditable(false);
                message.setLayout(new BorderLayout());
                message.add(new Label("Escriu el nombre de documents que vols llistar:"), BorderLayout.NORTH);
                message.add(num);

                int opt1 = JOptionPane.showOptionDialog(null, message, "Llistar documents semblants",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
                if(opt1 == 0) {
                    //System.out.println(num.getValue());
                    String[] tox = {"TF-IDF", "TF"};
                    int opt2 = JOptionPane.showOptionDialog(null, "Escull l'estratègia amb la que vols cercar:",
                            "Llistar documents semblants", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tox, tox[0]);
                    if (opt2 == 0 || opt2 == 1) {
                        //List<Pair<String, String>> rell = cp.llistarKDocumentsS("Jose", "Hola", Integer.parseInt(k), opt != 0);
                        boolean estrategia = false;
                        if(opt2 == 1) estrategia = true;
                        List<Pair<String, String>> docsSemblants = CtrlPresentacio.llistarKDocumentsS(autor, titol, (int) num.getValue(), estrategia);

                        Object[][] docsSemblantsObj = new Object[docsSemblants.size()][2];
                        for(int i = 0; i < docsSemblants.size(); ++i) {
                            Object[] docSemblantsObj = {docsSemblants.get(i).y, docsSemblants.get(i).x};
                            //System.out.println(docsSemblants.get(i).y + docsSemblants.get(i).x);
                            docsSemblantsObj[i] = docSemblantsObj;
                        }
                        String[] columns = {"Títols", "Autors"};
                        DefaultTableModel tm = new DefaultTableModel(docsSemblantsObj, columns) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };

                        /*JTable docs = new JTable(tm);
                        docs.setAutoCreateRowSorter(true);
                        docs.getTableHeader().setReorderingAllowed(false);
                        docs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        docs.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
                        docs.getColumnModel().getColumn(1).setCellRenderer(new GestioCell("text"));
                        JPanel panelDocs = new JPanel();
                        panelDocs.setLayout(new BorderLayout());
                        JScrollPane tableAutsScroll = new JScrollPane(docs);
                        panelDocs.add(tableAutsScroll, BorderLayout.CENTER);*/

                        JPanel panelDocs = new showingDocsTable(tm, documents);

                        String estrat = "TF-IDF";
                        if(estrategia) estrat = "TF";
                        JLabel label = new JLabel( "Aquests són els " + num.getValue() +
                                " documents més semblants al document " + titol + " de " +
                                autor + " amb l'estratègia " + estrat + ".");
                        panelDocs.add(label, BorderLayout.SOUTH);
                        JOptionPane.showMessageDialog(null, panelDocs, "Documents més semblants", JOptionPane.DEFAULT_OPTION);
                    }
                }
            }
        });

        cercaExp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panelBusquedaExp = new JPanel();
                JTextField newExp = new JTextField("",20);
                JPanel insertExp = new JPanel();
                insertExp.add(new JLabel("Escriu l'expressió per cercar:"));
                insertExp.add(newExp);
                panelBusquedaExp.setLayout(new BorderLayout());
                panelBusquedaExp.add(insertExp, BorderLayout.CENTER);

                String[] tox = {"Cerca", "Cancel·lar"};
                int opt1 = JOptionPane.showOptionDialog(null, panelBusquedaExp,
                        "Cercar per expressió booleana", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, tox, tox[0]);
                if (opt1 == 0 && !newExp.getText().equals("")) {
                    List<Pair<String, String>> docsXExp = CtrlPresentacio.cercarExpressioBooleana(newExp.getText());

                    Object[][] docsSemblantsObj = new Object[docsXExp.size()][2];
                    for(int i = 0; i < docsXExp.size(); ++i) {
                        Object[] docSemblantsObj = {docsXExp.get(i).y, docsXExp.get(i).x};
                        docsSemblantsObj[i] = docSemblantsObj;
                    }
                    String[] columns = {"Títols", "Autors"};
                    DefaultTableModel tm = new DefaultTableModel(docsSemblantsObj, columns) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };

                    JPanel panelDocs = new showingDocsTable(tm, documents);

                    String[] tox2 = {"Guardar expressió", "Cancel·lar"};
                    int opt2 = JOptionPane.showOptionDialog(null, panelDocs,
                            "Resultats de cerca per expressió", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, tox2, tox2[1]);
                    if (opt2 == 0) { //guardar l'expressió
                        JPanel panelGuardarExp = new JPanel();
                        JTextField newNom = new JTextField("",20);
                        JPanel insertNom = new JPanel();
                        insertNom.add(new JLabel("Escriu el nom de la nova expressió: "));
                        insertNom.add(newNom);
                        panelGuardarExp.setLayout(new BorderLayout());
                        panelGuardarExp.add(insertNom, BorderLayout.SOUTH);
                        JPanel expresioIntroduida = new JPanel();
                        newExp.setEditable(false);
                        expresioIntroduida.add(new JLabel("Expressió: "));
                        expresioIntroduida.add(newExp);
                        panelGuardarExp.add(expresioIntroduida, BorderLayout.NORTH);

                        int opt3 = JOptionPane.showOptionDialog(null, panelGuardarExp, "Guardar nova expressió booleana",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);

                        if(!newNom.getText().equals("") && opt3 == 0) {
                            boolean creat = CtrlPresentacio.creaExpressioBooleana(newNom.getText(), newExp.getText());
                            if(creat) {
                                JOptionPane.showMessageDialog(null, "S'ha guardat l'expressió booleana " +
                                        newExp.getText() + " amb nom " + newNom.getText() + ".", "Guardar nova expressió booleana",
                                        JOptionPane.DEFAULT_OPTION);
                            }
                            else { ///EL MOTIVO ES ESTE??
                                JOptionPane.showMessageDialog(null, "No s'ha creat l'expressió, el nom indicat ja existia.",
                                        null, JOptionPane.DEFAULT_OPTION);
                            }
                        }
                    }
                }
                if(newExp.getText() != null && !newExp.getText().equals("")) {

                }
                /*else if(newA.getText() != null && newT.getText() != null && (newT.getText().equals("") || newA.getText().equals(""))){ //FALTA PONER UN MENSAJE EN CASO DE Q SE DEJEN LOS 2 EN BLANCO Y SE CLIQ ACEPTAR
                    JOptionPane.showMessageDialog(null, "Introdueix un autor i un títol vàlid");
                }*/
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

    public void actualitzaTitol(String newT) {
       //System.out.println("EMPIEZO");
        //System.out.println(documents.getSelectedRowCount());
        //documents.getSelectedRowCount();
        String autor = (String) documents.getValueAt(documents.getSelectedRow(), 1);
        tableModel.addRow(new Object[]{newT, documents.getValueAt(documents.getSelectedRow(), 1), documents.getValueAt(documents.getSelectedRow(), 2)});
        tableModel.removeRow(documents.getSelectedRow());
        documents.repaint();
        int row = 0;
        for(int i = 0; i < documents.getRowCount() && row == 0; ++i) {
            if(((String) documents.getValueAt(i, 0)).equals(newT) && ((String) documents.getValueAt(i, 1)).equals(autor)) {
                row = i;
            }
        }
        documents.clearSelection();
        documents.addRowSelectionInterval(row, row);
    }

    public void actualitzaAutor(String newA) {
        //System.out.println("EMPIEZO");
        //System.out.println(documents.getSelectedRowCount());
        //documents.getSelectedRowCount();
        String titol = (String) documents.getValueAt(documents.getSelectedRow(), 0);
        tableModel.addRow(new Object[]{titol, newA, documents.getValueAt(documents.getSelectedRow(), 2)});
        tableModel.removeRow(documents.getSelectedRow());
        documents.repaint();
        int row = 0;
        for(int i = 0; i < documents.getRowCount() && row == 0; ++i) {
            if(((String) documents.getValueAt(i, 0)).equals(titol) && ((String) documents.getValueAt(i, 1)).equals(newA)) {
                row = i;
            }
        }
        documents.clearSelection();
        documents.addRowSelectionInterval(row, row);
        //System.out.println("ACABO");
    }

    public String getTitolDocObert() {
        return (String)documents.getValueAt(documents.getSelectedRow(), 0);
    }

    public String getAutorDocObert() {
        return (String)documents.getValueAt(documents.getSelectedRow(), 1);
    }

    private void newOrder(int column) {
        sortKeys.clear();
        if (columnRepetida != 1 && column == 1) {
            sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
            columnRepetida = column;
        } else if (columnRepetida != -0 && column == 0) {
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
            sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
            columnRepetida = column;
        } else if (columnRepetida == 1 && column == 1) {
            sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING)); //ASCENDING
            columnRepetida = -1;
        } else if (columnRepetida == 0 && column == 0) {
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
            sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING)); //ASCENDING
            columnRepetida = -1;
        }
        sorter.setSortKeys(sortKeys);
    }


    public static void main(String[] args) {
        CtrlPresentacio.mostraViewPrincipal();

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

