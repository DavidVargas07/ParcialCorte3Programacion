import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Numero n: ");
        int n = kb.nextInt(); // se pregunta n, siendo la anchura y altura del laberinto
        char matriz[][]=new char[n][n]; // cree una matriz de nxn
        System.out.print("Cadena: ");
        String cadena = kb.next(); // se pregunta la cadena de texto para hacer el laberinto

    }
}