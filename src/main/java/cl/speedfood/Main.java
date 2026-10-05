package cl.speedfood;

import cl.speedfood.vista.VentanaPrincipal;

import javax.swing.SwingUtilities;

/**
 * Clase principal de la aplicación SpeedFood.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class Main {

    /**
     * Inicia la aplicación.
     *
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}