package problems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        Map<String, Integer> newmap = new HashMap<>();
        int n = 1;
        for (String s : list) {
            if (newmap.containsKey(s)) {
                newmap.put(s, newmap.get(s) + 1);
            } else {
                newmap.put(s, n);
            }
            if (newmap.get(s) >= 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> newmap = new HashMap<>();
        for (String s : m1.keySet()) {
            if (m2.containsKey(s) && m1.get(s).equals(m2.get(s))) {
                newmap.put(s, m2.get(s));
            }
        }
        return newmap;
    }
}
