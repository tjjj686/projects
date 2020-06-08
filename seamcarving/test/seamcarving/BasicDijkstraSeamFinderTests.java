package seamcarving;

import edu.princeton.cs.algs4.Picture;
import edu.washington.cse373.BaseTest;
import graphs.Edge;
import graphs.Graph;
import graphs.shortestpaths.ShortestPathFinder;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class BasicDijkstraSeamFinderTests extends BaseTest {
    protected SeamFinder createSeamFinder() {
        return new DijkstraSeamFinder() {
            @Override
            protected <G extends Graph<V, Edge<V>>, V> ShortestPathFinder<G, V, Edge<V>> createPathFinder() {
                return super.createPathFinder();
            }
        };
    }

    protected EnergyFunction createEnergyFunction() {
        return new DualGradientEnergyFunction();
    }

    protected Picture loadPicture(String name) {
        return new Picture(Path.of("data").resolve(name).toFile());
    }

    protected SeamFinderAssert assertThat(SeamFinder seamFinder) {
        return new SeamFinderAssert(seamFinder);
    }

    @Test
    public void findVerticalSeam_returnsCorrectSeam() {
        Picture p = loadPicture("6x5.png");
        EnergyFunction energyFunction = createEnergyFunction();
        double[][] energies = SeamCarver.computeEnergies(p, energyFunction);

        SeamFinder seamFinder = createSeamFinder();
        assertThat(seamFinder).verticalSeam(energies).hasSameEnergyAs(4, 4, 3, 2, 2);
    }

    @Test
    public void findHorizontalSeam_returnsCorrectSeam() {
        Picture p = loadPicture("6x5.png");
        EnergyFunction energyFunction = createEnergyFunction();
        double[][] energies = SeamCarver.computeEnergies(p, energyFunction);

        SeamFinder seamFinder = createSeamFinder();
        assertThat(seamFinder).horizontalSeam(energies).hasSameEnergyAs(2, 2, 1, 2, 1, 1);
    }
}
