package presentacio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VistaGestioExpBool extends JFrame{
    private JPanel panel1;
    private JPanel tablePanel;
    private JButton novaExpressióBooleanaButton;
    private JButton ajudaButton;
    private JButton xButton;
    private JLabel CapDocLabel;

    public VistaGestioExpBool() {
        setContentPane(panel1);
        //setResizable(true);
        setSize(500, 500);
        setTitle("Gestió expressions booleanes");
        setMinimumSize(new Dimension(200, 200));

        /*Creacion tabla*/
        String[] colums = {"Nom", "Expressió Booleana", " "};
        Object[][] titols = {
                //{"Dia", "Pep", LocalDate.now() + " " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)},
                {"Kimetsu", "{p1 p2 p3} & !joan", },
                {"Exp2", "{p1 p2 p3 p4} & !joan", }};

        DefaultTableModel tableModel = new DefaultTableModel(titols, colums){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable expressions = new JTable(tableModel);
        expressions.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(expressions.getModel());
        sorter.setSortable(1, false);
        sorter.setSortable(2, false);
        expressions.setRowSorter(sorter);
        expressions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        /* Muestra pop up de opciones*/
        expressions.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columna = expressions.columnAtPoint(e.getPoint());
                if (columna==2) {
                    popOptExp.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        /*Opción de modificar*/
        modificaE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newExp = JOptionPane.showInputDialog("Escriu la nova expressió booleana:");
                if(newExp != null) {
                    int opt = JOptionPane.showConfirmDialog(null, "Segur que vols modificar l'expressió booleana amb nom " + expressions.getValueAt(expressions.getSelectedRow(), 0)  + " a " + newExp + " ?", "Modificar expressió booleana", JOptionPane.YES_NO_OPTION);
                    if (opt == 0) {
                        expressions.setValueAt(newExp, expressions.getSelectedRow(), 1);
                        JOptionPane.showMessageDialog(null, "S'ha modificat correctament l'expressió");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No s'ha modificat l'expressió.");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "No s'ha modificat l'expressió.");
                }
            }
        });

        eliminaE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(null, "Segur que vols borrar l'exressió booleana " + expressions.getValueAt(expressions.getSelectedRow(), 0) + " permanentment?", "Esborrar expressió booleana", JOptionPane.YES_NO_OPTION);
                if (opt == 0) {
                    tableModel.removeRow(expressions.getSelectedRow());
                    /////////faltaria lo del label q te indiq q no tienes ninguna
                    JOptionPane.showMessageDialog(null, "S'ha esborrat l'expressió booleana correctament");
                } else {
                    JOptionPane.showMessageDialog(null, "No s'ha esborrat l'expressió booleana");
                }
            }
        });

        /*Buscar per exp bool*/
        buscaXE.addActionListener(new ActionListener() { ///cuando se selecciona llama al ctrlpres, este le pasa los docs, esta view se cierra y se enseñan los docs en la main view
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        /*Nova exp bool*/
        novaExpressióBooleanaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panelCreacio = new JPanel();
                JTextField newNomExp = new JTextField(20);
                JPanel insertNomExp = new JPanel();
                insertNomExp.add(new JLabel("Escriu el nom de l'expressió booleana:"));
                insertNomExp.add(newNomExp);

                JTextField newExp = new JTextField(20);
                JPanel insertExp = new JPanel();
                insertExp.add(new JLabel("Escriu l'expressió:"));
                insertExp.add(newExp);

                panelCreacio.setLayout(new BorderLayout());
                panelCreacio.add(newNomExp, BorderLayout.NORTH);
                panelCreacio.add(newExp, BorderLayout.SOUTH);

                JOptionPane.showMessageDialog(null, panelCreacio, "Nova expressió booleana", JOptionPane.QUESTION_MESSAGE);

                tableModel.addRow(new Object[]{newNomExp.getText(), newExp.getText()});
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        validate();
    }

    public static void main(String[] args) {
        JFrame exp = new VistaGestioExpBool();}

}
