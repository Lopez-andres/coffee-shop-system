package Vista;

import javax.swing.*;

public class VentanaAccion extends JFrame {
    private JPanel panelPrincipal;
    private JLabel campoTexto;
    private JLabel seleccionMesa;
    private JComboBox<String> listaMesas;

    public  VentanaAccion(int cantidadMesas) {
        inicializarVentanaAccion(cantidadMesas);
    }
    private void inicializarVentanaAccion(int cantidadMesas){
        // Agregar mesas enumeradas al JComboBox
        for (int i = 1; i <= cantidadMesas; i++) {
            if (listaMesas != null) {
                listaMesas.addItem("Mesa " + i);
            }
        }

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
