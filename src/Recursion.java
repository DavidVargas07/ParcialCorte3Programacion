public class Recursion {
    static int limiteDeMovimientos = 100000; // Numero maximo de llamadas permitidas a explorar
    public static boolean busquedaRecursiva(char[][] matriz, int n, int posicionX, int posicionY, int pasos){
        pasos++;
        if (pasos > limiteDeMovimientos){
            return true;
        }
        if (matriz[posicionX][posicionY + 1] == 'X') {
            matriz[posicionX][posicionY] = '*';
            matriz[posicionX][posicionY+1] = 'S';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX + 1][posicionY] == 'X') {
            matriz[posicionX][posicionY] = '*';
            matriz[posicionX+1][posicionY] = 'S';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX][posicionY - 1] == 'X') {
            matriz[posicionX][posicionY-1] = 'S';
            matriz[posicionX][posicionY] = '*';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX - 1][posicionY] == 'X') {
            matriz[posicionX][posicionY] = '*';
            matriz[posicionX - 1][posicionY] = 'S';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else {
            if (matriz[posicionX][posicionY + 1] == ' ') {
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX][posicionY + 1] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return busquedaRecursiva(matriz, n, posicionX, posicionY + 1, pasos);
            } else if (matriz[posicionX + 1][posicionY] == ' ') {
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX + 1][posicionY] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return busquedaRecursiva(matriz, n, posicionX + 1, posicionY, pasos);
            } else if (matriz[posicionX][posicionY - 1] == ' ') {
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX][posicionY - 1] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return busquedaRecursiva(matriz, n, posicionX, posicionY - 1, pasos);
            } else if (matriz[posicionX - 1][posicionY] == ' ') {
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX - 1][posicionY] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return busquedaRecursiva(matriz, n, posicionX - 1, posicionY, pasos);
            }
        }
        return false;
    }
    public static boolean retroceso(char[][] matriz, int n, int posicionX, int posicionY, int pasos){
        if (matriz[posicionX][posicionY + 1] == ' ') {
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX + 1][posicionY] == ' ') {
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX][posicionY - 1] == ' ') {
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX - 1][posicionY] == ' ') {
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        }else {
            if (matriz[posicionX][posicionY + 1] == '*') {
                matriz[posicionX][posicionY] = 'R';
                matriz[posicionX][posicionY + 1] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return retroceso(matriz, n, posicionX, posicionY + 1, pasos);
            } else if (matriz[posicionX + 1][posicionY] == '*') {
                matriz[posicionX][posicionY] = 'R';
                matriz[posicionX + 1][posicionY] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return retroceso(matriz, n, posicionX + 1, posicionY, pasos);
            } else if (matriz[posicionX][posicionY - 1] == '*') {
                matriz[posicionX][posicionY] = 'R';
                matriz[posicionX][posicionY - 1] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return retroceso(matriz, n, posicionX, posicionY - 1, pasos);
            } else if (matriz[posicionX - 1][posicionY] == '*') {
                matriz[posicionX][posicionY] = 'R';
                matriz[posicionX - 1][posicionY] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return retroceso(matriz, n, posicionX - 1, posicionY, pasos);
            }
        }
        return true;
    }
}
