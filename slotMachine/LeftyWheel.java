/**
 * La rueda lefty: cuando gira, copia el simbolo de la rueda que tiene
 * a su izquierda. Si no tiene ninguna, gira como una rueda normal.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (septiembre 2026)
 */
public class LeftyWheel extends Wheel
{
    /**
     * Crea una rueda lefty.
     *
     * @param cantSymbols cuantos simbolos hay en la maquina
     */
    public LeftyWheel(int cantSymbols)
    {
        super(cantSymbols);
    }
    
    /**
     * Dice que tipo de rueda es.
     *
     * @return el texto "lefty"
     */    
    @Override
    public String getTipo(){
        return "lefty";
    }
    
    /**
     * Gira la rueda. Si tiene una rueda a la izquierda, copia su simbolo.
     * Si no la tiene (es la primera), se queda con el indice aleatorio.
     *
     * @param indiceAleatorio el indice sorteado por la maquina
     * @param izquierda la rueda que esta a su izquierda, o null si no hay
     */
    @Override
    public void girar(int indiceAleatorio, Wheel izquierda){
        if (izquierda != null){
            setVisibleIndex(izquierda.getVisibleIndex());
        }
        else{
            setVisibleIndex(indiceAleatorio);
        }
    }
}