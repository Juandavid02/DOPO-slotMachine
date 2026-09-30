import static org.junit.Assert.*;
import org.junit.Test;

public class SlotMachineContestTest
{
    @Test
    public void testCantidadDeRuedas()
    {
        for (int n = 2; n <= 8; n++){
            SlotMachine maquina = new SlotMachine(n);
            assertEquals(n, maquina.configuration().length);
        }
    }
    /**
     * Comprueba que solve(n) encuentre el jackpot.
     */
    @Test
    public void testSolveEncuentraJackpot()
    {
        for (int n = 2; n <= 8; n++){
            SlotMachineContest contest = new SlotMachineContest();
            int[][] acciones = contest.solve(n);
            SlotMachine maquina = contest.ultimaMaquina();
            assertNotNull(acciones);
            assertNotNull(maquina);
            assertTrue(maquina.isJackpot());
        }
    }
    
    /**
     * Comprueba que el algoritmo no supere
     * el límite de 10000 acciones.
     */
    @Test
    public void testLimiteDeAcciones()
    {
        for (int n = 2; n <= 8; n++){
            SlotMachineContest contest = new SlotMachineContest();
            int[][] acciones = contest.solve(n);
            assertTrue(acciones.length <= 10000);
        }
    }
}