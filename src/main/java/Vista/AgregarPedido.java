package Vista;

import Modelo.Productos;
import Modelo.PedidosMesas;
import javax.swing.*;
import java.util.ArrayList;

public class AgregarPedido extends JDialog {
    //se declaran los atributos
    private JPanel PanelPrincipal, PanelTitulosColumnas, PanelInformacionProductos, PanelButtons;
    private JTextField CantidadGaseosa, CantidadPapasFritas, CantidadJugo;
    private JButton cancelarButton, aceptarButton;
    private JLabel GaseosaJLabel, PapasFritasJLabel, JugoJLabel, PrecioGaseosa, PrecioPapasFritas, PrecioJugo;
    private final PedidosMesas mesaSeleccionada; //se crea para que toda la clase acceda a esta informacion durante la ejecucion

    /* este constructor recibe la ventana que invoca el JDialog, modal para bloquear la ventana
      Principal hasta que se cierre esta y la mesa a la que se agregara el pedido */

    public AgregarPedido(JFrame parent, boolean modal,  PedidosMesas mesaSeleccionada) {
        super(parent, modal); //llamada al constructor de JDialog configurado JFrame y JDialog
        this.mesaSeleccionada = mesaSeleccionada;

        aceptarButton.addActionListener(e -> {
            if (guardarPedido()) { // Si el pedido se guardó correctamente
                AgregarPedido.this.dispose(); // Cierra la ventana
            }
        });

        cancelarButton.addActionListener(e -> {
            AgregarPedido.this.dispose();
        });

        setTitle("Mesa " + mesaSeleccionada.toStringId());
        setSize(500, 350);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(PanelPrincipal);
        setVisible(true); //esto siempre debe ir de último
    }

    private boolean guardarPedido() {
        try {
            // Obtener cantidades de productos, haciendo uso de operador ternario en caso de que la cantidad del producto no sea >0
            //si el texto esta vacio se asigna 0, sino se convierte en un numero
            int cantGaseosa = getCantidadGaseosa().getText().isEmpty() ? 0 : Integer.parseInt(getCantidadGaseosa().getText());
            int cantPapas = getCantidadPapasFritas().getText().isEmpty() ? 0 : Integer.parseInt(getCantidadPapasFritas().getText());
            int cantJugo = getCantidadJugo().getText().isEmpty() ? 0 : Integer.parseInt(getCantidadJugo().getText());

            // Verificar si todas las cantidades son 0
            if (cantGaseosa <= 0 && cantPapas <= 0 && cantJugo <= 0) {
                JOptionPane.showMessageDialog(this, "Debe ingresar al menos un producto para crear un pedido.",
                        "Pedido vacío", JOptionPane.WARNING_MESSAGE);
                return false; // Indica que el pedido no fue guardado
            }

            // Agregar productos según las cantidades
            if (cantGaseosa > 0) { //a la mesa agregamos un producto con su respectiva cantidad
                this.mesaSeleccionada.agregarProducto(new Productos("Gaseosa", cantGaseosa, 2000));
            }
            if (cantPapas > 0) {
                this.mesaSeleccionada.agregarProducto(new Productos("Papas Fritas", cantPapas, 3000));
            }
            if (cantJugo > 0) {
                this.mesaSeleccionada.agregarProducto(new Productos("Jugo", cantJugo, 4200));
            }

            System.out.println("Pedido guardado para la mesa: " + mesaSeleccionada);
            return true; // Indica que el pedido fue guardado exitosamente

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /*Estos campos (JTextField) pertenecen a esta clase interfaz gráfica,
    donde el usuario ingresa manualmente la cantidad de productos*/

    public JTextField getCantidadGaseosa() {
        return CantidadGaseosa;
    }

    public JTextField getCantidadPapasFritas() {
        return CantidadPapasFritas;
    }

    public JTextField getCantidadJugo() {
        return CantidadJugo;
    }
}

