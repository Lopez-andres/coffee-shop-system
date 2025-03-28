package Modelo;

public record Productos(String nombre, int cantidad, int precio) {
    /*
    -   En Productos se hace el uso de record ya que esto es una version inmutable de una clase
    -   Por tanto no se hace uso de:  constructores, getters, setters ni métodos equals y hashCode
     */
    @Override
    public String toString() {
        return nombre + " x" + cantidad + " Precio Total: $ " + (precio * cantidad);
    }
}


