package Vista;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import Modelo.PedidosMesas;

import java.util.ArrayList;

public class VentanaInicio extends JFrame {
    private JPanel panelPrincipal; // Se inicializa el panel
    private JLabel Usuario;
    private JTextField campoUsuarioTexto;
    private JLabel Password;
    private JPasswordField passwordTexto;
    private JButton ingresaBoton;
    private JLabel numMesasLabel;
    private JTextField numMesasTexto;

    public VentanaInicio() {
        inicializarForma();
        ingresaBoton.addActionListener(_ -> validar()); //falta de arraylist
    }

    private void inicializarForma(){
        setTitle("VentanaInicio");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(410,490);
        setLocationRelativeTo(null); //se centra la ventana
    }

    private void validar(){
        //validacion para numero de mesas correctos
        int numMesas;
        try {
            numMesas = Integer.parseInt(this.numMesasTexto.getText()); // Convertimos el texto a número
            if (numMesas < 1 || numMesas > 10) {
                mostrarMensaje("El número de mesas debe estar entre 1 y 10.");
                return; // Sale del metodo si la validación falla
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Por favor, ingrese un número válido en el campo de mesas.");
            return; // Sale del metodo si no es un número
        }

        //validacion para usuario y contraseña correctos
        if("user1".equals(this.campoUsuarioTexto.getText()) && "1234".equals(new String(this.passwordTexto.getPassword()))){
            //cerrar la ventana actual
            this.dispose();

            ArrayList<PedidosMesas> listaMesas = new ArrayList<>();
            for (int i = 0; i < numMesas; i++) {
                listaMesas.add(new PedidosMesas(i + 1, listaMesas));
            }

            System.out.println(listaMesas); // Verificación de que la lista se llena correctamente

            VentanaPedidos ventanaPedidos = new VentanaPedidos(numMesas, listaMesas);

        }else if("user1".equals(this.campoUsuarioTexto.getText())){
            mostrarMensaje("Password incorrecto, por favor corregirlo!");
        }else if("1234".equals(new String(this.passwordTexto.getPassword()))){ // convierto el char de getpassword a string
            mostrarMensaje("Usuario incorrecto, por favor corregirlo!");
        }
        else{
            mostrarMensaje("Usuario y Password incorrectos, por favor corregirlos!");
        }
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup(); //cambiar el look and field a modo dark
        VentanaInicio ventanaInicio = new VentanaInicio();
        ventanaInicio.setVisible(true);
    }
}
