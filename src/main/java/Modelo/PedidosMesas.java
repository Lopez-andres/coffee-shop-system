package Modelo;

import Vista.AgregarPedido;
import Vista.VentanaPedidos;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidosMesas{
    private int idMesa = 0; //contador que indica el número de mesa
    public List<Productos> productos;
    private ArrayList<PedidosMesas> listaMesas;
    private ArrayList<PedidosMesas> historialPedidos;
    private boolean pagado;// Debe ser privada y recibida por el constructor

    private AgregarPedido AgPedido;

    // Constructor original
    public PedidosMesas(int idMesa, ArrayList<PedidosMesas> listaMesas) {
        this.idMesa = idMesa;
        this.listaMesas = listaMesas;
        this.productos = new ArrayList<>();
        this.historialPedidos = new ArrayList<>();
    }

    // **Nuevo constructor para el historial de pedidos**
    public PedidosMesas(int idMesa, ArrayList<Productos> productos, boolean pagado) {
        this.idMesa = idMesa;
        this.productos = new ArrayList<>(productos); // Copiamos los productos
        this.listaMesas = new ArrayList<>(); // Se deja vacío si es solo para historial
        this.historialPedidos = new ArrayList<>();
        this.pagado = pagado; // Ya está pagado porque es para historial
    }

    public void agregarProducto(Productos producto) {
        this.productos.add(producto);
    }

    public void agregarPedido(VentanaPedidos ventanaPedidos) {
        if (listaMesas == null || listaMesas.isEmpty()) {
            System.out.println("Error: listaMesas es null o está vacía en agregarPedido()");
            return;
        }

        int indexMesaSeleccionada = ventanaPedidos.getListaMesasJComboBox().getSelectedIndex() - 1;

        if (indexMesaSeleccionada >= 0 && indexMesaSeleccionada < listaMesas.size()) {
            PedidosMesas mesaSeleccionada = listaMesas.get(indexMesaSeleccionada);

            // Verificamos si ya existe un pedido para esta mesa
            if (mesaSeleccionada.AgPedido == null || mesaSeleccionada.pagado) {
                System.out.println("\n Creando nuevo pedido para la mesa " + mesaSeleccionada.idMesa);

                mesaSeleccionada.pagado = false;
                mesaSeleccionada.AgPedido = new AgregarPedido(ventanaPedidos, true, mesaSeleccionada);
            } else {
                System.out.println("Pedido ya existente para la mesa " + mesaSeleccionada.idMesa);
            }

        } else {
            System.out.println("Error: Índice de mesa fuera de rango.");
        }
    }

    public void verPedido(VentanaPedidos ventanaPedidos) {
        int indexMesaSeleccionada = ventanaPedidos.getListaMesasJComboBox().getSelectedIndex() - 1;

        if (indexMesaSeleccionada < 0 || indexMesaSeleccionada >= listaMesas.size()) {
            JOptionPane.showMessageDialog(null, "Seleccione una mesa válida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PedidosMesas mesaSeleccionada = listaMesas.get(indexMesaSeleccionada);

        if (mesaSeleccionada.AgPedido == null) {
            JOptionPane.showMessageDialog(null, "No hay un pedido registrado para esta mesa.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Llamamos al metodo corregido y mostramos el mensaje
        String mensaje = mostrarMensaje(mesaSeleccionada);
        JOptionPane.showMessageDialog(null, mensaje, "Resumen del Pedido",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public String mostrarMensaje(PedidosMesas mesaSeleccionada) {
        int total = calcularTotal(mesaSeleccionada);
        return "\tMesa: " + mesaSeleccionada.idMesa + "\n\n" +
                "Gaseosas: " + mesaSeleccionada.AgPedido.getCantidadGaseosa().getText() + "\n" +
                "Papas Fritas: " + mesaSeleccionada.AgPedido.getCantidadPapasFritas().getText() + "\n" +
                "Jugo: " + mesaSeleccionada.AgPedido.getCantidadJugo().getText() + "\n" +
                "\nTipo Producto: " + mesaSeleccionada.productos.size() +
                "\nTotal: $" + total;
    }

    private int calcularTotal(PedidosMesas mesa) {
        int total = 0;
        for (Productos producto : mesa.productos) {
            total += producto.getPrecio() * producto.getCantidad();
        }
        return total;
    }

    public void pagarPedido(VentanaPedidos ventanaPedidos) {
        int indexMesaSeleccionada = ventanaPedidos.getListaMesasJComboBox().getSelectedIndex() - 1;

        if (indexMesaSeleccionada < 0 || indexMesaSeleccionada >= listaMesas.size()) {
            JOptionPane.showMessageDialog(null, "Seleccione una mesa válida.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        PedidosMesas mesaSeleccionada = listaMesas.get(indexMesaSeleccionada);

        if (mesaSeleccionada.AgPedido == null) {
            JOptionPane.showMessageDialog(null, "No hay un pedido registrado para esta mesa.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si el pedido ya fue pagado
        if (mesaSeleccionada.pagado) {
            JOptionPane.showMessageDialog(null, "El pedido de esta mesa ya ha sido pagado.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String mensaje = mostrarMensaje(mesaSeleccionada);
        // Mostrar confirmación antes de proceder con el pago
        int confirmacion = JOptionPane.showConfirmDialog(null,
                mensaje + "\n\t¿Desea hacer el pago de la mesa?",
                "Confirmar Pago",JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            mesaSeleccionada.pagado = true;
            // Aquí podrías guardar el pedido en un historial antes de eliminarlo
            historialPedidos.add(new PedidosMesas(mesaSeleccionada.idMesa,
                    new ArrayList<Productos>(mesaSeleccionada.productos), pagado));
            mesaSeleccionada.productos.clear(); // Vaciar el pedido

            System.out.println("\nLa mesa en que se ha pagado ha sido: " + mesaSeleccionada.idMesa);
            System.out.println("\nlos productos de esta mesa son: " + mesaSeleccionada.productos);
            imprimirHistorial();
            System.out.println("\nLa cantidad de pedidos ha sido de " + historialPedidos.size());
            JOptionPane.showMessageDialog(null, "Pago realizado con éxito.",
                    "Pago Confirmado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void imprimirHistorial() {
        if (historialPedidos.isEmpty()) {
            System.out.println("No hay pedidos en el historial.");
            return;
        }

        System.out.println("Historial de pedidos de todas las mesas:");
        for (PedidosMesas pedido : historialPedidos) {
            System.out.println(pedido);
        }
    }

    public void eliminarPedido(VentanaPedidos ventanaPedidos) {
        int indexMesaSeleccionada = ventanaPedidos.getListaMesasJComboBox().getSelectedIndex() - 1;

        if (indexMesaSeleccionada < 0 || indexMesaSeleccionada >= listaMesas.size()) {
            JOptionPane.showMessageDialog(null, "Seleccione una mesa válida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PedidosMesas mesaSeleccionada = listaMesas.get(indexMesaSeleccionada);

        if (mesaSeleccionada.AgPedido == null) {
            JOptionPane.showMessageDialog(null, "No hay un pedido registrado para esta mesa.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmación antes de eliminar
        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de eliminar el pedido de la Mesa " + mesaSeleccionada.idMesa + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar pedido
            mesaSeleccionada.AgPedido = null;
            mesaSeleccionada.productos.clear(); // Vacía los productos asociados al pedido
            System.out.println("productos de la mesa " + mesaSeleccionada.idMesa + " han sido eliminados: "
                    + mesaSeleccionada.productos);

            JOptionPane.showMessageDialog(null, "Pedido eliminado correctamente.",
                    "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void ReporteVentas() {
        if (historialPedidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay pedidos en el historial de ventas.",
                    "Reporte de Ventas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Map<Integer, Integer> ventasPorMesa = new HashMap<>(); // Guarda la suma de ventas por cada mesa
        Map<Integer, Integer> cantidadPedidosPorMesa = new HashMap<>(); // Guarda cuántos pedidos ha tenido cada mesa
        Map<String, Integer> productosVendidos = new HashMap<>(); // Contador de productos vendidos

        // Recorrer el historial y calcular ventas por mesa y el producto más vendido
        for (PedidosMesas pedido : historialPedidos) {
            int totalMesa = 0;

            for (Productos producto : pedido.productos) {
                int subtotal = producto.getPrecio() * producto.getCantidad();
                totalMesa += subtotal;

                // Contar la cantidad total de cada producto vendido
                productosVendidos.put(producto.getNombre(), productosVendidos.getOrDefault(producto.getNombre(),
                        0) + producto.getCantidad());
            }

            // Sumar las ventas totales por mesa
            ventasPorMesa.put(pedido.idMesa, ventasPorMesa.getOrDefault(pedido.idMesa, 0) + totalMesa);
            cantidadPedidosPorMesa.put(pedido.idMesa, cantidadPedidosPorMesa.getOrDefault(pedido.idMesa, 0) + 1);
        }

        // Determinar el producto más vendido
        String productoMasVendido = "Ninguno";
        int maxCantidad = 0;

        for (Map.Entry<String, Integer> entry : productosVendidos.entrySet()) {
            if (entry.getValue() > maxCantidad) {
                productoMasVendido = entry.getKey();
                maxCantidad = entry.getValue();
            }
        }

        // Construir el mensaje del reporte
        StringBuilder mensaje = new StringBuilder("📊 Reporte de Ventas 📊\n\n");

        for (Map.Entry<Integer, Integer> entry : ventasPorMesa.entrySet()) {
            int mesaId = entry.getKey();
            int totalVentasMesa = entry.getValue();
            int pedidosMesa = cantidadPedidosPorMesa.get(mesaId);
            double promedioMesa = (double) totalVentasMesa / pedidosMesa;

            mensaje.append("🪑 Mesa ").append(mesaId)
                    .append(": Promedio de ventas $").append(String.format("%.0f", promedioMesa)).append("\n");
        }

        mensaje.append("\n🥇 Producto más vendido: ").append(productoMasVendido)
                .append(" (").append(maxCantidad).append(" unidades)");

        JOptionPane.showMessageDialog(null, mensaje.toString(), "📌 Reporte de Ventas",
                JOptionPane.INFORMATION_MESSAGE);
    }


    public String toString() {
        return " mesa " + idMesa + " | productos: " + productos; //nombreProducto //precio
    }

    public String toStringId() {
        return String.valueOf(idMesa);
    }
}
