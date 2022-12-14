package presentacio;

import transversal.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static javax.swing.SwingUtilities.isRightMouseButton;

public class ViewGestioExpBool extends JFrame{
    private JPanel panel1;
    private JPanel tablePanel;
    private JButton novaExpressióBooleanaButton;
    private JButton ajudaButton;
    private JButton esborrarExpressionsSeleccionadesButton;
    private JButton enrereButton;
    DefaultTableModel tableModel;

    public ViewGestioExpBool(JTable documents, CtrlPresentacio cp) {
        setContentPane(panel1);
        setSize(800, 500);
        setMinimumSize(new Dimension(500, 300));
        setTitle("Gestió expressions booleanes");

        /*Creacion tabla*/
        String[] colums = {"Nom", "Expressió Booleana", " "};
        Object[][] titols = {};

        tableModel = new DefaultTableModel(titols, colums){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable expressions = new JTable(tableModel);
        expressions.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(expressions.getModel());
        //sorter.setSortable(1, false);
        //sorter.setSortable(2, false);
        expressions.setRowSorter(sorter);
        //expressions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //expressions.setEnabled(false);///arreglar, necesito bloquear edicion tabla

        tablePanel.setLayout(new BorderLayout());
        JScrollPane tableScroll = new JScrollPane(expressions);
        tablePanel.add(tableScroll, BorderLayout.CENTER);

        expressions.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
        expressions.getColumnModel().getColumn(1).setCellRenderer(new GestioCell("text"));
        expressions.getColumnModel().getColumn(2).setCellRenderer(new GestioCell("icon"));
        expressions.setRowHeight(25);
        expressions.getColumnModel().getColumn(0).setPreferredWidth(100);
        expressions.getColumnModel().getColumn(1).setPreferredWidth(100);
        expressions.getColumnModel().getColumn(2).setPreferredWidth(25);
        expressions.getTableHeader().setReorderingAllowed(false);

        /*Creacion popup opciones*/
        JMenuItem modificaE=new JMenuItem("Modifica");
        JMenuItem eliminaE=new JMenuItem("Elimina");
        JMenuItem buscaXE=new JMenuItem("Busca");
        JPopupMenu popOptExp = new JPopupMenu();
        popOptExp.add(modificaE); popOptExp.add(eliminaE); popOptExp.add(buscaXE);

        JPopupMenu popBorrarExp = new JPopupMenu();
        JMenuItem eliminaExpsSelected=new JMenuItem("Elimina expressions seleccionades");
        popBorrarExp.add(eliminaExpsSelected);

        expressions.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(expressions.getRowCount() > 0) {
                    if (isRightMouseButton(e)) {
                        int row = expressions.rowAtPoint(e.getPoint());
                        boolean botoDretSeleccionat = false;
                        int[] selection = expressions.getSelectedRows();
                        for (int i = 0; i < selection.length && !botoDretSeleccionat; ++i) { ////MEJORABLE? SE PUEDE METER EN UN SET?
                            if (selection[i] == row) botoDretSeleccionat = true;
                        }
                        if (botoDretSeleccionat) { //si está seleccionada previamente -> borrarDocs
                            popBorrarExp.show(e.getComponent(), e.getX(), e.getY());
                        } else { //abrir popups opciones en un doc
                            expressions.clearSelection();
                            expressions.addRowSelectionInterval(row, row);
                            popOptExp.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                    else {
                        int columna = expressions.columnAtPoint(e.getPoint());
                        if (columna==2) {
                            popOptExp.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                }
                else if(expressions.getRowCount() == 0 && e.getClickCount() == 0) {
                    JOptionPane.showMessageDialog(null, "No hi ha cap expressió encara, les pots crear.", "Error cap expressió.", JOptionPane.DEFAULT_OPTION);
                }
            }
        });

        /* Se puede borrar desde el boton o con boton derecho sobre selección */
        ActionListener esborrarExpSeleccionatsAction = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                if(expressions.getRowCount() > 0) {
                    /*Creamos la tabla*/
                    if (expressions.getSelectedRowCount() > 0) {
                        Object[][] expressionsBorrar = new Object[expressions.getSelectedRowCount()][2];
                        Object[] columnsBorrar = {"Títols", "Autors"};

                        int index = 0;
                        int selectedRow[] = expressions.getSelectedRows();
                        for (int i : selectedRow) {
                            Object[] docBorrar = {expressions.getValueAt(i, 0), expressions.getValueAt(i, 1)};
                            expressionsBorrar[index] = docBorrar;
                            ++index;
                            //System.out.println(selectedRow[i]);
                        }

                        DefaultTableModel tm = new DefaultTableModel(expressionsBorrar, columnsBorrar) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        JPanel panelBorrar = new showingDocsTable(tm, expressions);
                        panelBorrar.add(new JLabel("S'esborraran les següents expressions booleanes:"), BorderLayout.NORTH);
                        panelBorrar.add(new JLabel("Estàs d'acord?"), BorderLayout.SOUTH);
                        //panelBorrar.setFillsViewportHeight(true);

                        /*Mostramos tabla*/
                        int opt = JOptionPane.showOptionDialog(null, panelBorrar, "Esborrar expressions seleccionades",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
                        if (opt == 0) { //s'esborren + missatge
                            for (int i = selectedRow.length; i > 0; --i) {
                                cp.deleteExpressioBooleana((String)expressions.getValueAt(expressions.getSelectedRow(), 0));
                                tableModel.removeRow(expressions.getSelectedRow());
                            }
                            JOptionPane.showMessageDialog(null, "S'han esborrat correctament les expressions", "Esborrar expressions seleccionades", JOptionPane.DEFAULT_OPTION);
                        } else { //misstage no s'han borrat
                            JOptionPane.showMessageDialog(null, "No s'han esborrat cap expressió", "Esborrar expressions seleccionades", JOptionPane.DEFAULT_OPTION);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No hi ha cap expressió seleccionada", "Error esborrar expressions seleccionades", JOptionPane.DEFAULT_OPTION);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No hi ha cap expressió creada.", "Cap expressió", JOptionPane.DEFAULT_OPTION);
                }
            }
        };

        eliminaE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(null, "Segur que vols borrar l'expressió booleana " +
                        expressions.getValueAt(expressions.getSelectedRow(), 0) + " permanentment?", "Esborrar expressió booleana",
                        JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    cp.deleteExpressioBooleana((String)expressions.getValueAt(expressions.getSelectedRow(), 0));
                    tableModel.removeRow(expressions.getSelectedRow());
                    JOptionPane.showMessageDialog(null, "S'ha esborrat l'expressió booleana correctament.");
                } else {
                    JOptionPane.showMessageDialog(null, "No s'ha esborrat l'expressió booleana.");
                }
            }
        });

        eliminaExpsSelected.addActionListener(esborrarExpSeleccionatsAction);
        esborrarExpressionsSeleccionadesButton.addActionListener(esborrarExpSeleccionatsAction);

        /*Opción de modificar*/
        modificaE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField newExp = new JTextField("",20);
                JPanel insertExp = new JPanel();
                insertExp.add(new JLabel("Escriu la nova expressió booleana: "));
                insertExp.add(newExp);

                String[] opts = {"Sí", "Cancel·la"};
                int opt = JOptionPane.showOptionDialog(null, insertExp, "Modificar expressió booleana",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);
                if(opt == 0 && !newExp.getText().equals("")) {
                    int opt2 = JOptionPane.showConfirmDialog(null, "Segur que vols modificar l'expressió booleana amb nom " +
                            expressions.getValueAt(expressions.getSelectedRow(), 0)  + " a " + newExp.getText() + " ?",
                            "Modificar expressió booleana", JOptionPane.YES_NO_OPTION);
                    if (opt2 == 0) {
                        cp.modExpressioBooleana((String)expressions.getValueAt(expressions.getSelectedRow(), 0), newExp.getText());
                        //expressions.setValueAt(newExp, expressions.getSelectedRow(), 1);
                        tableModel.addRow(new Object[]{expressions.getValueAt(expressions.getSelectedRow(), 0), newExp.getText()});
                        tableModel.removeRow(expressions.getSelectedRow());
                        JOptionPane.showMessageDialog(null, "S'ha modificat correctament l'expressió");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No s'ha modificat l'expressió.");
                    }
                }
                else if (opt == 0){ //camp buit
                    JOptionPane.showMessageDialog(null, "Indica una expressió vàlida, no deixis camps buits.",
                            "Error modificació", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*Buscar per exp bool*/
        buscaXE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); //se oculta y aparece al final para q se pueda ver como se selecciona en la main view
                List<Pair<String, String>> docsXExp = cp.cercarExpressioBooleana((String)expressions.getValueAt(expressions.getSelectedRow(), 1));

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
                JOptionPane.showMessageDialog(null, panelDocs, "Resultats de cerca per expressió", JOptionPane.DEFAULT_OPTION);
                setVisible(true);
            }
        });

        /*Nova exp bool*/
        novaExpressióBooleanaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField newNom = new JTextField("",20);
                JPanel insertNom = new JPanel();
                insertNom.add(new JLabel("Nom de l'expressió: "));
                insertNom.add(newNom);

                JTextField newExp = new JTextField("", 20);
                JPanel insertExp = new JPanel();
                insertExp.add(new JLabel("Expressió booleana: "));
                insertExp.add(newExp);

                JPanel panelCreacio = new JPanel();
                panelCreacio.setLayout(new BorderLayout());
                panelCreacio.add(insertNom, BorderLayout.NORTH);
                panelCreacio.add(insertExp, BorderLayout.SOUTH);

                String[] opts = {"Sí", "Cancel·la"};
                int opt = JOptionPane.showOptionDialog(null, panelCreacio, "Creació expressió booleana",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);

                JOptionPane.showMessageDialog(null, panelCreacio, "Creació", JOptionPane.QUESTION_MESSAGE);

                if(opt == 0 && !newNom.getText().equals("") && !newNom.getText().equals("")) {
                    boolean creat = cp.creaExpressioBooleana(newNom.getText(), newExp.getText());
                    if(creat) {
                        tableModel.addRow(new Object[]{newNom.getText(), newExp.getText()});
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "L'expressió booleana ja existia."); ///YA EXISTE SU NOMBRE O SU EXPRESIÓN?
                    }
                }
                else if(opt == 0 && (newNom.getText().equals("") || newNom.getText().equals(""))){
                    JOptionPane.showMessageDialog(null, "Indica un nom i una expressió vàlides, no deixis camps buits.",
                            "Error creació expressió booleana", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        enrereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); //HACE FALTA??
                dispose();
                //cp.mostraViewPrincipal();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false); //HACE FALTA??
                dispose();
                //cp.mostraViewPrincipal();
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
                    if(count == 0) message = "Amb el botó Nova expressió booleana es crea una nova expressió.";
                    else if(count == 1) message = "Amb el botó Enrere es torna a la pantalla d'edició de documents";
                    else if(count == 2) message = "Amb el botó Esborrar expressions seleccionades s'esborren les expressions que estiguin seleccionades a la taula. " +
                            "\nEs pot seleccionar arrastrant amb el ratolí o clicant amb Ctrl+Clic.";
                    else if(count == 3) message = "Amb els 3 punts verticals de cada expressió s'obre un panell d'opcions per l'expressió.";
                    else if(count == 4) message = "També hi ha les funcionalitats de poder fer clic dret sobre una expressió per mostrar les seves opcions " +
                            "\n(equival als 3 punts verticals). I la funcionalitat de fer clic dret sobre una de les expressions seleccionades i es mostrarà " +
                            "\nun botó per borrar totes les expressions seleccionades.";
                    else if(count == 5) message = "Es pot tancar el programa en qualsevol moment clicant al botó amb la X de la cantonada superior dreta";
                    if(count == 0) opt = JOptionPane.showOptionDialog(null,message, "Panell d'ajuda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Següent"}, "Següent");
                    else if(count < 5) opt = JOptionPane.showOptionDialog(null,message, "Panell d'ajuda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Anterior", "Següent"}, "Següent");
                    else opt = JOptionPane.showOptionDialog(null,message, "Panell d'ajuda", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Anterior", "Acaba"}, "Acaba");
                }
            }
        });

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        pack();
        validate();
    }

    public void initExp(List<Pair<String, String>> expList){
        for(int i = 0; i < expList.size(); ++i) {
            Pair p = expList.get(i);
            tableModel.addRow(new Object[]{p.y, p.x});
        }
    }
}
