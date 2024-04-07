package entities;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.example.entities.DPState;

public class DPStateTests {

    @Test
    void testObjectInitialization() {
        int count = 5;
        int numCarriers = 3;
        int score = 10;
        DPState dpState = new DPState(count, numCarriers, score);

        assertEquals(count, dpState.getCount());
        assertEquals(score, dpState.getScore());
        assertNotNull(dpState.getUsedCarriers());
        assertEquals(numCarriers, dpState.getUsedCarriers().length);

        for (boolean used : dpState.getUsedCarriers()) {
            assertFalse(used);
        }
    }

}
