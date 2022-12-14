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
    //CtrlPresentacio cp;


    public ViewPrincipal(String title, CtrlPresentacio cp) {
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
        //documentsList = cp.getTitolsAutors();
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
        documents = new JTable(tableModel);

        documents.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(documents.getModel());
        documents.setRowSorter(sorter);
        sortKeys = new ArrayList<>();
        sorter.setSortKeys(sortKeys);

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

        /* popup boton derecho sobre seleccion -> borrar */
        JMenuItem borrarDocsSeleccionats = new JMenuItem("Esborrar documents seleccionats");
        JPopupMenu popBorrarDocs = new JPopupMenu();
        popBorrarDocs.add(borrarDocsSeleccionats);

        /*Desplegable cerca*/
        busquedaButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                popBusqueda.show(e.getComponent(), busquedaButton.getX() + busquedaButton.getWidth() - 7, e.getY() - 30);
            }
        });

        /*eventos sobre la tabla*/
        documents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(documents.getRowCount() > 0) {
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
                    else {
                        int columna = documents.columnAtPoint(e.getPoint());
                        if (e.getClickCount() == 2 && columna!=3) {
                            String titol = (String)documents.getValueAt(documents.getSelectedRow(), 1);
                            String autor = (String)documents.getValueAt(documents.getSelectedRow(), 0);
                            cp.mostraViewEditar(titol, autor);
                            cp.ocultaViewPrincipal();
                        }
                        else if (columna == 3) {
                            popOptDoc.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                }
                else if(documents.getRowCount() == 0 && e.getClickCount() == 2) {
                    JOptionPane.showMessageDialog(null, "No hi ha cap autor encara, crea o importa un document.", "Error cap document.", JOptionPane.DEFAULT_OPTION);
                }
            }
        });

        /* borrardocs seleccionados */
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
                    JPanel panelBorrar = new showingDocsTable(tm, documents);
                    panelBorrar.add(new JLabel("S'esborraran els següents documents:"), BorderLayout.NORTH);
                    panelBorrar.add(new JLabel("Estàs d'acord?"), BorderLayout.SOUTH);

                    /*Mostramos tabla*/
                    int opt = JOptionPane.showOptionDialog(null, panelBorrar, "Esborrar documents seleccionats",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
                    if (opt == 0) { //s'esborren + missatge
                        for (int i = selectedRow.length; i > 0; --i) {
                            String titol = (String)tableModel.getValueAt(i, 0);
                            String autor = (String)tableModel.getValueAt(i, 0);
                            if(cp.esborrarDocument(autor, titol)) tableModel.removeRow(documents.getSelectedRow());
                        }
                        JOptionPane.showMessageDialog(null, "S'han esborrat correctament els documents", "Esborrar documents seleccionats", JOptionPane.DEFAULT_OPTION);
                    } else { //misstage no s'han borrat
                        JOptionPane.showMessageDialog(null, "No s'han esborrat cap document", "Esborrar documents seleccionats", JOptionPane.DEFAULT_OPTION);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No hi ha cap document seleccionat", "Esborrar documents seleccionats", JOptionPane.DEFAULT_OPTION);
                }
            }
        };

        /* Se puede borrar desde el boton o con boton derecho sobre selección */
        borrarDocsSeleccionats.addActionListener(esborrarDocsSeleccionatsAction);
        esborrarDocsButton.addActionListener(esborrarDocsSeleccionatsAction);

        /*modificar titulo*/
        modT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel insertTitol = new JPanel();
                JTextField newT = new JTextField("",20);
                insertTitol.add(new JLabel("Escriu el nou títol: "));
                insertTitol.add(newT);
                String[] opts = {"Sí", "Cancel·la"};
                int opt = JOptionPane.showOptionDialog(null, insertTitol, "Modificar títol",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);

                String titol = (String) documents.getValueAt(documents.getSelectedRow(), 0);
                String autor = (String) documents.getValueAt(documents.getSelectedRow(), 1);

                if(opt == 0 && !newT.getText().equals("")) {
                    int opt2 = JOptionPane.showConfirmDialog(null, "Segur que vols modificar el títol del document " +
                            titol + " de " + autor + " a " + newT.getText() + " ?", "Modificar títol", JOptionPane.YES_NO_OPTION);
                    if(opt2 == 0) {
                        boolean modificat = cp.modificarTitol(autor, titol, newT.getText()); //autor, titol, newT
                        if(modificat) {
                            //System.out.println(documents.getSelectedRow());
                            //tableModel.removeRow(documents.getSelectedRow());
                            //tableModel.addRow(new Object[]{newT, autor, "nop"});
                            documents.setValueAt(newT.getText(), documents.getSelectedRow(), 0);
                            newOrder(anteriorColumnPuls);
                            newOrder(anteriorColumnPuls);
                            JOptionPane.showMessageDialog(null, "S'ha modificat el títol a " + newT.getText() + ".");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "El document amb títol " + newT.getText() + " i autor " + autor + " ja existeix.");
                        }
                    }
                }
                else if(opt == 0) { //es titol buit
                    JOptionPane.showMessageDialog(null, "Introdueix un títol vàlid, no es permeten deixar camps buits.");
                }
                /*else {
                    JOptionPane.showMessageDialog(null, "No s'ha modificat el títol.");
                }*/
            }
        });

        /*modificar autor*/
        modA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel insertAutor = new JPanel();
                JTextField newA = new JTextField("", 20);
                insertAutor.add(new JLabel("Escriu el nou autor: "));
                insertAutor.add(newA);
                String[] opts = {"Sí", "Cancel·la"};
                int opt = JOptionPane.showOptionDialog(null, insertAutor, "Modificar autor",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);

                String titol = (String) documents.getValueAt(documents.getSelectedRow(), 0);
                String autor = (String) documents.getValueAt(documents.getSelectedRow(), 1);

                if (opt == 0 && !newA.getText().equals("")) {
                    int opt2 = JOptionPane.showConfirmDialog(null, "Segur que vols modificar l'autor del document " +
                            titol + " de " + autor + " a " + newA.getText() + " ?", "Modificar autor", JOptionPane.YES_NO_OPTION);
                    if (opt2 == 0) {
                        boolean modificat = cp.modificarAutor(autor, titol, newA.getText()); //autor, titol, newA
                        if (modificat) {
                            documents.setValueAt(newA.getText(), documents.getSelectedRow(), 1);
                            newOrder(anteriorColumnPuls);
                            newOrder(anteriorColumnPuls);
                            JOptionPane.showMessageDialog(null, "S'ha modificat l'autor a " + newA.getText() + ".");
                        } else {
                            JOptionPane.showMessageDialog(null, "El document amb títol " + newA.getText() + " i autor " +
                                    autor + " ja existeix.");
                        }
                    }
                } else if (opt == 0) { //es autor buit
                    JOptionPane.showMessageDialog(null, "Introdueix un autor vàlid, no es permeten deixar camps buits.");
                }
            }
        });

        /*borrar único doc*/
        borrarD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(null, "Segur que vols borrar el document " + documents.getValueAt(documents.getSelectedRow(), 0) + " de " + documents.getValueAt(documents.getSelectedRow(), 1) + " permanentment?", "Esborrar document", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    String titol = (String) documents.getValueAt(documents.getSelectedRow(), 0);
                    String autor = (String) documents.getValueAt(documents.getSelectedRow(), 1);
                    if(cp.esborrarDocument(autor, titol)) {
                        tableModel.removeRow(documents.getSelectedRow());
                        JOptionPane.showMessageDialog(null, "S'ha esborrat el document correctament");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No s'ha esborrat el document");
                }
            }
        });

        /*crear doc*/
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

                String[] opts = {"Sí", "Cancel·la"};
                int opt = JOptionPane.showOptionDialog(null, panelCreacio, "Creació document",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);

                if(opt == 0 && !newT.getText().equals("") && !newA.getText().equals("")) {
                    boolean creat = cp.crearDocument(newA.getText(), newT.getText());
                    if(creat) {
                        tableModel.addRow(new Object[]{newT.getText(), newA.getText(), LocalDate.now()
                                + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)});
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "El document amb títol " + newT.getText() + " i autor " + newA.getText() + " ja existeix.");
                    }
                }
                else if(opt == 0 && (newT.getText().equals("") || newA.getText().equals(""))) {
                    JOptionPane.showMessageDialog(null, "Indica un títol i autor vàlids, no deixis camps buits.",
                            "Error crear", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*importar docs, maximo 10, tipo txt o xml*/
        importaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.setFileFilter(new FileNameExtensionFilter("Compatibles amb l'aplicació, màxim 10", "txt", "xml"));
                chooser.setDialogTitle("Selecciona fitxers, màxim 10");
                chooser.setMultiSelectionEnabled(true);
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] arxius = chooser.getSelectedFiles();
                    if (arxius.length > 10) {
                        JOptionPane.showMessageDialog(null,
                                "Has seleccionat més arxius del màxim, s'importaran només els 10 primers.");
                    }
                    int min = 10;
                    if(arxius.length < 10) min = arxius.length;
                    List<String> paths = new ArrayList<>();
                    for(int i = 0; i < min; ++i) {
                        paths.add(arxius[i].getAbsolutePath());
                    }
                    List<Pair<String, String>> newDocs = cp.importaDocuments(paths);
                    for(int i = 0; i < newDocs.size(); ++i) {
                        Pair p = newDocs.get(i);
                        tableModel.addRow(new Object[]{p.y, p.x});
                    }
                }
            }
        });

        /*exporta único doc*/
        exportarD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("Selecciona una carpeta on guardar el document");
                chooser.setMultiSelectionEnabled(false);
                int result = chooser.showOpenDialog(null);
                if (result == chooser.APPROVE_OPTION) {
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

                    String[] opts = {"Sí", "Cancel·la"};
                    int opt = JOptionPane.showOptionDialog(null, panelExportacio, "Exportació document",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);

                    if(opt == 0 && !newNom.getText().equals("") && !((String)tipus.getSelectedItem()).equals("")) {
                        String titol = (String)tableModel.getValueAt(documents.getSelectedRow(), 0);
                        String autor = (String)tableModel.getValueAt(documents.getSelectedRow(), 1);
                        String path = chooser.getSelectedFile().getAbsolutePath();
                        String loc = path + "\\" + newNom.getText() + "." + (String)tipus.getSelectedItem(); //COMPROBAR Q SE HACE BIEN EL PATH
                        System.out.println(loc);
                        cp.exportaDocument(autor, titol, loc);
                    }
                    else if(opt == 0 && (newNom.getText().equals("") || ((String)tipus.getSelectedItem()).equals(""))) {
                        JOptionPane.showMessageDialog(null, "Indica un nom i un format vàlids, no deixis camps buits.",
                                "Error exportació", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        /*abre vista de gestión de exp booleanas*/
        gestioExpBoolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.mostraVistaGestioExpBool(documents);
            }
        });

        /*abrir contenido del doc, solo lectura*/
        mostrarD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.mostraViewMostrarCont((String)documents.getValueAt(documents.getSelectedRow(), 0), (String)documents.getValueAt(documents.getSelectedRow(), 1));
            }
        });

        /*documents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columna = documents.columnAtPoint(e.getPoint());
                if (e.getClickCount() == 2 && columna!=3) {
                    String titol = (String)documents.getValueAt(documents.getSelectedRow(), 1);
                    String autor = (String)documents.getValueAt(documents.getSelectedRow(), 0);
                    cp.mostraViewEditar(titol, autor);
                    cp.ocultaViewPrincipal();
                }
            }
        });*/

        /*CERQUES*/

        /*Llistar titols d'autor*/
        llistarTdeA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(documents.getRowCount() > 0) {
                    //String[] auts = cp.getAutors().toArray(new String[0]);
                    //String[] auts = {"Ana", "Carlos", "Joan", "Pepe"};
                    List<String> autorsList = cp.getAutors();
                    String[] autorsArray = autorsList.toArray(new String[0]);
                    JComboBox jca = new JComboBox(autorsArray);
                    jca.setEditable(false);

                    Object[] options = new Object[]{"Tria un autor: ", jca};

                    int opt = JOptionPane.showOptionDialog(null, options, "Llistar títols d'autor",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (opt == 0) {
                        String autorSelec = (String)jca.getSelectedItem();
                        List<String> titols = cp.llistarTitolsdAutors(autorSelec);
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
                        JPanel panelTits = new showingDocsTable(tm, documents);
                        JOptionPane.showMessageDialog(null, panelTits, "Títols de l'autor " + autorSelec, JOptionPane.DEFAULT_OPTION);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No hi ha cap autor encara, crea o importa un document.", "Error cap document.", JOptionPane.DEFAULT_OPTION);
                }
            }
        });

        /*llistar autors per prefix*/
        llistarAperP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(documents.getRowCount() > 0) {
                    JPanel insertPref = new JPanel();
                    JTextField pref = new JTextField("",20);
                    insertPref.add(new JLabel("Prefix de l'autor a llistar: "));
                    insertPref.add(pref);

                    String[] opts = {"Sí", "Cancel·la"};
                    int opt = JOptionPane.showOptionDialog(null, insertPref, "Llistar autors per prefix",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);

                    if (opt == 0) {
                        List<String> autors = cp.llistarAutorsPrefix(pref.getText());
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
                        JPanel panelAuts = new showingDocsTable(tm, documents);
                        JOptionPane.showMessageDialog(null, panelAuts, "Autors amb el prefix " + pref.getText(),
                                JOptionPane.DEFAULT_OPTION);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No hi ha cap autor encara, crea o importa un document.", "Error cap document.", JOptionPane.DEFAULT_OPTION);
                }
            }
        });

        /*cerca per rellevància*/
        cercaR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(documents.getRowCount() > 0) {
                    String paraules;
                    paraules = JOptionPane.showInputDialog(null, "Escriu les paraules que vols cercar per rellevància, separades per un espai:", "Llistar documents rellevants", -1);

                    JPanel message = new JPanel();
                    SpinnerModel value = new SpinnerNumberModel(1, 1, documents.getRowCount(), 1);
                    JSpinner num = new JSpinner(value);
                    JFormattedTextField tf = ((JSpinner.DefaultEditor)num.getEditor()).getTextField();
                    tf.setEditable(false);
                    message.setLayout(new BorderLayout());
                    message.add(new Label("Escriu el nombre de documents que vols llistar: "), BorderLayout.NORTH);
                    message.add(num);

                    int opt1 = JOptionPane.showOptionDialog(null, message, "Llistar documents semblants",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
                    if(opt1 == 0) {
                        String[] tox = {"TF-IDF", "TF"};
                        int opt2 = JOptionPane.showOptionDialog(null, "Escull l'estratègia amb la que vols cercar:", "Escollir estratègia", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tox, tox[0]);
                        if (opt2 == 0 || opt2 == 1) {
                            boolean estrategia = false;
                            if(opt2 == 1) estrategia = true;
                            List<Pair<String, String>> docsCondicio = cp.cercarPerRellevancia(paraules, (int) num.getValue(), estrategia);

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
                else {
                    JOptionPane.showMessageDialog(null, "No hi ha cap autor encara, crea o importa un document.", "Error cap document.", JOptionPane.DEFAULT_OPTION);
                }
            }
        });

        /*llistar K documents semblants*/
        llistarSemblants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String autor = (String) documents.getValueAt(documents.getSelectedRow(), 1);
                String titol = (String) documents.getValueAt(documents.getSelectedRow(), 0);
                JPanel message = new JPanel();
                SpinnerModel value = new SpinnerNumberModel(1, 1, documents.getRowCount()-1, 1);
                JSpinner num = new JSpinner(value);
                JFormattedTextField tf = ((JSpinner.DefaultEditor)num.getEditor()).getTextField(); //para evitar modificar por texto
                tf.setEditable(false);
                message.setLayout(new BorderLayout());
                message.add(new Label("Escriu el nombre de documents que vols llistar: "), BorderLayout.NORTH);
                message.add(num);

                int opt1 = JOptionPane.showOptionDialog(null, message, "Llistar documents semblants",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
                if(opt1 == 0) {
                    String[] tox = {"TF-IDF", "TF"};
                    int opt2 = JOptionPane.showOptionDialog(null, "Escull l'estratègia amb la que vols cercar:",
                            "Llistar documents semblants", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tox, tox[0]);
                    if (opt2 == 0 || opt2 == 1) {
                        boolean estrategia = false;
                        if(opt2 == 1) estrategia = true;
                        List<Pair<String, String>> docsSemblants = cp.llistarKDocumentsS(autor, titol, (int) num.getValue(), estrategia);

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

        /*cerca x expressió booleana*/
        cercaExp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(documents.getRowCount() > 0) {
                    JPanel panelBusquedaExp = new JPanel();
                    JTextField newExp = new JTextField("",20);
                    JPanel insertExp = new JPanel();
                    insertExp.add(new JLabel("Escriu l'expressió per cercar: "));
                    insertExp.add(newExp);
                    panelBusquedaExp.setLayout(new BorderLayout());
                    panelBusquedaExp.add(insertExp, BorderLayout.CENTER);

                    String[] tox = {"Cerca", "Cancel·lar"};
                    int opt1 = JOptionPane.showOptionDialog(null, panelBusquedaExp,
                            "Cercar per expressió booleana", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, tox, tox[0]);
                    if (opt1 == 0 && !newExp.getText().equals("")) {
                        List<Pair<String, String>> docsXExp = cp.cercarExpressioBooleana(newExp.getText());

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

                            String[] opts = {"Sí", "Cancel·la"};
                            int opt3 = JOptionPane.showOptionDialog(null, panelGuardarExp, "Guardar nova expressió booleana",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);

                            if(!newNom.getText().equals("") && opt3 == 0) {
                                boolean creat = cp.creaExpressioBooleana(newNom.getText(), newExp.getText());
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
                            else if(opt3 == 0) {
                                JOptionPane.showMessageDialog(null, "Indica un nom vàlid, no deixis camps buits.",
                                        "Error guardar expressió", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    /*if(newExp.getText() != null && !newExp.getText().equals("")) { //SE PUEDE BUSCAR POR EXPRESION VACIA?

                    }*/
                }
                else {
                    JOptionPane.showMessageDialog(null, "No hi ha cap autor encara, crea o importa un document.", "Error cap document.", JOptionPane.DEFAULT_OPTION);
                }
            }
        });

        /*indicaciones de los botones*/
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

        validate();
    }

    /*private void borrarDocs(Object[][] documentsBorrar) {
        List<Pair<String, String>> docsBorrarList = new ArrayList<>();
        Pair<String,String> p = new Pair();
        for(int i = 0; i < documentsBorrar.length; ++i) {
            p.x = (String) documentsBorrar[i][1];
            p.y = (String) documentsBorrar[i][0];
            //System.out.println(p.x + p.y);
            docsBorrarList.add(p);
        }
        cp.esborrarDocuments(docsBorrarList);
    }*/

    public void actualitzaTitol(String newT) {
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

    public void initDocs(List<Pair<String, String>> docsList){
        for(int i = 0; i < docsList.size(); ++i) {
            Pair p = docsList.get(i);
            tableModel.addRow(new Object[]{p.y, p.x});
        }
    }
}

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




