package presentacio;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Classe extends de DefaultTableCellRenderer. La funció d’aquesta classe és redefinir les cel·les de la JTable que 
 * mostra els documents per tal de donar un color concret, un estil concret o inclús afegir la icona dels tres punts 
 * verticals.
 * @author Christian Rivero 
 */
public class GestioCell extends DefaultTableCellRenderer {

    private Font normal = new Font("Roboto Light", Font.PLAIN, 12);
    private Font bold = new Font("Roboto Light", Font.BOLD, 12);

    /**
     * Constructor bàsic de la classe
     */
    public GestioCell() {}

    /**
     * Métode override que retorna el component amb l'estil desitjat.
     * @param table: JTable: JTable a la qaul pertany la cel·la.
     * @param value: Object: Valor de la cel·la.
     * @param selected: boolean: Booleà que indica si a cel·la està seleccionada.
     * @param focused: boolean: Booleà que indica si a cel·la està sent focused.
     * @param row: int: Fila a la qual pertany la cel·la.
     * @param column: int: Columna a la qual pertany la cel·la.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        Color colorFondo = new Color(192, 192, 192);

        if (selected) {
            this.setBackground(colorFondo);
        } else {
            this.setBackground(Color.white);
        }

        if (column == 0 || column == 1) {
            this.setHorizontalAlignment(JLabel.LEFT);
            this.setText((String) value);
            this.setBackground((selected) ? colorFondo : Color.WHITE);
            this.setFont(normal);
            return this;
        }

        else if (column == 2) {
            this.setHorizontalAlignment(JLabel.CENTER);
            this.setText((String) value);
            this.setForeground((selected) ? new Color(255, 255, 255) : new Color(33, 116, 34));
            this.setBackground((selected) ? colorFondo : Color.WHITE);
            this.setFont(bold);
            return this;
        }

        else if (column == 3) {
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(getClass().getResource("/presentacio/icons/moreOptions3.png")));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            return label;
        }

        return this;
    }
}



