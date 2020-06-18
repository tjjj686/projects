package seamcarving;

import edu.princeton.cs.algs4.Picture;

public class DualGradientEnergyFunction implements EnergyFunction {
    @Override
    public double apply(Picture picture, int x, int y) {
        if (x < 0 || y < 0 || x >= picture.width() || y >= picture.height() || picture.width() < 3 || picture.height() < 3) {
            throw new IndexOutOfBoundsException();
        }
        int rx = 0, gx = 0, bx = 0, ry = 0, gy = 0, by = 0;
        int rxFoward = 0, rxBackward = 0, rxCent = 0, gxFoward = 0, gxBackward = 0, gxCent = 0, bxFoward = 0, bxBackward = 0, bxCent = 0;
        int ryFoward = 0, ryBackward = 0, ryCent = 0, gyFoward = 0, gyBackward = 0, gyCent = 0, byFoward = 0, byBackward = 0, byCent = 0;
        if ((x + 2) < picture.width()) {
            rxFoward = -3 * picture.get(x, y).getRed() + 4 * picture.get(x + 1, y).getRed()
                - picture.get(x + 2, y).getRed();
            gxFoward = -3 * picture.get(x, y).getGreen() + 4 * picture.get(x + 1, y).getGreen()
                - picture.get(x + 2, y).getGreen();
            bxFoward = -3 * picture.get(x, y).getBlue() + 4 * picture.get(x + 1, y).getBlue()
                - picture.get(x + 2, y).getBlue();
        }

        if ((x - 2) >= 0) {
            rxBackward = -3 * picture.get(x, y).getRed() + 4 * picture.get(x - 1, y).getRed()
                - picture.get(x - 2, y).getRed();
            gxBackward = -3 * picture.get(x, y).getGreen() + 4 * picture.get(x - 1, y).getGreen()
                - picture.get(x - 2, y).getGreen();
            bxBackward = -3 * picture.get(x, y).getBlue() + 4 * picture.get(x - 1, y).getBlue()
                - picture.get(x - 2, y).getBlue();
        }

        if ((y + 2) < picture.height()) {
            ryFoward = -3 * picture.get(x, y).getRed() + 4 * picture.get(x, y + 1).getRed()
                - picture.get(x, y + 2).getRed();
            gyFoward = -3 * picture.get(x, y).getGreen() + 4 * picture.get(x, y + 1).getGreen()
                - picture.get(x, y + 2).getGreen();
            byFoward = -3 * picture.get(x, y).getBlue() + 4 * picture.get(x, y + 1).getBlue()
                - picture.get(x, y + 2).getBlue();
        }
        if ((y - 2) >= 0) {
            ryBackward = -3 * picture.get(x, y).getRed() + 4 * picture.get(x, y - 1).getRed()
                - picture.get(x, y - 2).getRed();
            gyBackward = -3 * picture.get(x, y).getGreen() + 4 * picture.get(x, y - 1).getGreen()
                - picture.get(x, y - 2).getGreen();
            byBackward = -3 * picture.get(x, y).getBlue() + 4 * picture.get(x, y - 1).getBlue()
                - picture.get(x, y - 2).getBlue();
        }
        if ((x + 1) < picture.width() && (x - 1) >= 0) {
            rxCent = picture.get(x + 1, y).getRed() - picture.get(x - 1, y).getRed();
            gxCent = picture.get(x + 1, y).getGreen() - picture.get(x - 1, y).getGreen();
            bxCent = picture.get(x + 1, y).getBlue() - picture.get(x - 1, y).getBlue();
        }
        if ((y + 1) < picture.height() && (y - 1) >= 0) {
            ryCent = picture.get(x, y + 1).getRed() - picture.get(x, y - 1).getRed();
            gyCent = picture.get(x, y + 1).getGreen() - picture.get(x, y - 1).getGreen();
            byCent = picture.get(x, y + 1).getBlue() - picture.get(x, y - 1).getBlue();
        }

        if (x == 0) {
            rx = rxFoward;
            gx = gxFoward;
            bx = bxFoward;
        }
        if (x == picture.width() - 1) {
            rx = rxBackward;
            gx = gxBackward;
            bx = bxBackward;
        }
        if (x > 0 && x < picture.width() - 1) {
            rx = rxCent;
            gx = gxCent;
            bx = bxCent;
        }
        if (y == 0) {
            ry = ryFoward;
            gy = gyFoward;
            by = byFoward;
        }
        if (y == picture.height() - 1) {
            ry = ryBackward;
            gy = gyBackward;
            by = byBackward;
        }
        if (y > 0 && y < picture.height() - 1) {
            ry = ryCent;
            gy = gyCent;
            by = byCent;
        }
        return Math.sqrt(Math.pow(rx, 2) + Math.pow(gx, 2) + Math.pow(bx, 2) +
            Math.pow(ry, 2) + Math.pow(gy, 2) + Math.pow(by, 2));

    }
}
