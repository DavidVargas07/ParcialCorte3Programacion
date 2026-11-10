//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

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