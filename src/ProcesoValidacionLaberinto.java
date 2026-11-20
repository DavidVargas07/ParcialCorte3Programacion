import java.util.Scanner;

public class ProcesoValidacionLaberinto {
    // devuelve la cadena valida, o null si el usuario quiere salir
    // tam[0] trae el anchoAlto que escribio el usuario y, al final, queda con el anchoAlto ya validado
    // (se usa una lista porque un int normal no se actualiza en el main)
    public static String Proceso_validacion_laberinto(int[] tam, String cadena, Scanner kb) {
        int anchoAlto = tam[0];
        // proceso de validacion del laberinto
        while (anchoAlto < 3 && anchoAlto != 0) {
            System.out.println();
            System.out.println("El ancho y alto debe ser mayor o igual a 3"); // evaluo que anchoAlto cumpla con anchoAlto>=3
            System.out.println("Para acabar el programa, escriba 0"); // se le da la opcion al usuario de acabrlo o no
            System.out.print("Numero de anchura y altura: ");
            anchoAlto = kb.nextInt(); // se pregunta anchoAlto, siendo la anchura y altura del laberinto
            kb.nextLine(); // consume el salto de linea que deja nextInt()
            System.out.println();
        }
        if (anchoAlto == 0) {
            System.out.println("El programa ha finalizado.");
            System.out.println("Buen dia :)");
            return null;
        } else {
            char[][] matrizTablero = new char[anchoAlto][anchoAlto]; // cree una matrizTablero de nxn
            System.out.print("Cadena: ");
            cadena = kb.nextLine();
            int[] pos = new int[4]; // guarda las posiciones de el inicio y final; inicio en X, inicio en Y, fin en X, fin en Y

            // proceso de validacion de la longitud de la cadena de texto
            while ((cadena.length() != anchoAlto * anchoAlto) && !cadena.isEmpty()) { // no sale hasta que el usuario haya escrito la cadena bien, o una cadena de un caracter
                System.out.println();
                System.out.println("La cadena no cumple con la longitud. :("); // evaluo si la cadena cumple la longitud
                System.out.println("Para acabar el programa, toque ENTER.");
                System.out.print("Cadena: ");
                cadena = kb.nextLine();
            }
            if (cadena.isEmpty()) { // el usuario quiere salir del programa
                System.out.println();
                System.out.println("El programa ha finalizado.");
                System.out.println("Buen dia :)");
                return null;
            }

            // la cadena ya tiene la longitud correcta; se revisan los caracteres
            while (!evaluarCaracteresTexto.evaluar_caracteres_texto(anchoAlto, cadena, matrizTablero, pos)) { // algun caracter de la cadena no es correcto y el usuario no ha salido
                System.out.println();
                System.out.println("La cadena debe tener solamente espacios ( ), paredes (+), un inicio (O) y un final (X)");
                System.out.println("Para acabar el programa, presione ENTER");
                System.out.print("Cadena: ");
                cadena = kb.nextLine(); // vuelve a preguntar la cadena
                System.out.println();
                if (cadena.isEmpty()) { // el usuario le dio ENTER
                    System.out.println("El programa ha finalizado.");
                    System.out.println("Buen dia :)");
                    return null; // el usuario quiere salir
                } else if (cadena.length() != anchoAlto * anchoAlto) {
                    System.out.println("La cadena no cumple con la longitud. :("); // se le da otra oportunidad; no se evalua para no salirse de la cadena
                }
            }
            tam[0] = anchoAlto; // devuelve al main el anchoAlto validado
            return cadena; // la cadena es valida
        }
    }
}