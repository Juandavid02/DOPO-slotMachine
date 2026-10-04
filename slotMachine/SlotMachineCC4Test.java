import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas compartidas del ciclo 4 de SlotMachine. Solo usan los tipos
 * que pide el enunciado: ruedas normal, lefty y rebel, y simbolos
 * normal, ephemeral y shy.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (octubre 2026)
 */
public class SlotMachineCC4Test
{
    private SlotMachine slotMachine;

    /**
     * Constructor por defecto de la clase de pruebas.
     */
    public SlotMachineCC4Test()
    {
    }

    /**
     * Crea una maquina nueva e invisible antes de cada prueba.
     * Se deja invisible para que no se abran ventanas ni mensajes
     * mientras corren las pruebas.
     */
    @BeforeEach
    public void setUp()
    {
        slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
    }

    /**
     * Verifica que se puedan agregar simbolos de los tres tipos.
     */
    @Test
    public void shouldAddSymbolsOfEveryType(){
        slotMachine.addSymbol("normal", 1, "red");
        slotMachine.addSymbol("ephemeral", 2, "blue");
        slotMachine.addSymbol("shy", 3, "green");
        assertTrue(slotMachine.ok());
        assertEquals(3, slotMachine.symbols().length);
    }

    /**
     * Verifica que la rueda rebel no se deje bloquear, intercambiar
     * ni eliminar, y que la maquina quede igual despues de intentarlo.
     */
    @Test
    public void shouldRebelWheelRefuseLockSwapAndDelete(){
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addWheel("rebel", 1);
        slotMachine.addWheel(2);
        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "blue");
        slotMachine.lock(1);
        assertFalse(slotMachine.ok());
        slotMachine.swap(1, 2);
        assertFalse(slotMachine.ok());
        slotMachine.delWheel(1);
        assertFalse(slotMachine.ok());
        assertEquals(2, slotMachine.configuration().length);
        assertEquals("red", slotMachine.configuration()[0]);
        assertEquals("blue", slotMachine.configuration()[1]);
    }

    /**
     * Verifica que la maquina funcione con los tres tipos de rueda y los
     * tres tipos de simbolo a la vez: todos los giros son exitosos y la
     * lefty copia siempre a la rueda de su izquierda.
     */
    @Test
    public void shouldSpinMachineWithEveryWheelAndSymbolType(){
        slotMachine.addSymbol("normal", 1, "red");
        slotMachine.addSymbol("ephemeral", 2, "blue");
        slotMachine.addSymbol("shy", 3, "green");
        slotMachine.addWheel("normal", 1);
        slotMachine.addWheel("lefty", 2);
        slotMachine.addWheel("rebel", 3);
        for (int i = 0; i < 10; i++){
            slotMachine.spin();
            assertTrue(slotMachine.ok());
            String[] config = slotMachine.configuration();
            assertEquals(config[0], config[1]);
        }
    }

    /**
     * Libera el escenario de pruebas.
     */
    @AfterEach
    public void tearDown()
    {
        slotMachine = null;
    }
}