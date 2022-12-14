package presentacio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class showingDocsTable extends JPanel {
    private int anteriorColumn;

    public showingDocsTable(DefaultTableModel tm, JTable documents) {
        anteriorColumn = -1;
        JTable table = new JTable(tm);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (table.getColumnCount() >= 1) {
            table.getColumnModel().getColumn(0).setCellRenderer(new GestioCell("text"));
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
        }
        if (table.getColumnCount() >= 2) {
            table.getColumnModel().getColumn(1).setCellRenderer(new GestioCell("text"));
            table.getColumnModel().getColumn(1).setPreferredWidth(100);
        }
        if (table.getColumnCount() >= 3) {
            table.getColumnModel().getColumn(2).setCellRenderer(new GestioCell("int"));
            table.getColumnModel().getColumn(2).setPreferredWidth(200);
        }
        if (table.getColumnCount() == 4) {
            table.getColumnModel().getColumn(3).setCellRenderer(new GestioCell("icon"));
            table.getColumnModel().getColumn(3).setPreferredWidth(10);
        }
        table.setRowHeight(25);

        if (table.getColumnCount() > 1) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(sorter);
            List<RowSorter.SortKey> sortKeys = new ArrayList<>();
            sorter.setSortKeys(sortKeys);

            JTableHeader header = table.getTableHeader();
            header.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    Point point = e.getPoint();
                    int column = table.columnAtPoint(point);
                    sortKeys.clear();
                    if (anteriorColumn != 1 && column == 1) {
                        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
                        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
                        anteriorColumn = column;
                    } else if (anteriorColumn != -0 && column == 0) {
                        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
                        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
                        anteriorColumn = column;
                    } else if (anteriorColumn == 1 && column == 1) {
                        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
                        sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING)); //ASCENDING
                        anteriorColumn = -1;
                    } else if (anteriorColumn == 0 && column == 0) {
                        sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
                        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING)); //ASCENDING
                        anteriorColumn = -1;
                    }
                    sorter.setSortKeys(sortKeys);
                }
            });
        }

        if(table.getColumnCount() > 1) {
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        String titol = (String) table.getValueAt(table.getSelectedRow(), 0);
                        String autor = (String) table.getValueAt(table.getSelectedRow(), 1);
                        int row = -1;
                        for (int i = 0; i < documents.getRowCount() && row == -1; ++i) {
                            String titolDocs = (String) documents.getValueAt(i, 0);
                            String autorDocs = (String) documents.getValueAt(i, 1);
                            if(titol.equals(titolDocs) && autor.equals(autorDocs)) row = i;
                        }
                        documents.clearSelection();
                        documents.addRowSelectionInterval(row, row);
                    }
                }
            });
        }
        setLayout(new BorderLayout());
        JScrollPane tableAutsScroll = new JScrollPane(table);
        add(tableAutsScroll);
    }
}
