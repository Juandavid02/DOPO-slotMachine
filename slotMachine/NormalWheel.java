/**
 * La rueda normal: se comporta como una rueda comun, sin reglas especiales y era la que se tenia.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (septiembre 2026)
 */
public class NormalWheel extends Wheel
{
    /**
     * Crea una rueda normal.
     *
     * @param cantSymbols cuantos simbolos hay en la maquina
     */
    public NormalWheel(int cantSymbols)
    {
        super(cantSymbols);
    }

    /**
     * Dice que tipo de rueda es.
     *
     * @return el texto "normal"
     */
    @Override
    public String getTipo(){
        return "normal";
    }

    /**
     * Dice de que color se dibuja el cuerpo de la rueda normal.
     *
     * @return el texto "magenta"
     */
    @Override
    public String getColorCuerpo(){
        return "magenta";
    }
}