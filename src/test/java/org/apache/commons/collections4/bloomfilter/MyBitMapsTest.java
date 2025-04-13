package org.apache.commons.collections4.bloomfilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Run with `mvn test -Dtest="MyBitMapsTest" -Drat.skip=true` flag.
// The Drat skip skips an irrelevant thing that otherwise prevents the project from building
public class MyBitMapsTest {

    private long[] testBitMap;

    @BeforeEach
    public void setup() {
        // Choose a multiple of sixty-four for cleanliness when testing out-of-bounds access (prevents overallocation)
        testBitMap = BitMaps.newBitMap(128);
        // And a simple test pattern
        for (int i = 0; i < 128; i += 2) {
            BitMaps.set(testBitMap, i);
        }
    }
    
    // Technique: Parameterized Test with @CsvSource AND Boundry Value Testing (Robust)
    @DisplayName("getLongBit returns expected value, or throws if out of bounds")
    @ParameterizedTest
    @CsvSource({
        "0, false, true", // min
        "1, false, false", // min + 1
        "-1, true, false", // min - 1
        "5, false, false", // typical value
        "127, false, false", // max
        "128, true, false", // max + 1
        "126, false, true" // max - 1
    })
    public void testGetLongBit(int pos, boolean isOob, boolean isSet) {
        if (isOob) {
            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> BitMaps.contains(testBitMap, pos));
        } else {
            Assertions.assertEquals(isSet, BitMaps.contains(testBitMap, pos));
        }
    }

}
