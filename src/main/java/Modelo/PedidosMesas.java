package Modelo;

import Vista.AgregarPedido;
import Vista.VentanaPedidos;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidosMesas{
    private final int idMesa; //contador que indica el número de mesa
    public List<Productos> productos;
    private final ArrayList<PedidosMesas> listaMesas;
    private final ArrayList<PedidosMesas> historialPedidos;
    private boolean pagado; //verifica si un pedido ha sido pagado o no, y se tendra en cuenta para el reporte de ventas
    private AgregarPedido AgPedido;
    private VentanaPedidos ventanaPedidos;

    // Constructor original - crear un pedido asociado a una mesa
    public PedidosMesas(int idMesa, ArrayList<PedidosMesas> listaMesas, VentanaPedidos ventanaPedidos) {
        this.ventanaPedidos = ventanaPedidos;
        this.idMesa = idMesa;
        this.listaMesas = listaMesas;
        this.productos = new ArrayList<>();
        this.historialPedidos = new ArrayList<>();
    }

    // **Nuevo constructor para registrar un pedido en el historial de ventas**
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

    public static void clearConsole() {
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
    }

    public void agregarPedido() {
        //se resta 1 porque la primera posicion del JComboBox es un texto listaMesas
        int indexMesaSeleccionada = ventanaPedidos.getListaMesasJComboBox().getSelectedIndex() - 1;

        if (listaMesas == null || listaMesas.isEmpty()) {
            System.out.println("Error: listaMesas es null o está vacía en agregarPedido()");
            return;
        }

        System.out.println("\nÍndice seleccionado: " + indexMesaSeleccionada);

        if (indexMesaSeleccionada >= 0 && indexMesaSeleccionada <= listaMesas.size()) {

            /*se obtiene el objeto PedidosMesas que se encuentra en esta posicion de la lista
            referente a la mesa escogida, y se agrega el pedido para esta mesa*/

            PedidosMesas mesaSeleccionada = listaMesas.get((indexMesaSeleccionada));

            /*Verificamos si aun no se ha creado un objeto AgregarPedido para esta mesa
            o si el pedido ya ha sido pagado*/
            if (mesaSeleccionada.AgPedido == null || mesaSeleccionada.pagado) {
                System.out.println("Creando nuevo pedido para la mesa " + mesaSeleccionada.idMesa);

                //se establece como un pedido no pagado
                mesaSeleccionada.pagado = false;

                /*se crea una ventana emergente para agregar los productos a la mesa contiene la VentanaPedidos, true para decir que
                la ventana es modal (se bloquea la interaction con ademas ventanas hasta cerrarla, la mesa a la que se agrega el pedido*/

                mesaSeleccionada.AgPedido = new AgregarPedido(ventanaPedidos, true, mesaSeleccionada);
            } else {
                JOptionPane.showMessageDialog(null, "Pedido ya existente para la mesa " + mesaSeleccionada.idMesa,
                        "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Ya hay un pedido en la mesa " + mesaSeleccionada.idMesa);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una mesa válida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: Índice de mesa fuera de rango. . Tamaño de la lista: " + listaMesas.size());
        }
    }

    public void verPedido() {
        //se resta 1 porque la primera posicion del JComboBox es un texto listaMesas
        int indexMesaSeleccionada = ventanaPedidos.getListaMesasJComboBox().getSelectedIndex() -1;

        if (indexMesaSeleccionada < 0 || indexMesaSeleccionada >= listaMesas.size()) {
            JOptionPane.showMessageDialog(null, "Seleccione una mesa válida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*se obtiene el objeto PedidosMesas que se encuentra en esta posicion de la lista
        referente a la mesa escogida, y se visualiza el pedido de esta mesa*/
        PedidosMesas mesaSeleccionada = listaMesas.get(indexMesaSeleccionada);

        //Verificamos si aun no se ha creado un objeto  para esta mesa
        if (mesaSeleccionada.AgPedido == null) {
            JOptionPane.showMessageDialog(null, "No hay un pedido registrado para esta mesa.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Llamamos al metodo mostrarMensaje sobre el pedido hecho a una mesa
        String mensaje = mostrarMensaje(mesaSeleccionada);

        JOptionPane.showMessageDialog(null, mensaje, "Resumen del Pedido",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public String mostrarMensaje(PedidosMesas mesaSeleccionada) {
        //se obtienen los datos correspondientes de cada mesa
        int total = calcularTotal(mesaSeleccionada);
        return "\tMesa: " + mesaSeleccionada.idMesa + "\n\n" +
                "Gaseosas: " + mesaSeleccionada.AgPedido.getCantidadGaseosa().getText() + "\n" +
                "Papas Fritas: " + mesaSeleccionada.AgPedido.getCantidadPapasFritas().getText() + "\n" +
                "Jugo: " + mesaSeleccionada.AgPedido.getCantidadJugo().getText() + "\n" +
                "\nTotal: $" + total;
    }

    private int calcularTotal(PedidosMesas mesa) {
        int total = 0;
        //se itera sobre a lista total de productos de la mesa
        for (Productos producto : mesa.productos) {
            /* se obtienen la cantidad y precio y se multiplican, y esto es sumado. */
            total += producto.precio() * producto.cantidad();
        }
        System.out.println("\n\nEl total del pedido es: " + total);
        return total;
    }

    public void pagarPedido() {
        //se resta 1 porque la primera posicion del JComboBox es un texto listaMesas
        int indexMesaSeleccionada = ventanaPedidos.getListaMesasJComboBox().getSelectedIndex()-1;

        if (indexMesaSeleccionada < 0 || indexMesaSeleccionada >= listaMesas.size()) {
            JOptionPane.showMessageDialog(null, "Seleccione una mesa válida.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*se obtiene el objeto PedidosMesas que se encuentra en esta posicion de la lista
        referente a la mesa escogida, y se paga el pedido de esta mesa*/

        PedidosMesas mesaSeleccionada = listaMesas.get(indexMesaSeleccionada);

        //Verificamos si aun no se ha creado un objeto  para esta mesa
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

        //mostrar el mensaje de pago, con los productos y el precio a la mesa consumida
        String mensaje = mostrarMensaje(mesaSeleccionada);

        // Mostrar confirmación antes de proceder con el pago
        int confirmacion = JOptionPane.showConfirmDialog(null,
                mensaje + "\n\t¿Desea hacer el pago de la mesa?",
                "Confirmar Pago",JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            //pago de la mesa pasa a ser true
            mesaSeleccionada.pagado = true;

            /*una vez pagado se elimina el pedido de listaMesas y este pedido pasa a historiaPedidos
            para el reporte de ventas y agregar mas pedidos a la mesa*/

            historialPedidos.add(new PedidosMesas(mesaSeleccionada.idMesa,
                    new ArrayList<>(mesaSeleccionada.productos), pagado));

            mesaSeleccionada.productos.clear(); // Vaciar el pedido

            clearConsole();
            System.out.println("\nLa mesa agregada para el historial es la mesa: " + mesaSeleccionada.idMesa);
            imprimirHistorial();
            System.out.println("\nLa cantidad de pedidos ha sido de " + historialPedidos.size()); //se visualiza la cantidad de pedidos
            JOptionPane.showMessageDialog(null, "Pago realizado con éxito.",
                    "Pago Confirmado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void imprimirHistorial() {
        //se evalua de que el ArrayList no este vacio
        if (historialPedidos.isEmpty()) {
            System.out.println("No hay pedidos en el historial.");
            return;
        }

        clearConsole();
        System.out.println("Historial de pedidos de todas las mesas:");

        //se recorre la lista de historial de pedidos y se imprime cada pedido
        for (PedidosMesas pedido : historialPedidos) {
            System.out.println(pedido);
        }
    }

    public void eliminarPedido() {
        //se resta 1 porque la primera posicion del JComboBox es un texto listaMesas
        int indexMesaSeleccionada = ventanaPedidos.getListaMesasJComboBox().getSelectedIndex() - 1;

        if (indexMesaSeleccionada < 0 || indexMesaSeleccionada >= listaMesas.size()) {
            JOptionPane.showMessageDialog(null, "Seleccione una mesa válida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*se obtiene el objeto PedidosMesas que se encuentra en esta posicion de la lista
        referente a la mesa escogida, y se agrega el pedido para esta mesa*/
        PedidosMesas mesaSeleccionada = listaMesas.get(indexMesaSeleccionada);

         /*Verificamos si aun no se ha creado un objeto asociado a esta mesa*/
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
            mesaSeleccionada.AgPedido = null; //Se establece que la mesa ya no tiene un pedido activo
            mesaSeleccionada.productos.clear(); // Vacía los productos asociados al pedido
            clearConsole();
            System.out.println("productos de la mesa " + mesaSeleccionada.idMesa + " han sido eliminados: "
                    + mesaSeleccionada.productos);

            JOptionPane.showMessageDialog(null, "Pedido eliminado correctamente.",
                    "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void ReporteVentas() {
        //Se evalua de que el ArrayList no este vacio
        if (historialPedidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay pedidos en el historial de ventas.",
                    "Reporte de Ventas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Map<Integer, Integer> ventasPorMesa = new HashMap<>(); // Suma de ventas por cada mesa (IdMesa -> totalVentas)
        Map<Integer, Integer> cantidadPedidosPorMesa = new HashMap<>(); // Cantidad de pedidos por mesa (IdMesa -> cantidadPedidos)
        Map<String, Integer> productosVendidos = new HashMap<>(); // Contador de productos vendidos (nombreProducto -> CantidadVendida)
        int totalVentas = 0; // 🔥 Nueva variable para total de ventas en general

        // Recorremos el historial de pedidos -> Producto mas vendido, cantidad de pedidos por mesa, ventas totales por mesa
        for (PedidosMesas pedido : historialPedidos) {
            int totalMesa = 0;

            //En cada pedido obtenemos el precio y cantidad de cada producto
            for (Productos producto : pedido.productos) {
                int subtotal = producto.precio() * producto.cantidad(); //cada producto
                totalMesa += subtotal; //toda la mesa
                totalVentas += subtotal; //  Se suma al total general

                /*Contar la cantidad total de cada producto vendido
                put es un metodo en un HashMap que actualiza o agrega la clave con su determinado valor (diccionarios en python)
                tiene su clave que es el nombre y su valor que es la cantidad y este se actualiza con la nueva cantidad*/

                productosVendidos.put(producto.nombre(), productosVendidos.getOrDefault(producto.nombre(), 0) + producto.cantidad());
            }

            /*Sumar las ventas totales por mesa y actualiza las ventas totales de las mesas
            la clave es el idMesa y su valor es el total de esa mesa que irá actualizándose según las ventas*/

            ventasPorMesa.put(pedido.idMesa, ventasPorMesa.getOrDefault(pedido.idMesa, 0) + totalMesa);

            /*la clave es el idMesa, luego se busca cuantos pedidos ha tenido la mesa, por defecto es 0, +1 es para un nuevo pedido
            el objetivo es calcular la cantidad de pedidos por mesa*/

            cantidadPedidosPorMesa.put(pedido.idMesa, cantidadPedidosPorMesa.getOrDefault(pedido.idMesa, 0) + 1);
        }

        // Determinar el producto más vendido
        String productoMasVendido = "Ninguno";
        int maxCantidad = 0;

        /*Aquí se determina cuál fue el producto mas vendido (se itera sobre cada producto y su # de ventas)
        - la clave es el nombreProducto y el valor es CantidadTotalVentasProducto */

        for (Map.Entry<String, Integer> entry : productosVendidos.entrySet()) {

            //entreySet() devuelve conjuntos clave:valor ----> nombreProducto : cantidadProducto
            if (entry.getValue() > maxCantidad) {
                //se compara la cantidad vendida del producto actual con la maxima registrada
                productoMasVendido = entry.getKey(); //se guarda el nombre del producto mas vendido
                maxCantidad = entry.getValue(); //la maxima cantidad de ventas de ese producto
            }
        }

        // Construir el mensaje del reporte
        StringBuilder mensaje = new StringBuilder("📊 Reporte de Ventas 📊\n\n");

        //se itera sobre el HashMap para obtener el total vendido de cada mesa
        for (Map.Entry<Integer, Integer> entry : ventasPorMesa.entrySet()) {
            int mesaId = entry.getKey();            //obtiene el número de mesa
            int totalVentasMesa = entry.getValue();         //total de dinero del pedido de esta mesa
            int pedidosMesa = cantidadPedidosPorMesa.get(mesaId);       //cantidad de pedidos hechos en esta mesa
            double promedioMesa = (double) totalVentasMesa / pedidosMesa;       //calcula el promedio de ventas de esta mesa

            mensaje.append("☕ Mesa ").append(mesaId)
                    .append(": Promedio de ventas $").append(String.format("%.0f", promedioMesa)).append("\n");
        }

        // Agregar total de ventas al reporte
        mensaje.append("\n💰 **Total de Ventas: $").append(totalVentas).append("**\n");

        mensaje.append("\n🥇 Producto más vendido: ").append(productoMasVendido)
                .append(" (").append(maxCantidad).append(" unidades)");

        JOptionPane.showMessageDialog(null, mensaje.toString(), "📌 Reporte de Ventas",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public String toString() {
        return " mesa " + idMesa + " | productos: " + productos;
    }

    public String toStringId() { //para el título de la ventana mesa
        return String.valueOf(idMesa);
    }
}
