import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.net.URL;

public class Main {
    final static String DESARROLLADOR = "Enrique Díaz Valenzuela";
    final static String VERSIONPROGRAMA = "0.4";
    final static String FECHAMODIFICACION = "2 / 12 / 2025";

    private static Color colorActual = Color.BLACK;
    private static float anchoTrazoActual = 1f;
    private static Path2D.Float trazoActual;

    public static void main(String[] args) {
        JFrame vPrincipal = new JFrame();
        configurarVPrincipal(vPrincipal);

        //region menuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu subMenuArchivo = new JMenu("Archivo");
        JMenu subVer = new JMenu("Ver");
        JMenu subAyuda = new JMenu("Ayuda");

        menuBar.add(subMenuArchivo);
        menuBar.add(subVer);
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

        //region subVer
        JCheckBoxMenuItem subBarraEstado = new JCheckBoxMenuItem("Mostrar barra de estado");
        subBarraEstado.setSelected(true);
        subVer.add(subBarraEstado);
        //endregion

        //region subAyuda
        JMenuItem subAcercaDe = new JMenuItem("A cerca de Este Programa");
        subAyuda.add(subAcercaDe);
        //endregion

        //region toolsBar
        JPanel menuHerramientas = new JPanel();
        configurarMenuHerramientas(menuHerramientas);

        //region formas
        JToolBar formas  = new JToolBar();
        JButton btnCiruclo = crearSubHerramienta("resources/circle.png");
        JButton btnCuadrado = crearSubHerramienta("resources/square.png");
        JButton btnTriangulo = crearSubHerramienta("resources/triangle.png");
        JButton btnPentagono = crearSubHerramienta("resources/pentagon.png");

        formas.add(btnCiruclo);
        formas.add(btnCuadrado);
        formas.add(btnTriangulo);
        formas.add(btnPentagono);
        //endregion

        //region herramientas
        JToolBar herramientas = new JToolBar();
        JButton btnLapiz = crearSubHerramienta("resources/brush.png");
        //JButton btnGoma = crearSubHerramienta("resources/eraser.png");    //no se va a hacer para esta entrega

        herramientas.add(btnLapiz);
        //herramientas.add(btnGoma);
        //endregion

        //region opcionesTrazo
        JToolBar opcionesTrazoSlider = new JToolBar();
        JToolBar opcionesTrazoColor = new JToolBar();

        JSlider slider = new JSlider(1, 80, 1);
        configurarSlider(slider);
        JButton btnColor = crearSubHerramienta("resources/palette.png");

        opcionesTrazoSlider.add(slider);
        opcionesTrazoColor.add(btnColor);
        //endregion

        //region ordenar menus
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
        //endregion

        menuHerramientas.add(seccionIzquierda, BorderLayout.WEST);
        menuHerramientas.add(seccionDerecha, BorderLayout.EAST);
        //endregion

        //region AreaDibujo
        AreaDibujo areaDibujo = new AreaDibujo();
        //endregion

        //region barraEstado
        JPanel estadoBar = new JPanel();
        estadoBar.setLayout(new BorderLayout());
        estadoBar.setPreferredSize(new Dimension(0, 20));

        //region info
        JPanel infoSection = new JPanel();
        infoSection.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 2));

        JLabel tamanioTrazo = new JLabel("Tamaño del trazo: " + anchoTrazoActual);

        JPanel panelColor = new JPanel();
        configurarPanelColor(panelColor);
        JLabel infoVersion = new JLabel("v " + VERSIONPROGRAMA);

        infoSection.add(tamanioTrazo);
        infoSection.add(panelColor);
        infoSection.add(infoVersion);
        //endregion

        estadoBar.add(infoSection, BorderLayout.EAST);
        //endregion

        vPrincipal.setJMenuBar(menuBar);
        vPrincipal.add(menuHerramientas, BorderLayout.NORTH);
        vPrincipal.add(areaDibujo, BorderLayout.CENTER);
        vPrincipal.add(estadoBar, BorderLayout.SOUTH);

        vPrincipal.setVisible(true);
        vPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //region FUNCIONALIDAD
        //BARRA DE ESTADO
        subBarraEstado.addActionListener(e -> {
            estadoBar.setVisible(subBarraEstado.isSelected());
        });

        //AYUDA
        subAcercaDe.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "Este programa ha sido desarrollado por: " + DESARROLLADOR + "\n" +
                            "Se encuentra en su versión " + VERSIONPROGRAMA + "\n" +
                            "La última modificación se hizo el " + FECHAMODIFICACION,
                    "A cerca de este Bloc de notas",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        //SLIDER
        slider.addChangeListener(e -> {
            anchoTrazoActual = slider.getValue();
            tamanioTrazo.setText("Tamaño del trazo: " + (int)anchoTrazoActual);
        });

        //SELECTOR DE COLOR
        btnColor.addActionListener(e -> {
            Color nuevoColor = JColorChooser.showDialog(vPrincipal, "Selecciona un color", colorActual);
            if (nuevoColor != null) {
                colorActual = nuevoColor;
                panelColor.setBackground(colorActual);
            }
        });

        //Evento al hacer clic
        areaDibujo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //Si es clic izquierdo, trazo
                if (SwingUtilities.isLeftMouseButton(e)) {
                    trazoActual = new Path2D.Float();
                    trazoActual.moveTo(e.getX(), e.getY());
                    areaDibujo.agregarTrazo(trazoActual, colorActual, anchoTrazoActual);
                }
            }
        });

        //Evento al arrastrar
        areaDibujo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                //Si mantenemos clic izquierdo, añadimos puntos al trazo actual
                if (!SwingUtilities.isRightMouseButton(e)) {
                    trazoActual.lineTo(e.getX(), e.getY());
                    areaDibujo.repaint();
                }
            }
        });

        //endregion
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

    private static void configurarSlider(JSlider slider) {
        slider.setPreferredSize(new Dimension(300, 24));
        slider.setFocusable(false);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
    }

    private static void configurarPanelColor(JPanel panelColor) {
        panelColor.setPreferredSize(new Dimension(30, 15));
        panelColor.setBackground(colorActual);
        panelColor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }
}