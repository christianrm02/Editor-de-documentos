package presentacio;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewMostrarCont extends ViewEditar {
    public ViewMostrarCont(String t, String a, String cont) {
        super(t, a, cont);
        textPane1.setEditable(false);
        desarButton.setVisible(false);
        exportarButton.setVisible(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //afegir tot el tractament per guardar tot a persistencia
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame view = new ViewMostrarCont("Hola q tal", "Paco", "Hola q tal, com estàs?");
    }
}