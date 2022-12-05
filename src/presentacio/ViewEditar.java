package presentacio;
import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import presentacio.JEditableLabel;

public class ViewEditar extends JFrame {
    private JPanel panel1;
    protected JButton desarButton;
    protected JButton exportarButton;
    private JButton xButton;
    private JButton sortirButton;
    private JLabel titol;
    private JLabel autor;
    protected JTextPane textPane1;

    public ViewEditar(String title, String t, String a, String cont) {
        setContentPane(panel1);
        setTitle(title + " " + t + ", " + a);
        setSize(1000, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        titol.setText(t);
        autor.setText(a);
        textPane1.setText(cont);
        /*
        sortirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contNou = textPane1.getText();
                if (!cont.equals(contNou)) cp.desarDocument(contNou);
                cp.tancaDocument();
            }
        });*/

        /*
        desarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contNou = textPane1.getText();
                if (!cont.equals(contNou)) cp.desarDocument(contNou);
            }
        });*/

        validate();
    }



    public static void main(String[] args) {
        JFrame view = new ViewEditar("Editor de textos", "Hola q tal", "Paco", "Hola q tal, com estàs?");
    }
}

