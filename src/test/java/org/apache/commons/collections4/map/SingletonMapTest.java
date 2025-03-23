/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections4.BoundedMap;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.OrderedMap;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests.
 *
 * @param <K> the key type.
 * @param <V> the value type.
 */
public class SingletonMapTest<K, V> extends AbstractOrderedMapTest<K, V> {

    private static final Integer ONE = Integer.valueOf(1);
    private static final Integer TWO = Integer.valueOf(2);
    private static final String TEN = "10";

    @Override
    public String getCompatibilityVersion() {
        return "4";
    }

    @Override
    @SuppressWarnings("unchecked")
    public V[] getNewSampleValues() {
        return (V[]) new Object[] { TEN };
    }

    @Override
    @SuppressWarnings("unchecked")
    public K[] getSampleKeys() {
        return (K[]) new Object[] { ONE };
    }

    @Override
    @SuppressWarnings("unchecked")
    public V[] getSampleValues() {
        return (V[]) new Object[] { TWO };
    }

    @Override
    public boolean isPutAddSupported() {
        return false;
    }

    @Override
    public boolean isRemoveSupported() {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SingletonMap<K, V> makeFullMap() {
        return new SingletonMap<>((K) ONE, (V) TWO);
    }

    @Override
    public OrderedMap<K, V> makeObject() {
        // need an empty singleton map, but that's not possible
        // use a ridiculous fake instead to make the tests pass
        return UnmodifiableOrderedMap.unmodifiableOrderedMap(ListOrderedMap.listOrderedMap(new HashMap<>()));
    }

    @Test
    public void testBoundedMap() {
        final SingletonMap<K, V> map = makeFullMap();
        assertEquals(1, map.size());
        assertTrue(map.isFull());
        assertEquals(1, map.maxSize());
        assertInstanceOf(BoundedMap.class, map);
    }

    @Test
    public void testClone() {
        final SingletonMap<K, V> map = makeFullMap();
        assertEquals(1, map.size());
        final SingletonMap<K, V> cloned = map.clone();
        assertEquals(1, cloned.size());
        assertTrue(cloned.containsKey(ONE));
        assertTrue(cloned.containsValue(TWO));
    }

    // public BulkTest bulkTestMapIterator() {
    // return new TestFlatMapIterator();
    // }
    //
    // public class TestFlatMapIterator extends AbstractTestOrderedMapIterator {
    // public TestFlatMapIterator() {
    // super("TestFlatMapIterator");
    // }
    //
    // public Object[] addSetValues() {
    // return TestSingletonMap.this.getNewSampleValues();
    // }
    //
    // public boolean supportsRemove() {
    // return TestSingletonMap.this.isRemoveSupported();
    // }
    //
    // public boolean supportsSetValue() {
    // return TestSingletonMap.this.isSetValueSupported();
    // }
    //
    // public MapIterator makeEmptyMapIterator() {
    // resetEmpty();
    // return ((Flat3Map) TestSingletonMap.this.map).mapIterator();
    // }
    //
    // public MapIterator makeFullMapIterator() {
    // resetFull();
    // return ((Flat3Map) TestSingletonMap.this.map).mapIterator();
    // }
    //
    // public Map getMap() {
    // // assumes makeFullMapIterator() called first
    // return TestSingletonMap.this.map;
    // }
    //
    // public Map getConfirmedMap() {
    // // assumes makeFullMapIterator() called first
    // return TestSingletonMap.this.confirmed;
    // }
    //
    // public void verify() {
    // super.verify();
    // TestSingletonMap.this.verify();
    // }
    // }

    @Test
    public void testKeyValue() {
        final SingletonMap<K, V> map = makeFullMap();
        assertEquals(1, map.size());
        assertEquals(ONE, map.getKey());
        assertEquals(TWO, map.getValue());
        assertInstanceOf(KeyValue.class, map);
    }

    // public void testCreate() throws Exception {
    // resetEmpty();
    // writeExternalFormToDisk(
    // (java.io.Serializable) map,
    // "src/test/resources/data/test/SingletonMap.emptyCollection.version4.obj");
    // resetFull();
    // writeExternalFormToDisk(
    // (java.io.Serializable) map,
    // "src/test/resources/data/test/SingletonMap.fullCollection.version4.obj");
    // }

    @Test
    public void singletonMapEqual_IfSingletonAndMapBothSize1WithSameKeyValuePair_ThenTrue() {
        // Arrange
        SingletonMap<String, Integer> singleton = new SingletonMap<String, Integer>("key", 1);
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        // Act
        map.put("key", 1);

        // Assert
        assert (singleton.equals(map));
    }

    @Test
    public void singletonMapEqual_IfMapIsGreaterThanSize1_ThenFalse() {
        // Arrange
        SingletonMap<String, Integer> singleton = new SingletonMap<String, Integer>("key", 1);
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        // Act
        map.put("key", 1);
        map.put(null, null);

        // Assert
        assert (!singleton.equals(map));
    }

    @Test
    public void singletonMapEqual_IfSingletonIsComparedToSelf_ThenTrue() {
        // Arrange
        SingletonMap<String, Integer> singleton = new SingletonMap<String, Integer>("key", 1);

        // Assert
        assert (singleton.equals(singleton));
    }

    @Test
    public void singletonMapEqual_IfBothSingletonButKeyIsDifferent_ThenFalse() {
        // Arrange
        SingletonMap<String, Integer> singleton = new SingletonMap<String, Integer>("key", 1);
        SingletonMap<String, Integer> map = new SingletonMap<String, Integer>("key ", 1);

        // Assert
        assert (!singleton.equals(map));
    }

    @Test
    public void singletonMapEqual_IfBothSingletonButValueIsDifferent_ThenFalse() {
        // Arrange
        SingletonMap<String, Integer> singleton = new SingletonMap<String, Integer>("key", 1);
        SingletonMap<String, Integer> map = new SingletonMap<String, Integer>("key", 11);

        // Assert
        assert (!singleton.equals(map));
    }

    @Test
    public void singletonMapEqual_IfObjectToCompareIsNotMap_ThenFalse() {
        // Arrange
        SingletonMap<String, Integer> singleton = new SingletonMap<String, Integer>();
        ArrayList<String> obj = new ArrayList<String>();
        // Act
        obj.add(null);

        // Assert
        assert (!singleton.equals(obj));
    }

    @Test
    public void singletonMapCreation_IfNoParametersArePassed_ThenKeyAndValueAreNull() {
        // Arrange
        SingletonMap<String, Integer> singleton = new SingletonMap<String, Integer>();

        // Assert
        assert (singleton.getKey() == null && singleton.getValue() == null);
    }

    @Test
    public void singletonMapCreation_IfKeyIsGivenAndThenValueChanged_Valid() {
        // Arrange
        SingletonMap<String, Integer> singleton = new SingletonMap<String, Integer>("key", null);

        // Act
        singleton.put("key", 1);

        // Assert
        assert (singleton.get("key") == 1);
    }

    @Test
    public void singletonMapCreation_IfNewKeyValuePassed_ThrowException() {
        // Arrange
        SingletonMap<String, Integer> singleton = new SingletonMap<String, Integer>();

        // Assert
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            singleton.put("key", 1);
        });
    }

    @Test
    public void singletonMapCreation_IfNewKeyPassed_ThrowException() {
        // Arrange
        SingletonMap<String, Integer> singleton = new SingletonMap<String, Integer>("key", 1);

        // Assert
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            singleton.put("new key", null);
        });
    }

}
