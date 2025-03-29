package org.apache.commons.collections4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Run with `mvn test -Dtest="MyArrayUtilsTest" -Drat.skip=true` flag.
// The Drat skip skips an irrelevant thing that otherwise prevents the project from building
public class MyArrayUtilsTest {
    
    // Technique: Equivalence Parititoning

    // My paritions:
    // 1) Array is null
    // 2) Array is non-null, does not contain element
    // 3) Array is non-null, contains element at/after startIndex
    // 4) Array is non-null, contains element before startIndex
    // 5) Array is non-null, contains muliple elements after startIndex
    // "element" specifically refers to the element that is being searched for above
    // Not using boundry-value-testing, might do that in a different test
    
    @Test
    @DisplayName("indexOf returns not found when array is null")
    public void indexOfReturnsNotFoundWhenArrayIsNull() {
        int index = ArrayUtils.indexOf(null, 5, 0);
        Assertions.assertEquals(-1, index);
    }

    @Test
    @DisplayName("indexOf returns not found when array does not contain element")
    public void indexOfReturnsNotFoundWhenArrayDoesNotContainElement() {
        int index = ArrayUtils.indexOf(new Integer[] { 1, 2, 3, 4 }, 5, 0);
        Assertions.assertEquals(-1, index);
    }

    @Test
    @DisplayName("indexOf returns index when array contains element after startIndex")
    public void indexOfReturnsIndexWhenArrayContainsElementAfterStartIndex() {
        int index = ArrayUtils.indexOf(new Integer[] { 1, 2, 3, 4, 5 }, 5, 2);
        Assertions.assertEquals(4, index);
    }

    @Test
    @DisplayName("indexOf returns not found when array contains element before startIndex")
    public void indexOfReturnsNotFoundWhenArrayContainsElementBeforeStartIndex() {
        int index = ArrayUtils.indexOf(new Integer[] { 1, 2, 3, 4, 5 }, 2, 2);
        Assertions.assertEquals(-1, index);
    }

    @Test
    @DisplayName("indexOf returns index when array contains multiple elements after startIndex")
    public void indexOfReturnsIndexWhenArrayContainsMultipleElementsAfterStartIndex() {
        int index = ArrayUtils.indexOf(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 5 }, 5, 2);
        Assertions.assertEquals(4, index);
    }

}
