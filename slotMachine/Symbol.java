/**
 * Un simbolo de la maquina tragamonedas. Por ahora solo guarda su color.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (septiembre 2026)
 */
public abstract class Symbol
{
    protected String color;

    /**
     * Crea un simbolo con el color dado.
     *
     * @param color el color del simbolo
     */
    public Symbol(String color){
        this.color = color;
    }

    /**
     * Devuelve el color del simbolo.
     *
     * @return el color del simbolo
     */
    public String getColor(){
        return color;
    }
    
    /**
     * Se llama cuando una rueda queda mostrando este simbolo.
     * Por defecto no hace nada.
     *
     * @param rueda la rueda que lo selecciono
     */
    public void seleccionado(Wheel rueda){
    }
    
    /**
     * Devuelve el tamano del simbolo en la rueda dada.
     *
     * @param rueda la rueda que muestra el simbolo
     * @return el tamano del simbolo
     */
    public int getTamano(Wheel rueda){
        return Medida.TAMANO_SIMBOLO.getValor();
    }
    
    /**
     * Dice si el simbolo se debe ver en la rueda dada.
     *
     * @param rueda la rueda que muestra el simbolo
     * @return true si se ve, false si no
     */
    public boolean esVisible(Wheel rueda){
        return true;
    }
    
    /**
     * Devuelve la forma con la que se dibuja el simbolo.
     *
     * @return "circulo", "cuadrado" o "triangulo"
     */
    public abstract String getForma();
    
    /**
     * Dice que tipo de simbolo es.
     *
     * @return el nombre del tipo
     */
    public abstract String getTipo();
}