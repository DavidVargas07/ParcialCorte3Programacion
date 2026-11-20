public class ImprimirMatriz {
    // funcion para imprimir la matrizTablero
    public static void imprimir(char[][] matrizTablero, int anchoAlto) {
        System.out.print("\u001B[48;5;208m\u001B[30m" + "+-" + "\u001B[0m"); // imprimir la ezquina superior izquierda
        for (int primeraFila = 0; primeraFila < anchoAlto; primeraFila++) {
            System.out.print("\u001B[48;5;208m\u001B[30m" + "---" + "\u001B[0m"); // imprimir los '-' del medio
        }
        System.out.print("\u001B[48;5;208m\u001B[30m" + "-+" + "\u001B[0m"); // imprimir la esquina superior derecha
        System.out.println();
        for (int columna = 0; columna < anchoAlto; columna++) {
            System.out.print("\u001B[48;5;208m\u001B[30m" + "| " + "\u001B[0m"); // imprimir los bordes izquierdos
            for (int fila = 0; fila < anchoAlto; fila++) {
                if (matrizTablero[columna][fila] == ' ') {
                    System.out.print("\u001B[47m\u001B[30m" + "   " + "\u001B[0m"); // para imprimir los espacios
                } else if (matrizTablero[columna][fila] == '+') {
                    System.out.print("\u001B[42m\u001B[30m" + " + " + "\u001B[0m"); // para imprimir las paredes internas
                } else if (matrizTablero[columna][fila] == 'O') {
                    System.out.print("\u001B[100m\u001B[31m" + " O " + "\u001B[0m"); // para imprimir el inicio
                } else if (matrizTablero[columna][fila] == 'X') {
                    System.out.print("\u001B[48;5;208m\u001B[30m" + " @ " + "\u001B[0m"); // para imprimir el final
                } else if (matrizTablero[columna][fila] == '*') {
                    System.out.print("\u001B[44m\u001B[97m" + " * " + "\u001B[0m"); // para imprimir el camino que encontro la serpiente
                } else if (matrizTablero[columna][fila] == 'R') {
                    System.out.print("\u001B[41m\u001B[97m" + " R " + "\u001B[0m"); // para imprimir las casillas en las que la serpiente retrocedio
                } else if (matrizTablero[columna][fila] == 'S') {
                    System.out.print("\u001B[43m\u001B[30m" + " S " + "\u001B[0m"); // para imprimir la cabeza de la serpiente
                }
            }
            System.out.print("\u001B[48;5;208m\u001B[30m" + " |" + "\u001B[0m"); // imprimir los bordes derechos
            System.out.println();
        }
        System.out.print("\u001B[48;5;208m\u001B[30m" + "+-" + "\u001B[0m"); // imprimir la esquina inferior izquierda
        for (int ultimaFila = 0; ultimaFila < anchoAlto; ultimaFila++) {
            System.out.print("\u001B[48;5;208m\u001B[30m" + "---" + "\u001B[0m"); // imprimir los '-' del medio
        }
        System.out.print("\u001B[48;5;208m\u001B[30m" + "-+" + "\u001B[0m"); // imprimir la esquina inferior derecha
    }
}
