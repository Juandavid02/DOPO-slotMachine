/**
 * El simbolo shy: es timido, cada vez que lo seleccionan alterna
 * entre visible e invisible.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (octubre 2026)
 */
public class ShySymbol extends Symbol{
    private boolean visible;
    /**
     * Crea un simbolo shy con el color dado.
     * Empieza visible.
     *
     * @param color el color del simbolo
     */
    public ShySymbol(String color){
        super(color);
        visible = true;
    }
    
     /**
     * Cada vez que lo seleccionan cambia de visible a invisible,
     * o de invisible a visible.
     */
    @Override
    public void seleccionado(){
        visible = !visible;
    }  
    
     /**
     * Dice si el simbolo se ve en este momento.
     *
     * @return true si esta visible, false si esta invisible
     */
    @Override
    public boolean esVisible(){
        return visible;
    }
    
    /**
     * Dice que tipo de simbolo es.
     *
     * @return el texto "shy"
     */
    public String getTipo(){
        return "shy";
    }
}