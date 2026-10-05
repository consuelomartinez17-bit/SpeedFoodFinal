package cl.speedfood.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa una entrega asociada a un pedido y un repartidor.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class Entrega {

    private int id;
    private int idPedido;
    private int idRepartidor;
    private LocalDate fecha;
    private LocalTime hora;

    /**
     * Constructor para crear una nueva entrega.
     *
     * @param idPedido identificador del pedido.
     * @param idRepartidor identificador del repartidor.
     * @param fecha fecha de la entrega.
     * @param hora hora de la entrega.
     */
    public Entrega(int idPedido, int idRepartidor,
                   LocalDate fecha, LocalTime hora) {
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    /**
     * Constructor para crear una entrega existente.
     *
     * @param id identificador de la entrega.
     * @param idPedido identificador del pedido.
     * @param idRepartidor identificador del repartidor.
     * @param fecha fecha de la entrega.
     * @param hora hora de la entrega.
     */
    public Entrega(int id, int idPedido, int idRepartidor,
                   LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    /**
     * Retorna una representación textual de la entrega.
     *
     * @return identificador de la entrega.
     */
    @Override
    public String toString() {
        return "Entrega " + id;
    }
}