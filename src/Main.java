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
            boolean[] sirve = new boolean[1]; // el booleano 'sirve' evalua si sirve o no
            int[] pos = new int[4]; // guarda las posiciones de el inicio y final ;
            // posiciones: pos[0]=inicioX, pos[1]=inicioY, pos[2]=finX, pos[3]=finY

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
                    } else if (cadena.length() != anchoAlto * anchoAlto) {
                        System.out.println("La cadena no cumple con la longitud. :(");
                    } else {
                        NoTocarComprobar.No_Tocar_Comprobar(anchoAlto, cadena, matriz, sirve, pos);
                    }
                }

                if (sirve[0]) { // lista booleana de un solo caracter para saber si la cadena es valida
                    System.out.println();
                    ImprimirMatriz.imprimir(matriz, anchoAlto); // ejecuta la funcion para imprimir la matriz
                    System.out.println();
                    System.out.println();

                    // estrategia Manhattan (prioridad heuristica)
                    System.out.println("ESTRATEGIA MANHATAN"); // titulo general de la estartegia
                    System.out.println(); // espacio por organizacion

                    boolean[] estado = new boolean[2]; // cree una lista de 2 booleanos, el 0 para saber si llego (true) y el segundo para saber si llego al maximo de llamadas
                    int[] cont = new int[6]; // cree una lista de 6 contadores, en orden guardan
                    // la longitud de la ruta,
                    // las celdas distintas,
                    // direcciones revisadas,
                    // retrocesos
                    // llamadas o veces en que entro a una casilla,
                    // movimientos totales

                    long[] tiempos = new long[5]; // tiempo de cada una de las 5 repeticiones, en nanosegundos

                    // imprimesion del laberinto en cada movimiento (no se toma en cuenta el tiempo)
                    System.out.println();
                    System.out.println("Recorrido de la serpiente:");
                    MatrizInicial.matriz_inicial(cadena, matriz, anchoAlto, pos[0], pos[1], pos[2], pos[3]); // la funcion imprime la matriz completa
                    Manhattan.resolver(matriz, anchoAlto, pos, estado, cont, true);

                    for (int r = 0; r < 5; r++) { // 5 repeticiones sin imprimir, para sacar la mediana del tiempo
                        MatrizInicial.matriz_inicial(cadena, matriz, anchoAlto, pos[0], pos[1], pos[2], pos[3]); // empezar sin el camino de la repeticion anterior
                        long inicio = System.nanoTime(); // tiempo antes de resolver ; no funciona time.time() por ser java
                        Manhattan.resolver(matriz, anchoAlto, pos, estado, cont, false);
                        long fin = System.nanoTime(); // tiempo despues de resolver (como final=time.time())
                        tiempos[r] = fin - inicio; // lo que se demoro esta repeticion
                    }

                    // ordenamiento burbuja de los tiempos para sacar la mediana
                    for (int i = 0; i < 4; i++) {
                        for (int l = 0; l < 4 - i; l++) {
                            if (tiempos[l] > tiempos[l + 1]) {
                                long aux = tiempos[l];
                                tiempos[l] = tiempos[l + 1];
                                tiempos[l + 1] = aux;
                            }
                        }
                    }

                    if (estado[0]) {
                        System.out.println("Camino encontrado:");
                        ImprimirMatriz.imprimir(matriz, anchoAlto);
                        System.out.println();
                    } else if (estado[1]) {
                        System.out.println("Se alcanzo el limite de " + Manhattan.LIMITE + " llamadas sin encontrar la X.");
                    } else {
                        System.out.println("No hay camino de la O a la X.");
                    }
                    System.out.println("Longitud de la ruta: " + cont[0]);
                    System.out.println("Celdas distintas examinadas: " + cont[1]);
                    System.out.println("Intentos: " + cont[2]);
                    System.out.println("Retrocesos: " + cont[3]);
                    System.out.println("Llamadas: " + cont[4]);
                    System.out.println("Movimientos totales (avances + retrocesos): " + cont[5]);
                    System.out.print("Tiempos (ordenados, en ms):");
                    for (int r = 0; r < 5; r++) {
                        System.out.print(" " + (tiempos[r] / 1000000.0)); // pasar de nanosegundos a milisegundos
                    }
                    System.out.println();
                    System.out.println("Mediana del tiempo: " + (tiempos[2] / 1000000.0) + " ms");

                    MatrizInicial.matriz_inicial(cadena, matriz, anchoAlto, pos[0], pos[1], pos[2], pos[3]); // dejar la matriz original para las otras estrategias
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

