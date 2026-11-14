import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Numero n: ");
        int n = kb.nextInt(); // se pregunta n, siendo la anchura y altura del laberinto
        kb.nextLine(); // consume el salto de linea que deja nextInt()

        // proceso de validacion de n
        if (n < 3) {
            System.out.print("n debe ser mayor o igual a 3"); // evaluo que n cumpla con n>=3
        } else {
            char[][] matriz = new char[n][n]; // cree una matriz de nxn
            System.out.print("Cadena: ");
            cadena = kb.nextLine(); // se pregunta la cadena de texto para hacer el laberinto
            int t = 0; // t va a servir para tomar la posicion de cada caracter en 'cadena'
            int o = 0; // o va a verificar que haya una sola O en el texto
            int x = 0; // x va a verificar que haya una sola X en el texto
            boolean sirve = true; // sirve para saber si puede continuar el programa despues de evaluar los caracteres
            int inicioX=0; // medir la posicion en X del inicio
            int inicioY=0; // medir la posicion en Y del inicio
            int finX=0; // medir la posicion en X del fin
            int finY=0; // medir la posicion en Y del fin

            // proceso de validacion de la cadena de texto
            if (cadena.length() != n * n) {
                System.out.print("La cadena no cumple con la longitud. :("); // evaluo si la cadena cumple la longitud
            } else {
                for (int i = 0; i < n; i++) { // evaluar que el texto tenga los caracteres y su cantidad correspondiente e introducirlos en la matriz
                    for (int l = 0; l < n; l++) {
                        if (cadena.charAt(t) == ' ') {
                            matriz[i][l] = cadena.charAt(t);
                        } else if (cadena.charAt(t) == '+') {
                            matriz[i][l] = cadena.charAt(t);
                        } else if (cadena.charAt(t) == 'O') {
                            matriz[i][l] = cadena.charAt(t);
                            inicioX = l; // guarda la posicion en X del inicio
                            inicioY = i; // guarda la posicion de Y del inicio
                            o++;
                        } else if (cadena.charAt(t) == 'X') {
                            matriz[i][l] = cadena.charAt(t);
                            finX = l; // guarda la posicion en X del fin
                            finY = i; // guarda la posicion de Y del fin
                            x++;
                        } else {
                            sirve = false; // si tiene otro caracter
                        }
                        t++;
                    }
                }
                if (!sirve || o != 1 || x != 1) { // el texto no funciona
                    System.out.print("El texto no funciona. :(");
                } else {

                    // Prioridad heurística - Distancia Manhattan
                    ImprimirMatriz.imprimir(matriz, n);
                    MatrizInicial.matriz_inicial(cadena, matriz, n, inicioX, inicioY, finX, finY);
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

