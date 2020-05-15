package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.QuickFindDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        // return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    private void addTo(ExtrinsicMinPQ<E> pq, List<E> edges, DisjointSets<V> disjointSets) {
        Set<V> myset = new HashSet<>();
        for (int i = 0; i < edges.size(); i++) {
            E item = edges.get(i);
            double w = item.weight();
            pq.add(item, w);
            myset.add(item.from());
            myset.add(item.to());
        }
        for (V item : myset) {
            disjointSets.makeSet(item);
        }
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        // Here's some code to get you started; feel free to change or rearrange it if you'd like.

        // sort edges in the graph in ascending weight order
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));
        DisjointSets<V> disjointSets = createDisjointSets();

        List<E> re = new ArrayList<>();
        ExtrinsicMinPQ<E> pq = new DoubleMapMinPQ<E>();
        addTo(pq, edges, disjointSets);
        while (!pq.isEmpty()) {
            E item = pq.removeMin();
            if (disjointSets.union(item.from(), item.to())) {
                re.add(item);
            }
        }
        int count = 0;
        if (re.size() != 0) {
            int id = disjointSets.findSet(re.get(0).from());
            for (int i = 0; i < re.size(); i++) {
                int idf = disjointSets.findSet(re.get(i).from());
                int ids = disjointSets.findSet(re.get(i).to());
                if (idf == id && ids == id) {
                    count++;
                }
            }
        }
        if (count == re.size() || edges.size() == 0 && graph.allVertices().size() < 2) {
            return new MinimumSpanningTree.Success<>(re);
        } else {
            return new MinimumSpanningTree.Failure<>();
        }
    }
}
