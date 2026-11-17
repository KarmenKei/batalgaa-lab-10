package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BonusAllDefsTest {

    @Test
    @DisplayName("AD01: parameter definitions and input validation")
    void invalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(null, 0));

        assertThrows(IllegalArgumentException.class,
                () -> DataFlowTargets.bonus(new int[]{1}, -1));
    }

    @Test
    @DisplayName("AD02: positive and absent attendance; present, streak, best and score definitions")
    void mixedAttendance() {
        assertEquals(5, DataFlowTargets.bonus(
                new int[]{1, 1, 0, 1, 1}, 3));
    }

    @Test
    @DisplayName("AD03: five consecutive days; best updated and bonus added")
    void fiveConsecutiveDays() {
        assertEquals(8, DataFlowTargets.bonus(
                new int[]{1, 1, 1, 1, 1}, 5));
    }

    @Test
    @DisplayName("AD04: empty attendance; initial definitions reach final conditions")
    void emptyAttendance() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{}, 0));
    }

    @Test
    @DisplayName("AD05: present below threshold; score remains zero")
    void attendanceBelowThreshold() {
        assertEquals(0, DataFlowTargets.bonus(
                new int[]{1, 0}, 2));
    }
}
