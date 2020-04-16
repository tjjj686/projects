package maps.experiments;

import maps.AbstractIterableMap;
import maps.ChainedHashMap;
import edu.washington.cse373.experiments.AnalysisUtils;
import edu.washington.cse373.experiments.PlotWindow;

import java.util.List;
import java.util.Map;
import java.util.function.LongUnaryOperator;

public class Experiment4HashingResizingLoadFactor {
    public static final long MAX_MAP_SIZE = 50000;
    public static final long STEP = 500;
    public static final int INITIAL_CHAIN_COUNT = 10;
    public static final int CHAIN_INITIAL_CAPACITY = 8;

    public static void main(String[] args) {
        new Experiment4HashingResizingLoadFactor().run();
    }

    public void run() {
        List<Long> sizes = AnalysisUtils.range(0L, MAX_MAP_SIZE, STEP);

        PlotWindow.launch("Experiment 4", "Map Size", "Elapsed Time (ns)",
            new LongUnaryOperator[]{this::f1, this::f2},
            new String[]{"f1", "f2"}, sizes, 1, .01);
    }

    protected Map<Long, Long> constructChainedHashMap(double resizingLoadFactor) {
        return new ChainedHashMap<>(resizingLoadFactor, INITIAL_CHAIN_COUNT, CHAIN_INITIAL_CAPACITY);
    }

    public long f1(long mapSize) {
        return timePuts(mapSize, constructChainedHashMap(0.75));
    }

    public long f2(long mapSize) {
        return timePuts(mapSize, constructChainedHashMap(300));
    }

    protected long timePuts(long numPuts, Map<Long, Long> map) {
        long start = System.nanoTime();
        for (long i = 0L; i < numPuts; i++) {
            map.put(i, 0L);
        }
        return System.nanoTime() - start;
    }
}
