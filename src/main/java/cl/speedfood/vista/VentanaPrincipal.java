package cl.speedfood.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema SpeedFood.
 *
 * Permite acceder a los módulos de gestión de repartidores,
 * pedidos y entregas.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class VentanaPrincipal extends JFrame {

    /**
     * Constructor de la ventana principal.
     */
    public VentanaPrincipal() {

        setTitle("SpeedFood - Sistema de Gestión");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(20, 20));
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        );

        // =========================
        // TÍTULO
        // =========================

        JLabel lblTitulo = new JLabel(
                "SISTEMA DE GESTIÓN SPEEDFOOD",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblSubtitulo = new JLabel(
                "Gestión de repartidores, pedidos y entregas",
                SwingConstants.CENTER
        );

        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel panelTitulo = new JPanel(new GridLayout(2, 1));
        panelTitulo.add(lblTitulo);
        panelTitulo.add(lblSubtitulo);

        // =========================
        // BOTONES
        // =========================

        JPanel panelBotones = new JPanel(
                new GridLayout(3, 1, 0, 15)
        );

        JButton btnRepartidores = new JButton("GESTIONAR REPARTIDORES");
        JButton btnPedidos = new JButton("GESTIONAR PEDIDOS");
        JButton btnEntregas = new JButton("GESTIONAR ENTREGAS");

        configurarBoton(btnRepartidores);
        configurarBoton(btnPedidos);
        configurarBoton(btnEntregas);

        panelBotones.add(btnRepartidores);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnEntregas);

        // =========================
        // EVENTOS
        // =========================

        btnRepartidores.addActionListener(e ->
                new GestionRepartidores().setVisible(true)
        );

        btnPedidos.addActionListener(e ->
                new GestionPedidos().setVisible(true)
        );

        btnEntregas.addActionListener(e ->
                new GestionEntregas().setVisible(true)
        );

        // =========================
        // FOOTER
        // =========================

        JLabel lblFooter = new JLabel(
                "Desarrollo Orientado a Objetos II - Semana 8",
                SwingConstants.CENTER
        );

        lblFooter.setFont(new Font("Arial", Font.PLAIN, 11));

        // =========================
        // ARMAR VENTANA
        // =========================

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        panelPrincipal.add(lblFooter, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Configura el aspecto básico de un botón del menú principal.
     *
     * @param boton botón que será configurado.
     */
    private void configurarBoton(JButton boton) {

        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(300, 55));
        boton.setFocusPainted(false);
    }
}