package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GcdTest {

    @Test
    @DisplayName("TC01: x(13)->20; y(13)->15(F)")
    void gcdZeroIterations() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("TC02: x(13)->16; x(17)->16,20; y(13)->15(T),16,17; y(18)->15(T,F),16,17; tmp(16)->18")
    void gcdTwoIterations() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }
}
