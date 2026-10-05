package cl.speedfood.vista;

import cl.speedfood.dao.EntregaDAO;
import cl.speedfood.dao.PedidoDAO;
import cl.speedfood.dao.RepartidorDAO;
import cl.speedfood.modelo.Entrega;
import cl.speedfood.modelo.Pedido;
import cl.speedfood.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.time.format.DateTimeParseException;

/**
 * Gestiona la interfaz gráfica de las entregas.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class GestionEntregas extends JFrame {

    private JComboBox<Pedido> cmbPedido;
    private JComboBox<Repartidor> cmbRepartidor;
    private JTextField txtFecha;
    private JTextField txtHora;

    private JTable tablaEntregas;
    private DefaultTableModel modeloTabla;

    private final EntregaDAO entregaDAO;
    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;

    /**
     * Constructor de la ventana de gestión de entregas.
     */
    public GestionEntregas() {

        entregaDAO = new EntregaDAO();
        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();

        setTitle("Gestión de Entregas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();
        cargarCombos();
        cargarEntregas();
    }

    /**
     * Crea los componentes principales de la interfaz.
     */
    private void crearInterfaz() {

        // =========================
        // PANEL PRINCIPAL
        // =========================

        JPanel panelPrincipal = new JPanel(
                new BorderLayout(15, 15)
        );

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25
                )
        );

        // =========================
        // TÍTULO
        // =========================

        JLabel lblTitulo = new JLabel(
                "GESTIÓN DE ENTREGAS",
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
                BorderFactory.createTitledBorder(
                        "Datos de la entrega"
                )
        );

        JPanel panelDatos = new JPanel(
                new GridBagLayout()
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // =========================
        // PEDIDO
        // =========================

        JLabel lblPedido = new JLabel("Pedido:");

        lblPedido.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        cmbPedido = new JComboBox<>();

        cmbPedido.setPreferredSize(
                new Dimension(300, 28)
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        panelDatos.add(lblPedido, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panelDatos.add(cmbPedido, gbc);

        // =========================
        // REPARTIDOR
        // =========================

        JLabel lblRepartidor = new JLabel(
                "Repartidor:"
        );

        lblRepartidor.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        cmbRepartidor = new JComboBox<>();

        cmbRepartidor.setPreferredSize(
                new Dimension(300, 28)
        );

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        panelDatos.add(lblRepartidor, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panelDatos.add(cmbRepartidor, gbc);

        // =========================
        // FECHA
        // =========================

        JLabel lblFecha = new JLabel("Fecha:");

        lblFecha.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        txtFecha = new JTextField(10);

        txtFecha.setToolTipText(
                "Formato: AAAA-MM-DD"
        );

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        panelDatos.add(lblFecha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;

        panelDatos.add(txtFecha, gbc);

        // =========================
        // HORA
        // =========================

        JLabel lblHora = new JLabel("Hora:");

        lblHora.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        txtHora = new JTextField(8);

        txtHora.setToolTipText(
                "Formato: HH:MM:SS"
        );

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;

        panelDatos.add(lblHora, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0;

        panelDatos.add(txtHora, gbc);

        // =========================
        // BOTONES
        // =========================

        JPanel panelBotones = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        10,
                        10
                )
        );

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        configurarBoton(btnAgregar);
        configurarBoton(btnEditar);
        configurarBoton(btnEliminar);
        configurarBoton(btnActualizar);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

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
                        "Pedido",
                        "Repartidor",
                        "Fecha",
                        "Hora"
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

        tablaEntregas = new JTable(modeloTabla);

        tablaEntregas.setRowHeight(28);

        tablaEntregas.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        tablaEntregas.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        tablaEntregas.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        tablaEntregas.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(120);

        tablaEntregas.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(120);

        tablaEntregas.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(120);

        tablaEntregas.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(100);

        JScrollPane scrollTabla = new JScrollPane(
                tablaEntregas
        );

        scrollTabla.setBorder(
                BorderFactory.createTitledBorder(
                        "Entregas registradas"
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
                e -> agregarEntrega()
        );

        btnEditar.addActionListener(
                e -> editarEntrega()
        );

        btnEliminar.addActionListener(
                e -> eliminarEntrega()
        );

        btnActualizar.addActionListener(
                e -> {
                    cargarCombos();
                    cargarEntregas();
                }
        );

        tablaEntregas.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int fila =
                                tablaEntregas.getSelectedRow();

                        if (fila != -1) {
                            cargarEntregaSeleccionada(fila);
                        }
                    }
                });
    }

    /**
     * Carga los pedidos y repartidores disponibles en los JComboBox.
     */
    private void cargarCombos() {

        cmbPedido.removeAllItems();
        cmbRepartidor.removeAllItems();

        try {

            List<Pedido> pedidos = pedidoDAO.readAll();

            for (Pedido pedido : pedidos) {
                cmbPedido.addItem(pedido);
            }

            List<Repartidor> repartidores = repartidorDAO.readAll();

            for (Repartidor repartidor : repartidores) {
                cmbRepartidor.addItem(repartidor);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los datos relacionados: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Carga las entregas registradas en la tabla.
     *
     * Muestra información legible del pedido y del repartidor,
     * manteniendo internamente los identificadores necesarios
     * para las operaciones CRUD.
     */
    private void cargarEntregas() {

        modeloTabla.setRowCount(0);

        try {

            List<Entrega> entregas = entregaDAO.readAll();

            for (Entrega entrega : entregas) {

                modeloTabla.addRow(new Object[]{
                        entrega.getId(),
                        obtenerTextoPedido(entrega.getIdPedido()),
                        obtenerTextoRepartidor(
                                entrega.getIdRepartidor()
                        ),
                        entrega.getFecha(),
                        entrega.getHora()
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar las entregas: "
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Obtiene una representación legible del pedido.
     *
     * @param idPedido identificador del pedido.
     * @return texto con ID y dirección del pedido.
     */
    private String obtenerTextoPedido(int idPedido) {

        for (int i = 0; i < cmbPedido.getItemCount(); i++) {

            Pedido pedido = cmbPedido.getItemAt(i);

            if (pedido.getId() == idPedido) {

                return pedido.getId()
                        + " - "
                        + pedido.getDireccion();
            }
        }

        return String.valueOf(idPedido);
    }

    /**
     * Obtiene una representación legible del repartidor.
     *
     * @param idRepartidor identificador del repartidor.
     * @return texto con ID y nombre del repartidor.
     */
    private String obtenerTextoRepartidor(int idRepartidor) {

        for (int i = 0;
             i < cmbRepartidor.getItemCount();
             i++) {

            Repartidor repartidor =
                    cmbRepartidor.getItemAt(i);

            if (repartidor.getId() == idRepartidor) {

                return repartidor.getId()
                        + " - "
                        + repartidor.getNombre();
            }
        }

        return String.valueOf(idRepartidor);
    }

    /**
     * Carga los datos de la entrega seleccionada en el formulario.
     *
     * @param fila fila seleccionada en la tabla.
     */
    private void cargarEntregaSeleccionada(int fila) {

        String textoPedido =
                modeloTabla.getValueAt(fila, 1).toString();

        String textoRepartidor =
                modeloTabla.getValueAt(fila, 2).toString();

        int idPedido = Integer.parseInt(
                textoPedido.split(" - ")[0]
        );

        int idRepartidor = Integer.parseInt(
                textoRepartidor.split(" - ")[0]
        );
        for (int i = 0; i < cmbPedido.getItemCount(); i++) {

            Pedido pedido = cmbPedido.getItemAt(i);

            if (pedido.getId() == idPedido) {
                cmbPedido.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < cmbRepartidor.getItemCount(); i++) {

            Repartidor repartidor = cmbRepartidor.getItemAt(i);

            if (repartidor.getId() == idRepartidor) {
                cmbRepartidor.setSelectedIndex(i);
                break;
            }
        }

        txtFecha.setText(
                modeloTabla.getValueAt(fila, 3).toString()
        );

        txtHora.setText(
                modeloTabla.getValueAt(fila, 4).toString()
        );
    }

    /**
     * Agrega una nueva entrega.
     */
    private void agregarEntrega() {

        Pedido pedido = (Pedido) cmbPedido.getSelectedItem();
        Repartidor repartidor = (Repartidor) cmbRepartidor.getSelectedItem();

        String fechaTexto = txtFecha.getText().trim();
        String horaTexto = txtHora.getText().trim();

        if (pedido == null || repartidor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido y un repartidor.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (fechaTexto.isEmpty() || horaTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la fecha y la hora.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            LocalDate fecha = LocalDate.parse(fechaTexto);
            LocalTime hora = LocalTime.parse(horaTexto);

            Entrega entrega = new Entrega(
                    pedido.getId(),
                    repartidor.getId(),
                    fecha,
                    hora
            );

            entregaDAO.create(entrega);

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega agregada correctamente."
            );

            limpiarFormulario();
            cargarEntregas();

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Fecha u hora no válidas. Use los formatos:\n"
                            + "Fecha: AAAA-MM-DD\n"
                            + "Hora: HH:MM:SS",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar la entrega: " + e.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Edita la entrega seleccionada.
     */
    private void editarEntrega() {

        int fila = tablaEntregas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una entrega."
            );

            return;
        }

        Pedido pedido = (Pedido) cmbPedido.getSelectedItem();
        Repartidor repartidor = (Repartidor) cmbRepartidor.getSelectedItem();

        String fechaTexto = txtFecha.getText().trim();
        String horaTexto = txtHora.getText().trim();
        if (fechaTexto.isEmpty() || horaTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la fecha y la hora.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (pedido == null || repartidor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido y un repartidor.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            LocalDate fecha = LocalDate.parse(fechaTexto);
            LocalTime hora = LocalTime.parse(horaTexto);

            int id = (int) modeloTabla.getValueAt(fila, 0);

            Entrega entrega = new Entrega(
                    id,
                    pedido.getId(),
                    repartidor.getId(),
                    fecha,
                    hora
            );

            entregaDAO.update(entrega);

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega actualizada correctamente."
            );

            limpiarFormulario();
            cargarEntregas();

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Fecha u hora no válidas. Use los formatos:\n"
                            + "Fecha: AAAA-MM-DD\n"
                            + "Hora: HH:MM:SS",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar la entrega: " + e.getMessage(),
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina la entrega seleccionada.
     */
    private void eliminarEntrega() {

        int fila = tablaEntregas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una entrega."
            );

            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar esta entrega?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            entregaDAO.delete(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega eliminada correctamente."
            );

            limpiarFormulario();
            cargarEntregas();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar la entrega: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarFormulario() {

        if (cmbPedido.getItemCount() > 0) {
            cmbPedido.setSelectedIndex(0);
        }

        if (cmbRepartidor.getItemCount() > 0) {
            cmbRepartidor.setSelectedIndex(0);
        }

        txtFecha.setText("");
        txtHora.setText("");

        tablaEntregas.clearSelection();
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