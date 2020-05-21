package seamcarving;

import edu.princeton.cs.algs4.Picture;
import edu.washington.cse373.BaseTest;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static java.lang.Math.sqrt;

public class BasicDualGradientEnergyFunctionTests extends BaseTest {
    protected static final double EPSILON = 1e-12;

    protected EnergyFunction createEnergyFunction() {
        return new DualGradientEnergyFunction();
    }

    /** Creates the sample image from the website instructions (which is the same as 3x4.png) */
    protected Picture createPicture() {
        Picture p = new Picture(3, 4);
        int[][][] pixelValues = {{{255, 101, 51}, {255, 101, 153}, {255, 101, 255}},
            {{255, 153, 51}, {255, 153, 153}, {255, 153, 255}},
            {{255, 203, 51}, {255, 204, 153}, {255, 205, 255}},
            {{255, 255, 51}, {255, 255, 153}, {255, 255, 255}}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int[] pixel = pixelValues[j][i];
                p.set(i, j, new Color(pixel[0], pixel[1], pixel[2]));
            }
        }
        return p;
    }

    @Test
    public void apply_onCenter_returnsCorrectValue() {
        Picture p = createPicture();

        EnergyFunction energyFunction = createEnergyFunction();
        double output = energyFunction.apply(p, 1, 2);
        assertThat(output).isCloseTo(sqrt(52024), within(EPSILON));
    }

    @Test
    public void apply_onTopEdge_returnsCorrectValue() {
        Picture p = createPicture();

        EnergyFunction energyFunction = createEnergyFunction();
        double output = energyFunction.apply(p, 1, 0);
        assertThat(output).isCloseTo(sqrt(52641), within(EPSILON));
    }

    @Test
    public void apply_onRightEdge_returnsCorrectValue() {
        Picture p = createPicture();

        EnergyFunction energyFunction = createEnergyFunction();
        double output = energyFunction.apply(p, 2, 2);
        assertThat(output).isCloseTo(sqrt(52024), within(EPSILON));
    }

    @Test
    public void apply_onBottomEdge_returnsCorrectValue() {
        Picture p = createPicture();

        EnergyFunction energyFunction = createEnergyFunction();
        double output = energyFunction.apply(p, 1, 3);
        assertThat(output).isCloseTo(sqrt(52020), within(EPSILON));
    }

    @Test
    public void apply_onLeftEdge_returnsCorrectValue() {
        Picture p = createPicture();

        EnergyFunction energyFunction = createEnergyFunction();
        double output = energyFunction.apply(p, 0, 1);
        assertThat(output).isCloseTo(sqrt(52020), within(EPSILON));
    }

    @Test
    public void apply_onTopLeftCorner_returnsCorrectValue() {
        Picture p = createPicture();

        EnergyFunction energyFunction = createEnergyFunction();
        double output = energyFunction.apply(p, 0, 0);
        assertThat(output).isCloseTo(sqrt(52852), within(EPSILON));
    }

    @Test
    public void apply_onTopRightCorner_returnsCorrectValue() {
        Picture p = createPicture();

        EnergyFunction energyFunction = createEnergyFunction();
        double output = energyFunction.apply(p, 2, 0);
        assertThat(output).isCloseTo(sqrt(52432), within(EPSILON));
    }

    @Test
    public void apply_onBottomLeftCorner_returnsCorrectValue() {
        Picture p = createPicture();

        EnergyFunction energyFunction = createEnergyFunction();
        double output = energyFunction.apply(p, 0, 3);
        assertThat(output).isCloseTo(sqrt(52852), within(EPSILON));
    }

    @Test
    public void apply_onBottomRightCorner_returnsCorrectValue() {
        Picture p = createPicture();

        EnergyFunction energyFunction = createEnergyFunction();
        double output = energyFunction.apply(p, 2, 3);
        assertThat(output).isCloseTo(sqrt(51220), within(EPSILON));
    }
}
