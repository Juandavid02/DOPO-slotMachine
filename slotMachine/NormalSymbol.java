/**
 * El simbolo normal: solo tiene un color y no hace nada especial.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (septiembre 2026)
 */
public class NormalSymbol extends Symbol
{
    /**
     * Crea un simbolo normal con el color dado.
     *
     * @param color el color del simbolo
     */
    public NormalSymbol(String color){
        super(color);
    }
    
    public String getTipo(){
        return "normal";
    }
}