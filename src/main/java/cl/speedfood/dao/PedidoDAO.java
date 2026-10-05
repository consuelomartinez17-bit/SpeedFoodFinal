package cl.speedfood.dao;

import cl.speedfood.conexion.ConexionDB;
import cl.speedfood.modelo.EstadoPedido;
import cl.speedfood.modelo.Pedido;
import cl.speedfood.modelo.TipoPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las operaciones de persistencia de los pedidos.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class PedidoDAO {

    /**
     * Registra un nuevo pedido en la base de datos.
     *
     * @param pedido pedido que será registrado.
     * @throws SQLException si ocurre un error durante la operación.
     */
    public void create(Pedido pedido) throws SQLException {

        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipo().name());
            ps.setString(3, pedido.getEstado().name());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    System.out.println("Pedido registrado con ID: " + rs.getInt(1));
                }
            }
        }
    }

    /**
     * Obtiene todos los pedidos registrados.
     *
     * @return lista de pedidos.
     * @throws SQLException si ocurre un error durante la consulta.
     */
    public List<Pedido> readAll() throws SQLException {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT id, direccion, tipo, estado FROM pedidos";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        TipoPedido.valueOf(rs.getString("tipo")),
                        EstadoPedido.valueOf(rs.getString("estado"))
                );

                pedidos.add(pedido);
            }
        }

        return pedidos;
    }

    /**
     * Actualiza los datos de un pedido existente.
     *
     * @param pedido pedido con los datos actualizados.
     * @throws SQLException si ocurre un error durante la operación.
     */
    public void update(Pedido pedido) throws SQLException {

        String sql = """
                UPDATE pedidos
                SET direccion = ?, tipo = ?, estado = ?
                WHERE id = ?
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipo().name());
            ps.setString(3, pedido.getEstado().name());
            ps.setInt(4, pedido.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Elimina un pedido de la base de datos.
     *
     * @param id identificador del pedido que será eliminado.
     * @throws SQLException sí ocurre un error durante la operación.
     */
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}