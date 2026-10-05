package cl.speedfood.modelo;

/**
 * Representa un pedido dentro del sistema SpeedFood.
 *
 * @author Consuelo Martinez
 * @version 1.0
 */
public class Pedido {

    private int id;
    private String direccion;
    private TipoPedido tipo;
    private EstadoPedido estado;

    /**
     * Constructor para crear un pedido nuevo.
     *
     * @param direccion dirección de entrega.
     * @param tipo tipo de pedido.
     * @param estado estado actual del pedido.
     */
    public Pedido(String direccion, TipoPedido tipo, EstadoPedido estado) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    /**
     * Constructor para crear un pedido existente.
     *
     * @param id identificador del pedido.
     * @param direccion dirección de entrega.
     * @param tipo tipo de pedido.
     * @param estado estado actual del pedido.
     */
    public Pedido(int id, String direccion, TipoPedido tipo, EstadoPedido estado) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public TipoPedido getTipo() {
        return tipo;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTipo(TipoPedido tipo) {
        this.tipo = tipo;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    /**
     * Retorna una representación textual del pedido.
     *
     * @return identificador y dirección del pedido.
     */
    @Override
    public String toString() {
        return id + " - " + direccion;
    }
}
