package presentacio;

import javax.swing.*;

public class ViewMostrarCont extends ViewEditar {
    public ViewMostrarCont(String title, String t, String a, String cont) {
        super(title, t, a, cont);
        textPane1.setEditable(false);
        desarButton.setVisible(false);
        exportarButton.setVisible(false);
    }

    public static void main(String[] args) {
        JFrame view = new ViewMostrarCont("Editor de textos", "Hola q tal", "Paco", "Hola q tal, com estàs?");
    }
}