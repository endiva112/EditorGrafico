import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class Main {
    final static String DESARROLLADOR = "Enrique Díaz Valenzuela";
    final static String VERSIONPROGRAMA = "0.1";
    final static String FECHAMODIFICACION = "2 / 12 / 2025";

    public static void main(String[] args) {
        JFrame vPrincipal = new JFrame();
        configurarVPrincipal(vPrincipal);

        //Componentes
        JMenuBar menuBar = new JMenuBar();

        //region menuBar
        JMenu subMenuArchivo = new JMenu("Archivo");
        JMenu subAyuda = new JMenu("Ayuda");

        menuBar.add(subMenuArchivo);
        menuBar.add(subAyuda);
        //endregion

        //region subMenuArchivo
        JMenuItem subNuevo = crearSubMenuArchivo("Nuevo", "resources/file-circle-plus-solid-full.png");
        JMenuItem subAbrir = crearSubMenuArchivo("Abrir", "resources/folder-open-solid-full.png");
        JMenuItem subGuardar = crearSubMenuArchivo("Guardar", "resources/floppy-disk-solid-full.png");
        JMenuItem subSalir = crearSubMenuArchivo("Salir", "resources/door-open-solid-full.png");

        subMenuArchivo.add(subNuevo);
        subMenuArchivo.add(subAbrir);
        subMenuArchivo.add(subGuardar);
        subMenuArchivo.add(subSalir);
        //endregion


        vPrincipal.setJMenuBar(menuBar);

        vPrincipal.setVisible(true);
        vPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void configurarVPrincipal(JFrame vPrincipal) {
        vPrincipal.setTitle("Editor Gráfico");
        vPrincipal.setLayout(new BorderLayout());

        URL mainIconURL = Main.class.getResource("/resources/feather-pointed-solid-full.png");
        if (mainIconURL != null) {
            ImageIcon mainIcon = new ImageIcon(mainIconURL);
            vPrincipal.setIconImage(mainIcon.getImage());
        }

        vPrincipal.setSize(930, 580);
        vPrincipal.setResizable(false);
        vPrincipal.setLocationRelativeTo(null);
        vPrincipal.getContentPane().setBackground(Color.WHITE);
    }

    private static JMenuItem crearSubMenuArchivo(String nombreOpcion, String urlIcono) {
        JMenuItem aux = new JMenuItem(nombreOpcion);

        URL IconURL = Main.class.getResource(urlIcono);
        if (IconURL != null) {
            ImageIcon icon = new ImageIcon(IconURL);
            Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            ImageIcon iconEscalado = new ImageIcon(img);

            aux.setIcon(iconEscalado);
        }
        return aux;
    }
}