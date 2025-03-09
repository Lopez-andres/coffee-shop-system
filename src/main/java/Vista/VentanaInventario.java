package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*import gm.VentanaInventarioo.servicio.ProductoServicio;
import gm.VentanaInventario.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;*/


//@Component
public class VentanaInventario extends JFrame{
    private JPanel panelPrincipal;
    private JTable tablaProductos;
    private JPanel panelTabla;
    private JPanel panelBotones;
    private JPanel panelFormulario;
    //IProductoServicio productoServicio;

    //@Autowired
    public VentanaInventario() {
        inicializarForma();
    }

    private void  inicializarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,650);
        setLocationRelativeTo(null); //se centra la ventana
    }

    public static void main(String[] args) {
        VentanaInventario ventanainventario = new VentanaInventario();
        ventanainventario.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //this.tablaProductos = new DefaultTableModel(2,4);

    }
}
