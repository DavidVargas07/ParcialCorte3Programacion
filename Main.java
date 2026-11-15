import java.util.Scanner:
public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("ingrese numero de ancho y alto: ");
        int anchoAlto = kb.nextInt(); // se pregunta anchoAlto, siendo la anchura y altura del laberinto
        kb.nextLine(); // consume el salto de linea que deja nextInt()
        String cadena = "XXX"; // inicializar la cadena
        boolean w = true; // saber si el usuario quiere salir o no en la cadena
    }
}