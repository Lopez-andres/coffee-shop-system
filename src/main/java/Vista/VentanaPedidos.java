package Vista;
import javax.swing.*;

import Modelo.ImagenMesa;
import Modelo.PedidosMesas;

import java.awt.*;
import java.util.ArrayList;

public class VentanaPedidos extends JFrame{
    private JPanel panelPrincipal;
    private JPanel panelImagenMesa;
    private ImagenMesa mesaImagen;
    private JPanel panelButtons;
    private JPanel panelReportes;
    private JComboBox<Integer> listaMesasJComboBox;
    private JButton agregarPedidoJButton;
    private JButton verPedidoJButton;
    private JButton eliminarPedidoJButton;
    private JButton cobrarPedidoJButton;
    private JButton reporteVentasJButton;
    private ArrayList<PedidosMesas> listaMesas;
    private PedidosMesas pdMesas;

    public VentanaPedidos(int numMesas, ArrayList<PedidosMesas> listaMesas) {
        this.listaMesas = (listaMesas != null) ? listaMesas : new ArrayList<>(); // Asegurar que no sea null
        this.pdMesas = new PedidosMesas(0, this.listaMesas); // Usar la lista correctamente inicializada

        inicializarVentanaAccion(numMesas);
        configuracionEventos();

        // 🔹 Agregar la imagen al panelImagenMesa
        mesaImagen = new ImagenMesa("src/main/java/mesa.png"); // Ruta de la imagen
        panelImagenMesa.setLayout(new BorderLayout()); // Asegurar layout
        panelImagenMesa.add(mesaImagen, BorderLayout.CENTER);
        panelImagenMesa.revalidate();
        panelImagenMesa.repaint();
    }

    private void configuracionEventos() {
        agregarPedidoJButton.addActionListener(e -> pdMesas.agregarPedido(this));
        verPedidoJButton.addActionListener(e -> pdMesas.verPedido(this));
        eliminarPedidoJButton.addActionListener(e -> pdMesas.eliminarPedido(this));
        cobrarPedidoJButton.addActionListener(e -> pdMesas.pagarPedido(this));
        reporteVentasJButton.addActionListener(e -> pdMesas.ReporteVentas());
    }

    private void inicializarVentanaAccion(int numMesas){
        // Agregar mesas enumeradas al JComboBox
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


