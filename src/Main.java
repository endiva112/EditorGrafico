import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Main {
    final static String DESARROLLADOR = "Enrique Díaz Valenzuela";
    final static String VERSIONPROGRAMA = "1.0";
    final static String FECHAMODIFICACION = "2 / 12 / 2025";

    private static Color colorActual = Color.BLACK;
    private static float anchoTrazoActual = 1f;
    private static Path2D.Float trazoActual;
    private static String herramientaActual = "lapiz"; // "lapiz", "circulo", "cuadrado", "triangulo", "pentagono"
    private static Point puntoInicial;
    private static boolean conRelleno = false; // Controla si las formas tienen relleno

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
        JButton btnCirculo = crearSubHerramienta("resources/circle.png");
        JButton btnCuadrado = crearSubHerramienta("resources/square.png");
        JButton btnTriangulo = crearSubHerramienta("resources/triangle.png");
        JButton btnPentagono = crearSubHerramienta("resources/pentagon.png");

        formas.add(btnCirculo);
        formas.add(btnCuadrado);
        formas.add(btnTriangulo);
        formas.add(btnPentagono);
        //endregion

        //region herramientas
        JToolBar herramientas = new JToolBar();
        JButton btnLapiz = crearSubHerramienta("resources/brush.png");

        herramientas.add(btnLapiz);
        //endregion

        //region opcionesTrazo
        JToolBar opcionesTrazoSlider = new JToolBar();
        JToolBar opcionesTrazoColor = new JToolBar();
        JToolBar opcionesRelleno = new JToolBar();

        JSlider slider = new JSlider(1, 80, 1);
        configurarSlider(slider);
        JButton btnColor = crearSubHerramienta("resources/palette.png");

        // Botones para relleno
        JButton btnSinRelleno = new JButton("Sin relleno");
        JButton btnConRelleno = new JButton("Con relleno");
        btnSinRelleno.setFocusable(false);
        btnConRelleno.setFocusable(false);

        // Resaltar el botón activo
        btnSinRelleno.setBackground(new Color(200, 200, 200));

        opcionesRelleno.add(btnSinRelleno);
        opcionesRelleno.add(btnConRelleno);

        opcionesTrazoSlider.add(slider);
        opcionesTrazoColor.add(btnColor);
        //endregion

        //region ordenar menus
        JPanel seccionIzquierda = new JPanel();
        seccionIzquierda.setLayout(new BoxLayout(seccionIzquierda, BoxLayout.X_AXIS));
        seccionIzquierda.add(Box.createHorizontalStrut(10));
        seccionIzquierda.add(formas);

        JPanel seccionDerecha = new JPanel();
        seccionDerecha.setLayout(new BoxLayout(seccionDerecha, BoxLayout.X_AXIS));
        seccionDerecha.add(herramientas);
        seccionDerecha.add(Box.createHorizontalStrut(10));
        seccionDerecha.add(opcionesRelleno);
        seccionDerecha.add(Box.createHorizontalStrut(10));
        seccionDerecha.add(opcionesTrazoSlider);
        seccionDerecha.add(Box.createHorizontalStrut(10));
        seccionDerecha.add(opcionesTrazoColor);
        seccionDerecha.add(Box.createHorizontalStrut(10));

        formas.setFloatable(false);
        formas.setBorder(BorderFactory.createEmptyBorder());

        herramientas.setFloatable(false);
        herramientas.setBorder(BorderFactory.createEmptyBorder());

        opcionesTrazoSlider.setFloatable(false);
        opcionesTrazoSlider.setBorder(BorderFactory.createEmptyBorder());

        opcionesTrazoColor.setFloatable(false);
        opcionesTrazoColor.setBorder(BorderFactory.createEmptyBorder());

        opcionesRelleno.setFloatable(false);
        opcionesRelleno.setBorder(BorderFactory.createEmptyBorder());
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

        //region menuContextual
        JPopupMenu menuContextual = new JPopupMenu();

        JMenuItem mDeshacerTrazo = new JMenuItem("Deshacer ultimo trazo");
        menuContextual.add(mDeshacerTrazo);

        JMenuItem mBorrarTodo = new JMenuItem("Borrar todo");
        menuContextual.add(mBorrarTodo);

        areaDibujo.setComponentPopupMenu(menuContextual);
        //endregion

        vPrincipal.setJMenuBar(menuBar);
        vPrincipal.add(menuHerramientas, BorderLayout.NORTH);
        vPrincipal.add(areaDibujo, BorderLayout.CENTER);
        vPrincipal.add(estadoBar, BorderLayout.SOUTH);

        vPrincipal.setVisible(true);
        vPrincipal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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
                    "A cerca de este Editor Gráfico",
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

        //OPCIONES DE RELLENO
        btnSinRelleno.addActionListener(e -> {
            conRelleno = false;
            btnSinRelleno.setBackground(new Color(200, 200, 200));
            btnConRelleno.setBackground(null);
        });

        btnConRelleno.addActionListener(e -> {
            conRelleno = true;
            btnConRelleno.setBackground(new Color(200, 200, 200));
            btnSinRelleno.setBackground(null);
        });

        //SELECCIÓN DE HERRAMIENTAS
        btnLapiz.addActionListener(e -> herramientaActual = "lapiz");
        btnCirculo.addActionListener(e -> herramientaActual = "circulo");
        btnCuadrado.addActionListener(e -> herramientaActual = "cuadrado");
        btnTriangulo.addActionListener(e -> herramientaActual = "triangulo");
        btnPentagono.addActionListener(e -> herramientaActual = "pentagono");

        //Evento al hacer clic
        areaDibujo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    puntoInicial = e.getPoint();

                    if (herramientaActual.equals("lapiz")) {
                        trazoActual = new Path2D.Float();
                        trazoActual.moveTo(e.getX(), e.getY());
                        areaDibujo.agregarTrazo(trazoActual, colorActual, anchoTrazoActual);
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && puntoInicial != null) {
                    if (!herramientaActual.equals("lapiz")) {
                        // Dibujar la forma final
                        dibujarForma(areaDibujo, puntoInicial, e.getPoint(), herramientaActual);
                    }
                    puntoInicial = null;
                }
            }
        });

        //Evento al arrastrar
        areaDibujo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!SwingUtilities.isRightMouseButton(e)) {
                    if (herramientaActual.equals("lapiz")) {
                        trazoActual.lineTo(e.getX(), e.getY());
                        areaDibujo.repaint();
                    }
                }
            }
        });

        //NUEVO
        subNuevo.addActionListener(e -> {
            nuevoArchivo(areaDibujo, vPrincipal);
        });

        //CARGAR
        subAbrir.addActionListener(e -> {
            abrirArchivo(areaDibujo, vPrincipal);
        });

        //GUARDAR
        subGuardar.addActionListener(e -> {
            guardarArchivo(areaDibujo, vPrincipal);
        });

        //SALIR
        subSalir.addActionListener(e -> {
            guardarYSalir(areaDibujo, vPrincipal);
        });

        //CERRAR VENTANA (X)
        vPrincipal.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                guardarYSalir(areaDibujo, vPrincipal);
            }
        });

        //BORRAR ULTIMO TRAZO
        mDeshacerTrazo.addActionListener(e -> {
            areaDibujo.borrarUltimoTrazo();
        });

        //BORRADO COMPLETO
        mBorrarTodo.addActionListener(e -> {
            areaDibujo.borrarTodo();
        });
        //endregion
    }

    private static void dibujarForma(AreaDibujo areaDibujo, Point inicio, Point fin, String forma) {
        int x = Math.min(inicio.x, fin.x);
        int y = Math.min(inicio.y, fin.y);
        int width = Math.abs(fin.x - inicio.x);
        int height = Math.abs(fin.y - inicio.y);

        Path2D.Float path = new Path2D.Float();

        switch (forma) {
            case "circulo" -> {
                // Dibujar círculo
                int size = Math.max(width, height);
                for (int i = 0; i <= 360; i++) {
                    double angle = Math.toRadians(i);
                    double px = x + size / 2.0 + (size / 2.0) * Math.cos(angle);
                    double py = y + size / 2.0 + (size / 2.0) * Math.sin(angle);
                    if (i == 0) {
                        path.moveTo(px, py);
                    } else {
                        path.lineTo(px, py);
                    }
                }
                path.closePath();
            }
            case "cuadrado" -> {
                // Dibujar cuadrado
                path.moveTo(x, y);
                path.lineTo(x + width, y);
                path.lineTo(x + width, y + height);
                path.lineTo(x, y + height);
                path.closePath();
            }
            case "triangulo" -> {
                // Dibujar triángulo
                int centroX = x + width / 2;
                path.moveTo(centroX, y);
                path.lineTo(x, y + height);
                path.lineTo(x + width, y + height);
                path.closePath();
            }
            case "pentagono" -> {
                // Dibujar pentágono
                int centroX = x + width / 2;
                int centroY = y + height / 2;
                int radio = Math.min(width, height) / 2;
                for (int i = 0; i < 5; i++) {
                    double angle = Math.toRadians(i * 72 - 90); // -90 para que apunte hacia arriba
                    double px = centroX + radio * Math.cos(angle);
                    double py = centroY + radio * Math.sin(angle);
                    if (i == 0) {
                        path.moveTo(px, py);
                    } else {
                        path.lineTo(px, py);
                    }
                }
                path.closePath();
            }
        }

        // Agregar la forma con o sin relleno
        areaDibujo.agregarForma(path, colorActual, anchoTrazoActual, conRelleno);
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
        aux.setFocusable(false);
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

    private static void guardarYSalir(AreaDibujo areaDibujo, JFrame ventana) {
        if (areaDibujo.isModificado()) {
            int opcion = preguntarGuardar(ventana);
            if (opcion == 0) {
                guardarArchivo(areaDibujo, ventana);
                ventana.dispose();
            } else if (opcion == 1) {
                ventana.dispose();
            }
        } else {
            ventana.dispose();
        }
    }

    private static int preguntarGuardar(JFrame padre) {
        Object[] opciones = {"Guardar", "Cerrar sin guardar", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(
                padre,
                "Hay cambios sin guardar. ¿Qué deseas hacer?",
                "Editor Gráfico",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        return seleccion;
    }

    private static void abrirArchivo(AreaDibujo areaDibujo, JFrame padre) {
        JFileChooser exploradorDeCarpetas = new JFileChooser();
        exploradorDeCarpetas.setDialogTitle("Abrir imagen");
        exploradorDeCarpetas.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes", "png", "jpg", "jpeg"));

        int eleccionUsuario = exploradorDeCarpetas.showOpenDialog(padre);

        if (eleccionUsuario == JFileChooser.APPROVE_OPTION) {
            File archivoACargar = exploradorDeCarpetas.getSelectedFile();
            try {
                BufferedImage imagen = ImageIO.read(archivoACargar);
                areaDibujo.cargarImagen(imagen);
                areaDibujo.marcarComoGuardado();

                JOptionPane.showMessageDialog(padre,
                        "Imagen cargada exitosamente",
                        "Editor Gráfico",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(padre,
                        "Error al cargar la imagen: " + e.getMessage(),
                        "Editor Gráfico",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void guardarArchivo(AreaDibujo areaDibujo, JFrame padre) {
        JFileChooser exploradorDeCarpetas = new JFileChooser();
        exploradorDeCarpetas.setDialogTitle("Guardar imagen");

        exploradorDeCarpetas.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes PNG", "png"));

        int eleccionUsuario = exploradorDeCarpetas.showSaveDialog(padre);

        if (eleccionUsuario == JFileChooser.APPROVE_OPTION) {
            File archivoAGuardar = exploradorDeCarpetas.getSelectedFile();

            if (!archivoAGuardar.getName().toLowerCase().endsWith(".png")) {
                archivoAGuardar = new File(archivoAGuardar.getAbsolutePath() + ".png");
            }

            try {
                BufferedImage imagen = new BufferedImage(
                        areaDibujo.getWidth(),
                        areaDibujo.getHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );

                Graphics2D g2 = imagen.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, imagen.getWidth(), imagen.getHeight());
                areaDibujo.paint(g2);
                g2.dispose();

                ImageIO.write(imagen, "PNG", archivoAGuardar);

                areaDibujo.marcarComoGuardado();

                JOptionPane.showMessageDialog(padre,
                        "Imagen guardada exitosamente",
                        "Editor Gráfico",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(padre,
                        "Error al guardar la imagen: " + ex.getMessage(),
                        "Editor Gráfico",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void nuevoArchivo(AreaDibujo areaDibujo, JFrame vPrincipal) {
        if (areaDibujo.isModificado()) {
            int opcion = preguntarGuardar(vPrincipal);
            if (opcion == 0) {
                guardarArchivo(areaDibujo, vPrincipal);
                areaDibujo.borrarTodo();
                areaDibujo.marcarComoGuardado();
            } else if (opcion == 1) {
                areaDibujo.borrarTodo();
                areaDibujo.marcarComoGuardado();
            }
        } else {
            areaDibujo.borrarTodo();
        }
    }
}