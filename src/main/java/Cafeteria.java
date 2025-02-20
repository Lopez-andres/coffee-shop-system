import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;

public class Cafeteria extends JFrame{
    private JPanel panelPrincipal;
    private JTextField CampoTexto;

    public Cafeteria(){
        InicializarCafeteria();
    }

    private void InicializarCafeteria(){
        setContentPane(panelPrincipal); //"Quiero que esta parte de la ventana (panel1) sea donde aparezcan los botones, etiquetas, textos, etc."
        setTitle("Cafeteria");
        setSize(1000,700); //tamaño
        setLocationRelativeTo(null); //para indicar que centramos la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cuando cerremos la ventana, tambien finalizamos la aplicacion
    }

    public static void main(String[] args) {
        Cafeteria cafeteria = new Cafeteria();
        cafeteria.setVisible(true); //para que se visualize el formulario
        //FlatLightLaf.setup(); modo claro
        FlatDarculaLaf.setup(); //modo oscuro
    }



}




