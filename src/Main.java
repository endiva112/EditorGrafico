import javax.swing.*;
import java.awt.*;
import java.net.URL;

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
        JMenuItem subNuevo = crearSubMenuArchivo("Nuevo", "resources/new.png");
        JMenuItem subAbrir = crearSubMenuArchivo("Abrir", "resources/open.png");
        JMenuItem subGuardar = crearSubMenuArchivo("Guardar", "resources/save.png");
        JMenuItem subSalir = crearSubMenuArchivo("Salir", "resources/exit.png");

        subMenuArchivo.add(subNuevo);
        subMenuArchivo.add(subAbrir);
        subMenuArchivo.add(subGuardar);
        subMenuArchivo.add(subSalir);
        //endregion

        //region toolsBar
        JPanel menuHerramientas = new JPanel();
        configurarMenuHerramientas(menuHerramientas);

        JToolBar formas  = new JToolBar();
        JButton btnCiruclo = crearSubHerramienta("resources/circle.png");
        JButton btnCuadrado = crearSubHerramienta("resources/square.png");
        JButton btnTriangulo = crearSubHerramienta("resources/triangle.png");
        JButton btnPentagono = crearSubHerramienta("resources/pentagon.png");

        formas.add(btnCiruclo);
        formas.add(btnCuadrado);
        formas.add(btnTriangulo);
        formas.add(btnPentagono);

        JToolBar herramientas = new JToolBar();
        JButton btnLapiz = crearSubHerramienta("resources/brush.png");
        JButton btnGoma = crearSubHerramienta("resources/eraser.png");

        herramientas.add(btnLapiz);
        herramientas.add(btnGoma);

        JToolBar opcionesTrazoSlider = new JToolBar();
        JToolBar opcionesTrazoColor = new JToolBar();

        JSlider slider = new JSlider(1, 80, 1);
        slider.setPreferredSize(new Dimension(300, 24));
        slider.setFocusable(false);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);

        JButton btnColor = crearSubHerramienta("resources/palette.png");

        opcionesTrazoSlider.add(slider);
        opcionesTrazoColor.add(btnColor);

        JPanel seccionIzquierda = new JPanel();
        seccionIzquierda.setLayout(new BoxLayout(seccionIzquierda, BoxLayout.X_AXIS));
        seccionIzquierda.add(Box.createHorizontalStrut(10)); // Margen izquierdo
        seccionIzquierda.add(formas);

        JPanel seccionDerecha = new JPanel();
        seccionDerecha.setLayout(new BoxLayout(seccionDerecha, BoxLayout.X_AXIS));
        seccionDerecha.add(herramientas);
        seccionDerecha.add(Box.createHorizontalStrut(10)); // Espacio entre herramientas y slider
        seccionDerecha.add(opcionesTrazoSlider);
        seccionDerecha.add(Box.createHorizontalStrut(10)); // Espacio entre slider y color
        seccionDerecha.add(opcionesTrazoColor);
        seccionDerecha.add(Box.createHorizontalStrut(10)); // Margen derecho

        formas.setFloatable(false);
        formas.setBorder(BorderFactory.createEmptyBorder());

        herramientas.setFloatable(false);
        herramientas.setBorder(BorderFactory.createEmptyBorder());

        opcionesTrazoSlider.setFloatable(false);
        opcionesTrazoSlider.setBorder(BorderFactory.createEmptyBorder());

        opcionesTrazoColor.setFloatable(false);
        opcionesTrazoColor.setBorder(BorderFactory.createEmptyBorder());

        menuHerramientas.add(seccionIzquierda, BorderLayout.WEST);
        menuHerramientas.add(seccionDerecha, BorderLayout.EAST);

        //endregion

        vPrincipal.setJMenuBar(menuBar);
        vPrincipal.add(menuHerramientas, BorderLayout.NORTH);

        vPrincipal.setVisible(true);
        vPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void configurarVPrincipal(JFrame vPrincipal) {
        vPrincipal.setTitle("Editor Gráfico");
        vPrincipal.setLayout(new BorderLayout());

        URL mainIconURL = Main.class.getResource("/resources/mainIcon.png");
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

    private static void configurarMenuHerramientas(JPanel menuHerramientas) {
        menuHerramientas.setLayout(new BorderLayout());
        menuHerramientas.setPreferredSize(new Dimension(0, 50));
    }

    private static JButton crearSubHerramienta(String urlIcono) {
        JButton aux = new JButton();

        URL IconURL = Main.class.getResource(urlIcono);
        if (IconURL != null) {
            ImageIcon icon = new ImageIcon(IconURL);
            Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon iconEscalado = new ImageIcon(img);
            aux.setIcon(iconEscalado);

        }
        aux.setFocusable(false); // se ve mejor al ser pulsado
        return aux;
    }
}