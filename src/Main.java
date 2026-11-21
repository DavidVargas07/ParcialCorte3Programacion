import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Creación del laberinto
        Scanner kb = new Scanner(System.in);
        System.out.println();
        System.out.print("Numero  ancho y alto: ");
        int anchoAlto = kb.nextInt();
        kb.nextLine(); // consume el salto de linea que deja nextInt()

        // Inicializacion de variables
        String cadena = "www";
        int[] pos = new int[4]; // O en x, O en y, X en x, X en Y
        boolean[] estado = new boolean[2]; // [0] para saber si llego (true) y [1] para saber si llego al maximo de llamadas
        int[] cont = new int[6]; // 6 contadores que guardan:
        // [0] la longitud de la ruta,
        // [1] las celdas distintas,
        // [2] direcciones revisadas,
        // [3] retrocesos
        // [4] llamadas o veces en que entro a una casilla,
        // [5] movimientos totales

        long[] tiempos = new long[5]; // tiempo de las 5 repeticiones, en nanosegundos
        char[][] matrizTablero; // tablero;

        int[] tam = {anchoAlto}; // lista para que la funcion pueda devolver el anchoAlto corregido
        cadena = ProcesoValidacionLaberinto.Proceso_validacion_laberinto(tam, cadena, kb);
        // devuelve la cadena valida, o null si el usuario quiere salir
        anchoAlto = tam[0]; // se actualiza con el anchoAlto que si cumple (>= 3)

        if (cadena != null) {
            // si sirve el numero y la cadena de texto
            matrizTablero = new char[anchoAlto][anchoAlto];
            evaluarCaracteresTexto.evaluar_caracteres_texto(anchoAlto, cadena, matrizTablero, pos); // llena la matriz y guarda las posiciones de la O y la X en pos

            System.out.println();

            System.out.println("Laberinto inicial:");
            ImprimirMatriz.imprimir(matrizTablero, anchoAlto);
            // ejecuta la funcion para imprimir la matriz

            System.out.println("Que funcion quieres que se utilize para encotrar el camino?");
            System.out.println("1. Manhatan | 2. Recursion | 3. El otro");
            int metodo = kb.nextInt();
            while (metodo < 1 || metodo >3 ){
                System.out.println("Ese numero no es correcto, pon uno correcto:");
                metodo = kb.nextInt();
            }
            System.out.println();
            if (metodo == 1) {
                // estrategia Manhattan (prioridad heuristica)
                System.out.println("ESTRATEGIA MANHATAN");
                System.out.println();

                // imprimesion del laberinto en cada movimiento (no se toma en cuenta el tiempo)
                System.out.println("Recorrido de la serpiente:");
                MatrizInicial.matriz_inicial(cadena, matrizTablero, anchoAlto); // la funcion imprime la matriz completa
                Manhattan.resolver(matrizTablero, anchoAlto, pos, estado, cont, true);

                for (int r = 0; r < 5; r++) { // 5 repeticiones sin imprimir, para sacar la mediana del tiempo
                    MatrizInicial.matriz_inicial(cadena, matrizTablero, anchoAlto); // empezar sin el camino de la repeticion anterior
                    long inicio = System.nanoTime(); // tiempo antes de resolver ; no funciona time.time() por ser java
                    Manhattan.resolver(matrizTablero, anchoAlto, pos, estado, cont, false);
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
                    ImprimirMatriz.imprimir(matrizTablero, anchoAlto);
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

                MatrizInicial.matriz_inicial(cadena, matrizTablero, anchoAlto); // dejar la matriz original para las otras estrategias
            } else if (metodo == 2) {

                boolean sePuede = Recursion.busquedaRecursiva(matrizTablero, anchoAlto, pos[0], pos[1], cont);
                while (!sePuede){
                    for (int i = 0; i < anchoAlto; i++) {
                        for (int j = 0; j < anchoAlto; j++) {
                            if (matrizTablero[i][j] == 'S') {
                                pos[0] = i;
                                pos[1] = j;
                            }
                        }
                    }
                    sePuede = Recursion.retroceso(matrizTablero, anchoAlto, pos[0], pos[1], cont);
                    for (int i = 0; i < anchoAlto; i++) {
                        for (int j = 0; j < anchoAlto; j++) {
                            if (matrizTablero[i][j] == 'S') {
                                pos[0]= i;
                                pos[1] = j;
                            }
                        }
                    }
                    sePuede = Recursion.busquedaRecursiva(matrizTablero, anchoAlto, pos[0], pos[1], cont);
                }
                ImprimirMatriz.imprimir(matrizTablero, anchoAlto);
                System.out.println("Longitud de la ruta: " + cont[0]);
                System.out.println("Celdas distintas examinadas: " + cont[1]);
                System.out.println("Intentos: " + cont[2]);
                System.out.println("Retrocesos: " + cont[3]);
                System.out.println("Llamadas: " + cont[4]);
                cont[5] = cont[3] + cont[4];
                System.out.println("Movimientos totales (avances + retrocesos): " + cont[5]);
                System.out.print("Tiempos (ordenados, en ms):");
            }else{
                // estrategia Manhattan (prioridad heuristica)
                System.out.println("Busqueda Exhaustiva");
                System.out.println();

                // imprimesion del laberinto en cada movimiento (no se toma en cuenta el tiempo)
                System.out.println("Recorrido de la serpiente:");
                MatrizInicial.matriz_inicial(cadena, matrizTablero, anchoAlto); // la funcion imprime la matriz completa
                FuncionamientoBusquedaExhaustiva.buscar(matriz, n, fila, col, pos, visitado, yaContada, estado, cont, true);

                for (int r = 0; r < 5; r++) { // 5 repeticiones sin imprimir, para sacar la mediana del tiempo
                    MatrizInicial.matriz_inicial(cadena, matrizTablero, anchoAlto); // empezar sin el camino de la repeticion anterior
                    long inicio = System.nanoTime(); // tiempo antes de resolver ; no funciona time.time() por ser java
                    FuncionamientoBusquedaExhaustiva.buscar(matriz, n, fila, col, pos, visitado, yaContada, estado, cont, false);                    long fin = System.nanoTime(); // tiempo despues de resolver (como final=time.time())
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
                    ImprimirMatriz.imprimir(matrizTablero, anchoAlto);
                    System.out.println();
                } else if (estado[1]) {
                    System.out.println("Se alcanzo el limite de " + FuncionamientoBusquedaExhaustiva.LIMITE + " llamadas sin encontrar la X.");
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

                MatrizInicial.matriz_inicial(cadena, matrizTablero, anchoAlto); // dejar la matriz original para las otras estrategias
            }
        }
    }
}