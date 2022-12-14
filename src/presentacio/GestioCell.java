package presentacio;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class GestioCell extends DefaultTableCellRenderer {

    private String tipo;
    private Font normal = new Font("Roboto Light", Font.PLAIN, 12);
    private Font bold = new Font("Roboto Light", Font.BOLD, 12);

    public GestioCell() {}

    /**
     * Constructor de la clase passant atribut tipus
     *
     * @param tipo
     */
    public GestioCell(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        Color colorFondo = new Color(192, 192, 192);

        if (selected) {
            this.setBackground(colorFondo);
        } else {
            this.setBackground(Color.white);
        }

        if (tipo.equals("text")) {
            this.setHorizontalAlignment(JLabel.LEFT);
            this.setText((String) value);
            this.setBackground((selected) ? colorFondo : Color.WHITE);
            this.setFont(normal);
            return this;
        }

        else if (tipo.equals("icon")) {
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(getClass().getResource("/presentacio/icons/moreOptions3.png")));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            return label;
        }

        else if (tipo.equals("int")) {
            this.setHorizontalAlignment(JLabel.CENTER);
            this.setText((String) value);
            this.setForeground((selected) ? new Color(255, 255, 255) : new Color(33, 116, 34));
            this.setBackground((selected) ? colorFondo : Color.WHITE);
            this.setFont(bold);
            return this;
        }
        return this;
    }
}



