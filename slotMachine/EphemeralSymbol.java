/**
 * El simbolo ephemeral: cada vez que lo seleccionan se hace mas
 * pequeno, hasta quedar como un punto.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (octubre 2026)
 */
public class EphemeralSymbol extends Symbol
{
    private int tamano;
    private static final int MINIMO = 2;
    private static final int PASO = 5;
    /**
     * Crea un simbolo ephemeral con el color dado.
     * Empieza con el tamano normal.
     *
     * @param color el color del simbolo
     */
    public EphemeralSymbol(String color){
        super(color);
        tamano = TAMANO_NORMAL;
    }
    
     /**
     * Cada vez que lo seleccionan se encoge un poco, pero nunca
     * por debajo del tamano de un punto.
     */
    @Override
    public void seleccionado(){
        tamano = Math.max(MINIMO, tamano - PASO);
    }
    
    /**
     * Devuelve el tamano actual del simbolo.
     *
     * @return el tamano actual
     */
    @Override
    public int getTamano(){
        return tamano;
    }
    
    public String getTipo(){
        return "ephemeral";
    }
}