import javax.swing.*;

public class Main {
    public static void main(String[] args) {


        //Muestra mensaje
        JOptionPane.showMessageDialog(null,"Hola mundo");
        JOptionPane.showMessageDialog(null,"Estamos en el año 2026");

        // Muestra mensaje para capturar datos
        int n = Integer.parseInt(JOptionPane.showInputDialog("Ingrese un numero: "));
        JOptionPane.showMessageDialog(null, "Este es el numero al cuadrado de: " + (n*n));

        //Mensajes de Error
        JOptionPane.showMessageDialog(null, "Este es el numero al cuadrado de: " + (n*n), "Error", JOptionPane.ERROR_MESSAGE);


    }
}