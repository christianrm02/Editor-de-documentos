package presentacio;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Vista per mostrar el contingut del document seleccionat a la ViewPrincipal.
 * @author Marc Roman
 */
public class ViewMostrarCont extends ViewEditar {
    /**
     * Mostra la vista ViewEditar sense les opcions de desar, exportar i modificar el contingut del document, és a dir, mostra el contingut del document.
     * @param cp: CtrlPresentacio: instància del controlador de presentació.
     * @param t: String: títol del document.
     * @param a: String: títol del document.
     * @param cont: String: contingut del document.
     */
    public ViewMostrarCont(CtrlPresentacio cp, String t, String a, String cont) {
        super(cp, t, a, cont);
        textPane1.setEditable(false);
        desarButton.setVisible(false);
        exportarButton.setVisible(false);
    }

    @Override
    protected void modificarTitol() {

    }


    protected void modificarAutor() {
    }
}