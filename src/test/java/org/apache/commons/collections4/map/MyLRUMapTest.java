package org.apache.commons.collections4.map;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Run with `mvn test -Dtest="MyLRUMapTest" -Drat.skip=true` flag.
// The Drat skip skips an irrelevant thing that otherwise prevents the project from building
public class MyLRUMapTest {
    
    // Technique: Equivalence Parititoning

    // My Partitions:
    // 1. Map is filled at/under capacity
    // 2. Map is filled over capacity, desired element new
    // 3. Map is filled over capacity, desired element not recently used
    // 4. Map is filled over capacity, desired element old recently used
    // 5. Map is not filled

    @Test
    @DisplayName("Can get element when map is filled at/under capacity")
    public void canGetElementWhenMapIsFilledAtUnderCapacity() {
        Map<String, Integer> lruMap = new LRUMap<>(3);
        lruMap.put("a", 1);
        lruMap.put("b", 2);
        Assertions.assertEquals(1, lruMap.get("a"));
    }

    @Test
    @DisplayName("Can get element when map is over capacity and desired element is new")
    public void canGetElementWhenMapIsFilledOverCapacityAndDesiredElementIsNew() {
        Map<String, Integer> lruMap = new LRUMap<>(3);
        lruMap.put("a", 1);
        lruMap.put("b", 2);
        lruMap.put("c", 3);
        lruMap.put("d", 4);
        Assertions.assertEquals(4, lruMap.get("d"));
    }

    @Test
    @DisplayName("Can not get element when map is over capacity and desired element is not recently used")
    public void canGetElementWhenMapIsFilledOverCapacityAndDesiredElementIsNotRecentlyUsed() {
        Map<String, Integer> lruMap = new LRUMap<>(3);
        lruMap.put("a", 1);
        lruMap.put("b", 2);
        lruMap.put("c", 3);
        lruMap.put("d", 4);
        Assertions.assertNull(lruMap.get("a"));
    }

    @Test
    @DisplayName("Can get element when map is over capacity and desired element is old but recently used")
    public void canGetElementWhenMapIsFilledOverCapacityAndDesiredElementIsOldButRecentlyUsed() {
        Map<String, Integer> lruMap = new LRUMap<>(3);
        lruMap.put("a", 1);
        lruMap.put("b", 2);
        lruMap.put("c", 3);
        lruMap.get("a");
        lruMap.put("d", 4);
        Assertions.assertEquals(1, lruMap.get("a"));
        // But "b" is evicted still
        Assertions.assertNull(lruMap.get("b"));
    }

    @Test
    @DisplayName("Can not get element when map is not filled")
    public void canGetElementWhenMapIsNotFilled() {
        Map<String, Integer> lruMap = new LRUMap<>(3);
        Assertions.assertNull(lruMap.get("a"));
    }

}
