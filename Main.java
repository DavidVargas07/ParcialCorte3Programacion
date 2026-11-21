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

        matriz = new char[n][n]; // Crea la matriz de n x n para el laberinto
        visitada = new boolean[n][n]; // Crea la matriz de visitadas (todas empiezan en false)
        int filaO = 0; // Guardara la fila donde esta el origen O
        int colO = 0; // Guardara la columna donde esta el origen O
        for (int i = 0; i < n * n; i++) { // Recorre cada caracter de la cadena
            matriz[i / n][i % n] = cadena.charAt(i); // Pone el caracter en su fila (i / n) y columna (i % n)
            if (cadena.charAt(i) == 'O') { // Revisa si el caracter es el origen O
                filaO = i / n; // Guarda la fila del origen
                colO = i % n; // Guarda la columna del origen
            }
        }

        if (explorar(filaO, colO)) { // Empieza a buscar la salida desde el origen O
            System.out.println("Ruta encontrada en " + pasos + " pasos"); // Muestra que si encontro la salida
        } else if (limite) { // Si no la encontro porque se acabaron las llamadas
            System.out.println("Limite alcanzado"); // Avisa que se llego al limite
        } else { // Si no hay ningun camino posible
            System.out.println("No hay salida"); // Avisa que no existe salida
        }

        for (int f = 0; f < n; f++) { // Recorre cada fila de la matriz
            for (int c = 0; c < n; c++) { // Recorre cada columna de la fila
                System.out.print("[" + matriz[f][c] + "]"); // Imprime la celda entre corchetes
            }
            System.out.println(); // Salta a la siguiente linea al terminar la fila
        }
        System.out.println("Llamadas: " + llamadas); // Muestra cuantas llamadas se hicieron
        System.out.println("Retrocesos: " + retrocesos); // Muestra cuantos retrocesos hubo
        System.out.println("Maximo teorico con memoria: " + (4 * n * n + 1) + " llamadas"); // Cota O(n^2): 4 vecinos por celda + la llamada inicial
    }

    static boolean explorar(int f, int c) { // Metodo recursivo: devuelve true si desde (f, c) se llega a X
        llamadas = llamadas + 1; // Suma una llamada al contador

        if (llamadas > LIMITE) { // Revisa si ya se paso el limite de llamadas
            limite = true; // Marca que se alcanzo el limite
            return false; // Detiene la busqueda por este camino
        }

        // Verifica si esta fuera, es pared '+' o si ya fue visitada antes
        if (f < 0 || f >= matriz.length || c < 0 || c >= matriz[0].length || // Revisa si la posicion esta fuera del laberinto
                matriz[f][c] == '+' || visitada[f][c]) { // Revisa si es pared o si ya fue explorada (memoria)
            return false; // No se puede pasar por esta celda
        }

        if (matriz[f][c] == 'X') { // Revisa si llegamos a la salida X
            return true; // Encontro la salida
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