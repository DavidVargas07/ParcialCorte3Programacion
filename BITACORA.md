# Bitácora – Parcial Corte 3 de Programación

**Fecha:** 14 de noviembre de 2026 (las horas son aproximadas, hora local)
**Proyecto:** laberinto en Java, en el que una serpiente va de la `O` a la `X`. Mi parte del parcial es la estrategia **Manhattan** (prioridad heurística). Mis compañeros hacen las otras dos.

---

## 14:30 – Interfaz de usuario
Commit «mejor interfaz de usuario». Ya pedía `n`, pedía la cadena del laberinto y la imprimía con colores.

## 14:57 – Errores en `Main` al validar la cadena
**Problemas**
- IntelliJ marcaba una condición como «siempre verdadera». La causa era el `if (cadena.length()==1)`, que estaba al revés y hacía que la matriz solo se llenara cuando la cadena era de un carácter.
- El `while` de la longitud de la cadena nunca la volvía a pedir, así que quedaba en un ciclo infinito.
- Faltaba un `kb.nextLine()` después de `kb.nextInt()`, y por eso la cadena se leía vacía.

**Solución:** los corregí yo mismo. Decidí no usar los arreglos que se me propusieron y hacerlo con mi propio código.

## 15:04 – La clase `NoTocarComprobar` quedó vacía
**Problema:** el contenido de la clase no estaba guardado en el disco y quedó solo la clase vacía.
**Solución:** recuperé la clase con su único método, `No_Tocar_Comprobar`.

## 15:17 – Llamar a `No_Tocar_Comprobar` desde `Main`
**Problemas**
- Error en la línea 46: la llamada estaba incompleta (`NoTocarComprobar.` sin nombre del método ni `;`).
- Al método le faltaba `static void`.
- IntelliJ avisaba `Condition '!sirve' is always 'false'`.

**Causa de fondo:** en Java los `int` y los `boolean` se pasan **por valor** (como una copia). El método cambiaba `sirve`, `inicioX`, `inicioY`, `finX` y `finY`, pero `main` nunca veía los cambios. Lo mismo pasaba en la línea 50.

## 16:04 – Cómo devolver los resultados sin `return`
Mi condición era **no usar `return`**.

**Alternativas que consideré**
1. Que el método devuelva el resultado con `return`. La descarté por la condición.
2. Variables `static` de la clase.
3. **Arreglos** (la que elegí).

**Decisión:** usar arreglos, porque los arreglos se pasan por referencia y el método y `main` comparten el mismo.
- `boolean[] sirve = new boolean[1]`, donde `sirve[0]` dice si la cadena es válida.
- `int[] pos = new int[4]` con `pos[0]` = inicioX, `pos[1]` = inicioY, `pos[2]` = finX, `pos[3]` = finY.

Para entenderlo y depurarlo primero lo probé en copias (`MainOpcion1`, `NoTocarComprobarOpcion1`). Cuando lo entendí, lo pasé a `Main` y a `NoTocarComprobar`, y borré las copias.

**Depurador de IntelliJ:** no podía ver línea por línea dentro de otras clases. La causa era que usaba *Step Over* (F8) en vez de *Step Into* (F7).

**16:47 a 16:52:** cuatro commits con estos cambios (`sirve` y `pos`).

## 17:07 – Diseño de la estrategia Manhattan
Mi idea: ver las 4 direcciones, calcular cuál deja a la serpiente más cerca de la `X`, ordenarlas y probarlas en ese orden, descartando las que tienen pared.

**Problemas previstos y decisiones**
- La serpiente podía dar vueltas sin fin → una matriz `visitado` para no pisar dos veces una casilla.
- Podía quedar en un callejón sin salida → **retroceder** (*backtracking*), en vez de detenerse.
- El camino se **marca dentro del laberinto**.

**Condiciones del parcial:** medir los intentos, los retrocesos, las celdas distintas examinadas, la longitud de la ruta y el tiempo; **5 repeticiones por caso y estrategia** para sacar la **mediana**; máximo de **100.000 llamadas**.

**Problema con laberintos grandes:** la recursión se llenaba y salía `StackOverflowError` (en un laberinto de 201×201 en zigzag).
**Solución:** aumentar la pila de Java con la opción `-Xss64m`. Va en las *VM options* de IntelliJ, o en la terminal con `java -Xss64m -cp out Main`.

**Cómo funciona `Manhattan.java`**
- Por cada casilla calculo `dist[]`, la distancia Manhattan de cada vecina a la `X`, y las ordeno con burbuja en `orden[]`.
- Si hay empate, el orden es arriba, abajo, izquierda y derecha.

## 17:37 a 18:03 – Entender las listas
Pedí una lista de todas las listas y qué significaba cada posición. Aclaré la relación entre `dist` (la distancia de cada dirección) y `orden` (la lista de direcciones que se reordena, sin tocar `dist`).

## 18:09 – Imprimir cada movimiento
Cambio pedido: en cada movimiento (avance o retroceso) se imprime todo el laberinto, con el movimiento hecho y los contadores al día. La cabeza de la serpiente se ve como `S` (amarillo) y el rastro como `*` (azul).
**Problema:** en un laberinto de 451×451 que llega al límite, imprimirlo todo tardaba más de 5 minutos.
**Decisión:** mantener «imprimir siempre todo». El parámetro `mostrar` permite que las 5 repeticiones del tiempo corran sin imprimir, para que la impresión no afecte la medición.

## 19:40 – Contadores
Aclaré qué miden los dos últimos valores de `cont`:
- `cont[4]`, las **llamadas**, que sirve para el límite de 100.000.
- `cont[5]`, los **movimientos totales** (avances + retrocesos).
- Se cumple `movimientos = (llamadas − 1) + retrocesos`.

## 19:53 – Tiempo
La función de tiempo del proyecto no existía, así que usé la idea de mi `tiempo.py` (`time.time()` al inicio y al final) con `System.nanoTime()`.
- Se mide solo `resolver`, sin la impresión.
- Los 5 tiempos se ordenan con burbuja y la mediana es `tiempos[2]`.
- **Decisión:** nanosegundos, mostrados en milisegundos. En segundos habría salido 0, porque un laberinto pequeño se resuelve en menos de un milisegundo.

## 20:55 a 21:09 – Simplificar el código
**Cambio pedido:** quitar `df` y `dc` y escribir las direcciones con números (`fila - 1`, `fila + 1`, `col - 1`, `col + 1`).
**Intento:** reemplazar las listas `pos`, `estado` y `cont` por variables `public static` con nombres propios (`InicioX`, `LlegoSerpiente`, `Retrocesos`, etc.). Funcionó, pero `static` quizás no se ha visto en clase.
**Decisión:** volver a las **listas** y poner el significado de cada posición en un comentario, **en la línea donde se usa por primera vez**:

| Lista | Posición | Nombre |
|-------|----------|--------|
| `pos` | 0, 1, 2, 3 | InicioX, InicioY, FinX, FinY |
| `estado` | 0, 1 | LlegoSerpiente, LlamadasMaximas |
| `cont` | 0 a 5 | LongitudRuta, celdasDistintas, DireccionesRevisadas, Retrocesos, Llamadas, Movimientos |

## 21:31 a 22:25 – Repaso del código de `buscar`
Estudié, línea por línea: qué hace `mostrar`, qué es `fila` y dónde se usa, la comparación `fila == pos[3] && col == pos[2]` (la casilla actual es la X, **no la O**), el ordenamiento burbuja de `orden`, el `k` del `for` (el turno dentro de `orden`) y el `if` de movimiento (dentro del laberinto, no es `+` y no está visitada). También la **recursión**: caso base (llegó a la X, se quedó sin direcciones o llegó al límite) y cómo cada llamada sabe que debe salir mirando `estado[0]` y `estado[1]`.

## 22:34 – Marcar los retrocesos
Cambio pedido: las casillas donde la serpiente retrocede se marcan con otro color. Ahora quedan como `R` con fondo rojo y texto blanco. No afecta la búsqueda, porque `visitado` es una matriz aparte.

---

## Estado final
- **Hecho:** validación de la entrada, estrategia Manhattan con retroceso, límite de 100.000 llamadas, métricas, 5 repeticiones con mediana del tiempo, impresión paso a paso y colores (`S`, `*`, `R`). README actualizado.
- **Pendiente:** las otras dos estrategias (mis compañeros), y confirmar con el enunciado si hay que correr varios casos seguidos o comparar las tres estrategias en una tabla.
- **Para ejecutar:** `javac -d out src/*.java` y luego `java -Xss64m -cp out Main`.
