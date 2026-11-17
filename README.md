# ParcialCorte3Programacion

## Laberinto

Programa en Java que lee un laberinto cuadrado de `n x n` como una sola cadena de texto, lo valida, lo guarda en una matriz de caracteres y lo imprime en consola con colores ANSI.

## Estructura del proyecto

| Archivo | Qué hace |
|---------|----------|
| `src/Main.java` | Pide `n` y la cadena, valida la longitud, llama a la validación de caracteres, imprime el laberinto y ejecuta la estrategia Manhattan 5 veces. |
| `src/NoTocarComprobar.java` | Método `No_Tocar_Comprobar`: recorre la cadena, llena la matriz, guarda las posiciones de la `O` y la `X` y dice si la cadena es válida. |
| `src/ImprimirMatriz.java` | Método `imprimir`: dibuja el laberinto con marco y colores. |
| `src/MatrizInicial.java` | Método `matriz_inicial`: vuelve a llenar la matriz desde la cadena original. Se usa antes de cada repetición y al final, para borrar el camino marcado. |
| `src/Manhattan.java` | Estrategia de prioridad heurística: la serpiente busca el camino de la `O` a la `X` guiándose por la distancia Manhattan y retrocede cuando se encierra. |

## Funcionamiento

1. **Tamaño del laberinto:** el programa pide un número `n`, que es el ancho y el alto del laberinto.
   - Si `n < 3`, muestra `n debe ser mayor o igual a 3` y lo vuelve a pedir.
   - Si el usuario escribe `0`, el programa termina.
2. **Cadena del laberinto:** pide una cadena de texto con el contenido del laberinto, leída fila por fila.
   - La cadena debe tener exactamente `n * n` caracteres. Si no, muestra `La cadena no cumple con la longitud. :(` y la vuelve a pedir.
   - Si el usuario escribe un solo carácter, el programa termina.
3. **Validación de caracteres:** `No_Tocar_Comprobar` guarda cada carácter en una matriz `char[n][n]`. Solo se permiten:

   | Carácter | Significado |
   |----------|-------------|
   | `' '` (espacio) | Camino libre |
   | `+` | Pared |
   | `O` | Punto de inicio (debe haber exactamente uno) |
   | `X` | Punto de llegada (debe haber exactamente uno) |

   Si hay otro carácter, o no hay exactamente una `O` y una `X`, muestra `La cadena debe tener solamente espacios ( ), paredes (+), un inicio (O) y un final (X)` y vuelve a pedir la cadena:
   - Si la nueva cadena no mide `n * n`, avisa que no cumple con la longitud y la pide otra vez.
   - Si el usuario presiona ENTER sin escribir nada, el programa termina.
4. **Impresión:** si la cadena es válida, se llama a `ImprimirMatriz.imprimir(matriz, n)`, que dibuja el laberinto completo.
5. **Estrategia Manhattan:** se muestra la distancia Manhattan de la `O` a la `X`. Después se imprime el recorrido de la serpiente movimiento por movimiento, se resuelve el laberinto 5 veces más para medir el tiempo y se imprimen el camino encontrado y las métricas (ver [Estrategia Manhattan](#estrategia-manhattan)).

## Cómo se pasan los datos entre clases

En Java, los `boolean` y los `int` se pasan **por valor**: el método recibe una copia, y lo que le cambie no llega a `main`. Los arreglos, en cambio, se pasan como **referencia**, así que el método y `main` trabajan sobre el mismo arreglo. Como el programa no usa `return`, los resultados viajan en arreglos que crea `main`:

| Arreglo | Contenido |
|---------|-----------|
| `boolean[] sirve` (1 posición) | `sirve[0]` es `true` si la cadena es válida y `false` si no. |
| `int[] pos` (4 posiciones) | `pos[0]` = `InicioX`, `pos[1]` = `InicioY`, `pos[2]` = `FinX`, `pos[3]` = `FinY` (la `X` es la columna y la `Y` es la fila). |

El método `No_Tocar_Comprobar` empieza con `sirve[0] = true`, lo cambia a `false` si encuentra un error, y `main` lee ese valor en el ciclo `while (!sirve[0] && w)`. La matriz funciona igual: es un arreglo, así que se llena desde el método.

La estrategia Manhattan usa la misma idea con `boolean[] estado` y `int[] cont`.

## Estrategia Manhattan

Está en `src/Manhattan.java`. Es una búsqueda **voraz** (*greedy best-first*) con **retroceso** (*backtracking*).

### Cómo decide la serpiente

En cada casilla, la serpiente:

1. Mira sus 4 vecinas: arriba, abajo, izquierda y derecha.
2. Calcula la **distancia Manhattan** de cada vecina hasta la `X`, sin contar los muros:
   `|finX - columna| + |finY - fila|`
3. Ordena las 4 direcciones de menor a mayor distancia, con un ordenamiento burbuja. Si hay empate, el orden es arriba, abajo, izquierda y derecha.
4. Prueba las direcciones en ese orden. Solo se mueve a una vecina que esté dentro del laberinto, que no sea pared (`+`) y que no haya pisado antes.

Si llega a un **callejón sin salida**, es decir, una casilla sin vecinas válidas, **retrocede** a la casilla anterior y prueba la siguiente dirección de su lista. Así, si existe un camino, siempre lo encuentra. Lo que no garantiza es que sea el **más corto**, porque la distancia Manhattan no tiene en cuenta los muros.

Mientras avanza, la cabeza de la serpiente se ve como `S` y las casillas que va dejando atrás quedan marcadas con `*`. Cuando retrocede de un callejón, esa casilla queda marcada con `R` (de *retrocedió*) y la `S` vuelve a la anterior. Por eso, al llegar a la `X`, el laberinto muestra el camino final con `*` y, con otro color, las casillas de los callejones donde tuvo que retroceder. Las `R` no se vuelven a pisar, porque la matriz `visitado` recuerda que ya estuvo ahí.

### Recorrido movimiento por movimiento

Cada vez que la serpiente se mueve, sea un avance o un retroceso, se imprimen:

1. El número del movimiento y qué hizo. Por ejemplo, `Se mueve hacia derecha a (fila 0, col 1)`, o `Callejon sin salida: retrocede a (fila 0, col 0)`. Si llega a la `X`, se agrega `-> llego a la X!`.
2. Los contadores **hasta ese momento**: ruta actual, celdas examinadas, intentos, retrocesos y llamadas.
3. El laberinto completo, con la `S`, el rastro de `*` y las `R` de las casillas donde retrocedió.

Esto lo hace el método `mostrarPaso`. El recorrido se imprime en **una sola ejecución** que no se mide en tiempo. Las 5 repeticiones del tiempo corren sin imprimir, para que la impresión no afecte la medición.

En laberintos grandes el recorrido puede ser muy largo. Por ejemplo, uno de 451×451 que llegue al límite imprime 100.000 laberintos de 451 filas, y puede tardar horas.

### Métodos

| Método | Qué hace |
|--------|----------|
| `resolver(matriz, n, pos, estado, cont, mostrar)` | Reinicia `estado` y `cont`, crea la matriz `visitado` y empieza la búsqueda desde la `O`. Si `mostrar` es `true`, imprime cada movimiento. |
| `buscar(matriz, n, fila, col, pos, visitado, estado, cont, mostrar)` | Método recursivo: es la serpiente parada en `(fila, col)` decidiendo hacia dónde moverse. |
| `mostrarPaso(matriz, n, cont, accion)` | Imprime el movimiento, los contadores actuales y el laberinto. |

Ninguno usa `return`. Los resultados se devuelven en estos arreglos:

| Posición | Nombre | Contenido |
|----------|--------|-----------|
| `estado[0]` | `LlegoSerpiente` | `true` si encontró la `X`. |
| `estado[1]` | `LlamadasMaximas` | `true` si llegó al límite de llamadas. |
| `cont[0]` | `LongitudRuta` | **Longitud de la ruta.** Mientras busca, es el largo del camino actual: sube al avanzar y baja al retroceder. Al final, es el número de movimientos de la `O` a la `X`, o 0 si no llegó. |
| `cont[1]` | `celdasDistintas` | **Celdas distintas examinadas:** casillas que pisó la serpiente, contando la `O`. |
| `cont[2]` | `DireccionesRevisadas` | **Intentos:** direcciones que revisó, sirvieran o no (muro, borde o ya visitada). Son hasta 4 por casilla. |
| `cont[3]` | `Retrocesos` | **Retrocesos:** veces que volvió a una casilla ya pisada al salir de un callejón sin salida. |
| `cont[4]` | `Llamadas` | **Llamadas:** veces que se llamó a `buscar`. |
| `cont[5]` | `Movimientos` | **Movimientos totales:** avances más retrocesos. Es el número que aparece en cada `Movimiento N`. |

Dentro de `buscar` hay tres arreglos pequeños más: `dist` (la distancia de cada vecina a la `X`), `orden` (las direcciones ordenadas: 0 arriba, 1 abajo, 2 izquierda, 3 derecha) y `nombres` (el texto de cada dirección). Las direcciones se escriben con los números directamente: arriba es `fila - 1`, abajo `fila + 1`, izquierda `col - 1` y derecha `col + 1`.

### Límite de llamadas

Si el laberinto no tiene solución o es muy grande, la búsqueda se detiene al llegar a **100.000 llamadas** (`Manhattan.LIMITE`) y el programa muestra `Se alcanzo el limite de 100000 llamadas sin encontrar la X.`

### 5 repeticiones y mediana del tiempo

`main` ejecuta la estrategia **5 veces** sobre el mismo laberinto. Antes de cada repetición llama a `MatrizInicial.matriz_inicial` para borrar el camino de la anterior. Los tiempos se guardan en `long[] tiempos`, se ordenan, y la **mediana** es `tiempos[2]`.

El tiempo se mide con `System.nanoTime()`, que es el equivalente en Java de `time.time()` en Python:

```java
long inicio = System.nanoTime();   // como inicio = time.time()
Manhattan.resolver(matriz, n, pos, estado, cont, false);
long fin = System.nanoTime();      // como final = time.time()
tiempos[r] = fin - inicio;         // lo que se demoró esta repetición
```

`System.nanoTime()` da el tiempo en **nanosegundos** (en vez de segundos como en Python), porque resolver un laberinto pequeño tarda menos de un milisegundo y con segundos saldría `0`. Al imprimir se divide entre `1000000.0` para mostrarlo en **milisegundos**. Solo se mide `resolver`, no la impresión.

Las métricas de conteo son iguales en las 5 repeticiones, porque la serpiente siempre toma las mismas decisiones. Al final, la matriz se restaura otra vez para que las otras estrategias partan del laberinto original.

### Qué imprime

Según el resultado de la búsqueda:
- Si encontró la `X`: `Camino encontrado:` y el laberinto con el camino marcado.
- Si llegó al límite: el aviso del límite de llamadas.
- Si no hay camino: `No hay camino de la O a la X.`

Después, en todos los casos, imprime la longitud de la ruta, las celdas examinadas, los intentos, los retrocesos, las llamadas, los movimientos totales, los 5 tiempos ordenados y la mediana.

## Impresión del laberinto

El método `imprimir(char[][] matriz, int n)` dibuja:

- **Marco exterior** (fondo naranja, código ANSI `48;5;208`):
  - Borde superior e inferior: `+-`, luego `---` por cada columna, y al final `-+`.
  - Bordes laterales: `| ` al inicio y ` |` al final de cada fila.
- **Cada celda** ocupa 3 caracteres de ancho y se colorea según su contenido:

  | Carácter en la matriz | Se imprime como | Colores |
  |-----------------------|-----------------|---------|
  | `' '` (camino) | `   ` | Fondo blanco |
  | `+` (pared) | ` + ` | Fondo verde, texto negro |
  | `O` (inicio) | ` O ` | Fondo gris oscuro, texto rojo |
  | `X` (llegada) | ` @ ` | Fondo naranja, texto negro |
  | `*` (rastro / camino encontrado) | ` * ` | Fondo azul, texto blanco |
  | `S` (cabeza de la serpiente) | ` S ` | Fondo amarillo, texto negro |
  | `R` (casilla donde retrocedió) | ` R ` | Fondo rojo, texto blanco |

La `X` se muestra en pantalla como `@`, con el mismo color del marco.

## Ejemplo de entrada

Con `n = 3`, la cadena tiene 9 caracteres (3 filas de 3):

```
Numero n: 3
Cadena: O   + + X
```

Esta cadena equivale a la matriz (fila por fila):

```
O  
 + 
+ X
```

Con esta cadena, `pos` queda en `[0, 0, 2, 2]`: el inicio está en la columna 0, fila 0, y el final en la columna 2, fila 2.

El programa imprime (sin colores):

```
+-----------+
|  O        |
|     +     |
|  +     @  |
+-----------+

Distancia Manhattan de la O a la X: 4
Recorrido de la serpiente:

Movimiento 0: Inicio: la serpiente esta en la O (fila 0, col 0)
Ruta actual: 0 | Celdas examinadas: 1 | Intentos: 0 | Retrocesos: 0 | Llamadas: 1
+-----------+
|  O        |
|     +     |
|  +     @  |
+-----------+

Movimiento 1: Se mueve hacia abajo a (fila 1, col 0)
Ruta actual: 1 | Celdas examinadas: 2 | Intentos: 1 | Retrocesos: 0 | Llamadas: 2
+-----------+
|  O        |
|  S  +     |
|  +     @  |
+-----------+

Movimiento 2: Callejon sin salida: retrocede a (fila 0, col 0)
Ruta actual: 0 | Celdas examinadas: 2 | Intentos: 5 | Retrocesos: 1 | Llamadas: 2
+-----------+
|  O        |
|  R  +     |
|  +     @  |
+-----------+

Movimiento 3: Se mueve hacia derecha a (fila 0, col 1)
Ruta actual: 1 | Celdas examinadas: 3 | Intentos: 6 | Retrocesos: 1 | Llamadas: 3
+-----------+
|  O  S     |
|  R  +     |
|  +     @  |
+-----------+

Movimiento 4: Se mueve hacia derecha a (fila 0, col 2)
Ruta actual: 2 | Celdas examinadas: 4 | Intentos: 8 | Retrocesos: 1 | Llamadas: 4
+-----------+
|  O  *  S  |
|  R  +     |
|  +     @  |
+-----------+

Movimiento 5: Se mueve hacia abajo a (fila 1, col 2)
Ruta actual: 3 | Celdas examinadas: 5 | Intentos: 9 | Retrocesos: 1 | Llamadas: 5
+-----------+
|  O  *  *  |
|  R  +  S  |
|  +     @  |
+-----------+

Movimiento 6: Se mueve hacia abajo a (fila 2, col 2) -> llego a la X!
Ruta actual: 4 | Celdas examinadas: 6 | Intentos: 10 | Retrocesos: 1 | Llamadas: 6
+-----------+
|  O  *  *  |
|  R  +  *  |
|  +     @  |
+-----------+
Camino encontrado:
+-----------+
|  O  *  *  |
|  R  +  *  |
|  +     @  |
+-----------+
Longitud de la ruta: 4
Celdas distintas examinadas: 6
Intentos: 10
Retrocesos: 1
Llamadas: 6
Movimientos totales (avances + retrocesos): 6
Tiempos (ordenados, en ms): 0.00775 0.007791 0.008208 0.009292 0.014667
Mediana del tiempo: 0.008208 ms
```

Hay 1 retroceso porque desde la `O` las direcciones abajo y derecha empatan con distancia 3, y abajo va primero. La serpiente baja, se encuentra un callejón sin salida, vuelve a la `O` y sigue por la derecha. La casilla donde retrocedió queda marcada con `R`.

Con la cadena `O+  + + X` no hay solución: la `O` solo puede bajar, y de esa casilla no hay salida. El programa muestra `No hay camino de la O a la X.`

## Cómo ejecutarlo

Desde la raíz del proyecto (se compilan todas las clases, porque `Main` usa las demás):

```bash
javac -d out src/*.java
java -Xss64m -cp out Main
```

`-Xss64m` aumenta el tamaño de la pila de Java. La búsqueda es recursiva, y en laberintos grandes puede tener miles de llamadas una dentro de otra. Con la pila por defecto, el programa se cae con `StackOverflowError`: pasa, por ejemplo, con un laberinto en zigzag de 201×201. Con `-Xss64m` llega sin problema hasta el límite de 100.000 llamadas.

En IntelliJ se configura en **Run → Edit Configurations… → Main → Modify options → Add VM options**, escribiendo `-Xss64m`.

Para que se vean los colores, usa una terminal que soporte códigos ANSI (por ejemplo, la terminal de IntelliJ, la de macOS o la de Linux).
