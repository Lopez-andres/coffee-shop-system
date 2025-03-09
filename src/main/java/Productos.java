public class Productos {
    private String nombre;
    private int precio;

    public Productos(String nombre, int precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return nombre; //para que JcomboBox muestre el nombre del producto
    }
}
