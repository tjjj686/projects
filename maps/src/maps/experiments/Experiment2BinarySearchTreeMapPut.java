package maps.experiments;

import maps.AbstractIterableMap;
import maps.BinarySearchTreeMap;
import edu.washington.cse373.experiments.AnalysisUtils;
import edu.washington.cse373.experiments.PlotWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.LongUnaryOperator;

public class Experiment2BinarySearchTreeMapPut {
    /*
    Note: please do not change these constants (or the constants in any of the other experiments)
    while working on your writeup
    */
    public static final long MAX_MAP_SIZE = 10000;
    public static final long STEP = 100;

    public static void main(String[] args) {
        new Experiment2BinarySearchTreeMapPut().run();
    }

    public void run() {
        List<Long> sizes = AnalysisUtils.range(0L, MAX_MAP_SIZE, STEP);

        PlotWindow.launch("Experiment 2", "Map Size", "Elapsed Time (ns)",
            new LongUnaryOperator[]{this::test1, this::test2, this::test3},
            new String[]{"f1", "f2", "f3"}, sizes, 1000, .05);
    }

    public long test1(long mapSize) {
        List<Long> values = new ArrayList<>(AnalysisUtils.range(1, mapSize, 1));

        return timePuts(values);
    }

    public long test2(long mapSize) {
        List<Long> values = new ArrayList<>(AnalysisUtils.range(1, mapSize, 1));
        Collections.reverse(values);

        return timePuts(values);
    }

    public long test3(long mapSize) {
        List<Long> values = new ArrayList<>(AnalysisUtils.range(1, mapSize, 1));
        Collections.shuffle(values);

        return timePuts(values);
    }

    public long timePuts(List<Long> values) {
        AbstractIterableMap<Long, Long> map = new BinarySearchTreeMap<>();

        long start = System.nanoTime();
        for (Long value : values) {
            map.put(value, value);
        }
        return System.nanoTime() - start;
    }

}
