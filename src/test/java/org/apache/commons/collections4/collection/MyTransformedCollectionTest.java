package org.apache.commons.collections4.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

// Run with `mvn test -Dtest="MyTransformedCollectionTest" -Drat.skip=true` flag.
// The Drat skip skips an irrelevant thing that otherwise prevents the project from building
// I had to manually import Mockito because this project uses EasyMock by default
public class MyTransformedCollectionTest {
    
    // Technique: Mocked object created with @Mock annotation
    // Obviously, there's no way you'd be mocking a list in a real application, but this is to demonstrate
    @Mock
    private List<Integer> myList;

    @Mock
    private Iterator<Integer> myIterator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Can add collection to transformed collection")
    public void canAddCollectionToTransformedCollection() {
        Mockito.when(myList.size()).thenReturn(5);
        Mockito.when(myList.iterator()).thenReturn(myIterator);

        AtomicInteger integer = new AtomicInteger(0); // Because annoyingly Java doesn't let me just use an int
        // I've had to use thenAnswer a few times in personal projects
        Mockito.when(myIterator.next()).thenAnswer(i -> integer.getAndIncrement());
        Mockito.when(myIterator.hasNext()).thenAnswer(i -> integer.get() < 5);

        TransformedCollection<Integer> collection = new TransformedCollection<>(new ArrayList<>(), a -> a * 4);
        collection.addAll(myList);

        Assertions.assertArrayEquals(
            new Integer[] { 0, 4, 8, 12, 16 },
            collection.toArray(new Integer[5])
        );
    }

}
