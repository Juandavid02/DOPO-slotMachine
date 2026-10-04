/**
 * La rueda lazy: es perezosa, solo se mueve una de cada dos veces que gira.
 * En el primer giro se mueve, en el segundo se queda quieta, y asi
 * sucesivamente.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (octubre 2026)
 */
public class LazyWheel extends Wheel
{
    private boolean gira;   // true si en el proximo giro si se mueve

    /**
     * Crea una rueda lazy. El primer giro si la mueve.
     *
     * @param cantSymbols cuantos simbolos hay en la maquina
     */
    public LazyWheel(int cantSymbols)
    {
        super(cantSymbols);
        gira = true;
    }

    /**
     * Dice que tipo de rueda es.
     *
     * @return el texto "lazy"
     */
    @Override
    public String getTipo(){
        return "lazy";
    }

    /**
     * Gira la rueda solo una de cada dos veces. Cuando le toca, se queda
     * con el indice que le dan; cuando no, se queda como estaba.
     *
     * @param indiceAleatorio el indice sorteado por la maquina
     * @param izquierda la rueda que esta a su izquierda (esta rueda no la usa)
     * @return true si esta vez se movio, false si se quedo quieta
     */
    @Override
    public boolean girar(int indiceAleatorio, Wheel izquierda){
        boolean seMovio = gira;
        if (gira){
            setVisibleIndex(indiceAleatorio);
        }
        gira = !gira;
        return seMovio;
    }
    
    /**
     * Dice de que color se dibuja el cuerpo de la rueda lazy.
     *
     * @return el texto "lightGray"
     */
    @Override
    public String getColorCuerpo(){
        return "lightGray";
    }
}