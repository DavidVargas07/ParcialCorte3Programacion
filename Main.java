import java.util.Scanner;
public class Main {

    static final int LIMITE = 100000; // Numero maximo de llamadas permitidas a explorar
    static int llamadas = 0; // Cuenta cuantas veces se ha llamado a explorar
    static boolean limite = false; // Se vuelve true si se supera el LIMITE de llamadas
    static int pasos = 0; // Cuenta los pasos de la ruta encontrada de O a X
    static int retrocesos = 0; // Cuenta cuantas veces se devolvio por un camino sin salida
    static char matriz[][]; // Matriz que guarda el laberinto
    static boolean visitada[][]; // Memoria global: marca las celdas ya exploradas (nunca se desmarcan)
    static int df[] = {-1, 1, 0, 0}; // Cambio de fila para moverse: arriba, abajo, izquierda, derecha
    static int dc[] = {0, 0, -1, 1}; // Cambio de columna para moverse: arriba, abajo, izquierda, derecha

    public static void main(String[] args) { // Metodo principal donde inicia el programa
        Scanner entrada = new Scanner(System.in); // Crea el lector de datos del teclado

        System.out.print("Ingrese n: "); // Pide al usuario el tamano del laberinto
        int n = entrada.nextInt(); // Lee el tamano n (el laberinto sera de n x n)
        entrada.nextLine(); // Descarta el Enter que deja nextInt para que la siguiente lectura reciba el laberinto
        System.out.print("Ingrese el laberinto (" + (n * n) + " caracteres): "); // Pide el laberinto como una sola cadena
        String cadena = entrada.nextLine(); // Lee la cadena completa del laberinto

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