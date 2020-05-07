package graphs.minspantrees;

import graphs.BaseEdge;

import java.util.Collection;
import java.util.Set;

/**
 * A result object returned by a {@link MinimumSpanningTreeFinder}.
 */
public interface MinimumSpanningTree<V, E extends BaseEdge<V, E>> {
    /** Returns true iff the graph has an MST, i.e., is connected. */
    boolean exists();

    /**
     * Returns the edges in the MST.
     * @throws UnsupportedOperationException if no MST exists
     */
    Collection<E> edges();

    /**
     * Returns the total weight of the edges in the MST.
     * @throws UnsupportedOperationException if no MST exists
     */
    default double totalWeight() {
        return edges().stream().mapToDouble(E::weight).sum();
    }

    class Success<V, E extends BaseEdge<V, E>> implements MinimumSpanningTree<V, E> {
        private final Collection<E> edges;

        Success() {
            this.edges = Set.of();
        }

        Success(Collection<E> edges) {
            this.edges = edges;
        }

        @Override
        public boolean exists() {
            return true;
        }

        public Collection<E> edges() {
            return edges;
        }
    }

    class Failure<V, E extends BaseEdge<V, E>> implements MinimumSpanningTree<V, E> {
        Failure() {}

        @Override
        public boolean exists() {
            return false;
        }

        public Collection<E> edges() {
            throw new UnsupportedOperationException("Graph is not connected.");
        }
    }
}
