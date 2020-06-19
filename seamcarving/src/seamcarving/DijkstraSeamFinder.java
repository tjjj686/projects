package seamcarving;

import graphs.Edge;
import graphs.Graph;
import graphs.shortestpaths.DijkstraShortestPathFinder;
import graphs.shortestpaths.ShortestPath;
import graphs.shortestpaths.ShortestPathFinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DijkstraSeamFinder implements SeamFinder {
    class MyVertex {
        private double energy;
        private int row;
        private int col;

        MyVertex(double energy) {
            this.energy = energy;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MyVertex myVertex = (MyVertex) o;
            return Double.compare(myVertex.energy, energy) == 0 &&
                row == myVertex.row &&
                col == myVertex.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(energy, row, col);
        }
    }

    private final class MyHorGraph implements Graph<MyVertex, Edge<MyVertex>> {
        protected final Map<MyVertex, Set<Edge<MyVertex>>> adjacencyList;

        private MyHorGraph() {
            this.adjacencyList = new HashMap<>();
        }

        public void buildMyHorGraph(MyVertex[][] myVertex, MyVertex start, MyVertex end) {
            for (int w = 0; w < myVertex[0].length; w++) {
                for (int h = 0; h < myVertex.length; h++) {
                    if (w == myVertex[0].length - 1) {
                        Set<Edge<MyVertex>> mySet = new HashSet();
                        mySet.add(new Edge(myVertex[h][w], end, end.energy));
                        adjacencyList.put(myVertex[h][w], mySet);
                    } else {
                        adjacencyList.put(myVertex[h][w], findHorEdge(myVertex, w, h));
                    }
                }
            }
            adjacencyList.put(start, addHorEdge(myVertex, start));

        }

        @Override
        public Collection<Edge<MyVertex>> outgoingEdgesFrom(MyVertex vertex) {
            return Collections.unmodifiableSet(adjacencyList.getOrDefault(vertex, Set.of()));
        }
    }
    private final ShortestPathFinder<Graph<MyVertex, Edge<MyVertex>>, MyVertex, Edge<MyVertex>> pathFinder;

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

    private MyVertex[][] buildMyVertex(double[][] energies) {
        MyVertex[][] re = new MyVertex[energies[0].length][energies.length];
        for (int w = 0; w < energies[0].length; w++) {
            for (int h = 0; h < energies.length; h++) {
                re[w][h] = new MyVertex(energies[h][w]);
                re[w][h].row = w;
                re[w][h].col = h;
            }
        }
        return re;
    }

    private Set<Edge<MyVertex>> findHorEdge(MyVertex[][] myVertex, int w, int h) {
        Set<Edge<MyVertex>> mySet = new HashSet<>();
        if (h == 0) {
            mySet.add(new Edge(myVertex[h][w], myVertex[h][w + 1], myVertex[h][w + 1].energy));
            mySet.add(new Edge(myVertex[h][w], myVertex[h + 1][w + 1], myVertex[h + 1][w + 1].energy));
        } else if (h == myVertex.length - 1) {
            mySet.add(new Edge(myVertex[h][w], myVertex[h][w + 1], myVertex[h][w + 1].energy));
            mySet.add(new Edge(myVertex[h][w], myVertex[h - 1][w + 1], myVertex[h - 1][w + 1].energy));
        } else if (h + 1 < myVertex.length) {
            mySet.add(new Edge(myVertex[h][w], myVertex[h][w + 1], myVertex[h][w + 1].energy));
            mySet.add(new Edge(myVertex[h][w], myVertex[h - 1][w + 1], myVertex[h - 1][w + 1].energy));
            mySet.add(new Edge(myVertex[h][w], myVertex[h + 1][w + 1], myVertex[h + 1][w + 1].energy));
        }
        return mySet;
    }

    private Set<Edge<MyVertex>> addHorEdge(MyVertex[][] myVertex, MyVertex start) {
        Set<Edge<MyVertex>> mySet = new HashSet<>();
        for (int h = 0; h < myVertex.length; h++) {
            mySet.add(new Edge(start, myVertex[h][0], myVertex[h][0].energy));
        }
        return mySet;
    }

    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        List<Integer> re = new ArrayList<>();
        //instantiating the graph implementation and choosing the start and end vertices
        MyHorGraph g = new MyHorGraph();
        MyVertex start = new MyVertex(10000);
        MyVertex end = new MyVertex(Math.random());
        MyVertex[][] myVertex = buildMyVertex(energies);
        g.buildMyHorGraph(myVertex, start, end);
        ShortestPath<MyVertex, Edge<MyVertex>> path = pathFinder.findShortestPath(g, start, end);
        //System.out.println(1);
        System.out.println("h " + myVertex.length);
        System.out.println("w " + myVertex[0].length);
        //System.out.println(myVertex[0].length);
        for (int i = 0; i < myVertex[0].length; i++) {
            re.add(path.edges().get(i).to().row);
        }
        //System.out.println(re);
        return re;
    }

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        double[][] transposed = transpose(energies);
        return findHorizontalSeam(transposed);

    }

    private double[][] transpose(double[][] energies) {
        double[][] result = new double[energies[0].length][energies.length];
        for (int w = 0; w < energies[0].length; w++) {
            for (int h = 0; h < energies.length; h++) {
                result[w][h] = energies[h][w];
            }
        }
        return result;
    }
}
