package ash.maze.domain;

import ash.maze.domain.Maze;
import ash.maze.domain.Point;
import javaslang.collection.Array;
import javaslang.collection.Set;
import lombok.Getter;

import java.util.Objects;

public class SolvedMaze {

    private final Maze maze;
    @Getter
    private final Array<Point> route;
    private final Set<Point> visitedPoints;

    public SolvedMaze(Maze maze, Array<Point> route, Set<Point> visitedPoints) {
        Objects.requireNonNull(maze, "Maze cannot be null");
        Objects.requireNonNull(route, "Route cannot be null");
        Objects.requireNonNull(visitedPoints, "VisitedPoints cannot be null");

        this.maze = maze;
        this.route = route;
        this.visitedPoints = visitedPoints;
    }

    public String getVisitedPoints(){
        return maze.printSolvedRoute(visitedPoints.toArray());
    }

    public String getSolvedRoute(){
        return maze.printSolvedRoute(route);
    }

}
