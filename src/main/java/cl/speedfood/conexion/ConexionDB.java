package cl.speedfood.conexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestiona la conexión entre la aplicación SpeedFood
 * y la base de datos MySQL.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class ConexionDB {

    /**
     * Establece una conexión con la base de datos SpeedFast.
     *
     * @return conexión activa con la base de datos.
     * @throws SQLException si ocurre un error al establecer la conexión.
     */
    public static Connection conectar() throws SQLException {

        Properties propiedades = new Properties();

        try (InputStream archivo = ConexionDB.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (archivo == null) {
                throw new SQLException(
                        "No se encontró el archivo db.properties."
                );
            }

            propiedades.load(archivo);

        } catch (IOException e) {

            throw new SQLException(
                    "No se pudo cargar el archivo db.properties.",
                    e
            );
        }

        String url = propiedades.getProperty("db.url");
        String usuario = propiedades.getProperty("db.usuario");
        String password = propiedades.getProperty("db.password");

        return DriverManager.getConnection(url, usuario, password);
    }
}