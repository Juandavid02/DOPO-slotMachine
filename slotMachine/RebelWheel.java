/**
 * La rueda rebelde: no se deja bloquear, ni intercambiar, ni eliminar.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (septiembre 2026)
 */
public class RebelWheel extends Wheel
{
    /**
     * Crea una rueda rebelde.
     *
     * @param cantSymbols cuantos simbolos hay en la maquina
     */
    public RebelWheel(int cantSymbols)
    {
        super(cantSymbols);
    }
    
    /**
     * Dice que tipo de rueda es.
     *
     * @return el texto "rebel"
     */
    @Override
    public String getTipo(){
        return "rebel";
    }
    
    /**
     * La rueda rebelde nunca se deja intercambiar.
     *
     * @return siempre false
     */
    
    @Override
    public boolean puedeIntercambiarse(){
        return false;
    }
    
    /**
     * La rueda rebelde nunca se deja eliminar.
     *
     * @return siempre false
     */
    @Override
    public boolean puedeEliminarse(){
        return false;
    }
    
    /**
     * No se deja bloquear, asi que no hace nada y locked se queda en false.
     *
     * @param value se ignora
     */
    @Override
    public void setLocked(boolean value){
         // la rueda rebelde no se deja bloquear, asi que no se hace nada. 
         // IA generativa: Para poder saber que si un metodo no hace nada false por el constructor
    }
}