package presentacio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Main extends JFrame {
    private JPanel panel1;
    private JButton creaButton;
    private JButton importaButton;
    private JButton ajudaButton;
    private JButton xButton;
    private JPanel tablePanel;
    private JButton button1;
    private JButton busquedaButton;


    public Main(String title) {
        setContentPane(panel1);
        setTitle("Editor de textos");
        setSize(1000, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        /*list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = list1.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Object o = list1.getModel().getElementAt(index);
                        System.out.println("Double-clicked on: " + o.toString());
                    }
                }
            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });*/


        JLabel moreOptions = new JLabel();
        Icon iconMoreOptions = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/icons/moreOptions.png")));
        moreOptions.setIcon(iconMoreOptions);

        String[] colums = {"Titols", "Autors", "Darrera modificació", ""};
        Object[][] titols = {
                {"Dia", "Pep", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Day", "Pepe", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Rio", "Romero", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Camino", "Alex", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Dia", "Zacarias", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"SD", "Juan", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"La hostia", "JAGGER", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Illo no veas", "Illojuan", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Siempre tipo fuego", "Oak", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Adelante Pikachu", "Ash", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Tornado de fuego", "Axel", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Mano mágica", "Marc", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Lo tengo!", "Layton", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Ay ay ay", "Luke", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Roooar", "Luxray", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Yo q sé", "Yo", LocalDate.now() + " " + LocalTime.now(), moreOptions},
                {"Lápiz", "Antes", LocalDate.of(1972, 2, 23) + " " + LocalTime.of(20, 01, 15, 0003), moreOptions},
                {"Vida", "Después", LocalDate.of(1972, 2, 23) + " " + LocalTime.of(20, 03, 15, 0003), moreOptions}};
        DefaultTableModel tableModel = new DefaultTableModel(titols, colums);
        JTable documents = new JTable(tableModel);
        documents.setAutoResizeMode(5);
        documents.setAutoCreateRowSorter(true);
        tablePanel.setLayout(new BorderLayout());
        JScrollPane tableScroll = new JScrollPane(documents);
        documents.setFillsViewportHeight(true);
        tablePanel.add(tableScroll, BorderLayout.CENTER);


        JMenu menu;
        JMenuItem i1, i2, i3;
        JMenuBar mb = new JMenuBar();
        menu = new JMenu("Búsqueda");
        i1=new JMenuItem("Llistar títols autor");
        i2=new JMenuItem("Llistar autors prefix");
        i3=new JMenuItem("Cerca per rellevància");
        menu.add(i1); menu.add(i2); menu.add(i3);
        mb.add(menu);
        JPopupMenu popBusqueda = new JPopupMenu();
        popBusqueda.add(i1); popBusqueda.add(i2); popBusqueda.add(i3);

        busquedaButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                popBusqueda.show(e.getComponent(), busquedaButton.getX()+busquedaButton.getWidth(), e.getY()-30);
            }
        });

        validate();

    }

    public static void main(String[] args) {
        JFrame view = new Main("Editor de textos");
    }
}

/*
String[] colums = {"Titols", "Autors", "Darrera modificació"};
        Object[][] titols = {
                {"Dia", "Pep", LocalDateTime.now()},
                {"Day", "Pepe"},
                {"Rio", "Romero"},
                {"Camino", "Alex"},
                {"Dia", "Zacarias"},
                {"SD", "Juan"},
                {"La hostia", "JAGGER"},
                {"Illo no veas", "Illojuan"},
                {"Siempre tipo fuego", "Oak"},
                {"Adelante Pikachu", "Ash"},
                {"Tornado de fuego", "Axel"},
                {"Mano mágica", "Marc"},
                {"Lo tengo!", "Layton"},
                {"Ay ay ay", "Luke"},
                {"Roooar", "Luxray"},
                {"Yo q sé", "Yo"},
                {"Lápiz", "Boli"},
                {"Vida", "Alex"}};
*/

