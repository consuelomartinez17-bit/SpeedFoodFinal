package cl.speedfood.vista;

import cl.speedfood.dao.RepartidorDAO;
import cl.speedfood.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Gestiona la interfaz gráfica de los repartidores.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class GestionRepartidores extends JFrame {

    private JTextField txtNombre;
    private JTable tablaRepartidores;
    private DefaultTableModel modeloTabla;

    private final RepartidorDAO repartidorDAO;

    /**
     * Constructor de la ventana de gestión de repartidores.
     */
    public GestionRepartidores() {

        repartidorDAO = new RepartidorDAO();

        setTitle("Gestión de Repartidores");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();
        cargarRepartidores();
    }

    /**
     * Crea los componentes principales de la interfaz.
     */
    private void crearInterfaz() {

        // =========================
        // PANEL PRINCIPAL
        // =========================

        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        // =========================
        // TÍTULO
        // =========================

        JLabel lblTitulo = new JLabel(
                "GESTIÓN DE REPARTIDORES",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        // =========================
        // FORMULARIO
        // =========================

        JPanel panelFormulario = new JPanel(new BorderLayout(10, 10));

        panelFormulario.setBorder(
                BorderFactory.createTitledBorder("Datos del repartidor")
        );

        JPanel panelDatos = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 10)
        );

        JLabel lblNombre = new JLabel("Nombre:");

        lblNombre.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        txtNombre = new JTextField(25);

        panelDatos.add(lblNombre);
        panelDatos.add(txtNombre);

        // =========================
        // BOTONES
        // =========================

        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 10)
        );

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        configurarBoton(btnAgregar);
        configurarBoton(btnEditar);
        configurarBoton(btnEliminar);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        panelFormulario.add(panelDatos, BorderLayout.CENTER);
        panelFormulario.add(panelBotones, BorderLayout.SOUTH);

        // =========================
        // PANEL SUPERIOR
        // =========================

        JPanel panelSuperior = new JPanel(
                new BorderLayout(10, 10)
        );

        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);

        // =========================
        // TABLA
        // =========================

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre"}, 0
        ) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaRepartidores = new JTable(modeloTabla);

        tablaRepartidores.setRowHeight(28);

        tablaRepartidores.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        tablaRepartidores.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        tablaRepartidores.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(70);

        tablaRepartidores.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(350);

        JScrollPane scrollTabla = new JScrollPane(tablaRepartidores);

        scrollTabla.setBorder(
                BorderFactory.createTitledBorder(
                        "Repartidores registrados"
                )
        );

        // =========================
        // ARMAR VENTANA
        // =========================

        panelPrincipal.add(
                panelSuperior,
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                scrollTabla,
                BorderLayout.CENTER
        );

        setContentPane(panelPrincipal);

        // =========================
        // EVENTOS
        // =========================

        btnAgregar.addActionListener(
                e -> agregarRepartidor()
        );

        btnEditar.addActionListener(
                e -> editarRepartidor()
        );

        btnEliminar.addActionListener(
                e -> eliminarRepartidor()
        );

        tablaRepartidores.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int fila =
                                tablaRepartidores.getSelectedRow();

                        if (fila != -1) {

                            txtNombre.setText(
                                    modeloTabla
                                            .getValueAt(fila, 1)
                                            .toString()
                            );
                        }
                    }
                });
    }

    /**
     * Carga los repartidores registrados en la tabla.
     */
    private void cargarRepartidores() {

        modeloTabla.setRowCount(0);

        try {

            List<Repartidor> repartidores = repartidorDAO.readAll();

            for (Repartidor repartidor : repartidores) {

                modeloTabla.addRow(new Object[]{
                        repartidor.getId(),
                        repartidor.getNombre()
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los repartidores: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Agrega un nuevo repartidor.
     */
    private void agregarRepartidor() {

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un nombre.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (nombre.length() > 100) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre no puede superar los 100 caracteres.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            repartidorDAO.create(new Repartidor(nombre));

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor agregado correctamente."
            );

            txtNombre.setText("");
            cargarRepartidores();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al agregar el repartidor: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Edita el repartidor seleccionado.
     */
    private void editarRepartidor() {

        int fila = tablaRepartidores.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un repartidor."
            );

            return;
        }

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un nombre.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (nombre.length() > 100) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre no puede superar los 100 caracteres.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        try {

            repartidorDAO.update(
                    new Repartidor(id, nombre)
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado correctamente."
            );

            txtNombre.setText("");
            cargarRepartidores();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar el repartidor: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina el repartidor seleccionado.
     */
    private void eliminarRepartidor() {

        int fila = tablaRepartidores.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un repartidor."
            );

            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este repartidor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            repartidorDAO.delete(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor eliminado correctamente."
            );

            txtNombre.setText("");
            cargarRepartidores();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar el repartidor: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Configura el aspecto de los botones de la interfaz.
     *
     * @param boton botón que será configurado.
     */
    private void configurarBoton(JButton boton) {

        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setPreferredSize(new Dimension(110, 35));
        boton.setFocusPainted(false);
    }
}