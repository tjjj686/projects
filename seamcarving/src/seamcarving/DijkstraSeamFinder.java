package seamcarving;

import graphs.Edge;
import graphs.Graph;
import graphs.shortestpaths.DijkstraShortestPathFinder;
import graphs.shortestpaths.ShortestPathFinder;

import java.util.List;

public class DijkstraSeamFinder implements SeamFinder {
    // TODO: replace all 4 references to "Object" on the line below with whatever vertex type
    //  you choose for your graph
    private final ShortestPathFinder<Graph<Object, Edge<Object>>, Object, Edge<Object>> pathFinder;

    public DijkstraSeamFinder() {
        this.pathFinder = createPathFinder();
    }

    protected <G extends Graph<V, Edge<V>>, V> ShortestPathFinder<G, V, Edge<V>> createPathFinder() {
        /*
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
        */
        return new DijkstraShortestPathFinder<>();
    }

    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
