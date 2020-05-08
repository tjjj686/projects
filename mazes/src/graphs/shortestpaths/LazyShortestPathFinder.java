package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;

import java.util.List;
import java.util.Objects;

/**
 * Very basic, syntactically-correct but semantically-incorrect shortest paths finder.
 *
 * Checks the start vertex for an edge directly to the end vertex, and gives up if it doesn't.
 */

public class LazyShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    implements ShortestPathFinder<G, V, E> {
    @Override
    public ShortestPath<V, E> findShortestPath(G graph, V start, V end) {
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }

        for (E edge : graph.outgoingEdgesFrom(start)) {
            if (edge.to().equals(end)) {
                return new ShortestPath.Success<>(List.of(edge));
            }
        }

        return new ShortestPath.Failure<>();
    }
}
