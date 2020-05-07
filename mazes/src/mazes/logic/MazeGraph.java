package mazes.logic;

import graphs.AdjacencyListUndirectedGraph;
import graphs.EdgeWithData;
import mazes.entities.Room;
import mazes.entities.Wall;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A convenience class that sets the generic parameters for a {@link AdjacencyListUndirectedGraph}
 * representing a maze.
 *
 * Also includes a factory method to construct a graph from walls, instead of edges.
 */
public class MazeGraph extends AdjacencyListUndirectedGraph<Room, EdgeWithData<Room, Wall>> {
    /**
     * @see AdjacencyListUndirectedGraph#AdjacencyListUndirectedGraph(Collection)
     */
    public MazeGraph(Collection<EdgeWithData<Room, Wall>> edges) {
        super(edges);
    }

    /**
     * Constructs a new MazeGraph from the given walls creating edges for each wall, where the
     * vertices are the rooms on either side of the wall, and the weight is the distance between
     * the centers of the two rooms.
     */
    public static MazeGraph fromWalls(Collection<Wall> walls) {
        List<EdgeWithData<Room, Wall>> edges = walls.stream()
            .map(wall -> new EdgeWithData<>(wall.getRoom1(), wall.getRoom2(), wall.getDistance(), wall))
            .collect(Collectors.toList());

        return new MazeGraph(edges);
    }

}
