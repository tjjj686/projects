package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 *
 * @see ShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    implements ShortestPathFinder<G, V, E> {
    ExtrinsicMinPQ<V> pq = createMinPQ();
    Map<V, E> edgeToV = new HashMap<>();
    Map<V, Double> distToV = new HashMap<>();
    List<E> edge;
    Set<V> discovered = new HashSet<V>();

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
        You'll also need to change the part of the class declaration that says
        `ArrayHeapMinPQ<T extends Comparable<T>>` to `ArrayHeapMinPQ<T>`.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    private void update(List<E> re, V end, V start) {
        V cur = end;
        Stack<E> myStack = new Stack<>();
        while (edgeToV.containsKey(cur)) {
            myStack.push(edgeToV.get(cur));
            cur = edgeToV.get(cur).from();
        }
        while (!myStack.isEmpty()) {
            re.add(myStack.pop());
        }

    }

    @Override
    public ShortestPath<V, E> findShortestPath(G graph, V start, V end) {
        boolean find = false;
        pq.add(start, 0);
        distToV.put(start, 0.0);
        discovered.add(start);
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }
        while (!pq.isEmpty()) {
            V from = pq.removeMin();
            for (E e : graph.outgoingEdgesFrom(from)) {
                if (!e.to().equals(from)) {
                    V to = e.to();
                    if (!distToV.containsKey(to)) {
                        distToV.put(to, Double.POSITIVE_INFINITY);
                    }
                    double oldDist = distToV.get(to);
                    double newDist = distToV.get(from) + e.weight();
                    if (newDist < oldDist) {
                        distToV.put(to, newDist);
                        edgeToV.put(to, e);
                        discovered.add(to);
                        if (pq.contains(to)) {
                            pq.changePriority(to, newDist);
                        } else {
                            pq.add(to, newDist);

                        }
                    }
                }
            }
            if (from.equals(end)) {
                find = true;
                edge = new ArrayList<E>();
                update(edge, end, start);
                System.out.println(edge);
                break;
            }
        }
        if (find) {
            System.out.println(edge);
            return new ShortestPath.Success<>(edge);
        }
        return new ShortestPath.Failure<>();
    }
}
