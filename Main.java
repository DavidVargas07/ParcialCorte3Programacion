public class RecursionConMemoria {
    static int limiteDeMovimientos = 100000; // Numero maximo de llamadas permitidas a explorar

    // Se llama igual que la estrategia A: crea la matriz booleana de memoria y empieza a buscar
    public static boolean busquedaConMemoria(char[][] matriz, int n, int posicionX, int posicionY, int[] cont){
        boolean[][] visitado = new boolean[matriz.length][matriz[0].length]; // Memoria: todas empiezan en false
        return explorar(matriz, n, posicionX, posicionY, cont, visitado);
    }

    public static boolean explorar(char[][] matriz, int n, int posicionX, int posicionY, int[] cont, boolean[][] visitado){
        cont[2]++;
        cont[0]++;
        cont[4]++;
        if (cont[2] > limiteDeMovimientos){
            return true;
        }
        visitado[posicionX][posicionY] = true; // Se marca para siempre, nunca se vuelve a explorar

        // Si la X esta al lado se llega a la salida (mismo orden que la estrategia A)
        if (matriz[posicionX][posicionY + 1] == 'X') {
            cont[1] += 1;
            matriz[posicionX][posicionY] = '*';
            matriz[posicionX][posicionY + 1] = 'S';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX + 1][posicionY] == 'X') {
            cont[1] += 2;
            matriz[posicionX][posicionY] = '*';
            matriz[posicionX + 1][posicionY] = 'S';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX][posicionY - 1] == 'X') {
            cont[1] += 3;
            matriz[posicionX][posicionY] = '*';
            matriz[posicionX][posicionY - 1] = 'S';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX - 1][posicionY] == 'X') {
            cont[1] += 4;
            matriz[posicionX][posicionY] = '*';
            matriz[posicionX - 1][posicionY] = 'S';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        }

        // Se prueban TODOS los vecinos libres que no se han visitado, no solo el primero
        if (matriz[posicionX][posicionY + 1] == ' ') {
            if (visitado[posicionX][posicionY + 1] == false) { // Solo si nunca se ha visitado (memoria)
                cont[1] += 1;
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX][posicionY + 1] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                if (explorar(matriz, n, posicionX, posicionY + 1, cont, visitado)) {
                    return true;
                }
                matriz[posicionX][posicionY] = 'S'; // Se devolvio a esta celda
            }
        }
        if (matriz[posicionX + 1][posicionY] == ' ') {
            if (visitado[posicionX + 1][posicionY] == false) { // Solo si nunca se ha visitado (memoria)
                cont[1] += 2;
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX + 1][posicionY] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                if (explorar(matriz, n, posicionX + 1, posicionY, cont, visitado)) {
                    return true;
                }
                matriz[posicionX][posicionY] = 'S'; // Se devolvio a esta celda
            }
        }
        if (matriz[posicionX][posicionY - 1] == ' ') {
            if (visitado[posicionX][posicionY - 1] == false) { // Solo si nunca se ha visitado (memoria)
                cont[1] += 3;
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX][posicionY - 1] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                if (explorar(matriz, n, posicionX, posicionY - 1, cont, visitado)) {
                    return true;
                }
                matriz[posicionX][posicionY] = 'S'; // Se devolvio a esta celda
            }
        }

        // Marca la celda en la memoria global
        visitada[f][c] = true; // Marca la celda para no volver a explorarla nunca
        int k = 0; // Indice del movimiento que se va a probar (0 a 3)

        while (k < 4) { // Prueba los 4 movimientos posibles
            int nf = f + df[k]; // Calcula la fila del vecino
            int nc = c + dc[k]; // Calcula la columna del vecino

            if (explorar(nf, nc)) { // Llama a explorar desde el vecino (recursion)
                pasos = pasos + 1; // Si por ahi se llego a X, suma un paso a la ruta
                if (matriz[f][c] == ' ') { // Revisa si la celda es un espacio libre (no es la O)
                    matriz[f][c] = 'o'; // Marca la celda con 'o' para mostrar la ruta
                }
                return true; // Avisa a la llamada anterior que si hay camino
            }
            k = k + 1; // Pasa al siguiente movimiento
        }

        // Al no encontrar salida por aqui, la celda queda marcada en visitada (memoria global)
        // para que ninguna otra ruta la vuelva a explorar
        retrocesos = retrocesos + 1; // Suma un retroceso porque este camino no tiene salida
        return false; // Avisa a la llamada anterior que por aqui no hay camino
    }
}