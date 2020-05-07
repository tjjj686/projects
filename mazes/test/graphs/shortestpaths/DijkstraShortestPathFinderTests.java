package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.BaseGraphTests;
import graphs.Edge;
import graphs.Graph;
import graphs.InfiniteGraph;
import graphs.InfiniteIntWrapperGraph;
import graphs.ZeroEdgeGraph;
import graphs.shortestpaths.ShortestPathFinderAssert.ShortestPathAssert;
import org.junit.jupiter.api.Test;
import priorityqueues.ExtrinsicMinPQ;
import utils.IntWrapper;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class DijkstraShortestPathFinderTests extends BaseGraphTests {

    protected <G extends Graph<V, E>, V, E extends BaseEdge<V, E>> ShortestPathAssert<V, E>
    assertThatShortestPathOf(G graph, V start, V end) {
        DijkstraShortestPathFinder<G, V, E> shortestPathFinder = new DijkstraShortestPathFinder<>() {
            /** Override method to simulate behavior on grader. */
            @Override
            protected <T> ExtrinsicMinPQ<T> createMinPQ() {
                return super.createMinPQ();
            }
        };
        return new ShortestPathAssert<>(shortestPathFinder.findShortestPath(graph, start, end), graph, start, end);
    }

    @Test
    void find_onLectureExampleDirectedGraph_returnsCorrectPath() {
        Graph<String, Edge<String>> graph = directedGraph(
            edge("s", "w", 1),
            edge("s", "u", 20),
            edge("w", "x", 1),
            edge("x", "u", 1),
            edge("u", "v", 1),
            edge("v", "t", 1)
        );

        assertThatShortestPathOf(graph, "s", "t")
            .hasVertices("s", "w", "x", "u", "v", "t")
            .hasWeightCloseTo(5);
    }

    @Test
    void find_onGraphWithSingleEdge_returnsCorrectPath() {
        Graph<String, Edge<String>> graph = graph(edge("s", "t", Math.PI));

        assertThatShortestPathOf(graph, "s", "t")
            .hasVertices("s", "t")
            .hasWeightCloseTo(Math.PI);
    }

    @Test
    void find_withSameStartAndEndVertex_returnsCorrectPath() {
        Graph<String, Edge<String>> graph = graph(edge("s", "t", 500));

        assertThatShortestPathOf(graph, "s", "s")
            .hasVertices("s")
            .hasWeightCloseTo(0);
    }

    @Test
    void find_onInfiniteGraph_returnsCorrectPath() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            Graph<Integer, Edge<Integer>> graph = new InfiniteGraph();

            assertThatShortestPathOf(graph, 0, 5)
                .hasVertices(0, 1, 2, 3, 4, 5)
                .hasWeightCloseTo(5);
        });
    }

    @Test
    void find_onZeroEdgeGraph_returnsDoesNotExist() {
        Graph<Integer, Edge<Integer>> graph = new ZeroEdgeGraph(2);
        assertThatShortestPathOf(graph, 0, 1).doesNotExist();
    }

    @Test
    void find_onGraphWithMultiplePathsBetweenStartAndEnd_returnsCorrectPath() {
        Graph<String, Edge<String>> graph = graph(
            edge("s", "a", 2),
            edge("s", "b", 3),
            edge("s", "c", 6),

            edge("a", "b", 2),
            edge("a", "t", 100),

            edge("b", "c", 3),
            edge("b", "t", 10)
        );

        assertThatShortestPathOf(graph, "s", "t")
            .hasVertices("s", "b", "t")
            .hasWeightCloseTo(13);
    }

    @Test
    void find_onGraphWhereLeastCostPathHasMoreEdgesThanOtherPaths_returnsCorrectPath() {
        Graph<String, Edge<String>> graph = graph(
            edge("s", "a", 1),
            edge("a", "b", 1),
            edge("b", "c", 1),
            edge("c", "t", 1),
            edge("s", "d", 2),
            edge("d", "t", 3),
            edge("d", "e", 1)
        );

        assertThatShortestPathOf(graph, "s", "t")
            .hasVertices("s", "a", "b", "c", "t")
            .hasWeightCloseTo(4);
    }

    @Test
    void find_onGraphRequiringRelaxation_returnsCorrectPath() {
        Graph<String, Edge<String>> graph = graph(
            edge("s", "a", 2),
            edge("s", "b", 4),
            edge("s", "c", 6),
            edge("a", "b", 1),
            edge("b", "t", 1),
            edge("c", "t", 1)
        );

        assertThatShortestPathOf(graph, "s", "t")
            .hasVertices("s", "a", "b", "t")
            .hasWeightCloseTo(4);
    }

    /**
     * This test is likely to fail for code that uses == instead of the equals method.
     */
    @Test
    void find_onGraphWithCustomVertexObjects_returnsCorrectPath() {
        Graph<IntWrapper, Edge<IntWrapper>> graph = graph(
            edge(new IntWrapper(0), new IntWrapper(1)),
            edge(new IntWrapper(1), new IntWrapper(2))
        );

        IntWrapper v0 = new IntWrapper(0);
        IntWrapper v1 = new IntWrapper(1);
        IntWrapper v2 = new IntWrapper(2);
        assertThatShortestPathOf(graph, v0, v2)
            .hasVertices(v0, v1, v2);
    }

    /**
     * This test is likely to time out for code that uses == instead of the equals method.
     */
    @Test
    void find_withSameStartAndEndVertex_onGraphWithCustomVertexObjects_returnsCorrectPath() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            Graph<IntWrapper, Edge<IntWrapper>> graph = new InfiniteIntWrapperGraph();

            assertThatShortestPathOf(graph, new IntWrapper(0), new IntWrapper(0))
                .hasVertices(new IntWrapper(0));
        });
    }

    @Test
    void find_onGraphWithParallelEdgesSortedWeights_returnsCorrectPath() {
        Graph<String, Edge<String>> graph = graph(
            edge("s", "t", 1),
            edge("s", "t", 2),
            edge("s", "t", 3),
            edge("s", "t", 4),
            edge("s", "t", 5)
        );

        assertThatShortestPathOf(graph, "s", "t")
            .hasVertices("s", "t")
            .hasWeightCloseTo(1);
    }

    @Test
    void find_onGraphWithParallelEdgesUnsortedWeights_returnsCorrectPath() {
        Graph<String, Edge<String>> graph = graph(
            edge("s", "t", 5),
            edge("s", "t", 3),
            edge("s", "t", 1),
            edge("s", "t", 2),
            edge("s", "t", 4)
        );

        assertThatShortestPathOf(graph, "s", "t")
            .hasVertices("s", "t")
            .hasWeightCloseTo(1);
    }

    @Test
    void find_onGraphWithSelfLoop_returnsCorrectPath() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            Graph<String, Edge<String>> graph = graph(
                edge("s", "a", 2),
                edge("a", "a", 0), // self loop with appealing weight
                edge("a", "t", 2)
            );

            assertThatShortestPathOf(graph, "s", "t")
                .hasVertices("s", "a", "t")
                .hasWeightCloseTo(4);
        });
    }

    @Test
    void find_withDisconnectedVertices_returnsDoesNotExist() {
        Graph<String, Edge<String>> graph = graph(
            edge("s", "a", 1),
            edge("b", "t", 1)
        );

        assertThatShortestPathOf(graph, "s", "t").doesNotExist();
    }

    @Test
    void find_onGraphWithUniformWeights_returnsCorrectPath() {
        Graph<String, Edge<String>> graph = graph(
            edge("s", "a", 1),
            edge("s", "b", 1),
            edge("s", "t", 1),

            edge("a", "b", 1),
            edge("a", "t", 1),

            edge("b", "t", 1)
        );

        assertThatShortestPathOf(graph, "s", "t")
            .hasVertices("s", "t")
            .hasWeightCloseTo(1);
    }
}
