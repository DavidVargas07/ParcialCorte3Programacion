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

        //Al no encontrar salida por aquí, libera la celda para otras rutas
        visitada[f][c] = false;
        retrocesos = retrocesos + 1;
        return false;
    }
}