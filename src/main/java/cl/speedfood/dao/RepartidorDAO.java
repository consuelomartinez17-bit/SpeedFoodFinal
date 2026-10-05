package cl.speedfood.dao;

import cl.speedfood.conexion.ConexionDB;
import cl.speedfood.modelo.Repartidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las operaciones de persistencia de los repartidores.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class RepartidorDAO {

    /**
     * Registra un nuevo repartidor en la base de datos.
     *
     * @param repartidor repartidor que será registrado.
     * @throws SQLException si ocurre un error durante la operación.
     */
    public void create(Repartidor repartidor) throws SQLException {

        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, repartidor.getNombre());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    System.out.println("Repartidor registrado con ID: " + rs.getInt(1));
                }
            }
        }
    }

    /**
     * Obtiene todos los repartidores registrados.
     *
     * @return lista de repartidores.
     * @throws SQLException si ocurre un error durante la consulta.
     */
    public List<Repartidor> readAll() throws SQLException {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT id, nombre FROM repartidores";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Repartidor repartidor = new Repartidor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );

                repartidores.add(repartidor);
            }
        }

        return repartidores;
    }

    /**
     * Actualiza el nombre de un repartidor existente.
     *
     * @param repartidor repartidor con los datos actualizados.
     * @throws SQLException si ocurre un error durante la operación.
     */
    public void update(Repartidor repartidor) throws SQLException {

        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, repartidor.getNombre());
            ps.setInt(2, repartidor.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Elimina un repartidor de la base de datos.
     *
     * @param id identificador del repartidor que será eliminado.
     * @throws SQLException si ocurre un error durante la operación.
     */
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM repartidores WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}