import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Numero n: ");
        int n = kb.nextInt(); // se pregunta n, siendo la anchura y altura del laberinto
        kb.nextLine(); // consume el salto de linea que deja nextInt()

        // proceso de validacion de n
        if (n<3) {
            System.out.print("n debe ser mayor o igual a 3"); // evaluo que n cumpla con n>=3
        } else {
            char matriz[][] = new char[n][n]; // cree una matriz de nxn
            System.out.print("Cadena: ");
            String cadena = kb.nextLine(); // se pregunta la cadena de texto para hacer el laberinto
            int t = 0; // t va a servir para tomar la posicion de cada caracter en 'cadena'
            int o = 0; // o va a verificar que haya una sola O en el texto
            int x = 0; // x va a verificar que haya una sola X en el texto
            boolean sirve = true; // sirve para saber si puede continuar el programa despues de evaluar los caracteres

            // proceso de validacion de la cadena de texto
            if (cadena.length() != n * n) {
                System.out.print("La cadena no cumple con la longitud. :("); // evaluo si la cadena cumple la longitud
            } else {
                for (int i = 0; i < n; i++) { // evaluar que el texto tenga los caracteres y su cantidad correspondiente e introducirlos en la matriz
                    for (int l = 0; l < n; l++) {
                        if (cadena.charAt(t)==' ') {
                            matriz[i][l]=cadena.charAt(t);
                        } else if (cadena.charAt(t)=='+') {
                            matriz[i][l]=cadena.charAt(t);
                        } else if (cadena.charAt(t)=='O') {
                            matriz[i][l]=cadena.charAt(t);
                            o++;
                        } else if (cadena.charAt(t)=='X') {
                            matriz[i][l]=cadena.charAt(t);
                            x++;
                        } else {
                            sirve = false; // si tiene otro caracter
                        }
                        t++;
                    }
                }
                if (!sirve || o!=1 || x!=1) { // el texto no funciona
                    System.out.print("El texto no funciona. :(");
                } else {
                    imprimir()
                }
            }
        }
    }
    public static char imprimir[char matriz[a][b]] {
        System.out.println("\u001B[48;5;208m\u001B[30m" + "+-" + "\u001B[0m");
        for (int i=0;i<0;i++) {
            System.out.println("\u001B[48;5;208m\u001B[30m" + "---" + "\u001B[0m");
        }
            System.out.print("-+");
    }
}
