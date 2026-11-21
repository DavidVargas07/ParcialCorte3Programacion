public class FuncionamientoBusquedaExhaustiva {
    public static final int LIMITE = 100000; // maximo de llamadas permitidas (por si no hay solucion o el laberinto es muy grande)

    // mostrar: true para imprimir el laberinto en cada movimiento
    public static void resolver(char[][] matriz, int n, int[] pos, boolean[] estado, int[] cont, boolean mostrar) {
        estado[0] = false; // mide si la serpiente llego a la X (true) o no (false)
        estado[1] = false; // mide si el programa llego a 100.000 llamadas (true) o aun no (false)
        for (int k = 0; k < cont.length; k++) {
            cont[k] = 0; // llena cada valor de la lista en 0. asi, se reinica por cada una de las 5 repeticiones
        }
        boolean[][] visitado = new boolean[n][n]; // matriz paralela para saber si la celda ya esta en la ruta actual
        boolean[][] yaContada = new boolean[n][n]; // matriz auxiliar para contar celdas distintas examinadas (cont[1])

        yaContada[pos[1]][pos[0]] = true;
        cont[4]++; // veces que se llama a buscar (la primera llamada es la de la O)
        cont[1]++; // celdas distintas que pisa la serpiente (la O es la primera)

        if (mostrar) { // sabe si se debe mostrar el laberinto o no
            mostrarPaso(matriz, n, cont, "Inicio: la serpiente esta en la O (fila " + pos[1] + ", col " + pos[0] + ")");
        }

        buscar(matriz, n, pos[1], pos[0], pos, visitado, yaContada, estado, cont, mostrar); // empieza en la O

        if (!estado[0]) { // entra si la serpiente no ha llegado
            cont[0] = 0; // cont[0] = LongitudRuta: al final, los movimientos de la O a la X (0 si no llego)
        }
    }

    // la serpiente esta en (fila, col) y decide hacia donde moverse
    public static void buscar(char[][] matriz, int n, int fila, int col, int[] pos, boolean[][] visitado, boolean[][] yaContada, boolean[] estado, int[] cont, boolean mostrar) {
        if (fila == pos[3] && col == pos[2]) { // evalua si ya llego a la X
            estado[0] = true; // llego a la X
        } else {
            visitado[fila][col] = true; // marca la casilla como parte de la ruta actual

            String[] nombres = {"arriba", "abajo", "izquierda", "derecha"}; // nombre de cada direccion, para imprimir

            // probar las 4 direcciones en orden fijo: 0 arriba, 1 abajo, 2 izquierda, 3 derecha
            for (int k = 0; k < 4 && !estado[0] && !estado[1]; k++) {
                cont[2]++; // va a revisar una direccion mas
                int nf = fila; // nf es la fila de la vecina
                int nc = col;  // nc es la columna de la vecina

                if (k == 0) {
                    nf = fila - 1; // arriba
                } else if (k == 1) {
                    nf = fila + 1; // abajo
                } else if (k == 2) {
                    nc = col - 1;  // izquierda
                } else {
                    nc = col + 1;  // derecha
                }

                // evalua si la casilla vecina esta dentro del laberinto, no es muro (+) y no esta en la ruta actual
                if (nf >= 0 && nf < n && nc >= 0 && nc < n && matriz[nf][nc] != '+' && !visitado[nf][nc]) {

                    if (cont[4] >= LIMITE) { // ya no se puede seguir
                        estado[1] = true; // se alcanzo el limite de llamadas
                    } else {
                        // avanzar: la casilla que deja queda como rastro (*) y la cabeza (@) pasa a la vecina
                        if (matriz[fila][col] == 'S') {
                            matriz[fila][col] = '*'; // ahora es parte del cuerpo
                        }
                        if (matriz[nf][nc] == ' ') {
                            matriz[nf][nc] = 'S'; // ahora ese espacio es la cabeza
                        }

                        cont[4]++; // hace una llamada mas
                        if (!yaContada[nf][nc]) {
                            yaContada[nf][nc] = true;
                            cont[1]++; // cuenta solo si es una celda distinta no pisada antes
                        }
                        cont[0]++; // la ruta actual crece un paso
                        cont[5]++; // se hizo un movimiento

                        if (mostrar) {
                            String llegada = "";
                            if (nf == pos[3] && nc == pos[2]) {
                                llegada = " -> llego a la X!";
                            }
                            mostrarPaso(matriz, n, cont, "Se mueve hacia " + nombres[k] + " a (fila " + nf + ", col " + nc + ")" + llegada);
                        }

                        // llamada recursiva desde la vecina
                        buscar(matriz, n, nf, nc, pos, visitado, yaContada, estado, cont, mostrar);

                        // si no encontro la X y no llego al limite, deshace el movimiento (retrocede)
                        if (!estado[0] && !estado[1]) {
                            cont[3]++; // suma retroceso
                            cont[0]--; // la ruta actual pierde ese paso
                            cont[5]++; // retroceder tambien es un movimiento

                            if (matriz[nf][nc] == 'S') {
                                matriz[nf][nc] = ' '; // libera visualmente la casilla al retroceder
                            }
                            if (matriz[fila][col] == '*') {
                                matriz[fila][col] = 'S'; // la cabeza vuelve a esta casilla
                            }
                            if (mostrar) {
                                mostrarPaso(matriz, n, cont, "Callejon sin salida: retrocede a (fila " + fila + ", col " + col + ")");
                            }
                        }
                    }
                }
            }

            // Si ninguna de las 4 direcciones lleva a la meta, libera esta celda para que otras rutas puedan usarla
            if (!estado[0] && !estado[1]) {
                visitado[fila][col] = false;
            }
        }
    }

    // imprime el movimiento, los contadores hasta el momento y el laberinto
    public static void mostrarPaso(char[][] matriz, int n, int[] cont, String accion) {
        System.out.println();
        System.out.println("Movimiento " + cont[5] + ": " + accion);
        System.out.println("Ruta actual: " + cont[0] + " | Celdas examinadas: " + cont[1] + " | Intentos: " + cont[2] + " | Retrocesos: " + cont[3] + " | Llamadas: " + cont[4]);
        ImprimirMatriz.imprimir(matriz, n);
        System.out.println();
    }
}
