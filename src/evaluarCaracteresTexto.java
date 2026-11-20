public class evaluarCaracteresTexto {
    // sirve[0]: queda en true si la cadena es valida
    public static boolean evaluar_caracteres_texto(int anchoAlto, String cadena, char[][] matrizTablero, int[] pos) {
        int t = 0; // posicion de caracter en cadena
        int o = 0; // o va a verificar que haya una sola O en el texto
        int x = 0; // x va a verificar que haya una sola X en el texto
        sirve[0] = true; // se asume que sirve hasta encontrar un error
        for (int i = 0; i < n; i++) { // evaluar que el texto tenga los caracteres y su cantidad correspondiente e introducirlos en la matriz
            for (int l = 0; l < n; l++) {
                if (cadena.charAt(t) == ' ') {
                    matriz[i][l] = cadena.charAt(t);
                } else if (cadena.charAt(t) == '+') {
                    matriz[i][l] = cadena.charAt(t);
                } else if (cadena.charAt(t) == 'O') {
                    matriz[i][l] = cadena.charAt(t);
                    pos[0] = l; // pos[0] = InicioX: la columna de la O
                    pos[1] = i; // pos[1] = InicioY: la fila de la O
                    o++;
                } else if (cadena.charAt(t) == 'X') {
                    matriz[i][l] = cadena.charAt(t);
                    pos[2] = l; // pos[2] = FinX: la columna de la X
                    pos[3] = i; // pos[3] = FinY: la fila de la X
                    x++;
                } else {
                    sirve[0] = false; // si tiene otro caracter
                }
                t++;
            }
        }
        if (o != 1 || x != 1) {
            sirve[0] = false; // debe haber un solo inicio y un solo final
        }
    }
}