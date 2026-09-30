# 🎰 slotMachine

Un simulador de máquina tragamonedas hecho en Java con BlueJ. Tiene ruedas, símbolos de colores, animaciones y un modo ganador que se nota a simple vista. Además, incluye un solucionador para un problema real de programación competitiva: el **Problem I (Slot Machine)** de la Maratón Internacional de Programación 2025.

Lo desarrollé como proyecto del curso de Desarrollo Orientado por Objetos (DOPO) en la Escuela Colombiana de Ingeniería Julio Garavito, y fue creciendo por ciclos: primero un simulador básico, luego más control sobre las ruedas, después la solución de la maratón, y ahora tipos distintos de ruedas y símbolos.

## ¿Qué es y cómo funciona?

Una máquina tiene **ruedas**, y cada rueda tiene **símbolos**. Cada símbolo es simplemente un color (con los nombres del estándar CSS: `red`, `blue`, `gold`…). Al girar una rueda, sus símbolos avanzan y cambia el que se ve. La máquina gana (*jackpot*) cuando todas las ruedas muestran el mismo símbolo, y en ese momento cambia de aspecto para que se note.

Puedes usarla de dos maneras:

- **Visible:** ves las figuras en pantalla y los giros se animan paso a paso.
- **Invisible:** funciona igual, pero sin dibujar nada. Es lo que usan las pruebas y el solucionador.

Si intentas algo que no se puede hacer (por ejemplo, girar una rueda que no existe) y la máquina está visible, te sale un aviso con `JOptionPane`. En modo invisible simplemente no pasa nada, y puedes consultar `ok()` para saber si la última operación funcionó.

Si prefieres una máquina lista para jugar, `new SlotMachine(5)` crea una con 5 ruedas y 5 símbolos, mezclados al azar.

## Qué se puede hacer con ella

**Armarla**
- `addWheel(pos)` y `delWheel(pos)` para agregar o quitar ruedas.
- `addSymbol(pos, color)` y `delSymbol(color)` para los símbolos.
- `placeSymbol(rueda, color)` para dejar un símbolo específico a la vista.

**Moverla**
- `spin()` gira todas las ruedas y `spin(rueda)` solo una.
- `spin(rueda, pasos)` la gira varias posiciones seguidas.
- `spin(String[])` deja la máquina en una configuración concreta.
- `swap(a, b)` intercambia dos ruedas.
- `lock(rueda)` y `unlock(rueda)` fijan o sueltan una rueda para que no gire.

**Consultarla**
- `symbols()` devuelve los colores de los símbolos, en el orden de la rueda.
- `configuration()` devuelve lo que se ve en cada rueda, de izquierda a derecha.
- `distinctSymbols()` cuenta cuántos símbolos distintos hay a la vista.
- `isJackpot()` dice si la máquina está en estado ganador.

**Controlarla**
- `makeVisible()` y `makeInvisible()` para mostrarla u ocultarla.
- `exit()` para terminar el simulador.

Un par de reglas para no llevarse sorpresas: las posiciones empiezan en **1**; si pasas una menor, se usa la primera, y si pasas una mayor al máximo, se usa la última.

## El reto de la maratón

El Problem I de la maratón de programación internacional 2025 Slot Machine. trata de encontrar la forma de dejar todas las ruedas de una máquina mostrando el mismo símbolo. La clase `SlotMachineContest` lo resuelve con dos métodos:

- `solve(n)` calcula la secuencia de acciones `{i, j}` que hay que hacer para ganar con una máquina de `n` ruedas y `n` símbolos.
- `simulate(n)` toma una máquina aleatoria de ese tamaño y **reproduce esas acciones a la vista**, hasta que gana.

`SlotMachine` no resuelve el problema: solo sirve de banco de pruebas y de pantalla. Para calcular la solución únicamente usa `SlotMachine(n)`, `spin(rueda, pasos)` y `distinctSymbols()`, y se mantiene invisible. Solo cuando toca mostrar el resultado, `simulate` la hace visible.

## Decisiones de diseño

- **El jackpot no apaga la máquina.** Ganar solo cambia su aspecto (los colores pasan a un tono de celebración y vuelven a los normales si deja de estar ganada). 
- **Cerrar el simulador** es una decisión explícita con `exit()`.
- **`isJackpot()` es solo una consulta.** No modifica nada, así se puede llamar las veces que sea sin efectos secundarios.
- **Todo es extensible.** La interfaz de `SlotMachine` se mantiene estable mientras el proyecto crece, y la librería de figuras `shapes` se reutiliza y se amplía cuando hace falta algo nuevo.

## Pruebas

Las pruebas unitarias corren siempre en modo invisible y están pensadas alrededor de dos preguntas: *¿qué debería hacer?* y *¿qué no debería hacer?* Por eso hay tanto casos normales como casos inválidos (bloqueos, posiciones fuera de rango, colores que no existen, intercambios imposibles).

- `SlotMachineC2Test`: intercambio, bloqueo, giros por pasos y configuraciones dadas.
- `SlotMachineContestTest`: creación con `n` ruedas y solución de la maratón.

En BlueJ, clic derecho sobre la clase de pruebas → **Test All**.

## Principios SOLID aplicados

Me apoyé sobre todo en dos principios: **S** y **O**.

### S: Responsabilidad única
Cada clase tiene una sola razón para cambiar:

- `SlotMachine` orquesta la máquina: recibe las acciones del usuario y coordina el resto.
- `Wheel` sabe cómo es una rueda: sus símbolos, su giro y si está fija o no.
- `SlotMachineContest` se dedica solo a resolver la maratón.
- Las figuras y el dibujo viven en `shapes`, así que la lógica de la máquina no se mezcla con los detalles gráficos.

### O: Abierto a extensión, cerrado a modificación
Agregar comportamiento nuevo no debería obligar a reescribir lo que ya funciona:

## Conceptos de POO aplicados

### Encapsulación y ocultamiento de información
Los atributos de `SlotMachine` y `Wheel` son privados. Desde afuera solo se accede por métodos públicos (`addWheel`, `spin`, `configuration`, `isJackpot`…). Así nadie puede dejar la máquina en un estado inválido tocando sus datos directamente.

### Abstracción
`SlotMachine` ofrece una interfaz simple (agregar, girar, consultar) que esconde los detalles de cómo se guardan las ruedas, cómo se dibujan las figuras o cómo se anima un giro. Quien la usa no necesita saber nada de eso.

### Herencia
Con los tipos de ruedas (`lefty`, `rebel`, `normal`) y de símbolos (`ephemeral`, `shy`, `normal`) la idea es la misma: heredan de una base común y cambian solo lo que los hace distintos.

### Polimorfismo
La máquina trata a todas las ruedas y símbolos por igual, sin importar su tipo. Al girar, bloquear o intercambiar, cada tipo responde a su manera: una rueda `lefty` copia a su vecina, una `rebel` no se deja bloquear, un símbolo `ephemeral` se encoge y uno `shy` se oculta. `SlotMachine` no necesita un `if` por tipo.

### Composición y colaboración entre objetos
Una `SlotMachine` **tiene** ruedas y cada `Wheel` **tiene** símbolos. Las clases colaboran enviándose mensajes, y ninguna hace el trabajo de otra.

### Sobrecarga (overloading) de métodos
`spin()`, `spin(rueda)`, `spin(rueda, pasos)` y `spin(String[])` comparten nombre y se distinguen por sus parámetros. Lo mismo pasa con `SlotMachine()` y `SlotMachine(n)`.

### Manejo de estado
`ok()` guarda si la última operación se pudo realizar, y `isJackpot()` es una consulta pura que no cambia nada.

## Documentación

En la carpeta `docs/` están los diagramas de clases y de secuencia (hechos en Astah) y la retrospectiva del proyecto.


## Autor

**Juan David Rojas Heredia**
Estudiante de Ingeniería de Inteligencia Artificial, Escuela Colombiana de Ingeniería Julio Garavito.

