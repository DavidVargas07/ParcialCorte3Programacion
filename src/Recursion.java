public class Recursion {
    static int limiteDeMovimientos = 100000; // Numero maximo de llamadas permitidas a explorar
    public static boolean busquedaRecursiva(char[][] matriz, int n, int posicionX, int posicionY, int[] cont){
        cont[2]++;
        cont[0]++;
        cont[4]++;
        if (cont[2] > limiteDeMovimientos){
            return true;
        }
        if (matriz[posicionX][posicionY + 1] == 'X') {
            cont[1] += 1;
            matriz[posicionX][posicionY] = '*';
            matriz[posicionX][posicionY+1] = 'S';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX + 1][posicionY] == 'X') {
            cont[1] += 2;
            matriz[posicionX][posicionY] = '*';
            matriz[posicionX+1][posicionY] = 'S';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX][posicionY - 1] == 'X') {
            cont[1] += 3;
            matriz[posicionX][posicionY-1] = 'S';
            matriz[posicionX][posicionY] = '*';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX - 1][posicionY] == 'X') {
            cont[1] += 4;
            matriz[posicionX][posicionY] = '*';
            matriz[posicionX - 1][posicionY] = 'S';
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else {
            if (matriz[posicionX][posicionY + 1] == ' ') {
                cont[1] += 1;
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX][posicionY + 1] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return busquedaRecursiva(matriz, n, posicionX, posicionY + 1, cont);
            } else if (matriz[posicionX + 1][posicionY] == ' ') {
                cont[1] += 2;
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX + 1][posicionY] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return busquedaRecursiva(matriz, n, posicionX + 1, posicionY, cont);
            } else if (matriz[posicionX][posicionY - 1] == ' ') {
                cont[1] += 3;
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX][posicionY - 1] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return busquedaRecursiva(matriz, n, posicionX, posicionY - 1, cont);
            } else if (matriz[posicionX - 1][posicionY] == ' ') {
                cont[1] += 4;
                matriz[posicionX][posicionY] = '*';
                matriz[posicionX - 1][posicionY] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return busquedaRecursiva(matriz, n, posicionX - 1, posicionY, cont);
            }
        }
        return false;
    }
    public static boolean retroceso(char[][] matriz, int n, int posicionX, int posicionY,  int [] cont){
        cont[2]++;
        cont[3]++;
        cont[0]--;
        if (cont[2] > limiteDeMovimientos){
            return true;
        }
        if (matriz[posicionX][posicionY + 1] == ' ') {
            cont[1] += 1;
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX + 1][posicionY] == ' ') {
            cont[1] += 2;
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX][posicionY - 1] == ' ') {
            cont[1] += 3;
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        } else if (matriz[posicionX - 1][posicionY] == ' ') {
            cont[1] += 4;
            ImprimirMatriz.imprimir(matriz, n);
            return true;
        }else {
            if (matriz[posicionX][posicionY + 1] == '*') {
                cont[1] += 1;
                matriz[posicionX][posicionY] = 'R';
                matriz[posicionX][posicionY + 1] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return retroceso(matriz, n, posicionX, posicionY + 1,  cont);
            } else if (matriz[posicionX + 1][posicionY] == '*') {
                cont[1] += 2;
                matriz[posicionX][posicionY] = 'R';
                matriz[posicionX + 1][posicionY] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return retroceso(matriz, n, posicionX + 1, posicionY,  cont);
            } else if (matriz[posicionX][posicionY - 1] == '*') {
                cont[1] += 3;
                matriz[posicionX][posicionY] = 'R';
                matriz[posicionX][posicionY - 1] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return retroceso(matriz, n, posicionX, posicionY - 1,  cont);
            } else if (matriz[posicionX - 1][posicionY] == '*') {
                cont[1] += 4;
                matriz[posicionX][posicionY] = 'R';
                matriz[posicionX - 1][posicionY] = 'S';
                ImprimirMatriz.imprimir(matriz, n);
                return retroceso(matriz, n, posicionX - 1, posicionY,  cont);
            }
        }
        return true;
    }
}