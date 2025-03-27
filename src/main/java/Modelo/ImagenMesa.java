package Modelo;

import javax.swing.*;
import java.awt.*;

public class ImagenMesa extends JPanel {
    private final Image imagen;

    public ImagenMesa() {
        ImageIcon icono = new ImageIcon("src/main/java/mesa.png");
        this.imagen = icono.getImage(); //aqui se obtiene la imagen
        setPreferredSize(new Dimension(300, 300)); // Aumentar tamaño del panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Borra el contenido previo del panel

        if (imagen != null) {
            // Dibuja la imagen en el panel
            g.drawImage(imagen, 80, 50, getWidth(), getHeight(), this);
        } else {
            // Si la imagen no se encuentra, muestra un mensaje de error
            g.setColor(Color.RED);
            g.drawString("Imagen no encontrada", 10, 20);
        }
    }
}

