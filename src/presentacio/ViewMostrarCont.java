package presentacio;

import javax.swing.*;

public class ViewMostrarCont extends ViewEditar {
    public ViewMostrarCont(CtrlPresentacio cp, String t, String a, String cont) {
        super(cp, t, a, cont);
        textPane1.setEditable(false);
        desarButton.setVisible(false);
        exportarButton.setVisible(false);
    }

    public static void main(String[] args) {
        JFrame view = new ViewMostrarCont(new CtrlPresentacio(), "Hola q tal", "Paco", "Hola q tal, com estàs?");
    }
}