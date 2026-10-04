import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Collections;//Poder usar el shuffle
import javax.swing.JOptionPane;
/**
 * Una máquina tragamonedas que puede contener múltiples ruedas y símbolos.
 * Las ruedas pueden ser de varios tipos (normal, lefty, lazy y rebel) y los
 * símbolos también (normal, ephemeral y shy). Las ruedas pueden girarse
 * aleatoriamente o configurarse para mostrar símbolos específicos. La máquina
 * también puede detectar cuando ocurre un jackpot y se puede cerrar con exit().
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (22 agosto 2026)
 */
public class SlotMachine
{
    private List<Wheel> wheels; 
    private List<Symbol> symbols;
    private boolean visible;
    private boolean ok;          // si la ultima operacion salio bien
    private boolean cerrada;     // true despues de llamar exit()
    private boolean girada;      // true si ya se giro o se coloco algun simbolo
    private boolean figurasCreadas;
    private boolean ganada = false;   // si la maquina esta en jackpot en este momento
    private String lastMessage;
    private Random random;
    private Rectangle machine;
    private Rectangle brazoHorizontal;
    private Rectangle brazoVertical;
    private Circle perilla;
    
    /**
     * Crea una máquina tragamonedas vacía, sin ruedas ni símbolos.
     * Empieza visible y con la última operación marcada como exitosa.
     * Las figuras no se crean aquí, se crean la primera vez que se dibuja la máquina.
     */
    public SlotMachine(){
        wheels = new ArrayList<Wheel>();
        symbols = new ArrayList<Symbol>();
        visible = true;
        ok = true;
        random = new Random();
        girada = false;
        cerrada = false;
        figurasCreadas = false; 
    }
    /**
     * Crea una nueva máquina tragamonedas con exactamente n ruedas normales y n
     * símbolos normales de colores al azar. Si n es mayor que 8 se usa 8 y si
     * es menor que 1 se usa 1 (solo hay 8 colores disponibles). La
     * máquina queda invisible por defecto, como corresponde a su uso de resolver el problema sin revelar el estado de las ruedas.
     *
     * @param n el número de ruedas (y de símbolos) que tendrá la máquina.
     */
    public SlotMachine(int n){
        this();
        if (n > 8){
            n = 8;
        }
        else if (n < 1){
            n = 1;
        }
        visible = false;
        List<String> disponibles = new ArrayList<>();
        disponibles.add("red");
        disponibles.add("black");
        disponibles.add("blue");
        disponibles.add("yellow");
        disponibles.add("green");
        disponibles.add("white");
        disponibles.add("orange");
        disponibles.add("cyan");        
        Collections.shuffle(disponibles); // mezcla el orden al azar IA generativa
        List<String> color = disponibles.subList(0, n);
        for (int i = 0; i < n; i++){
            addSymbol(i + 1, color.get(i));
        }
        for (int i = 0; i < n; i++){
            addWheel(i + 1);
        }
    }
    
    //Ayuda de IA para darnos la de idea de como hacer las figuras sin necesidad de usar el Construstor
    /**
     * Crea las figuras de la máquina: el cuerpo, los dos brazos y la perilla.
     * Solo se crean la primera vez. Si la máquina ya estaba en jackpot,
     * las pinta con los colores de celebración.
     */
    private void crearFiguras(){
        if (!figurasCreadas){
            int margenX = Medida.MARGEN_X.getValor();
            int margenY = Medida.MARGEN_Y.getValor();
            machine = new Rectangle();
            machine.changeColor("gray");
            machine.changeSize(120, 120);
            machine.moveHorizontal(margenX);
            machine.moveVertical(margenY);
    
            brazoVertical = new Rectangle();
            brazoVertical.changeColor("gray");
            brazoVertical.changeSize(100, 15);
            brazoVertical.moveHorizontal(margenX - 30);
            brazoVertical.moveVertical(margenY + 10);
    
            brazoHorizontal = new Rectangle();
            brazoHorizontal.changeColor("gray");
            brazoHorizontal.changeSize(15, 30);
            brazoHorizontal.moveHorizontal(margenX - 30);
            brazoHorizontal.moveVertical(margenY + 95);
    
            perilla = new Circle();
            perilla.changeColor("red");
            perilla.changeSize(30);
            perilla.moveHorizontal(margenX - 38);
            perilla.moveVertical(margenY);
    
            figurasCreadas = true;
            if (ganada){
                pintarCelebracion(true);
            }
        }
    }
    
    //Idea de IA para que los mensajes los usaramos solamente si estaban visibles guardando el mensaje
    //y pasandolo como atributo a este metodo
    /**
     * Registra un mensaje informativo para el usuario. Si la máquina está
     * visible, además lo muestra en un diálogo; si está invisible (como
     * durante las pruebas automáticas), el mensaje solo queda disponible
     * mediante lastMessage(), sin bloquear la ejecución con una ventana.
     *
     * @param message el mensaje a registrar y, si corresponde, mostrar
     */
    private void showMessage(String message){
        lastMessage = message;
        if (visible){
            JOptionPane.showMessageDialog(null, message);
        }
    }
    
    /**
     * Obtiene el último mensaje informativo generado por la máquina.
     *
     * @return el texto del último mensaje, o null si aún no se ha generado ninguno
     */
    public String lastMessage(){
        return lastMessage;
    }
    
    /**
     * Agrega una rueda normal en la posicion indicada.
     * Es un atajo para addWheel con el tipo "normal".
     *
     * @param pos la posicion deseada para la nueva rueda
     */
    public void addWheel(int pos)
    {
        addWheel("normal", pos);
    }

    /**
     * Agrega una rueda del tipo indicado en la posicion indicada.
     * Los tipos son "normal", "lefty", "rebel" y "lazy". Si la posicion es mayor
     * que el numero de ruedas, se agrega al final. Si es menor que uno,
     * se agrega al principio. Si el tipo no existe, no se agrega nada.
     *
     * @param type el tipo de rueda que se desea agregar
     * @param pos la posicion deseada para la nueva rueda
     */
    public void addWheel(String type, int pos)
    {
        if (!isCerrada()){
            Wheel wheel;
            if ("normal".equals(type)){
                wheel = new NormalWheel(symbols.size());
            }
            else if ("lefty".equals(type)){
                wheel = new LeftyWheel(symbols.size());
            }
            else if ("rebel".equals(type)){
                wheel = new RebelWheel(symbols.size());
            }
            else if ("lazy".equals(type)){
                wheel = new LazyWheel(symbols.size());
            }
            else {
                showMessage("Accion no permitida: El tipo de rueda no existe.");
                ok = false;
                return;
            }
            if (pos > wheels.size()){
                wheels.add(wheel);
                showMessage("Se agrego una rueda en la ultima posicion");
            }
            else if (pos < 1){
                wheels.add(0, wheel);
                showMessage("Se agrego una rueda en la primera posicion");
            }
            else {
                wheels.add(pos - 1, wheel);
            }
            ok = true;
            actualizar();
            verificarJackpot();
        }
    }
    
    /**
     * Elimina una rueda de la máquina tragamonedas en la posición indicada.
     * Si la posición es menor que uno, se elimina la primera rueda.
     * Si la posición es mayor que el número de ruedas, se elimina la última
     * rueda. Si la rueda no se deja eliminar (como la rebelde) o no hay
     * ruedas, no se elimina nada y se muestra un mensaje.
     *
     * @param pos la posición de la rueda que se desea eliminar
     */
    public void delWheel(int pos)
    {   
        if (!isCerrada()){
            if (!wheels.isEmpty()){
                int index;
                String mensaje = "";
                if (pos < 1 ){
                    index = 0;
                    mensaje = "Se elimino la primera rueda";
                }
                else if (pos > wheels.size()){
                    index = wheels.size() - 1;
                    mensaje = "Se elimino la ultima rueda";
                }
                else{
                    index = pos - 1;
                }
                if (wheels.get(index).puedeEliminarse()){
                    wheels.get(index).makeInvisible();
                    wheels.remove(index);
                    ok = true;
                    if (!mensaje.equals("")){
                        showMessage(mensaje);
                    }
                }
                else{
                    showMessage("Accion no permitida: La rueda no se deja eliminar");
                    ok = false;
                }
            }
            else {
                showMessage("Accion no permitida: No hay ruedas para eliminar");
                ok = false;
            }
            actualizar();
            verificarJackpot();
        }
    }
    
    /**
     * Intercambia los simbolos que muestran dos ruedas.
     * Si una posicion es menor que uno se usa la primera rueda, y si es mayor
     * que el numero de ruedas se usa la ultima (con un mensaje informativo).
     * No se intercambia si las dos ruedas son la misma, si alguna esta
     * bloqueada o si alguna no se deja intercambiar (como la rebelde).
     *
     * @param wheel1 la posicion de la primera rueda
     * @param wheel2 la posicion de la segunda rueda
     */
    public void swap(int wheel1, int wheel2){
        if (!isCerrada()){
            if (!wheels.isEmpty()){
                boolean flag = false; 
                String mensaje = "";
                if (wheel1<1){
                    wheel1 = 1;
                    mensaje += "Se va a intercambiar la primera rueda ya que el valor dado es una rueda en una posicion menor que 1.\n";
                    flag = true;
                }
                if (wheel2<1){
                    wheel2 = 1;
                    mensaje += "Se va a intercambiar la primera rueda ya que el valor dado es una rueda en una posicion  menor que 1.\n";
                    flag = true;
                }
                if (wheel1 > wheels.size()){
                    wheel1 = wheels.size();
                    mensaje += "Se va a intercambiar la ultima rueda ya que el valor dado es una rueda en una posicion que es mayor que el tamaño.\n";
                    flag = true;
                }
                if (wheel2 > wheels.size()){
                    wheel2 = wheels.size();
                    mensaje += "Se va a intercambiar la ultima rueda ya que el valor dado es una rueda en una posicion que es mayor que el tamaño.\n";
                    flag = true;
                }
                ok = true;
                if (!(wheel1 == wheel2)){
                    if (wheels.get(wheel1 - 1).isLocked() || wheels.get(wheel2 - 1).isLocked()){
                        ok = false;
                        showMessage("Accion no permitida: Una de las ruedas esta bloqueada.");
                    }
                    else if (!wheels.get(wheel1 - 1).puedeIntercambiarse() || !wheels.get(wheel2 - 1).puedeIntercambiarse()){
                        ok = false;
                        showMessage("Accion no permitida: Una de las ruedas no se deja intercambiar.");
                    }
                    else{
                        int idx1 = wheels.get(wheel1 - 1).getVisibleIndex();
                        int idx2 = wheels.get(wheel2 - 1).getVisibleIndex();                
                        wheels.get(wheel1 - 1).setVisibleIndex(idx2);
                        wheels.get(wheel2 - 1).setVisibleIndex(idx1);
                        if (flag){
                            showMessage(mensaje);
                        } 
                        actualizar();
                    }
                }
                else {
                    ok = false;
                    showMessage("Accion no permitida: Las ruedas son las mismas");
                }
            }
             else{
                showMessage("Accion no permitida: No hay ruedas para cambiar.");
                ok = false;    
            }     
        }
    }
    
    /**
     * Bloquea la rueda indicada para que no sea modificada por los métodos
     * de giro (spin). Si la posición indicada es menor que uno, se bloquea
     * la primera rueda; si es mayor que el número de ruedas, se bloquea la
     * última, mostrando en ambos casos un mensaje informativo. La operación
     * solo se realiza si la máquina tiene al menos una rueda. Si la rueda
     * no se deja bloquear (como la rebelde), muestra un mensaje y no la bloquea.
     *
     * @param wheel la posición de la rueda que se desea bloquear
     */ 
    public void lock(int wheel){
        if (!isCerrada()){
            if (!wheels.isEmpty()){
                boolean flag = false;
                String  mensaje  = "";
                if (wheel<1){
                    wheel = 1;
                    mensaje += "Se va a usar la primera rueda ya que el valor dado es una rueda en una posicion menor que 1.\n";
                    flag = true;
                }
                else if (wheel > wheels.size()){
                    wheel = wheels.size();
                    mensaje += "Se va a usar la ultima rueda ya que el valor dado es una rueda que esta a fuera del tamaño.\n";
                    flag = true;
                }
                wheels.get(wheel-1).setLocked(true);
                if (wheels.get(wheel-1).isLocked()){
                    ok = true;
                    if (flag){
                            showMessage(mensaje);
                        } 
                    actualizar();
                }
                else{
                    showMessage("Accion no permitida: la rueda no se deja bloquear.");
                    ok = false;
                }
            }
            else{
                showMessage("Accion no permitida: No hay ruedas para bloquear.");
                ok = false;
            }
        }
    }
    
    /**
     * Busca un simbolo por su color y devuelve su posicion.
     *
     * @param color el color del simbolo que se busca
     * @return la posicion del simbolo, o -1 si no existe
     */
    private int indiceDe(String color){
        for (int i = 0; i < symbols.size(); i++){
            if (symbols.get(i).getColor().equals(color)){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Desbloquea la rueda indicada, permitiendo que vuelva a ser modificada
     * por los métodos de giro (spin). Si la posición indicada es menor que
     * uno, se desbloquea la primera rueda; si es mayor que el número de
     * ruedas, se desbloquea la última, mostrando en ambos casos un mensaje
     * informativo. La operación solo se realiza si la máquina tiene al
     * menos una rueda.
     *
     * @param wheel la posición de la rueda que se desea desbloquear
     */
    public void unlock(int wheel){
        if (!isCerrada()){
            if (!wheels.isEmpty()){
                boolean flag = false;
                String  mensaje  = "";
                if (wheel<1){
                    wheel = 1;
                    mensaje += "Se va a usar la primera rueda ya que el valor dado es una rueda en una posicion menor que 1.\n";
                    flag = true;
                }
                else if (wheel > wheels.size()){
                    wheel = wheels.size();
                    mensaje += "Se va a usar la ultima rueda ya que el valor dado es una rueda que esta a fuera del tamaño.\n";
                    flag = true;
                }      
                wheels.get(wheel - 1).setLocked(false);
                ok = true;
                if (flag){
                        showMessage(mensaje);
                } 
                actualizar();
            }
            else {
                showMessage("Accion no permitida: No hay ruedas para desbloquear.");
                ok = false;
            }
        }
    }
    
    /**
     * Agrega un simbolo normal en la posicion indicada.
     * Es un atajo para addSymbol con el tipo "normal".
     *
     * @param pos la posicion deseada para el nuevo simbolo
     * @param color el color del simbolo que se desea agregar
     */
    public void addSymbol(int pos, String color)
    {
        addSymbol("normal", pos, color);
    }
    
    /**
     * Crea un simbolo del tipo y color indicados.
     *
     * @param type el tipo de simbolo: "normal", "ephemeral" o "shy"
     * @param color el color del simbolo
     * @return el simbolo creado, o null si el tipo no existe
     */
    private Symbol crearSimbolo(String type, String color){
        if ("normal".equals(type)){
            return new NormalSymbol(color);
        }
        else if ("ephemeral".equals(type)){
            return new EphemeralSymbol(color);
        }
        else if ("shy".equals(type)){
            return new ShySymbol(color);
        }
        return null;
    }
    
    /**
     * Dice si el color es uno de los que se pueden usar en un simbolo.
     *
     * @param color el color que se quiere revisar
     * @return true si el color esta disponible, false si no (o si es null)
     */
    private boolean colorDisponible(String color){
        String[] colores = {"red", "black", "blue", "yellow", "green", "white", "orange", "cyan"};
        for (int i = 0; i < colores.length; i++){
            if (colores[i].equals(color)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Agrega un simbolo del tipo indicado en la posicion indicada.
     * Los tipos son "normal", "ephemeral" y "shy". El simbolo solo se
     * agrega si el tipo existe, el color esta disponible y no esta repetido.
     * Si la posicion es mayor que el numero de simbolos, se agrega al final.
     * Si es menor o igual a uno, se agrega al principio.
     *
     * @param type el tipo de simbolo que se desea agregar
     * @param pos la posicion deseada para el nuevo simbolo
     * @param color el color del simbolo. Los colores disponibles son "red",
     * "black", "blue", "yellow", "green", "white", "orange" y "cyan".
     */
    public void addSymbol(String type, int pos, String color)
    {
        if (!isCerrada()){
            Symbol symbol = crearSimbolo(type, color);
            if (symbol == null){
                showMessage("Accion no permitida: El tipo de simbolo no existe.");
                ok = false;
            }
            else if (!colorDisponible(color)){
                showMessage("Accion no permitida: El color no esta disponible.");
                ok = false;
            }
            else if (indiceDe(color) != -1){
                showMessage("Accion no permitida: El color ya se encuentra entre las opciones");
                ok = false;
            }
            else {
                int idx;
                if (pos > symbols.size()){
                    idx = symbols.size();
                }
                else if (pos <= 1){
                    idx = 0;
                }
                else {
                    idx = pos - 1;
                }
                boolean habiaSimbolos = !symbols.isEmpty();
                symbols.add(idx, symbol);
                if (habiaSimbolos){
                    for (int i = 0; i < wheels.size(); i++){
                        int actual = wheels.get(i).getVisibleIndex();
                        if (actual >= idx){
                            wheels.get(i).setVisibleIndex(actual + 1);
                        }
                    }
                }
                ok = true;
            }
            actualizar();
            verificarJackpot();
        }
    }
    
    /**
     * Elimina un símbolo de la máquina tragamonedas.
     * Las ruedas que lo estaban mostrando pasan a mostrar el primer símbolo.
     * Si el símbolo indicado no existe, la operación no se realiza
     * y se muestra un mensaje de error.
     *
     * @param color el color del símbolo que se desea eliminar
     */
    public void delSymbol(String color)
    {
        if (!isCerrada()){
            int indice = indiceDe(color);
            if (indice == -1){
                
                showMessage("Accion no permitida: No se puede eliminar el símbolo " + color + " porque no existe.");
                ok = false;
            }
            else {
                symbols.remove(indice);
                for (int i = 0; i < wheels.size(); i++){
                    int actual = wheels.get(i).getVisibleIndex();
                    if (actual == indice){
                        wheels.get(i).setVisibleIndex(0);
                    }
                    else if (actual > indice){
                        wheels.get(i).setVisibleIndex(actual - 1);
                    }
                }
                ok = true;
            }
            actualizar();
            verificarJackpot();
        }
    }
    
    /**
     * Coloca un símbolo específico en la rueda indicada.
     * El símbolo debe existir entre los símbolos disponibles.
     * Si la posición de la rueda es menor o igual a uno, se selecciona
     * la primera rueda. Si la posición es mayor que el número de ruedas,
     * se selecciona la última rueda.
     *
     * @param wheel la posición de la rueda donde se colocará el símbolo
     * @param color el color del símbolo que se desea colocar en la rueda.
     */
    public void placeSymbol(int wheel, String color)
    {
        if (!isCerrada()){
            if (wheels.isEmpty()){
                ok = false;
                showMessage("Accion no permitida: No hay ruletas.");
                actualizar();
            }
            else {
                int index = indiceDe(color);
                if (index == -1){
                    showMessage("Accion no permitida: No se encontro el simbolo porque no existe.");
                    ok = false;
                }
                else {
                    if (wheel <= 1 ){
                        wheel = 1;
                    }
                    else if (wheel > wheels.size()){
                        wheel = wheels.size();
                    }
                    wheels.get(wheel-1).setVisibleIndex(index);
                    ok = true;
                    girada = true;
                    actualizar();
                    verificarJackpot();
                }
            }
        }
    }    
    
    /**
     * Le avisa al simbolo que la rueda en la posicion dada lo esta mostrando
     * (por ejemplo, el ephemeral se encoge y el shy cambia de visibilidad).
     * No hace nada si no hay simbolos.
     *
     * @param index la posicion de la rueda en la lista (empieza en 0)
     */
    private void avisarSeleccion(int index){
        if (!symbols.isEmpty()){
            Wheel rueda = wheels.get(index);
            symbols.get(rueda.getVisibleIndex()).seleccionado(rueda);
        }
    }
    
    /**
     * Gira una rueda específica con un índice aleatorio, siempre que dicha
     * rueda no esté bloqueada. Cada tipo de rueda decide qué hace con ese
     * índice (la lefty copia a su vecina, la lazy a veces se queda quieta).
     * Si la rueda se movió, se le avisa al símbolo que quedó visible.
     * La posición de la rueda se ajusta a la primera o última rueda si la
     * posición indicada está fuera del rango válido.
     * <p>
     * Este método es privado porque es una operación interna utilizada
     * por la máquina tragamonedas al realizar un giro y no debe ser
     * llamada directamente desde fuera de la clase.
     *
     * @param wheel la posición de la rueda que se desea girar
     */
    private void turnWheel(int wheel){
        if (wheel <= 1){
            wheel = 1;
        }
        if (wheel > wheels.size()){
            wheel = wheels.size();
        }
        if (!wheels.get(wheel - 1).isLocked()){
            //.nextInt es un metodo de Random que genera un número entero aleatorio
            // entre 0 (incluido) y symbols.size() (excluido). Este metodo fue consultado desde la API de JAVA
            int randomIndex = random.nextInt(symbols.size());
            Wheel izquierda = wheel > 1 ? wheels.get(wheel - 2) : null;
            boolean seMovio = wheels.get(wheel - 1).girar(randomIndex, izquierda);
            if (seMovio){
                avisarSeleccion(wheel - 1);
            }
        }
    }
    
    /**
     * Gira la rueda indicada de la máquina tragamonedas.
     * La rueda se gira con un símbolo seleccionado aleatoriamente, según
     * las reglas de su tipo. Si está bloqueada no cambia.
     * La operación solo se realiza si hay ruedas y símbolos disponibles.
     *
     * @param wheel la posición de la rueda que se desea girar
     */
    public void spin(int wheel)
    {
        if (!isCerrada()){
            if (symbols.isEmpty() || wheels.isEmpty()){
                showMessage("Accion no permitida: No se puede girar la ruleta porque esta vacia la ruleta o no hay simbolos disponobles.");
                ok = false;
            }
            else{
                turnWheel(wheel);
                ok = true;
                girada = true;
            }
            actualizar();
            verificarJackpot();
        }
    }

    /**
     * Rota una rueda específica un número determinado de pasos (ya sea hacia adelante o hacia atras).
     * Si la máquina está visible, muestra el avance paso a paso con una pequeña
     * pausa entre cada paso; si está invisible, calcula el resultado final
     * de inmediato sin tocar el Canvas ni ninguna figura. Al final le avisa
     * al símbolo que quedó visible. No rota si la rueda está bloqueada.
     *
     * @param wheel la posición de la rueda que se desea rotar
     * @param steps el número de pasos que debe avanzar la rueda
     */
    public void spin(int wheel, int steps){
        if (!isCerrada()){
            if (symbols.isEmpty() || wheels.isEmpty()){
                showMessage("Accion no permitida: No se puede rotar la rueda porque esta vacia la ruleta o no hay simbolos disponibles.");
                ok = false;
                return;
            }
            if (wheel <= 1){
                wheel = 1;
            }
            if (wheel > wheels.size()){
                wheel = wheels.size();
            }
            Wheel selected = wheels.get(wheel - 1);
            if (selected.isLocked()){
                showMessage("Accion no permitida: No se puede rotar la rueda porque esta bloqueada.");
                ok = false;
                return;
            }
            // Modificado según la explicación de la IA: se usa el operador ternario (?) para simplificar 
            // un if-else; define dirección 1 (adelante) si los pasos son >= 0, o -1 (atrás) si son negativos.
            int direccion = (steps >= 0) ? 1 : -1;
            int pasos = Math.abs(steps);
            for (int i = 0; i < pasos; i++){
                selected.rotate(direccion, symbols.size());
                if (visible){
                    actualizar();
                    Canvas.getCanvas().wait(100);
                }
            }
            avisarSeleccion(wheel - 1);
            actualizar();
            girada = true;
            ok = true;
            verificarJackpot();
        }
    }
    
    /**
     * Establece la configuración completa de la máquina tragamonedas de una
     * sola vez, asignando a cada rueda el símbolo correspondiente del arreglo
     * recibido, en el mismo orden de las ruedas.
     * La operación solo se realiza si el número de símbolos coincide con el
     * número de ruedas, y si todos los símbolos indicados existen entre los
     * símbolos disponibles. Las ruedas bloqueadas no cambian (se avisa con un mensaje).
     *
     * @param setSymbols un arreglo con el símbolo que se desea asignar a cada
     * rueda, en el mismo orden que las ruedas
     */
    public void spin(String[] setSymbols){
        if (!isCerrada()){
            if (wheels.isEmpty()){
                showMessage("Accion no permitida: No hay ruedas en la maquina.");
                ok = false;
            }
            else if (setSymbols.length != wheels.size()){
                showMessage("Accion no permitida: La cantidad de simbolos no coincide con la cantidad de ruedas.");
                ok = false;
            }
            else {
                boolean allValid = true;
                for (int i = 0; i < setSymbols.length; i++){
                    if (indiceDe(setSymbols[i]) == -1){
                        allValid = false;
                    }
                }
                if (!allValid){
                    showMessage("Accion no permitida: Uno o mas simbolos indicados no existen.");
                    ok = false;
                }
        
                else {
                    boolean anyLocked = false;
                    String mensaje = "Se completo la accion";
                    for (int i = 0; i < setSymbols.length; i++){
                        if (!wheels.get(i).isLocked()){
                            int index = indiceDe(setSymbols[i]);
                            wheels.get(i).setVisibleIndex(index);
                            avisarSeleccion(i);
                        }
                        else {
                            mensaje += ", excepto para la rueda numero " + (i + 1) + " porque esta bloqueada";
                            anyLocked = true;
                        }
                    }
                    if (anyLocked){
                    showMessage(mensaje);
                    }
                    ok = true;
                    girada = true;
                }
            }
            actualizar();
            verificarJackpot();
        }
    }    
    
    /**
     * Gira todas las ruedas de la máquina tragamonedas.
     * Cada rueda se gira con un símbolo seleccionado aleatoriamente, según
     * las reglas de su tipo. Las ruedas bloqueadas no cambian.
     * La operación solo se realiza si hay ruedas y símbolos disponibles.
     */
    public void spin()
    {
        if (!isCerrada()){
            if (symbols.isEmpty() || wheels.isEmpty()){
                showMessage("Accion no permitida: No se puede girar la ruleta porque esta vacia la ruleta o no hay ruletas disponobles.");
                ok = false;
            }
            else{
               for (int i = 1; i <= wheels.size(); i++){
                    turnWheel(i);
                }
                girada = true;
                ok = true;
                actualizar();
                verificarJackpot();
            }
        }
    }

    // Uso de IA generativa para comprender como pasar la lista de simbolos a un arreglo.
    /**
     * Obtiene todos los símbolos disponibles en la máquina tragamonedas.
     * Se devuelven como un arreglo con el color de cada símbolo, en el orden en que están.
     *
     * @return un arreglo con el color de cada símbolo disponible
     */ 
    public String[] symbols()
    {
        String [] colores = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++){
            colores[i]=symbols.get(i).getColor();
        }
        return colores;
    }
    
    //String[] es un arreglo con tamaño fijo accediendo con config[i]
    /**
     * Obtiene la configuración actual de la máquina tragamonedas.
     * La configuración contiene el color del símbolo que se muestra actualmente
     * en cada rueda, en el mismo orden de las ruedas. Si no hay símbolos,
     * cada posición queda en null.
     *
     * @return un arreglo con el color del símbolo actual de cada rueda, o null si 
     * la maquina esta cerrada
     */
    public String[] configuration(){
        if (!isCerrada()){
            String [] config = new String[wheels.size()];
            for (int i = 0; i < wheels.size(); i++){
                if (!symbols.isEmpty()){
                    int idx = wheels.get(i).getVisibleIndex();
                    config[i] = symbols.get(idx).getColor();
                }
                else{
                    config[i] = null; 
                }
            }
            return config;
        }
        else {
            return null;
        }
    }

    /**
     * Obtiene el número de símbolos diferentes que se muestran actualmente
     * en las ruedas de la máquina, es decir, cuántos símbolos distintos
     * hay en la configuración visible en este momento (no el número
     * total de símbolos registrados en la máquina).
     *
     * @return el número de símbolos distintos actualmente visibles, o 0
     * si la máquina está cerrada o no tiene ruedas.
     */
    public int distinctSymbols()
    {
        String[] config = configuration();
        if (config == null){
            return 0;
        }
        Set<String> vistos = new HashSet<String>(); //IA generativa para poder tener el conjunto y no contar los repetidos
        for (int i = 0; i < config.length; i++){
            vistos.add(config[i]);
        }
        return vistos.size();
    }
    
    /**
     * Actualiza el estado gráfico de la máquina cuando está visible:
     * crea las figuras si aún no existen, redimensiona el cuerpo según
     * la cantidad de ruedas, reposiciona cada rueda y actualiza su color.
     * Si la máquina está invisible, no hace nada (no crea ni toca figuras).
     */
    private void actualizar(){
        if (!visible){
            return;
        }
        crearFiguras();
        if (wheels.isEmpty()){
            machine.changeSize(120, 120);
        }
        else {
            int separacion = Medida.SEPARACION.getValor();
            int anchoRueda = Medida.ANCHO_RUEDA.getValor();
            int anchoTotal = separacion * (wheels.size() + 1) + anchoRueda * wheels.size();
            machine.changeSize(120, anchoTotal);
            for (int i=0; i < wheels.size(); i++){
                wheels.get(i).setPosition(Medida.MARGEN_X.getValor() + separacion + i * (anchoRueda + separacion), Medida.MARGEN_Y.getValor() + 25);
            }
        }

        machine.makeVisible();
        brazoVertical.makeVisible();
        brazoHorizontal.makeVisible();
        perilla.makeVisible();
        for (int i=0; i < wheels.size(); i++){
            dibujarRueda(i);
        }
    }
    
    /**
     * Dibuja la rueda i con la forma, el color, el tamano y la visibilidad
     * del simbolo que muestra.
     *
     * @param i la posicion de la rueda en la lista (empieza en 0)
     */
    private void dibujarRueda(int i){
        Wheel rueda = wheels.get(i);
        if (symbols.isEmpty()){
            rueda.makeVisible(false);
        }
        else {
            Symbol simbolo = symbols.get(rueda.getVisibleIndex());
            rueda.setForma(simbolo.getForma());
            rueda.changeColor(simbolo.getColor());
            rueda.changeSymbolSize(simbolo.getTamano(rueda));
            rueda.makeVisible(simbolo.esVisible(rueda));        
        }
    }
    
    /**
     * Hace visible la máquina tragamonedas junto con todos sus componentes
     * gráficos (cuerpo, brazos, perilla y ruedas). 
     * Además cambia el valor de visible a true y, si hay jackpot,
     * pinta los colores de celebración.
     */
    public void makeVisible(){
        if (!isCerrada()){
            visible = true;
            actualizar();
            pintarCelebracion(ganada);
        }
    }
    
    /**
     * Oculta la máquina tragamonedas. Si las figuras nunca se llegaron a
     * crear (porque la máquina siempre estuvo invisible), no hace nada.
     */
    public void makeInvisible(){
        if (!isCerrada()){
            visible = false;
            if (figurasCreadas){
                machine.makeInvisible();
                brazoVertical.makeInvisible();
                brazoHorizontal.makeInvisible();
                perilla.makeInvisible();
                for (int i = 0; i < wheels.size(); i++){
                    wheels.get(i).makeInvisible();
                }
            }
        }
    }
    
    /**
     * Comprueba si la máquina tragamonedas se encuentra actualmente visible o no.
     *
     * @return true si la máquina es visible o false en caso contrario
     */
    public boolean isVisible(){
        return visible;
    }
    
    /**
     * Indica si la máquina está en jackpot. Es una consulta sin efectos
     * secundarios: no muestra mensajes, no cambia colores y no cierra la
     * máquina. Hay jackpot cuando existen al menos dos ruedas, al menos dos
     * símbolos, ya se giró o colocó algún símbolo, y todas las ruedas
     * muestran el mismo símbolo. Una máquina cerrada con exit() nunca está
     * en jackpot.
     *
     * @return true si todas las ruedas muestran el mismo símbolo y se cumplen
     * las condiciones anteriores; false en caso contrario
     */
    public boolean isJackpot(){
        return hayJackpot();
    }

    /**
     * Calcula si la configuración actual es un jackpot, sin mostrar mensajes.
     *
     * @return true si todas las ruedas muestran el mismo símbolo
     */
    private boolean hayJackpot(){
        return !cerrada && girada && wheels.size() >= 2 && symbols.size() >= 2 && distinctSymbols() == 1;
    }
    
    /**
     * Compara el estado actual con el anterior (ganada) y reacciona solo
     * cuando cambia: al pasar a jackpot aplica los colores de celebración y
     * muestra el mensaje una vez; al salir del jackpot restaura los colores
     * normales. Si el estado no cambia, no hace nada.
     */
    private void verificarJackpot(){
        boolean actual = hayJackpot();
        if (actual && !ganada){
            ganada = true;
            pintarCelebracion(true);
            showMessage("¡FELICIDADES HAS GANADO!");
        }
        else if (!actual && ganada){
            ganada = false;
            pintarCelebracion(false);
        }
    }
    
    /**
     * Cambia el color del cuerpo, los brazos y la perilla según se esté
     * celebrando un jackpot o no. Si las figuras aún no se han creado o la
     * máquina está invisible, no hace nada (crearFiguras aplica el color
     * correcto al crearlas).
     *
     * @param celebrando true para los colores de jackpot, false para los normales
     */
    private void pintarCelebracion(boolean celebrando){
        if (figurasCreadas && visible){
            String colorCuerpo = celebrando ? "green" : "gray"; //Operador ternario idea de IA para dismuir codigo
            String colorPerilla = celebrando ? "yellow" : "red";
            machine.changeColor(colorCuerpo);
            brazoHorizontal.changeColor(colorCuerpo);
            brazoVertical.changeColor(colorCuerpo);
            perilla.changeColor(colorPerilla);
            // Redibujar las ruedas encima del cuerpo
            for (int i = 0; i < wheels.size(); i++){
                dibujarRueda(i);
            }
        }
    }
    
    /**
     * Verifica si la máquina ya fue cerrada con exit(). Si es así, muestra
     * un mensaje informativo y marca la operación como no exitosa.
     *
     * @return true si la máquina está cerrada, false en caso contrario
     */
    private boolean isCerrada(){
        if (cerrada){
            showMessage("Accion no permitida: la maquina fue cerrada con exit() y ya no se puede usar.");
            ok = false;
        }
        return cerrada;
    }

    /**
     * Cierra definitivamente la máquina tragamonedas.
     * La máquina se oculta y queda bloqueada de
     * forma permanente: ya no se pueden agregar ni eliminar ruedas o símbolos,
     * girar, bloquear/desbloquear ruedas, ni volver a hacerla visible.
     * Tampoco se podrá consultar su configuración final ni si
     * hubo jackpot una vez cerrada. Esta operación no se puede deshacer.
     */
    public void exit(){
        makeInvisible();
        cerrada = true;
        ok = true;
    }
    
    /**
     * Comprueba si la última operación fue exitosa.
     *
     * @return true si la última operación fue exitosa; false en caso contrario
     */
    public boolean ok()
    {
        return ok;
    }
}