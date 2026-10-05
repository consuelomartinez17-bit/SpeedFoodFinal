package cl.speedfood.vista;

import cl.speedfood.dao.PedidoDAO;
import cl.speedfood.modelo.EstadoPedido;
import cl.speedfood.modelo.Pedido;
import cl.speedfood.modelo.TipoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Gestiona la interfaz gráfica de los pedidos.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class GestionPedidos extends JFrame {

    private JTextField txtDireccion;
    private JComboBox<TipoPedido> cmbTipo;
    private JComboBox<EstadoPedido> cmbEstado;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private final PedidoDAO pedidoDAO;

    /**
     * Constructor de la ventana de gestión de pedidos.
     */
    public GestionPedidos() {

        pedidoDAO = new PedidoDAO();

        setTitle("Gestión de Pedidos");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();
        cargarPedidos();
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
                "GESTIÓN DE PEDIDOS",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        // =========================
        // FORMULARIO
        // =========================

        JPanel panelFormulario = new JPanel(
                new BorderLayout(10, 10)
        );

        panelFormulario.setBorder(
                BorderFactory.createTitledBorder("Datos del pedido")
        );

        JPanel panelDatos = new JPanel(
                new GridBagLayout()
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // =========================
        // DIRECCIÓN
        // =========================

        JLabel lblDireccion = new JLabel("Dirección:");

        lblDireccion.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        txtDireccion = new JTextField(30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panelDatos.add(lblDireccion, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelDatos.add(txtDireccion, gbc);

        // =========================
        // TIPO
        // =========================

        JLabel lblTipo = new JLabel("Tipo:");

        lblTipo.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        cmbTipo = new JComboBox<>(
                TipoPedido.values()
        );

        cmbTipo.setPreferredSize(
                new Dimension(150, 28)
        );

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panelDatos.add(lblTipo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelDatos.add(cmbTipo, gbc);

        // =========================
        // ESTADO
        // =========================

        JLabel lblEstado = new JLabel("Estado:");

        lblEstado.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        cmbEstado = new JComboBox<>(
                EstadoPedido.values()
        );

        cmbEstado.setPreferredSize(
                new Dimension(150, 28)
        );

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelDatos.add(lblEstado, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panelDatos.add(cmbEstado, gbc);

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

        panelFormulario.add(
                panelDatos,
                BorderLayout.CENTER
        );

        panelFormulario.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        // =========================
        // PANEL SUPERIOR
        // =========================

        JPanel panelSuperior = new JPanel(
                new BorderLayout(10, 10)
        );

        panelSuperior.add(
                lblTitulo,
                BorderLayout.NORTH
        );

        panelSuperior.add(
                panelFormulario,
                BorderLayout.CENTER
        );

        // =========================
        // TABLA
        // =========================

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Dirección",
                        "Tipo",
                        "Estado"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);

        tablaPedidos.setRowHeight(28);

        tablaPedidos.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        tablaPedidos.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        tablaPedidos.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        tablaPedidos.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(350);

        tablaPedidos.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(130);

        tablaPedidos.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(150);

        JScrollPane scrollTabla = new JScrollPane(
                tablaPedidos
        );

        scrollTabla.setBorder(
                BorderFactory.createTitledBorder(
                        "Pedidos registrados"
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
                e -> agregarPedido()
        );

        btnEditar.addActionListener(
                e -> editarPedido()
        );

        btnEliminar.addActionListener(
                e -> eliminarPedido()
        );

        tablaPedidos.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int fila =
                                tablaPedidos.getSelectedRow();

                        if (fila != -1) {

                            txtDireccion.setText(
                                    modeloTabla
                                            .getValueAt(fila, 1)
                                            .toString()
                            );

                            cmbTipo.setSelectedItem(
                                    TipoPedido.valueOf(
                                            modeloTabla
                                                    .getValueAt(fila, 2)
                                                    .toString()
                                    )
                            );

                            cmbEstado.setSelectedItem(
                                    EstadoPedido.valueOf(
                                            modeloTabla
                                                    .getValueAt(fila, 3)
                                                    .toString()
                                    )
                            );
                        }
                    }
                });
    }

    /**
     * Carga los pedidos registrados en la tabla.
     */
    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        try {

            List<Pedido> pedidos = pedidoDAO.readAll();

            for (Pedido pedido : pedidos) {

                modeloTabla.addRow(new Object[]{
                        pedido.getId(),
                        pedido.getDireccion(),
                        pedido.getTipo(),
                        pedido.getEstado()
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los pedidos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Agrega un nuevo pedido.
     */
    private void agregarPedido() {

        String direccion = txtDireccion.getText().trim();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar una dirección.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        TipoPedido tipo = (TipoPedido) cmbTipo.getSelectedItem();
        EstadoPedido estado = (EstadoPedido) cmbEstado.getSelectedItem();

        try {

            Pedido pedido = new Pedido(
                    direccion,
                    tipo,
                    estado
            );

            pedidoDAO.create(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido agregado correctamente."
            );

            limpiarFormulario();
            cargarPedidos();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al agregar el pedido: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Edita el pedido seleccionado.
     */
    private void editarPedido() {

        int fila = tablaPedidos.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido."
            );

            return;
        }

        String direccion = txtDireccion.getText().trim();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar una dirección.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        TipoPedido tipo = (TipoPedido) cmbTipo.getSelectedItem();
        EstadoPedido estado = (EstadoPedido) cmbEstado.getSelectedItem();

        try {

            Pedido pedido = new Pedido(
                    id,
                    direccion,
                    tipo,
                    estado
            );

            pedidoDAO.update(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido actualizado correctamente."
            );

            limpiarFormulario();
            cargarPedidos();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar el pedido: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina el pedido seleccionado.
     */
    private void eliminarPedido() {

        int fila = tablaPedidos.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido."
            );

            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este pedido?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            pedidoDAO.delete(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado correctamente."
            );

            limpiarFormulario();
            cargarPedidos();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar el pedido: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarFormulario() {

        txtDireccion.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        tablaPedidos.clearSelection();
    }

    /**
     * Configura el aspecto de los botones de la interfaz.
     *
     * @param boton botón que será configurado.
     */
    private void configurarBoton(JButton boton) {

        boton.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        boton.setPreferredSize(
                new Dimension(110, 35)
        );

        boton.setFocusPainted(false);
    }
}
