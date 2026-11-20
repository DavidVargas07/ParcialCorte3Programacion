public class MatrizInicial {

    //volver a traer la matrizTablero original despues de evaluar cada metodo
    public static void matriz_inicial(String cadena, char[][] matrizTablero, int anchoAlto) {
        int t=0;
        for (int i = 0; i < anchoAlto; i++) { // evaluar que el texto tenga los caracteres y su cantidad correspondiente e introducirlos en la matrizTablero
            for (int l = 0; l < anchoAlto; l++) {
                if (cadena.charAt(t) == ' ') {
                    matrizTablero[i][l] = cadena.charAt(t);
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