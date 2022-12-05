package presentacio;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class GestioCell extends DefaultTableCellRenderer {

    private String tipo = "text";

    //se definen por defecto los tipos de datos a usar
    private Font normal = new Font("Verdana", Font.PLAIN, 12);
    private Font bold = new Font("Verdana", Font.BOLD, 12);
    //etiqueta que almacenar el icono a mostrar
    private JLabel label = new JLabel();
    //iconos disponibles para ser mostrados en la etiqueta dependiendo de la columna que lo contenga
    private ImageIcon options = new ImageIcon(getClass().getResource("/presentacio/icons/moreOptions3.png"));


    public GestioCell() {

    }

    /**
     * Constructor explicito con el tipo de dato que tendr� la celda
     *
     * @param tipo
     */
    public GestioCell(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {


        //definimos colores por defecto
        Color colorFondo = null;
        Color colorFondoPorDefecto = new Color(192, 192, 192);
        Color colorFondoSeleccion = new Color(140, 140, 140);

        /*
         * Si la celda del evento es la seleccionada se asigna el fondo por defecto para la selecci�n
         */
        if (selected) {
            this.setBackground(colorFondoPorDefecto);
        } else {
            //Para las que no est�n seleccionadas se pinta el fondo de las celdas de blanco
            this.setBackground(Color.white);
        }

        /*
         * Se definen los tipos de datos que contendr�n las celdas basado en la instancia que
         * se hace en la ventana de la tabla al momento de construirla
         */
        if (tipo.equals("text")) {
            //si es tipo texto define el color de fondo del texto y de la celda as� como la alineaci�n
            if (focused) {
                colorFondo = colorFondoSeleccion;
            } else {
                colorFondo = colorFondoPorDefecto;
            }
            this.setHorizontalAlignment(JLabel.LEFT);
            this.setText((String) value);
            //this.setForeground( (selected)? new Color(255,255,255) :new Color(0,0,0) );
            //this.setForeground( (selected)? new Color(255,255,255) :new Color(32,117,32) );
            this.setBackground((selected) ? colorFondo : Color.WHITE);
            this.setFont(normal);
            //this.setFont(bold);
            return this;
        }

        //si el tipo es icono entonces valida cual icono asignar a la etiqueta.
        if (tipo.equals("icon")) {
            label.setIcon(options);
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            return label;
        }

        //definie si el tipo de dato el numerico para personalizarlo
        if (tipo.equals("int")) {
            if (focused) {
                colorFondo = colorFondoSeleccion;
            } else {
                colorFondo = colorFondoPorDefecto;
            }
            // System.out.println(value);
            this.setHorizontalAlignment(JLabel.CENTER);
            this.setText((String) value);
            this.setForeground((selected) ? new Color(255, 255, 255) : new Color(32, 117, 32));
            this.setBackground((selected) ? colorFondo : Color.WHITE);
            // this.setBackground( (selected)? colorFondo :Color.MAGENTA);
            this.setFont(bold);
            return this;
        }

        return this;


    }
}



