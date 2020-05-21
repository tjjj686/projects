package seamcarving;

import java.util.List;

public interface SeamFinder {
    /**
     * Calculates and returns a minimum-energy horizontal seam in the current image.
     * The returned array will have the same length as the width of the image.
     * A value of v at index i of the output indicates that pixel (i, v) is in the seam.
     * @param energies must be non-null and non-empty, and must contain non-null and non-empty
     *                 arrays of uniform lengths
     */
    List<Integer> findHorizontalSeam(double[][] energies);

    /**
     * Calculates and returns a minimum-energy vertical seam in the current image.
     * The returned array will have the same length as the height of the image.
     * A value of v at index i of the output indicates that pixel (v, i) is in the seam.
     * @param energies must be non-null and non-empty, and must contain non-null and non-empty
     *                 arrays of uniform lengths
     */
    List<Integer> findVerticalSeam(double[][] energies);
}
