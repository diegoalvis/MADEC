/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author ALVIS
 */
public class Imagen extends javax.swing.JPanel {
 
    private int valor;
    
    public Imagen(JPanel jPanel1, int val) {
        this.setSize(jPanel1.getWidth()-10, jPanel1.getHeight()-10); //se selecciona el tamaño del panel
        valor = val;
    }
 
    //Se crea un método cuyo parámetro debe ser un objeto Graphics
 
    public void paint(Graphics grafico) {
        Dimension height = getSize();
 
    //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
 
        ImageIcon Img = new ImageIcon(getClass().getResource("/images/MadecLogo"+valor+".png"));
 
        //se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
 
        grafico.drawImage(Img.getImage(), 10, 20, height.width, height.height, null);
        setOpaque(false);
        super.paintComponent(grafico);
    }
}