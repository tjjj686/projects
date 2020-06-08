package seamcarving;

import edu.princeton.cs.algs4.Picture;

@FunctionalInterface
public interface EnergyFunction {
    /**
     * Returns the energy of pixel (x, y) in the given image.
     * @throws IndexOutOfBoundsException if (x, y) is not inside of the given image,
     *                                   or if image has less than 3 pixels in either dimension.
     */
    double apply(Picture picture, int x, int y);
}
