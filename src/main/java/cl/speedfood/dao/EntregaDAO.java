package cl.speedfood.dao;

import cl.speedfood.conexion.ConexionDB;
import cl.speedfood.modelo.Entrega;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las operaciones de persistencia de las entregas.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class EntregaDAO {

    /**
     * Registra una nueva entrega en la base de datos.
     *
     * @param entrega entrega que será registrada.
     * @throws SQLException si ocurre un error durante la operación.
     */
    public void create(Entrega entrega) throws SQLException {

        String sql = """
                INSERT INTO entregas
                (id_pedido, id_repartidor, fecha, hora)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, Date.valueOf(entrega.getFecha()));
            ps.setTime(4, Time.valueOf(entrega.getHora()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    System.out.println(
                            "Entrega registrada con ID: " + rs.getInt(1)
                    );
                }
            }
        }
    }

    /**
     * Obtiene todas las entregas registradas.
     *
     * @return lista de entregas.
     * @throws SQLException si ocurre un error durante la consulta.
     */
    public List<Entrega> readAll() throws SQLException {

        List<Entrega> entregas = new ArrayList<>();

        String sql = """
                SELECT id, id_pedido, id_repartidor, fecha, hora
                FROM entregas
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Entrega entrega = new Entrega(
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getTime("hora").toLocalTime()
                );

                entregas.add(entrega);
            }
        }

        return entregas;
    }

    /**
     * Actualiza una entrega existente.
     *
     * @param entrega entrega con los datos actualizados.
     * @throws SQLException si ocurre un error durante la operación.
     */
    public void update(Entrega entrega) throws SQLException {

        String sql = """
                UPDATE entregas
                SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ?
                WHERE id = ?
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, Date.valueOf(entrega.getFecha()));
            ps.setTime(4, Time.valueOf(entrega.getHora()));
            ps.setInt(5, entrega.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Elimina una entrega de la base de datos.
     *
     * @param id identificador de la entrega que será eliminada.
     * @throws SQLException sí ocurre un error durante la operación.
     */
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM entregas WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}