package org.apache.commons.collections4.comparators;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Run with `mvn test -Dtest="MyBooleanComparatorTest" -Drat.skip=true` flag.
// The Drat skip skips an irrelevant thing that otherwise prevents the project from building
public class MyBooleanComparatorTest {
    
    // Technique: Paramaterized Test with @CsvSource
    @Test
    @DisplayName("Test BooleanComparator compare")
    @ParameterizedTest
    @CsvSource({
        "true, true, false, -1",
        "false, true, false, 1",
        "true, false, true, 1",
        "false, false, true, -1",
        "true, true, true, 0",
        "true, false, false, 0",
        "false, true, true, 0",
        "false, false, false, 0",
    })
    public void testBooleanComparatorCompare(boolean trueFirst, boolean iA, boolean iB, int expected) {
        int result = new BooleanComparator(trueFirst).compare(iA, iB);
        Assertions.assertEquals(expected, result);
    }

}
