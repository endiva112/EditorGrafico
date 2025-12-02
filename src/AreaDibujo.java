import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JComponent;

public class AreaDibujo extends JComponent {

    private ArrayList<Trazo> trazos;
    private boolean modificado;
    private BufferedImage imagenFondo;

    public AreaDibujo() {
        trazos = new ArrayList<>();
        modificado = false;
    }

    public void agregarTrazo(Path2D trazo, Color color, float anchoTrazo) {
        trazos.add(new Trazo(trazo, color, anchoTrazo, false)); // false = sin relleno
        modificado = true;
        repaint();
    }

    public void agregarForma(Path2D trazo, Color color, float anchoTrazo, boolean relleno) {
        trazos.add(new Trazo(trazo, color, anchoTrazo, relleno));
        modificado = true;
        repaint();
    }

    public void borrarTodo() {
        trazos.clear();
        imagenFondo = null;
        modificado = true;
        repaint();
    }

    public void borrarUltimoTrazo() {
        if (trazos.size() > 0) {
            trazos.remove(trazos.size() - 1);
            modificado = true;
            repaint();
        }
    }

    public boolean isModificado() {
        return modificado;
    }

    public void marcarComoGuardado() {
        modificado = false;
    }

    public int getCantidadTrazos() {
        return trazos.size();
    }

    public void cargarImagen(BufferedImage imagen) {
        this.imagenFondo = imagen;
        this.trazos.clear();
        this.modificado = false;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Primero dibuja la imagen de fondo si existe
        if (imagenFondo != null) {
            g2.drawImage(imagenFondo, 0, 0, null);
        }

        for (Trazo trazoActual : trazos) {
            g2.setColor(trazoActual.color);
            g2.setStroke(new BasicStroke(trazoActual.anchoTrazo));

            if (trazoActual.relleno) {
                g2.fill(trazoActual.trazo); // Rellenar la forma
            } else {
                g2.draw(trazoActual.trazo); // Solo el contorno
            }
        }
    }

    private class Trazo {
        private Path2D trazo;
        private Color color;
        private float anchoTrazo;
        private boolean relleno;

        public Trazo(Path2D trazo, Color color, float anchoTrazo, boolean relleno) {
            this.trazo = trazo;
            this.color = color;
            this.anchoTrazo = anchoTrazo;
            this.relleno = relleno;
        }
    }
}