# SpeedFoodFinal

## Sistema de Gestión de Pedidos - SpeedFast

Aplicación de escritorio desarrollada en Java para la gestión de repartidores,
pedidos y entregas de la empresa SpeedFast.

El proyecto implementa operaciones CRUD utilizando JDBC y MySQL, integradas
con una interfaz gráfica desarrollada con Java Swing.

---

## Tecnologías utilizadas

- Java 25
- Java Swing
- JDBC
- MySQL
- MySQL Connector/J 9.4.0
- Maven
- IntelliJ IDEA
- Git y GitHub

---

## Funcionalidades

### Gestión de Repartidores

Permite:

- Registrar repartidores.
- Listar repartidores.
- Editar repartidores.
- Eliminar repartidores.

La información se muestra mediante una tabla `JTable`.

### Gestión de Pedidos

Permite:

- Registrar pedidos.
- Listar pedidos.
- Editar pedidos.
- Eliminar pedidos.

Cada pedido contiene:

- Dirección.
- Tipo de pedido.
- Estado del pedido.

Tipos disponibles:

- COMIDA
- ENCOMIENDA
- EXPRESS

Estados disponibles:

- PENDIENTE
- EN_REPARTO
- ENTREGADO

### Gestión de Entregas

Permite:

- Registrar entregas.
- Listar entregas.
- Editar entregas.
- Eliminar entregas.

Cada entrega se relaciona con:

- Un pedido.
- Un repartidor.
- Una fecha.
- Una hora.

Los pedidos y repartidores se seleccionan mediante `JComboBox`
cargados desde la base de datos.

---

## Arquitectura del proyecto

El proyecto utiliza una organización por capas:

```text
src/main/java/cl/speedfood/
│
├── conexion/
│   └── ConexionDB.java
│
├── dao/
│   ├── EntregaDAO.java
│   ├── PedidoDAO.java
│   └── RepartidorDAO.java
│
├── modelo/
│   ├── Entrega.java
│   ├── EstadoPedido.java
│   ├── Pedido.java
│   ├── Repartidor.java
│   └── TipoPedido.java
│
├── vista/
│   ├── GestionEntregas.java
│   ├── GestionPedidos.java
│   ├── GestionRepartidores.java
│   └── VentanaPrincipal.java
│
└── Main.java

---

## Autora

**Consuelo Martinez**  
Duoc UC — Desarrollo Orientado a Objetos II  
Actividad Sumativa — Semana 8