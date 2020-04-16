package maps.experiments;

import maps.AbstractIterableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    /**
     * Constructs an array map containing keys from 0 to `size` (with dummy values).
     */
    public static AbstractIterableMap<Long, Long> createMapOfSize(
        long size,
        Supplier<AbstractIterableMap<Long, Long>> mapConstructor) {
        AbstractIterableMap<Long, Long> map = mapConstructor.get();
        for (long i = 0; i < size; i += 1) {
            map.put(i, -1L);
        }
        return map;
    }

    public static List<char[]> generateRandomStrings(long numStrings, int stringLength) {
        return generateRandomStrings(numStrings, stringLength, Function.identity());
    }

    public static <STRING> List<STRING> generateRandomStrings(long numStrings,
                                                              int stringLength,
                                                              Function<char[], STRING> stringConstructor) {
        // define a seed for the random number generator, so that experiment results are consistent
        Random rand = new Random(numStrings);

        // use Java's Stream API to build a list of random STRING objects
        return Stream.generate(() -> {
            // define code to generate each STRING
            char[] array = new char[stringLength];
            for (int j = 0; j < stringLength; j++) {
                array[j] = (char) (rand.nextInt('z' - 'a') + 'a');
            }
            return stringConstructor.apply(array);
        })
            .limit(numStrings)                                  // run the code numStrings times
            .collect(Collectors.toCollection(ArrayList::new));  // add all items into an ArrayList
    }
}
