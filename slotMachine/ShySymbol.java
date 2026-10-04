import java.util.Set;
import java.util.HashSet;
/**
 * El simbolo shy: es timido, cada vez que lo seleccionan alterna
 * entre visible e invisible.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (octubre 2026)
 */
public class ShySymbol extends Symbol
{
    private Set<Wheel> ocultoEn;   // las ruedas en las que el simbolo esta invisible ahora

    /**
     * Crea un simbolo shy con el color dado.
     * Empieza visible.
     *
     * @param color el color del simbolo
     */
    public ShySymbol(String color){
        super(color);
        ocultoEn = new HashSet<Wheel>();
    }

    /**
     * Cada vez que una rueda lo selecciona, en esa rueda cambia de visible
     * a invisible o de invisible a visible. Las otras ruedas no se afectan.
     *
     * @param rueda la rueda que lo selecciono
     */
    @Override
    public void seleccionado(Wheel rueda){
        if (ocultoEn.contains(rueda)){
            ocultoEn.remove(rueda);
        }
        else {
            ocultoEn.add(rueda);
        }
    }  
    
    /**
     * Dice si el simbolo se ve en la rueda dada.
     *
     * @param rueda la rueda que muestra el simbolo
     * @return true si esta visible en esa rueda, false si esta invisible
     */
    @Override
    public boolean esVisible(Wheel rueda){
        return !ocultoEn.contains(rueda);
    }
    
    /**
     * Dice que tipo de simbolo es.
     *
     * @return el texto "shy"
     */
    @Override
    public String getTipo(){
        return "shy";
    }
    
    /**
     * El simbolo shy se dibuja como un triangulo.
     *
     * @return el texto "triangulo"
     */
    @Override
    public String getForma(){
        return "triangulo";
    }
}