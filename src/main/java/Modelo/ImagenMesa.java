package Modelo;
import javax.swing.*;
import java.awt.*;

public class ImagenMesa extends JPanel {
    private final Image imagen;

    public ImagenMesa(String rutaImagen) {
        this.imagen = new ImageIcon(rutaImagen).getImage();
        setPreferredSize(new Dimension(200, 200)); // Ajustar tamaño del panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 80, 50, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.RED);
            g.drawString("Imagen no encontrada", 10, 20);
        }
    }
}

