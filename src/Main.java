import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Numero n: ");
        int n = kb.nextInt(); // se pregunta n, siendo la anchura y altura del laberinto
        kb.nextLine(); // consume el salto de linea que deja nextInt()
        String cadena = "XXX"; // inicializar la cadena

        // proceso de validacion de n
        while (n < 3 && n!=0) {
            System.out.println("n debe ser mayor o igual a 3"); // evaluo que n cumpla con n>=3
            System.out.println("Para acabar el programa, escriba 0"); // se le da la opcion al usuario de acabrlo o no
            System.out.print("Numero n: ");
            n = kb.nextInt(); // se pregunta n, siendo la anchura y altura del laberinto
            System.out.println();
        }
        if (n==0) {
            System.out.println("El programa ha finalizado.");
            System.out.println("Buen dia :)");
        } else {
            char[][] matriz = new char[n][n]; // cree una matriz de nxn
            System.out.print("Cadena: ");
            cadena = kb.nextLine(); // se pregunta la cadena de texto para hacer el laberinto
            int t = 0; // t va a servir para tomar la posicion de cada caracter en 'cadena'
            int o = 0; // o va a verificar que haya una sola O en el texto
            int x = 0; // x va a verificar que haya una sola X en el texto
            boolean sirve = true; // sirve para saber si puede continuar el programa despues de evaluar los caracteres
            int inicioX = 0; // medir la posicion en X del inicio
            int inicioY = 0; // medir la posicion en Y del inicio
            int finX = 0; // medir la posicion en X del fin
            int finY = 0; // medir la posicion en Y del fin

            // proceso de validacion de la longitud de la cadena de texto
            while ((cadena.length() != n * n) && cadena.length()!=1) { // no sale hasta que el usuario haya escrito la cadena bien, o una cadena de un caracter
                System.out.println("La cadena no cumple con la longitud. :("); // evaluo si la cadena cumple la longitud
                System.out.println("Para acabar el programa, escriba un solo caracter de eleccion.");
                System.out.print("Cadena: ");
                cadena = kb.nextLine();
            }
            if (cadena.length()==1) { // el usuario quiere salir del programa
                System.out.println("El programa ha finalizado.");
                System.out.println("Buen dia :)");
            }
            if (cadena.length()==n*n) { // se cumple con la longitud requerida
                NoTocarComprobar.No_Tocar_Comprobar(n, cadena, matriz, sirve, pos);
                while (!sirve[0] && w) { // algun caracter de la cadena no es correcto y el usuario no ha salido
                    System.out.println("La cadena debe tener solamente espacios ( ), paredes (+), un inicio (O) y un final (X)"); // evaluo que n cumpla con n>=3
                    System.out.println("Para acabar el programa, presione ENTER"); // se le da la opcion al usuario de acabrlo o no
                    System.out.print("Cadena: ");
                    cadena = kb.nextLine(); // vuelve a preguntar la cadena
                    System.out.println();
                    if (cadena.isEmpty()) {
                        w=false; // el usuario quiere salir
                    } else if (cadena.length() != n * n) {
                        System.out.println("La cadena no cumple con la longitud. :(");
                    } else {
                        NoTocarComprobar.No_Tocar_Comprobar(n, cadena, matriz, sirve, pos);
                    }
                }
                if (sirve[0]) {
                    ImprimirMatriz.imprimir(matriz, n);
                    System.out.println();
                } else {
                    System.out.println("El programa ha finalizado.");
                    System.out.println("Buen dia :)");
                }
            }
        }
    }

    // funcion para imprimir la matriz
    public static void imprimir(char[][] matriz, int n) {
        System.out.print("\u001B[48;5;208m\u001B[30m" + "+-" + "\u001B[0m"); // imprimir la ezquina superior izquierda
        for (int i = 0; i < n; i++) {
            System.out.print("\u001B[48;5;208m\u001B[30m" + "---" + "\u001B[0m"); // imprimir los '-' del medio
        }
        System.out.print("\u001B[48;5;208m\u001B[30m" + "-+" + "\u001B[0m"); // imprimir la esquina superior derecha
        System.out.println();
        for (int i=0;i<n;i++) {
            System.out.print("\u001B[48;5;208m\u001B[30m" + "| " + "\u001B[0m"); // imprimir los bordes izquierdos
            for (int l=0;l<n;l++) {
                if (matriz[i][l] == ' ') {
                    System.out.print("\u001B[47m\u001B[30m" + "   " + "\u001B[0m"); // para imprimir los espacios
                } else if (matriz[i][l] == '+') {
                    System.out.print("\u001B[42m\u001B[30m" + " + " + "\u001B[0m"); // para imprimir las paredes internas
                } else if (matriz[i][l] == 'O') {
                    System.out.print("\u001B[100m\u001B[31m" + " O " + "\u001B[0m"); // para imprimir el inicio
                } else if (matriz[i][l] == 'X') {
                    System.out.print("\u001B[48;5;208m\u001B[30m" + " @ " + "\u001B[0m"); // para imprimir el final
                }
            }
            System.out.print("\u001B[48;5;208m\u001B[30m" + " |" + "\u001B[0m"); // imprimir los bordes derechos
            System.out.println();
        }
        System.out.print("\u001B[48;5;208m\u001B[30m" + "+-" + "\u001B[0m"); // imprimir la esquina inferior izquierda
        for (int i = 0; i < n; i++) {
            System.out.print("\u001B[48;5;208m\u001B[30m" + "---" + "\u001B[0m"); // imprimir los '-' del medio
        }
        System.out.print("\u001B[48;5;208m\u001B[30m" + "-+" + "\u001B[0m"); // imprimir la esquina inferior derecha
    }

    //volver a traer la matriz original despues de evaluar cada metodo
    public static void matriz_inicial(String cadena, char[][] matriz, int t, int n, int inicioX, int inicioY, int finX, int finY) {
        for (int i = 0; i < n; i++) { // evaluar que el texto tenga los caracteres y su cantidad correspondiente e introducirlos en la matriz
            for (int l = 0; l < n; l++) {
                if (cadena.charAt(t) == ' ') {
                    matriz[i][l] = cadena.charAt(t);
                } else if (cadena.charAt(t) == '+') {
                    matriz[i][l] = cadena.charAt(t);
                } else if (cadena.charAt(t) == 'O') {
                    matriz[i][l] = cadena.charAt(t);
                } else if (cadena.charAt(t) == 'X') {
                    matriz[i][l] = cadena.charAt(t);
                }
                t++;
            }
        }
    }
}

