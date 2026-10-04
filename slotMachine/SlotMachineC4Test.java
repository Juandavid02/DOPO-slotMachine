import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
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
    
    // Pruebas las redas
     /**
     * Verifica que se puedan agregar los cuatro tipos de rueda.
     */
    @Test
    public void shouldAddWheelOfEachType(){
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addWheel("rebel", 3);
        machine.addWheel("lazy", 4);
        assertTrue(machine.ok());
        assertEquals(4, machine.configuration().length);
    }
    
    /**
     * Verifica que un tipo null no lance excepcion y no agregue la rueda.
     */
    @Test
    public void shouldNotAddWheelWithNullType(){
        machine.addWheel(null, 1);
        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);
    }
    
    
    /**
     * Verifica el nombre del tipo de cada rueda.
     */
    @Test
    public void shouldReturnTypeOfEachWheel(){
        assertEquals("normal", new NormalWheel(2).getTipo());
        assertEquals("lefty", new LeftyWheel(2).getTipo());
        assertEquals("rebel", new RebelWheel(2).getTipo());
        assertEquals("lazy", new LazyWheel(2).getTipo());
    }
    
    /**
     * Verifica que cada tipo de rueda tenga un color de cuerpo distinto,
     * para que se distingan visualmente.
     */
    @Test
    public void shouldHaveDifferentBodyColorForEachWheelType(){
        Set<String> colores = new HashSet<String>();
        colores.add(new NormalWheel(2).getColorCuerpo());
        colores.add(new LeftyWheel(2).getColorCuerpo());
        colores.add(new RebelWheel(2).getColorCuerpo());
        colores.add(new LazyWheel(2).getColorCuerpo());
        assertEquals(4, colores.size());
    }
    
    //Pruebas de la rueda rebel
    
    /**
     * Verifica que la rueda rebel no se deje bloquear.
     */
    @Test
    public void shouldNotLockRebelWheel(){
        machine.addWheel("rebel", 1);
        machine.lock(1);
        assertFalse(machine.ok());
        assertEquals("Accion no permitida: la rueda no se deja bloquear.", machine.lastMessage());
    }

    /**
     * Verifica que la rueda rebel no se deje intercambiar y que las
     * dos ruedas conserven su simbolo.
     */
    @Test
    public void shouldNotSwapRebelWheel(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel("rebel", 1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.swap(1, 2);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }

    /**
     * Verifica que la rueda rebel no se deje eliminar.
     */
    @Test
    public void shouldNotDeleteRebelWheel(){
        machine.addWheel("rebel", 1);
        machine.delWheel(1);
        assertFalse(machine.ok());
        assertEquals("Accion no permitida: La rueda no se deja eliminar", machine.lastMessage());
        assertEquals(1, machine.configuration().length);
    }
    
    //Pruebas de la rueda lefty
    
    /**
     * Verifica que la lefty copie el simbolo de la rueda de su izquierda
     * al girar todas las ruedas.
     */
    @Test
    public void shouldCopyLeftWheelOnSpin(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
           for (int i = 0; i < 20; i++){
            machine.spin();
            String[] config = machine.configuration();
            assertEquals(config[0], config[1]);
        }
    }
    
    /**
     * Verifica que una lefty sin rueda a la izquierda gire como una normal.
     */
    @Test
    public void shouldSpinLeftyWithoutLeftNeighbor(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel("lefty", 1);
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
    
    /**
     * Verifica que la lefty copie el simbolo actual de su izquierda
     * cuando se gira solo ella.
     */
    @Test
    public void shouldCopyCurrentLeftSymbolWhenSpinningOnlyLefty(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "red");
        machine.spin(2);
        assertEquals("red", machine.configuration()[1]);
    }
    
    /**
     * Verifica que una lefty bloqueada no copie a su izquierda.
     */
    @Test
    public void shouldNotCopyLeftWheelWhenLocked(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(2, "red");
        machine.lock(2);
        assertTrue(machine.ok());
        for (int i = 0; i < 20; i++){
            machine.spin();
            assertEquals("red", machine.configuration()[1]);
        }
    }
    
    //Pruebas de lazy
    /**
     * Verifica que la lazy no se mueva en el segundo giro.
     */
    @Test
    public void shouldNotMoveLazyWheelOnSecondSpin(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel("lazy", 1);
        machine.spin();
        String despuesDelPrimero = machine.configuration()[0];
        machine.spin();
        assertEquals(despuesDelPrimero, machine.configuration()[0]);
    }
    /**
     * Verifica que la lazy se mueva una de cada dos veces y que
     * girar avise si se movio o no.
     */
    @Test
    public void shouldLazyWheelMoveEveryOtherTime(){
        Wheel lazy = new LazyWheel(3);
        assertTrue(lazy.girar(1, null));
        assertEquals(1, lazy.getVisibleIndex());
        assertFalse(lazy.girar(2, null));
        assertEquals(1, lazy.getVisibleIndex());
        assertTrue(lazy.girar(2, null));
        assertEquals(2, lazy.getVisibleIndex());
    }    
    /**
     * Verifica que la lazy si se mueva en el primer giro.
     */
    @Test
    public void shouldMoveLazyWheelOnFirstSpin(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel("lazy", 1);
        boolean cambio = false;
        for (int i = 0; i < 50; i++){
            String antes = machine.configuration()[0];
            machine.spin();
            if (!machine.configuration()[0].equals(antes)){
                cambio = true;
            }
            machine.spin();
        }
        assertTrue(cambio);
    }
    
    /**
     * Verifica que spin(wheel, steps) rote la lazy siempre, porque
     * la pereza solo aplica a los giros aleatorios.
     */
    @Test
    public void shouldRotateLazyWheelWithSteps(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel("lazy", 1);
        machine.placeSymbol(1, "red");
        machine.spin(1, 1);
        assertEquals("blue", machine.configuration()[0]);
        machine.spin(1, 1);
        assertEquals("red", machine.configuration()[0]);
    }
    
    // Pruebas de los simbolos

    /**
     * Verifica que se puedan agregar los tres tipos de simbolo.
     */
    @Test
    public void shouldAddSymbolOfEachType(){
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("ephemeral", 2, "blue");
        machine.addSymbol("shy", 3, "green");
        assertTrue(machine.ok());
        assertEquals(3, machine.symbols().length);
    }
    
    /**
     * Verifica que un tipo de simbolo null no lance excepcion y no
     * agregue el simbolo.
     */
    @Test
    public void shouldNotAddSymbolWithNullType(){
        machine.addSymbol(null, 1, "red");
        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }
    
    /**
     * Verifica que un color null no lance excepcion y no agregue el simbolo.
     */
    @Test
    public void shouldNotAddSymbolWithNullColor(){
        machine.addSymbol(1, null);
        assertFalse(machine.ok());
        assertEquals("Accion no permitida: El color no esta disponible.", machine.lastMessage());
        assertEquals(0, machine.symbols().length);
    }
    
    /**
     * Verifica que una rueda creada antes de tener simbolos muestre el
     * primer simbolo que se agregue.
     */
    @Test
    public void shouldShowFirstSymbolWhenWheelWasCreatedFirst(){
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        assertEquals("red", machine.configuration()[0]);
    }
    
     /**
     * Verifica el tipo y la forma de cada simbolo, y que las formas
     * sean distintas entre si para que se distingan visualmente.
     */
    @Test
    public void shouldHaveDifferentShapeForEachSymbolType(){
        Symbol normal = new NormalSymbol("red");
        Symbol ephemeral = new EphemeralSymbol("red");
        Symbol shy = new ShySymbol("red");
        assertEquals("normal", normal.getTipo());
        assertEquals("ephemeral", ephemeral.getTipo());
        assertEquals("shy", shy.getTipo());
        assertEquals("circulo", normal.getForma());
        assertEquals("cuadrado", ephemeral.getForma());
        assertEquals("triangulo", shy.getForma());
    }
    
    /**
     * Verifica que cada simbolo guarde el color con el que se creo.
     */
    @Test
    public void shouldKeepColorOfEachSymbol(){
        assertEquals("red", new NormalSymbol("red").getColor());
        assertEquals("blue", new EphemeralSymbol("blue").getColor());
        assertEquals("green", new ShySymbol("green").getColor());
    }
    
    /**
     * Verifica que al agregar un simbolo antes de otros, las ruedas
     * sigan mostrando el mismo simbolo que tenian.
     */
    @Test
    public void shouldKeepWheelSymbolWhenAddingSymbolBefore(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.placeSymbol(1, "blue");
        machine.addSymbol(1, "green");
        assertEquals("blue", machine.configuration()[0]);
    }
    
    // Pruebas del simbolo normal

    /**
     * Verifica que el simbolo normal no cambie al ser seleccionado.
     */
    @Test
    public void shouldNormalSymbolIgnoreSelection(){
        Wheel rueda = new NormalWheel(2);
        Symbol normal = new NormalSymbol("red");
        normal.seleccionado(rueda);
        normal.seleccionado(rueda);
        assertEquals(30, normal.getTamano(rueda));
        assertTrue(normal.esVisible(rueda));
    }
     // Pruebas del simbolo ephemeral

    /**
     * Verifica que el ephemeral empiece con el tamano normal.
     */
    @Test
    public void shouldEphemeralStartWithNormalSize(){
        Wheel rueda = new NormalWheel(2);
        assertEquals(30, new EphemeralSymbol("red").getTamano(rueda));
    }

    /**
     * Verifica que el ephemeral se encoja cada vez que lo seleccionan.
     */
    @Test
    public void shouldEphemeralShrinkWhenSelected(){
        Wheel rueda = new NormalWheel(2);
        Symbol ephemeral = new EphemeralSymbol("red");
        ephemeral.seleccionado(rueda);
        assertEquals(25, ephemeral.getTamano(rueda));
        ephemeral.seleccionado(rueda);
        assertEquals(20, ephemeral.getTamano(rueda));
    }
    
    /**
     * Verifica que el ephemeral se detenga en el tamano de un punto
     * y no siga bajando.
     */
    @Test
    public void shouldEphemeralStopAtMinimumSize(){
        Wheel rueda = new NormalWheel(2);
        Symbol ephemeral = new EphemeralSymbol("red");
        for (int i = 0; i < 20; i++){
            ephemeral.seleccionado(rueda);
        }
        assertEquals(2, ephemeral.getTamano(rueda));
        ephemeral.seleccionado(rueda);
        assertEquals(2, ephemeral.getTamano(rueda));
    }
    
     /**
     * Verifica que el shy alterne entre visible e invisible cada vez
     * que lo seleccionan.
     */
    @Test
    public void shouldShyToggleWhenSelected(){
        Wheel rueda = new NormalWheel(2);
        Symbol shy = new ShySymbol("red");
        shy.seleccionado(rueda);
        assertFalse(shy.esVisible(rueda));
        shy.seleccionado(rueda);
        assertTrue(shy.esVisible(rueda));
    }
    
    /**
     * Verifica que el shy trabaje por separado en cada rueda.
     */
    @Test
    public void shouldShyToggleIndependentlyPerWheel(){
        Wheel rueda1 = new NormalWheel(2);
        Wheel rueda2 = new NormalWheel(2);
        Symbol shy = new ShySymbol("red");
        shy.seleccionado(rueda1);
        assertFalse(shy.esVisible(rueda1));
        assertTrue(shy.esVisible(rueda2));
    }  
    
    /**
     * Verifica que el ephemeral se encoja por separado en cada rueda.
     */
    @Test
    public void shouldEphemeralShrinkIndependentlyPerWheel(){
        Wheel rueda1 = new NormalWheel(2);
        Wheel rueda2 = new NormalWheel(2);
        Symbol ephemeral = new EphemeralSymbol("red");
        ephemeral.seleccionado(rueda1);
        assertEquals(25, ephemeral.getTamano(rueda1));
        assertEquals(30, ephemeral.getTamano(rueda2));
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