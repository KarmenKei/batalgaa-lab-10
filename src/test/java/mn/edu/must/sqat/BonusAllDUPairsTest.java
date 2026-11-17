package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BonusAllDUPairsTest {

    @Test
    @DisplayName("DU01: line 32 true and false; parameter uses")
    void inputValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(null, 0));

        assertThrows(IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(new int[]{1}, -1));
    }

    @Test
    @DisplayName("DU02: initial definitions; empty loop; score 49 to 56")
    void emptyAttendance() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{}, 1));
    }

    @Test
    @DisplayName("DU03: positive and absent days; streak reset and restart")
    void streakResetAndRestart() {
        assertEquals(0, DataFlowTargets.bonus(
                new int[]{1, 0, 1, 1}, 5));
    }

    @Test
    @DisplayName("DU04: score 51 to 56; threshold true and best false")
    void thresholdReachedWithoutFiveDayStreak() {
        assertEquals(5, DataFlowTargets.bonus(
                new int[]{1, 1}, 2));
    }

    @Test
    @DisplayName("DU05: score 49 to 54; best true while threshold false")
    void fiveDayStreakBelowThreshold() {
        assertEquals(3, DataFlowTargets.bonus(
                new int[]{1, 1, 1, 1, 1}, 6));
    }

    @Test
    @DisplayName("DU06: score 51 to 54; both bonuses apply")
    void bothBonusesApply() {
        assertEquals(8, DataFlowTargets.bonus(
                new int[]{1, 1, 1, 1, 1}, 5));
    }
}
