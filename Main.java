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
        if (matriz[posicionX - 1][posicionY] == ' ') {
            if (visitado[posicionX - 1][posicionY] == false) { // Solo si nunca se ha visitado (memoria)
                cont[1] += 4;
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX - 1][posicionY] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                if (explorar(matriz, n, posicionX - 1, posicionY, cont, visitado)) {
                    return true;
                }
                return true; // Avisa a la llamada anterior que si hay camino
            }
            k = k + 1; // Pasa al siguiente movimiento
        }

        // Ningun vecino llevo a la X: retroceso
        cont[2]++;
        cont[3]++;
        cont[0]--;
        matriz[posicionX][posicionY] = 'R';
        ImprimirMatriz.imprimir(matriz, n);
        return false;
    }
}