
/**
 * Write a description of class NormalWheel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NormalWheel extends Wheel
{

    /**
     * Constructor for objects of class NormalWheel
     */
    public NormalWheel(int cantSymbols)
    {
        super(cantSymbols);
    }

    public String getTipo(){
        return "normal";
    }
}