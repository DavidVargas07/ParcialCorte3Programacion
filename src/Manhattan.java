public class Manhattan {
    public static final int LIMITE = 100000; // maximo de llamadas permitidas (por si no hay solucion o el laberinto es muy grande)

    // mostrar: true para imprimir el laberinto en cada movimiento
    public static void resolver(char[][] matrizTablero, int n, int[] pos, boolean[] estado, int[] cont, boolean mostrar) {
        estado[0] = false; // mide si la serpiente llego a la X (true) o no (false)
        estado[1] = false; // mide si el programa llego a 100.000 llamadas (true) o aun no (false)
        for (int k = 0; k < cont.length; k++) {
            cont[k] = 0; // llena cada valor de la lista en 0. asi, se reinica por cada una de las 5 repeticiones
        }
        boolean[][] visitado = new boolean[n][n]; // matriz paralela para saber si la serpiente ya paso o no por ahi.
        cont[4]++; // veces que se llama a buscar (la primera llamada es la de la O)
        cont[1]++; // celdas distintas que pisa la serpiente (la O es la primera)
        if (mostrar) { // sabe si se debe mostrar el laberinto o no
            mostrarPaso(matriz, n, cont, "Inicio: la serpiente esta en la O (fila " + pos[1] + ", col " + pos[0] + ")");
        }
        buscar(matriz, n, pos[1], pos[0], pos, visitado, estado, cont, mostrar); // empieza en la O: pos[1] = InicioY (la fila de la O), pos[0] = InicioX (la columna de la O)
        if (!estado[0]) { // entra si la serpiente no ha llegado
            cont[0] = 0; // cont[0] = LongitudRuta: mientras busca es el largo del camino actual; al final, los movimientos de la O a la X (0 si no llego)
        }
    }

    // la serpiente esta en (fila, col) y decide hacia donde moverse
    // (la llamada y la celda ya se contaron antes de llegar aqui, al momento de moverse)
    public static void buscar(char[][] matriz, int n, int fila, int col, int[] pos, boolean[][] visitado, boolean[] estado, int[] cont, boolean mostrar) { // mahattan
        if (fila == pos[3] && col == pos[2]) { // evalua si ya llego
            estado[0] = true; // llego a la X
        } else {
            visitado[fila][col] = true; // marca la casilla como pisada; no vuelve a entrar

            int[] distancia = new int[4]; // nueva lista de 4 valores que guarda las distancia Manhattan de cada vecina hasta la X
            int[] orden = {0, 1, 2, 3}; // la lista de direcciones que se va a ordenar: 0 arriba, 1 abajo, 2 izquierda, 3 derecha
            String[] nombres = {"arriba", "abajo", "izquierda", "derecha"}; // nombre de cada direccion, para imprimir

            // guarda las distancias Manhattan de cada posicion a X en la lista distancia
            distancia[0] = Math.abs(pos[2] - col) + Math.abs(pos[3] - (fila - 1)); // vecina de arriba; una fila menos
            distancia[1] = Math.abs(pos[2] - col) + Math.abs(pos[3] - (fila + 1)); // vecina de abajo; una fila mas
            distancia[2] = Math.abs(pos[2] - (col - 1)) + Math.abs(pos[3] - fila); // vecina de la izquierda; una columna menos
            distancia[3] = Math.abs(pos[2] - (col + 1)) + Math.abs(pos[3] - fila); // vecina de la derecha; una columna mas

            // la direccion con menor distancia queda de primera en la lista orden
            for (int i = 0; i < 3; i++) { // pasadas
                for (int l = 0; l < 3 - i; l++) { // veces en que se compara; cada vez, solo se debe comparar 1 vez menos
                    if (distancia[orden[l]] > distancia[orden[l + 1]]) { // saber si la distancia que corresponde a la lista 'orden' es mayor o menor a la siguiente
                        int aux = orden[l]; // aux guarda temporalmente la mas grande
                        orden[l] = orden[l + 1]; // cambia el orden en la lista 'orden' a la mayor
                        orden[l + 1] = aux; // cambia el orden de la lista a la menor. imtercambian
                    }
                }
            }

            // probar las direcciones en orden, hasta encontrar la X o llegar al limite
            for (int k = 0; k < 4 && !estado[0] && !estado[1]; k++) { // k va a representar la posicion dentro de 'orden'
                // se asegura de que la serpiente no haya llegado a X y no haya llegado a las 100.000 llamadas

                cont[2]++; // va a revisar una direccion mas
                int nf = fila; // nf es la fila de la vecina
                int nc = col; // nc es la columna de la vecina

                // va a evaluar cual de las 4 opciones quedo de primera
                if (orden[k] == 0) {
                    nf = fila - 1; // cambia nf para que el siguiente movimiento sea para arriba
                } else if (orden[k] == 1) {
                    nf = fila + 1; // cambia nf para que el siguiente movimiento sea para abajo
                } else if (orden[k] == 2) {
                    nc = col - 1; // cambia cf para que el siguiente movimiento sea para izquierda
                } else {
                    nc = col + 1; // cambia cf para que el siguiente movimiento sea para derecha
                }
                if (nf >= 0 && nf < n && nc >= 0 && nc < n && matriz[nf][nc] != '+' && !visitado[nf][nc]) {
                    // evalua si la casilla vecina esté dentro del laberinto, no sea un muro (+) y no haya sido visitada

                    if (cont[4] >= LIMITE) { // ya no se puede seguir
                        estado[1] = true; // cambia el valor a false
                    } else {
                        // avanzar: la casilla que deja queda como rastro (*) y la cabeza (S) pasa a la vecina
                        if (matriz[fila][col] == 'S') { // ahora la cabeza cambio
                            matriz[fila][col] = '*'; // ahora es parte del cuerpo
                        }
                        if (matriz[nf][nc] == ' ') { // ahora cambia el espacio para que sea cabeza
                            matriz[nf][nc] = 'S'; // ahora ese espacio es la cabeza
                        }
                        cont[4]++; // hace una llamada mas
                        cont[1]++; // una celda distinta mas (nunca entra dos veces a la misma)
                        cont[0]++; // la ruta actual crece un paso
                        cont[5]++; // se hizo un movimiento
                        if (mostrar) { // se puede imprimir el laberinto?
                            String llegada = ""; // aviso si la vecina es la X
                            if (nf == pos[3] && nc == pos[2]) { // llego a la X; compara valores
                                llegada = " -> llego a la X!";
                            }
                            // imprime el movimiento, los contadores hasta el momento y el laberinto
                            mostrarPaso(matriz, n, cont, "Se mueve hacia " + nombres[orden[k]] + " a (fila " + nf + ", col " + nc + ")" + llegada);
                        }

                        buscar(matriz, n, nf, nc, pos, visitado, estado, cont, mostrar); // sigue desde la vecina
                        // se llama a si misma, hasta que estado[0] sea true, retrocede, o llega al limite de llamadas

                        if (!estado[0] && !estado[1]) { // vuelve a la casilla anterior
                            cont[3]++; // suma retroceso; veces que vuelve a una casilla ya pisada
                            cont[0]--; // la ruta actual pierde ese paso
                            cont[5]++; // retroceder tambien es un movimiento
                            if (matriz[nf][nc] == 'S') {
                                matriz[nf][nc] = 'R'; // la casilla del callejon queda marcada como retrocedida (R)
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