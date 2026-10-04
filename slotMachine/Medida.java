/**
 * Medidas fijas de la maquina, las ruedas y los simbolos.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (octubre 2026)
 */
public enum Medida
{
    MARGEN_X(60),
    MARGEN_Y(30),
    SEPARACION(25),
    ANCHO_RUEDA(70),
    TAMANO_SIMBOLO(30),
    TAMANO_MINIMO(2),
    PASO_ENCOGER(5);

    private final int valor;

    /**
     * Crea una medida con su valor.
     *
     * @param valor el valor de la medida
     */
    Medida(int valor){
        this.valor = valor;
    }

    /**
     * Devuelve el valor de la medida.
     *
     * @return el valor de la medida
     */
    public int getValor(){
        return valor;
    }
}