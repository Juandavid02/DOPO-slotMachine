import java.util.Random;
/**
 * La clase Wheel es la rueda base de la maquina. Tiene un cuerpo cuadrado y
 * un simbolo que puede ser un circulo, un cuadrado o un triangulo.
 * Es abstracta: cada tipo de rueda (normal, lefty, lazy, rebel) decide su
 * color, su tipo y como se comporta al girar.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (22 agosto 2026)
 */
public abstract class Wheel
{
    private int visibleIndex;
    private Rectangle wheelFigure;
    private Circle circuloFigure;
    private Rectangle cuadradoFigure;
    private Triangle trianguloFigure;
    private String forma = "circulo";
    private String colorSimbolo = "red";
    private int tamanoSimbolo = Medida.TAMANO_SIMBOLO.getValor();
    private int desplazamientoCuadrado = 0;   // lo que se ha corrido el cuadrado para mantenerlo centrado
    private boolean locked;
    private boolean figurasCreadas;
    private int currentX;
    private int currentY;

    /**
     * Constructor de la clase Wheel.
     * Deja un indice visible aleatorio, la rueda desbloqueada y en la posicion (0, 0).
     * Las figuras no se crean aqui, se crean la primera vez que se necesitan.
     *
     * @param cantSymbols numero total de simbolos posibles en la rueda.
     *  Si es mayor que 0, se selecciona un indice aleatorio.
     *  Si es 0 o negativo, el indice visible sera 0.
     */
    public Wheel(int cantSymbols)
    {
        Random random = new Random();
        if (cantSymbols > 0){
            visibleIndex = random.nextInt(cantSymbols);
        } else {
            visibleIndex = 0;
        }
        locked = false;
        currentX = 0;
        currentY = 0;
        figurasCreadas = false;
    }
    
    /**
     * Crea las figuras de la rueda: el cuerpo y una figura por cada forma
     * de simbolo (circulo, cuadrado y triangulo). Solo se crean la primera vez.
     */
    private void crearFiguras()
    {
        if (!figurasCreadas){
            int ancho = Medida.ANCHO_RUEDA.getValor();
            int tamano = Medida.TAMANO_SIMBOLO.getValor();
            int margen = (ancho - tamano) / 2;   // lo que separa el simbolo del borde de la rueda
            wheelFigure = new Rectangle();
            wheelFigure.changeSize(ancho, ancho);
            wheelFigure.changeColor(getColorCuerpo());
            circuloFigure = new Circle();
            circuloFigure.moveHorizontal(margen);
            circuloFigure.moveVertical(margen);
            cuadradoFigure = new Rectangle();
            cuadradoFigure.moveHorizontal(margen);
            cuadradoFigure.moveVertical(margen);
            // La x del triangulo es su centro: el margen mas la mitad del simbolo
            trianguloFigure = new Triangle();
            trianguloFigure.moveHorizontal(margen + tamano / 2);
            trianguloFigure.moveVertical(margen);
            figurasCreadas = true;
        }        
    }

    /**
     * Devuelve el índice del símbolo actualmente visible.
     *
     * @return índice del símbolo visible.
     */
    public int getVisibleIndex()
    {
        return visibleIndex;
    }
    
    /**
     * Establece manualmente el índice del símbolo visible.
     *
     * @param index nuevo índice del símbolo visible.
     */
    public void setVisibleIndex(int index)
    {
        visibleIndex = index;
    }
    
    /**
     * Cambia el color del simbolo. Se ve cuando se llama makeVisible.
     *
     * @param color el nuevo color del simbolo
     */
    public void changeColor(String color){
        crearFiguras();
        colorSimbolo = color;
    }
    
    /**
     * Establece la posición de la rueda en coordenadas (x, y).
     * Mueve el cuerpo de la rueda y las figuras del simbolo
     * (circulo, cuadrado y triangulo) a la nueva ubicación.
     * Crea las figuras si aún no existían.
     *
     * @param x nueva posición en el eje X.
     * @param y nueva posición en el eje Y.
     */
    public void setPosition(int x, int y)
    {
        crearFiguras();
        int dx = x - currentX;
        int dy = y - currentY;
        wheelFigure.moveHorizontal(dx);
        wheelFigure.moveVertical(dy);
        circuloFigure.moveHorizontal(dx);
        circuloFigure.moveVertical(dy);
        cuadradoFigure.moveHorizontal(dx);
        cuadradoFigure.moveVertical(dy);
        trianguloFigure.moveHorizontal(dx);
        trianguloFigure.moveVertical(dy);
        currentX = x;
        currentY = y;
    }
    
    /**
     * Permite bloquear o desbloquear la rueda.
     * Algunas ruedas (como la rebelde) no se dejan bloquear.
     *
     * @param value true para bloquear la rueda, false para permitir que gire.
     */
    public void setLocked(boolean value){
        locked=value;
    }
    
    /**
     * Indica si la rueda se encuentra actualmente bloqueada.
     *
     * @return true si la rueda está bloqueada, false en caso contrario.
     */
    public boolean isLocked(){
        return locked;
    }
    
    /**
     * Rota la rueda un número de pasos (hacia adelante o hacia atrás), moviendo el índice
     * visible de forma circular según el total de símbolos disponibles.
     * Si totalSymbols es 0 o negativo no hace nada.
     *
     * @param steps número de pasos que debe avanzar la rueda
     * @param totalSymbols número total de símbolos disponibles en la máquina
     */
    public void rotate(int steps, int totalSymbols){
        if (totalSymbols <= 0){
            return;
        }
        visibleIndex = ((visibleIndex + steps) % totalSymbols + totalSymbols) % totalSymbols;
    }
    
    /**
     * Cambia la forma del simbolo: "circulo", "cuadrado" o "triangulo".
     * Se ve cuando se llama makeVisible.
     *
     * @param forma la nueva forma
     */
    public void setForma(String forma){
        crearFiguras();
        this.forma = forma;
    }
    
    /**
     * Cambia el tamano del simbolo. Se ve cuando se llama makeVisible.
     *
     * @param size el nuevo tamano
     */
    public void changeSymbolSize(int size){
        crearFiguras();
        tamanoSimbolo = size;
    }
    
    /**
     * Oculta las tres figuras del simbolo.
     */
    private void ocultarSimbolo(){
        circuloFigure.makeInvisible();
        cuadradoFigure.makeInvisible();
        trianguloFigure.makeInvisible();
    }
    
    /**
     * Muestra la rueda en pantalla. Si flag es true tambien muestra el simbolo,
     * con la forma, el tamano y el color que tenga la rueda en ese momento.
     * Si flag es false solo se ve el cuerpo de la rueda.
     *
     * @param flag true para mostrar tambien el simbolo, false para mostrar solo la rueda
     */
    public void makeVisible(boolean flag){
        crearFiguras();
        wheelFigure.makeVisible();
        ocultarSimbolo();
        if (flag){
            if (forma.equals("cuadrado")){
                // Se mueve la mitad de lo que se achico para que quede en el centro IA generativa
                int desplazamiento = (Medida.TAMANO_SIMBOLO.getValor() - tamanoSimbolo) / 2;
                cuadradoFigure.changeSize(tamanoSimbolo, tamanoSimbolo);
                cuadradoFigure.moveHorizontal(desplazamiento - desplazamientoCuadrado);
                cuadradoFigure.moveVertical(desplazamiento - desplazamientoCuadrado);
                desplazamientoCuadrado = desplazamiento;
                cuadradoFigure.changeColor(colorSimbolo);
                cuadradoFigure.makeVisible();
            }
            else if (forma.equals("triangulo")){
                trianguloFigure.changeSize(tamanoSimbolo, tamanoSimbolo);
                trianguloFigure.changeColor(colorSimbolo);
                trianguloFigure.makeVisible();
            }
            else {
                circuloFigure.changeSize(tamanoSimbolo);
                circuloFigure.changeColor(colorSimbolo);
                circuloFigure.makeVisible();
            }
        }
    }
    
    /**
     * Oculta la rueda y el símbolo de la pantalla. Si las figuras nunca
     * se llegaron a crear no hace nada.
     */
    public void makeInvisible(){
        if (figurasCreadas){
            wheelFigure.makeInvisible();
            ocultarSimbolo();
        }
    }
    
    /**
     * Gira la rueda. La rueda normal se queda con el indice que le dan.
     * Otros tipos de rueda pueden hacer algo distinto.
     *
     * @param indiceAleatorio el indice sorteado por la maquina
     * @param izquierda la rueda que esta a su izquierda, o null si no hay
     * @return true si la rueda se movio, false si se quedo quieta
     */
    public boolean girar(int indiceAleatorio, Wheel izquierda){
        visibleIndex = indiceAleatorio;
        return true;
    }
    
    /**
     * Dice si esta rueda se puede intercambiar con otra.
     * Por defecto si se puede; la rueda rebelde lo cambia.
     *
     * @return true si se puede intercambiar, false si no
     */
    public boolean puedeIntercambiarse(){
        return true;
    }
    
    /**
     * Dice si esta rueda se puede eliminar de la maquina.
     * Por defecto si se puede; la rueda rebelde lo cambia.
     *
     * @return true si se puede eliminar, false si no
     */
    public boolean puedeEliminarse(){
        return true;
    }
    
    /**
     * Devuelve el color con el que se dibuja el cuerpo de la rueda.
     * Cada tipo de rueda tiene el suyo.
     *
     * @return el color del cuerpo
     */
    public abstract String getColorCuerpo();
    
    /**
     * Devuelve el nombre del tipo de rueda ("normal", "lefty", "lazy" o "rebel").
     * Cada tipo de rueda lo define.
     *
     * @return el nombre del tipo de rueda
     */
    public abstract String getTipo();
}