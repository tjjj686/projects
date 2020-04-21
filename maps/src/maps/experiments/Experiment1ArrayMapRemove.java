package maps.experiments;

import maps.AbstractIterableMap;
import maps.ArrayMap;
import edu.washington.cse373.experiments.AnalysisUtils;
import edu.washington.cse373.experiments.PlotWindow;

import java.util.List;
import java.util.function.LongUnaryOperator;

public class Experiment1ArrayMapRemove {
    /*
    Note: please do not change these constants (or the constants in any of the other experiments)
    while working on your writeup
    */
    public static final long MAX_MAP_SIZE = 15000;
    public static final long STEP = 100;

    public static void main(String[] args) {
        new Experiment1ArrayMapRemove().run();
    }

    public void run() {
        List<Long> sizes = AnalysisUtils.range(0L, MAX_MAP_SIZE, STEP);

        PlotWindow.launch("Experiment 1", "Map Size", "Elapsed Time (ns)",
                new LongUnaryOperator[]{this::f1, this::f2},
                new String[]{"f1", "f2"}, sizes, 1000, .05);
    }

    protected AbstractIterableMap<Long, Long> createArrayMapOfSize(long mapSize) {
        return Utils.createMapOfSize(mapSize, ArrayMap::new);
    }

    public long f1(long mapSize) {
        // don't include the cost of constructing the map when running the tests
        AbstractIterableMap<Long, Long> map = createArrayMapOfSize(mapSize);

        long start = System.nanoTime();

        map.remove(0L);
        for (long i = mapSize - 1; i >= 1; i--) {
            map.remove(i);
        }

        return System.nanoTime() - start;
    }

    public long f2(long mapSize) {
        AbstractIterableMap<Long, Long> map = createArrayMapOfSize(mapSize);

        long start = System.nanoTime();

        for (long i = mapSize - 1; i >= 0; i--) {
            map.remove(i);
        }

        return System.nanoTime() - start;
    }
}
