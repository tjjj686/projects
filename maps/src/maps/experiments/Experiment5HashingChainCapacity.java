package maps.experiments;

import maps.ChainedHashMap;
import edu.washington.cse373.experiments.AnalysisUtils;
import edu.washington.cse373.experiments.PlotWindow;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.LongUnaryOperator;

public class Experiment5HashingChainCapacity {
    public static final long MAX_LIST_SIZE = 80000;
    public static final long STEP = 1000;

    public static final int LENGTH_PER_ARRAY = 50;
    public static final double RESIZING_LOAD_FACTOR_THRESHOLD = 0.75;
    public static final int INITIAL_CHAIN_COUNT = 10;

    public static void main(String[] args) {
        new Experiment5HashingChainCapacity().run();
    }

    public void run() {
        List<Long> sizes = AnalysisUtils.range(0L, MAX_LIST_SIZE, STEP);

        PlotWindow.launch("Experiment 5", "Map Size", "Elapsed Time (ms)",
            new LongUnaryOperator[]{this::f1, this::f2, this::f3},
            new String[]{"f1", "f2", "f3"}, sizes);
    }

    protected Map<Integer, char[]> constructChainedHashMap(int chainInitialCapacity) {
        return new ChainedHashMap<>(RESIZING_LOAD_FACTOR_THRESHOLD, INITIAL_CHAIN_COUNT, chainInitialCapacity);
    }

    public long f1(long mapSize) {
        return timePuts((int) mapSize, constructChainedHashMap(2));
    }

    public long f2(long mapSize) {
        return timePuts((int) mapSize, constructChainedHashMap(500));
    }

    public long f3(long mapSize) {
        return timePuts((int) mapSize, constructChainedHashMap(1000));
    }

    protected long timePuts(int numPuts, Map<Integer, char[]> map) {
        List<char[]> chars = Utils.generateRandomStrings(numPuts, LENGTH_PER_ARRAY);

        long start = System.currentTimeMillis();
        for (char[] array : chars) {
            map.put(Arrays.hashCode(array), array);
        }
        return System.currentTimeMillis() - start;
    }
}
