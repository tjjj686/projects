package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        StringBuilder re = new StringBuilder("[");
        if (array.length != 0) {
            for (int i = 0; i < array.length - 1; i++) {
                re.append(array[i]).append(", ");
            }
            re.append(array[array.length - 1]).append("]");
            return re.toString();
        } else {
            re.append("]");
            return re.toString();
        }
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        int[] re = new int[array.length];
        for (int i = 0; !(i >= array.length); i++) {
            re[i] = array[array.length - 1 - i];
        }
        return re;
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        int l = array.length;
        if (l != 0) {
            int tep = array[l - 1];
            System.arraycopy(array, 0, array, 1, l - 1);
            array[0] = tep;
        }
    }
}
