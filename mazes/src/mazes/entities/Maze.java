package mazes.entities;

import java.awt.Point;
import java.util.Collections;
import java.util.Set;

/**
 * Represents a maze.
 */
public class Maze {
    private final Set<Room> rooms;
    private final Set<Wall> walls;
    private final Set<Wall> untouchableWalls;

    public Maze(Set<Room> rooms, Set<Wall> walls, Set<Wall> untouchableWalls) {
        this.rooms = Collections.unmodifiableSet(rooms);
        this.walls = Collections.unmodifiableSet(walls);
        this.untouchableWalls = Collections.unmodifiableSet(untouchableWalls);
    }

    /**
     * Returns the set of all walls in the maze.
     */
    public Set<Room> getRooms() {
        return this.rooms;
    }

    /**
     * Returns the set of all removable walls between rooms.
     */
    public Set<Wall> getRemovableWalls() {
        return this.walls;
    }

    /**
     * Returns the set of all unremovable walls between rooms.
     *
     * In other words, when we're generating a maze, these walls must ALWAYS be present in the final
     * maze, for one reason or another.
     */
    public Set<Wall> getUnremovableWalls() {
        return this.untouchableWalls;
    }

    /**
     * Returns the room containing the given point.
     *
     * Returns `null` if there does not exist a room under that point for some reason.
     */
    public Room getRoom(Point point) {
        for (Room room : this.rooms) {
            if (room.contains(point)) {
                return room;
            }
        }
        return null;
    }
}
