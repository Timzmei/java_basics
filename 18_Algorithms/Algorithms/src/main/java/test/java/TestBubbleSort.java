package test.java;

import binary_search.BinarySearch;
import bubble_sort.BubbleSort;
import junit.framework.TestCase;
import org.junit.Assert;

public class TestBubbleSort extends TestCase {

    int[] array;

    @Override
    public void setUp() throws Exception {
        array = new int[] {2, 5, 7, 8, 3, 6, 9, 1, 4};



    }

    public void testBubble(){
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] actual = BubbleSort.sort(array);

        Assert.assertArrayEquals(expected, actual);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
