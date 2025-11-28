import javax.swing.*;
import java.awt.*;

public class Main {
    static void main() {
        /**
         * En esta tarea, debéis diseñar y programar un editor gráfico, (similar a la aplicación Paint de Windows).
         * Debe poder realizar las siguientes tareas y cumplir los siguientes requisitos:
         *
         * Debe poder cargar y guardar ficheros de imagen desde o hacia el sistemae de archivos.
         * Debe disponer de un menú principal con las opciones básicas
         * Debe disponer de una barra de herramientas con algunas opciones.
         * Debe disponer de un menú contextual con algunas opciones.
         * Debe mostrar mensajes totalmente informativos o de error cuando se realicen las operaciones pertinentes.
         * Debe poderse realizar trazos a mano alzada y poder cambiar las propiedades de dichos trazos.
         * Debe poderse incluir en el lienzo figuras geométricas (tanto con relleno como sin relleno).
         * Cualquier otra funcionalidad que estimes conveniente.
         */

        JFrame vP = new JFrame();//vP = ventanaPrincipal
        vP.setTitle("Editor gráfico");
        vP.setLayout(new BorderLayout());

        vP.setSize(930, 580);
        vP.setResizable(true);
        vP.setLocationRelativeTo(null);
        vP.getContentPane().setBackground(Color.WHITE);

        //comentario

        vP.setVisible(true);
        vP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
