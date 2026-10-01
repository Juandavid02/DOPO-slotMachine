import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas compartida entre los grupos
 * Ciclo 2 del proyecto SlotMachine.
 *
 * @author Juan David Rojas Heredia
 * @version 1.0 (05-09-2026)
 */
public class SlotMachineC4Test
{
   private SlotMachine machine;
    /**
     * Constructor por defecto de la clase de pruebas SlotMachineC2Test.
     * No realiza ninguna inicialización.
     */
    public SlotMachineC4Test()
    {
    }

    /**
     * Prepara el escenario de cada prueba.
     * Se ejecuta antes de cada método de prueba, creando una nueva instancia
     * de SlotMachine para garantizar que cada prueba comience con un
     * estado limpio e independiente.
     */
    @BeforeEach
    public void setUp()
    {
        machine = new SlotMachine();
        machine.makeInvisible();
    }
    
    //Pruebas de la rueda rebel
    @Test 
    public void shouldNotLockRebelWheel(){
        machine.addWheel(1, "rebel");
        machine.lock(1);
        assertFalse(machine.ok());
        assertEquals(machine.lastMessage(), "Accion no permitida: la rueda no se deja bloquear.");
    }
    
    @Test
    public void shouldNotSwapRebelWheel(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1, "rebel");
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.swap(1,2);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }
    
   @Test
    public void shouldNotDeleteRebelWheel(){
        machine.addWheel(1, "rebel");
        machine.delWheel(1);
        assertFalse(machine.ok());
        assertEquals(machine.lastMessage(), "Accion no permitida: La rueda no se deja eliminar");
        assertEquals(1, machine.configuration().length);
    }
    
    //Pruebas de la rueda lefty
    
    @Test
    public void shouldCopyLeftWheelOnSpin(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1, "normal");
        machine.addWheel(2, "lefty");
           for (int i = 0; i < 20; i++){
            machine.spin();
            String[] config = machine.configuration();
            assertEquals(config[0], config[1]);
        }
    }
    
    @Test
    public void shouldSpinLeftyWithoutLeftNeighbor(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1, "lefty");
        String primero = machine.configuration()[0];
        boolean cambio = false;
        for (int i = 0; i < 50; i++){
            machine.spin();
            if (!machine.configuration()[0].equals(primero)){
                cambio = true;
            }
        }
        assertTrue(cambio);
    }

    @Test
    public void shouldNotCopyLeftWheelWhenLocked(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1, "normal");
        machine.addWheel(2, "lefty");
        machine.placeSymbol(2, "red");
        machine.lock(2);
        assertTrue(machine.ok());
        for (int i = 0; i < 20; i++){
            machine.spin();
            assertEquals("red", machine.configuration()[1]);
        }
    }
    /**
     * Libera el escenario de pruebas.
     * Se ejecuta después de cada método de prueba, eliminando la
     * referencia a la instancia de SlotMachine utilizada.
     */
    @AfterEach
    public void tearDown()
    {
        machine = null;
    }
}