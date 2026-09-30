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
}