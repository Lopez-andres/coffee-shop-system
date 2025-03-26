package Modelo;

public class Productos {
    private final String nombre;
    private final int precio;
    private final int cantidad;

    public Productos(String nombre, int cantidad, int precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return nombre + " x" + cantidad + " Precio Total: $ " + (precio * cantidad);
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrecio() {
        return precio;
    }
}
