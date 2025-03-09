package Vista;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelPrincipal; // Se inicializa el panel
    private JLabel Usuario;
    private JTextField campoUsuarioTexto;
    private JLabel Password;
    private JPasswordField passwordTexto;
    private JButton ingresaBoton;
    private JLabel numMesas;
    private JTextField numMesasTexto;

    public VentanaPrincipal() {
        inicializarForma();
        ingresaBoton.addActionListener(e -> validar());
    }

    private void inicializarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(410,490);
        setLocationRelativeTo(null); //se centra la ventana
    }

    private void validar(){
        //Aqui se leeran los valores de los campos de texto
        var usuario = this.campoUsuarioTexto.getText(); //recuperamos la informacion de la caja de texto de usuario
        var password = new String(this.passwordTexto.getPassword()); //recuperamos la informacion de la caja de texto de password
        var numMesasTexto = this.numMesasTexto.getText().trim(); //capturamos la informacion y dejamos espacios en blanco

        //validacion para numero de mesas correctos
        int numMesas = 0;
        try {
            numMesas = Integer.parseInt(numMesasTexto); // Convertimos el texto a número

            if (numMesas < 1 || numMesas > 10) {
                mostrarMensaje("El número de mesas debe estar entre 1 y 10.");
                return; // Sale del metodo si la validación falla
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Por favor, ingrese un número válido en el campo de mesas.");
            return; // Sale del metodo si no es un número
        }

        //validacion para usuario y contraseña correctos
        if("user1".equals(usuario) && "1234".equals(password)){
            //abrir la nueva ventana y cerrar la actual

            VentanaInventario ventanainventario = new VentanaInventario();
            ventanainventario.setVisible(true);

            /*VentanaAccion ventanaAccion = new VentanaAccion(numMesas);
            ventanaAccion.setVisible(true);*/
            this.dispose(); //Cierra la ventana actual

        }else if("user1".equals(usuario)){
            mostrarMensaje("Password incorrecto, por favor corregirlo!");
        }else if("1234".equals(password)){
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
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.setVisible(true);
    }
}
