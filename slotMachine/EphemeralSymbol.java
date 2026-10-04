import java.util.Map;
import java.util.HashMap;
/**
 * El simbolo ephemeral: cada vez que lo seleccionan se hace mas
 * pequeno, hasta quedar como un punto.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (octubre 2026)
 */
public class EphemeralSymbol extends Symbol
{
    private Map<Wheel, Integer> tamanos;

    /**
     * Crea un simbolo ephemeral con el color dado.
     * Empieza con el tamano normal.
     *
     * @param color el color del simbolo
     */
    public EphemeralSymbol(String color){
        super(color);
        tamanos = new HashMap<Wheel, Integer>();
    }

    /**
     * Cada vez que una rueda lo selecciona, en esa rueda se encoge un poco,
     * pero nunca por debajo del tamano de un punto.
     *
     * @param rueda la rueda que lo selecciono
     */
    @Override
    public void seleccionado(Wheel rueda){
        int nuevo = getTamano(rueda) - Medida.PASO_ENCOGER.getValor();
        tamanos.put(rueda, Math.max(Medida.TAMANO_MINIMO.getValor(), nuevo));
    }
    
    /**
     * Devuelve el tamano actual del simbolo en la rueda dada.
     *
     * @param rueda la rueda que muestra el simbolo
     * @return el tamano actual en esa rueda
     */
    @Override
    public int getTamano(Wheel rueda){
        Integer tamano = tamanos.get(rueda);
        if (tamano == null){
            return Medida.TAMANO_SIMBOLO.getValor();
        }
        return tamano;
    }
    
    public String getTipo(){
        return "ephemeral";
    }
    
    /**
     * El simbolo ephemeral se dibuja como un cuadrado.
     *
     * @return el texto "cuadrado"
     */
    @Override
    public String getForma(){
        return "cuadrado";
    }
}