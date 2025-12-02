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
    private boolean modificado; // Variable para rastrear cambios
    private BufferedImage imagenFondo;  //Esto me permitirá cargar png como fondo de mi AreaDibujo

    public AreaDibujo() {
        trazos = new ArrayList<>();
        modificado = false; // Inicialmente no hay cambios
    }

    public void agregarTrazo(Path2D trazo, Color color, float anchoTrazo) {
        trazos.add(new Trazo(trazo, color, anchoTrazo));
        modificado = true; // Se ha hecho un cambio
        repaint();
    }

    public void borrarTrazos() {
        if (!trazos.isEmpty()) {
            trazos.clear();
            modificado = true; // Se ha hecho un cambio
            repaint();
        }
    }

    public void borrarUltimoTrazo() {
        if (trazos.size() > 0) {
            trazos.remove(trazos.size() - 1);
            modificado = true; // Se ha hecho un cambio
            repaint();
        }
    }

    // Para saber si hay cambios sin guardar
    public boolean isModificado() {
        return modificado;
    }

    // Para marcar como guardado (después de guardar el archivo)
    public void marcarComoGuardado() {
        modificado = false;
    }

    public void cargarImagen(BufferedImage imagen) {
        this.imagenFondo = imagen;
        this.trazos.clear(); // Limpia los trazos anteriores
        this.modificado = false;
        repaint();
    }

    // Para obtener el número de trazos (útil para saber si está vacío)
    public int getCantidadTrazos() {
        return trazos.size();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Primero dibuja la imagen de fondo si existe
        if (imagenFondo != null) {
            g2.drawImage(imagenFondo, 0, 0, null);
        }

        // Luego dibuja los trazos encima
        for (Trazo trazoActual : trazos) {
            g2.setColor(trazoActual.color);
            g2.setStroke(new BasicStroke(trazoActual.anchoTrazo));
            g2.draw(trazoActual.trazo);
        }
    }

    private class Trazo {
        private Path2D trazo;
        private Color color;
        private float anchoTrazo;

        public Trazo(Path2D trazo, Color color, float anchoTrazo) {
            this.trazo = trazo;
            this.color = color;
            this.anchoTrazo = anchoTrazo;
        }
    }
}