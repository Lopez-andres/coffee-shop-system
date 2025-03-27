package Vista;

import Modelo.Productos;
import Modelo.PedidosMesas;

import javax.swing.*;

public class AgregarPedido extends JDialog {
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
            guardarPedido();
            AgregarPedido.this.dispose();
        });

        cancelarButton.addActionListener(e -> AgregarPedido.this.dispose());

        setTitle("Mesa " + mesaSeleccionada.toStringId());
        setSize(500, 350);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(PanelPrincipal);
        setVisible(true); //esto siempre debe ir de último
    }

    private void guardarPedido() {
        try {
            /*se hace uso del operador ternario, en caso de que no se llene algun campo de cantidad
            se tome el valor de este campo por defecto como 0*/

            int cantGaseosa = getCantidadGaseosa().getText().isEmpty() ? 0 : Integer.parseInt(getCantidadGaseosa().getText());
            int cantPapas = getCantidadPapasFritas().getText().isEmpty() ? 0 : Integer.parseInt(getCantidadPapasFritas().getText());
            int cantJugo = getCantidadJugo().getText().isEmpty() ? 0 : Integer.parseInt(getCantidadJugo().getText());

            boolean pedidoAgregado = false;
            if (cantGaseosa > 0) {
                this.mesaSeleccionada.agregarProducto(new Productos("Gaseosa",cantGaseosa,2000)); // Precio de ejemplo
                pedidoAgregado = true;
            }
            if (cantPapas > 0) {
                this.mesaSeleccionada.agregarProducto(new Productos("Papas Fritas",cantPapas, 3000));
                pedidoAgregado = true;
            }
            if (cantJugo > 0) {
                this.mesaSeleccionada.agregarProducto(new Productos("Jugo",cantJugo,4200));
                pedidoAgregado = true;
            }

            //contradigo, es decir si es false pasa a ser true y no se ha agregado ningun pedido
            if (!pedidoAgregado) {
                JOptionPane.showMessageDialog(this, "Debe ingresar al menos un " +
                                "producto para crear un pedido.", "Pedido vacío", JOptionPane.WARNING_MESSAGE);
                return; // No cierra la ventana si no hay productos
            }

            System.out.println("Pedido guardado para " + mesaSeleccionada);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
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

