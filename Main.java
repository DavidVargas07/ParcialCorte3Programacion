import java.util.Scanner:
public class Main {
    public static void main(String[] args) {
        //esto es solo un codigo para ver mas o menos como es la base del codigo, no creo que funcione como está
        llamadas = llamadas + 1;

        if (llamadas > LIMITE) {
            limite = true;
            return false;
        }

        // Verifica si está fuera, es pared '+' o si ya forma parte de la ruta actual
        if (f < 0 || f >= matriz.length || c < 0 || c >= matriz[0].length ||
                matriz[f][c] == '+' || visitada[f][c]) {
            return false;
        }

        if (matriz[f][c] == 'X') {
            return true;
        }

        // Marca la celda como parte de la ruta actual
        visitada[f][c] = true;
        int k = 0;

        while (k < 4) {
            int nf = f + df[k];
            int nc = c + dc[k];

            if (explorar(nf, nc)) {
                pasos = pasos + 1;
                if (matriz[f][c] == ' ') {
                    matriz[f][c] = 'o';
                }
                return true;
            }
            k = k + 1;
        }

        //Al no encontrar salida por aquí, libera la celda para otras rutas
        visitada[f][c] = false;
        retrocesos = retrocesos + 1;
        return false;
    }
}