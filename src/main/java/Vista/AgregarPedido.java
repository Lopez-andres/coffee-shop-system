package Vista;

import Modelo.Productos;
import Modelo.PedidosMesas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarPedido extends JDialog {
    private JPanel PanelPrincipal;
    private JPanel PanelTitulosColumnas;
    private JPanel PanelInformacionProductos;
    private JPanel PanelButtons;
    private JTextField CantidadGaseosa;
    private JTextField CantidadPapasFritas;
    private JTextField CantidadJugo;
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JLabel GaseosaJLabel;
    private JLabel PapasFritasJLabel;
    private JLabel JugoJLabel;
    private JLabel PrecioGaseosa;
    private JLabel PrecioPapasFritas;
    private JLabel PrecioJugo;
    private PedidosMesas mesaSeleccionada;

    public AgregarPedido(JFrame parent, boolean modal,  PedidosMesas mesaSeleccionada) {
        super(parent, modal);
        this.mesaSeleccionada = mesaSeleccionada;

        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPedido();
                AgregarPedido.this.dispose();
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarPedido.this.dispose();
            }
        });

        setTitle("Mesa " + mesaSeleccionada.toStringId());
        setSize(500, 350);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(PanelPrincipal);
        setVisible(true); //esto siempre debe ir de último
        // Agrega aquí los componentes necesarios, por ejemplo: JTable, JLabel, etc.
    }

    private void guardarPedido() {
        try {
            // se hace uso del operador ternario
            int cantGaseosa = CantidadGaseosa.getText().isEmpty() ? 0 : Integer.parseInt(CantidadGaseosa.getText());
            int cantPapas = CantidadPapasFritas.getText().isEmpty() ? 0 : Integer.parseInt(CantidadPapasFritas.getText());
            int cantJugo = CantidadJugo.getText().isEmpty() ? 0 : Integer.parseInt(CantidadJugo.getText());

            boolean pedidoAgregado = false;
            if (cantGaseosa > 0) {
                mesaSeleccionada.agregarProducto(new Productos("Gaseosa",cantGaseosa,2000)); // Precio de ejemplo
                pedidoAgregado = true;
            }
            if (cantPapas > 0) {
                mesaSeleccionada.agregarProducto(new Productos("Papas Fritas",cantPapas, 3000));
                pedidoAgregado = true;
            }
            if (cantJugo > 0) {
                mesaSeleccionada.agregarProducto(new Productos("Jugo",cantJugo,4200));
                pedidoAgregado = true;
            }

            if (!pedidoAgregado) {
                JOptionPane.showMessageDialog(this, "Debe ingresar al menos un " +
                                "producto para crear un pedido.", "Pedido vacío", JOptionPane.WARNING_MESSAGE);
                return; // No cierra la ventana si no hay productos
            }

            System.out.println("Pedido guardado para " + mesaSeleccionada.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

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
