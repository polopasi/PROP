package domain;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {
        /** Uses each element's hash code to ensure consistent sorting even if the target type does not implement Comparable. */
        public static <T> List<T> toSortedList(Collection<T> collection) {
            return collection.stream()
                    .sorted((a, b) -> Integer.compare(a.hashCode(), b.hashCode()))
                    .collect(Collectors.toList());
        }
        
        /** Uses each element's hash code to ensure consistent sorting even if the target type does not implement Comparable. */
        public static <T> List<T> toSortedList(T[] array) {
            return Arrays.stream(array)
                    .sorted((a, b) -> Integer.compare(a.hashCode(), b.hashCode()))
                    .collect(Collectors.toList());
        }
    
        /**
         * Sorts both collections to ensure they have the same elements, as Collection
         * does not have a specified order.
         * 
         * Does not need T to implement Comparable.
         */
        public static <T> void assertSortedEquals(T[] array, Collection<T> collection) {
            assertEquals(toSortedList(array), toSortedList(collection));
        }
    
        public static <T> void assertListEquals(T[] array, List<T> list) {
            assertEquals(Arrays.asList(array), list);
        }
        
        public static <T> void assertListEquals(T[][] array, List<List<T>> list) {
            final var listArray = Arrays.stream(array)
                    .map(a -> Arrays.asList(a))
                    .collect(Collectors.toList());
            assertEquals(listArray, list);
        }
}
