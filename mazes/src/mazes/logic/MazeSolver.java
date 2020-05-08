package mazes.logic;

import graphs.EdgeWithData;
import graphs.shortestpaths.ShortestPath;
import graphs.shortestpaths.ShortestPathFinder;
import mazes.entities.Room;
import mazes.entities.Wall;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Essentially a wrapper around a {@link ShortestPathFinder} that includes a convenience method
 * to solve a maze specified by a list of pathway "walls."
 */
public class MazeSolver {
    private final ShortestPathFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> shortestPathFinder;

    public MazeSolver(ShortestPathFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> shortestPathFinder) {
        this.shortestPathFinder = shortestPathFinder;
    }

    /**
     * Returns an optional containing the list of pathway "walls" to traverse to get from start to
     * end, if possible. If no path exists, returns an empty Optional.
     */
    public Optional<List<Wall>> solveMaze(Set<Wall> pathways, Room start, Room end) {
        MazeGraph graph = MazeGraph.fromWalls(pathways);

        ShortestPath<Room, EdgeWithData<Room, Wall>> shortestPath = shortestPathFinder.findShortestPath(
            graph, start, end);

        if (shortestPath.exists()) {
            List<Wall> walls = shortestPath.edges().stream().map(EdgeWithData::data).collect(Collectors.toList());
            return Optional.of(walls);
        }
        return Optional.empty();
    }
}
