PROJECT TITLE: SlotMachine

PURPOSE OF PROJECT:
Este proyecto simula una máquina tragamonedas (slot machine) mediante clases en Java.
Permite agregar y eliminar ruedas (Wheel) y símbolos, girar una rueda individual o todas
a la vez, colocar símbolos manualmente en una rueda específica, y detectar cuando ocurre
un jackpot (cuando todas las ruedas muestran el mismo símbolo, con un mínimo de dos ruedas).
La representación gráfica se realiza mediante las clases Rectangle (la carcasa de la
máquina y de cada rueda) y Circle (el símbolo visible dentro de cada rueda).

VERSION or DATE: 1.0 (22 agosto 2026)

HOW TO START THIS PROJECT:
1. Abrir el proyecto en BlueJ (o el IDE que se esté usando).
2. Asegurarse de que las clases Rectangle y Circle (del paquete de figuras usado en el
   curso) estén presentes en el proyecto, ya que Wheel y SlotMachine dependen de ellas.
3. Crear un objeto de la clase SlotMachine, por ejemplo:
       SlotMachine maquina = new SlotMachine();
4. Usar los métodos públicos de SlotMachine para interactuar con la máquina (ver
   USER INSTRUCTIONS más abajo).

AUTHORS: Juan David Rojas y César Morales

USER INSTRUCTIONS:
- addSymbol(int pos, String color): agrega un símbolo nuevo en la posición indicada.
  Colores disponibles: "red", "black", "blue", "yellow", "green", "white", "orange", "cyan".
- delSymbol(String color): elimina un símbolo existente por su color.
- addWheel(int pos): agrega una rueda nueva en la posición indicada.
- delWheel(int pos): elimina la rueda en la posición indicada.
- spin(): gira todas las ruedas de la máquina, asignando símbolos aleatorios.
- spin(int wheel): gira solo la rueda indicada.
- placeSymbol(int wheel, String symbol): coloca manualmente un símbolo específico en
  una rueda determinada.
- configuration(): devuelve un arreglo con el símbolo que muestra actualmente cada rueda.
- symbols(): devuelve un arreglo con todos los símbolos disponibles.
- distinctSymbols(): devuelve el número de símbolos diferentes disponibles.
- isJackpot(): indica si todas las ruedas muestran el mismo símbolo (jackpot).
- ok(): indica si la última operación realizada fue exitosa.
- makeVisible() / makeInvisible(): muestra u oculta la máquina y sus ruedas en pantalla.

Nota: se recomienda agregar al menos dos símbolos y dos ruedas antes de intentar girar
la máquina, de lo contrario las operaciones no se permitirán.