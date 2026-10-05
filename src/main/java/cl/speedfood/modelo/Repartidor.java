package cl.speedfood.modelo;

/**
 * Representa un repartidor dentro del sistema SpeedFood.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class Repartidor {

    private int id;
    private String nombre;

    /**
     * Constructor para crear un repartidor nuevo.
     *
     * @param nombre nombre del repartidor.
     */
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor para crear un repartidor existente.
     *
     * @param id identificador del repartidor.
     * @param nombre nombre del repartidor.
     */
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
