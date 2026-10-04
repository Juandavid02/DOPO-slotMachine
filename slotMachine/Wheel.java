import java.util.Random;
/**
 * La clase Wheel representa una rueda que contiene un símbolo circular en su interior.
 * Se utiliza para simular ruedas con símbolos visibles como en una máquina tragamonedas
 * @author Juan David Rojas
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
    private int desplazamientoCuadrado = 0;
    private boolean locked;
    private boolean figurasCreadas;
    private int currentX;
    private int currentY;
     /**
     * Constructor de la clase Wheel.
     * Inicializa la rueda con un número de símbolos y asigna un índice visible aleatorio.
     * También crea las figuras gráficas (rectángulo y círculo) y las posiciona.
     *
     * @param cantSymbols número total de símbolos posibles en la rueda.
     *  Si es mayor que 0, se selecciona un índice aleatorio.
     *  Si es 0 o negativo, el índice visible será 0.
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
     * Permite modificar el estado de las ruedas para que esten 
     * bloquadas o desbloqueadas
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
     * Rota la rueda un número de pasos (hacia adelante o hacia atras), avanzando el índice
     * visible circulatmente según el total de símbolos disponibles
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
    
    public boolean puedeIntercambiarse(){
        return true;
    }
    
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
    
    public abstract String getTipo();
}