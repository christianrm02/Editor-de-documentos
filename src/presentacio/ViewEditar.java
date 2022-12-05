package presentacio;
import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.io.IOException;
import presentacio.JEditableLabel;

public class ViewEditar extends JFrame {
    private JPanel panel1;
    private JButton desarButton;
    private JButton exportarButton;
    private JButton xButton;
    private JButton sortirButton;
    private JLabel titol;
    private JLabel autor;
    private JTextPane textPane1;

    public ViewEditar(String title, String t, String a, String cont) {
        setContentPane(panel1);
        setTitle(title + " " + t + ", " + a);
        setSize(1000, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        titol.setText(t);
        autor.setText(a);
        textPane1.setText(cont);

        validate();
    }



    public static void main(String[] args) {
        JFrame view = new ViewEditar("Editor de textos", "Hola q tal", "Paco", "Hola q tal, com estàs?");
    }
}

