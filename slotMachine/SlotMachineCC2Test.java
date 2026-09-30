import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas compartida entre los grupos
 * Ciclo 2 del proyecto SlotMachine.
 *
 * @author Juan David Rojas Heredia y César Morales Sandoval
 * @version 1.0 (05-09-2026)
 */
public class SlotMachineCC2Test
{
    private SlotMachine machine;

    /**
     * Constructor por defecto de la clase de pruebas SlotMachineCC2Test.
     * No realiza ninguna inicialización.
     */
    public SlotMachineCC2Test()
    {}

    /**
     * Prepara el escenario de cada prueba. Se ejecuta antes de cada
     * método de prueba, creando una nueva instancia de SlotMachine en
     * modo invisible, para garantizar que cada prueba comience con un
     * estado limpio e independiente y sin abrir ningún elemento gráfico.
     */
    @BeforeEach
    public void setUp()
    {
        machine = new SlotMachine();
        machine.makeInvisible();
    }

    // Grupo: GarcíaJ-QuezadaK
    /**
     * spin(wheel, 0) no debería mover la rueda — girar cero pasos es una
     * operación válida que no cambia nada, no un error.
     */
    @Test
    public void shouldSucceedWithoutChangingConfigurationWhenSpinningZeroSteps() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        String before = machine.configuration()[0];
        machine.spin(1, 0);
        assertTrue(machine.ok());
        assertEquals(before, machine.configuration()[0]);
    }

    // Grupo: MeloR-SanabriaE
    /**
     * Debería: una rueda fijada no debe moverse ante un intento de
     * giro, y debe volver a poder girar después de un unlock.
     */
    @Test
    public void shouldKeepLockedWheelFixedAndAllowSpinAfterUnlock() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.placeSymbol(1, "red"); // estado conocido
        machine.lock(1);
        machine.spin(1, 2); // intenta girar la rueda fija
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]); // no debió moverse
        machine.unlock(1);
        machine.spin(1, 1); // ahora sí debe poder girar
        assertTrue(machine.ok());
    }
}