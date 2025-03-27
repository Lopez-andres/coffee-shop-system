package Vista;

import javax.swing.*;
import Modelo.ImagenMesa;
import Modelo.PedidosMesas;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPedidos extends JFrame{
    private JPanel panelPrincipal, panelImagenMesa, panelButtons, panelReportes;
    private JComboBox<Integer> listaMesasJComboBox;
    private JButton agregarPedidoJButton, verPedidoJButton, eliminarPedidoJButton, cobrarPedidoJButton, reporteVentasJButton;
    private JButton GuiaUsuarioJButton;
    private ArrayList<PedidosMesas> listaMesas;
    private final PedidosMesas pdMesas; //instancia que me sirve para llamar metodos de la clase
    private ImagenMesa mesaImagen; // Declarar la imagen de la mesa

    public VentanaPedidos(int numMesas, ArrayList<PedidosMesas> listaMesas) {
        this.listaMesas = (listaMesas != null) ? listaMesas : new ArrayList<>(); // Asegurar que no sea this.listaMesas no sea null, y en caso de que si se cree una nueva lista
        this.pdMesas = new PedidosMesas(0, this.listaMesas, this); // Usar la lista correctamente inicializada

        inicializarVentanaAccion(numMesas);
        configuracionEventos();

        // 🔹 Crear y agregar el panel de la imagen de la mesa con el botón de cambio de imagen
        mesaImagen = new ImagenMesa();
        panelImagenMesa.setLayout(new BorderLayout());
        panelImagenMesa.add(mesaImagen, BorderLayout.CENTER);
    }

    private void configuracionEventos() {
        // _ es solo un marcador de posición cuando el parámetro no se usa.
        agregarPedidoJButton.addActionListener(_ -> pdMesas.agregarPedido());
        verPedidoJButton.addActionListener(_ -> pdMesas.verPedido());
        eliminarPedidoJButton.addActionListener(_ -> pdMesas.eliminarPedido());
        cobrarPedidoJButton.addActionListener(_ -> pdMesas.pagarPedido());
        reporteVentasJButton.addActionListener(_ -> pdMesas.ReporteVentas());
        GuiaUsuarioJButton.addActionListener(_ -> mensajeGuiaUsuario());
    }

    private void mensajeGuiaUsuario(){
        JOptionPane.showMessageDialog(null,
                "<html><body style='font-size:14px;'>"
                        + "<b>📖 Bienvenido a <i>VeraBox Colombia</i> 📖</b><br><br>"
                        + "🍽️ <b>1.</b> Seleccione una mesa en la lista desplegable.<br>"
                        + "🛒 <b>2.</b> Agregue productos al pedido con <i>'Agregar Pedido'</i>.<br>"
                        + "🔍 <b>3.</b> Revise el pedido con <i>'Ver Pedido'</i>.<br>"
                        + "❌ <b>4.</b> Para corregir algo, use <i>'Eliminar Pedido'</i>.<br>"
                        + "💳 <b>5.</b> Para finalizar, haga clic en <i>'Pagar Pedido'</i>.<br><br>"
                        + "📊 Puede generar un reporte de ventas en el botón correspondiente."
                        + "</body></html>",
                "📌 Guía de Usuario", JOptionPane.INFORMATION_MESSAGE);
    }

    private void inicializarVentanaAccion(int numMesas){
        // Agrega las mesas enumeradas al JComboBox para que el usuario puede seleccionarlas
        for (int i = 1; i <= numMesas; i++) {
            if (listaMesasJComboBox != null) {
                listaMesasJComboBox.addItem(i);
            }
        }

        setTitle("VentanaPedidos");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JComboBox<Integer> getListaMesasJComboBox() {
        return listaMesasJComboBox;
    }
}


