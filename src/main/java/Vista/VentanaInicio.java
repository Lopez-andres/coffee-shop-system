package Vista;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import Modelo.PedidosMesas;
import java.awt.*;
import java.util.ArrayList;

public class VentanaInicio extends JFrame {
    private JPanel panelPrincipal; // Se inicializa el panel
    private JLabel Usuario, Password, numMesasLabel, LoginJLabel;
    private JTextField campoUsuarioTexto, numMesasTexto;
    private JPasswordField passwordTexto;
    private JButton ingresarButton, modeButton;

    private boolean modoOscuro = true; // Estado inicial en oscuro

    public VentanaInicio() {
        inicializarForma();
        ingresarButton.addActionListener(_ -> validar()); //Se asocia este boton con su respectiva validacion
        modeButton.addActionListener(_ -> cambioColor()); //Se asocia este boton a cambiar el tema cuando se presione
    }

    private void cambioColor() {
        try {
            if (modoOscuro) {
                UIManager.setLookAndFeel(new FlatLightLaf()); // Modo claro
            } else {
                UIManager.setLookAndFeel(new FlatDarculaLaf()); // Modo oscuro
            }
            modoOscuro = !modoOscuro; // Alternar estado

            SwingUtilities.updateComponentTreeUI(this); // Aplicar cambios y actualizar la interfaz
            restaurarFuente(); // Volver a aplicar la fuente
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void restaurarFuente() {
        //se define la fuente de cada componente
        Font fuente = new Font("Monospaced", Font.PLAIN, 20); // Fuente Monospaced, tamaño 20, estilo normal
        Font fuente2 = new Font("Monospaced", Font.BOLD, 30);
        LoginJLabel.setFont(fuente2);
        Usuario.setFont(fuente);
        Password.setFont(fuente);
        numMesasLabel.setFont(fuente);
        campoUsuarioTexto.setFont(fuente);
        passwordTexto.setFont(fuente);
        numMesasTexto.setFont(fuente);
        ingresarButton.setFont(fuente);
        modeButton.setFont(fuente);
    }

    private void inicializarForma(){
        //se maneja la inicializacion de la ventana y sus dimensiones
        setTitle("VentanaInicio");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(410,490);
        setLocationRelativeTo(null); //se centra la ventana
    }

    private void validar(){
        String usuario = campoUsuarioTexto.getText().trim();
        String password = new String(passwordTexto.getPassword()).trim();
        String numMesasStr = numMesasTexto.getText().trim();

        // Verificar si los campos están vacíos
        if (usuario.isEmpty() || password.isEmpty() || numMesasStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos antes de continuar.",
                    "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return; // Detener ejecución si hay campos vacíos
        }

        //validacion para numero de mesas correctas
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

            // Crear una única instancia de VentanaPedidos
            VentanaPedidos ventanaPedidos = new VentanaPedidos(numMesas, listaMesas);

            /*Se recorre desde 0 hasta el número de mesas ingresado (numMesas, ejem: 3)
            En cada iteración, se crea un nuevo objeto de la clase PedidosMesas que contiene
            el id de la mesa y la lista de productos y esto se añade listaMesas

            Cada PedidosMesas recibe la lista de todas las mesas como referencia*/

            for (int i = 0; i < numMesas; i++) {
                listaMesas.add(new PedidosMesas(i+1, listaMesas, ventanaPedidos));
            }

            System.out.println(listaMesas); // Verificación de que la lista se llena correctamente

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
