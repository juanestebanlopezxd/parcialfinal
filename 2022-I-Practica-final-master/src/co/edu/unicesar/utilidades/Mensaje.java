/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicesar.utilidades;

import co.edu.unicesar.vista.GuiConsulta;
import static co.edu.unicesar.vista.GuiRegistrar.titulo;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author JAIRO
 */
public class Mensaje {
    
    public static void mostrar(JFrame ventana, String titulo, String msg, int icono){
         JOptionPane.showMessageDialog(ventana, msg, titulo, icono);
    }
   
    public static void mostrar(GuiConsulta aThis, String confirmacion, String registro_eliminadi, int INFORMATION_MESSAGE) {
         JOptionPane.showMessageDialog(aThis, registro_eliminadi, confirmacion, INFORMATION_MESSAGE);
    }
    
}
