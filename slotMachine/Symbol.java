/**
 * Un simbolo de la maquina tragamonedas. Por ahora solo guarda su color.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (septiembre 2026)
 */
public abstract class Symbol
{
    protected String color;
    protected static final int TAMANO_NORMAL = 30;

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
     */
    public void seleccionado(){
    }
    
    /**
     * Devuelve el tamano del simbolo.
     *
     * @return el tamano del simbolo
     */
    public int getTamano(){
        return TAMANO_NORMAL;
    }
    
    /**
     * Dice si el simbolo se debe ver o no.
     *
     * @return true si se ve, false si no
     */
    public boolean esVisible(){
        return true;
    }
    
    /**
     * Dice que tipo de simbolo es.
     *
     * @return el nombre del tipo
     */
    public abstract String getTipo();
}