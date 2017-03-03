package ash.maze.explorer;

import ash.maze.domain.Maze;
import ash.maze.domain.Point;
import javaslang.collection.Array;
import javaslang.collection.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SolvedMaze {

    private final Maze maze;
    @Getter
    private final Array<Point> route;
    private final Set<Point> visitedPoints;

    public String getVisitedPoints(){
        return maze.printSolvedRoute(visitedPoints.toArray());
    }

    public String getSolvedRoute(){
        return maze.printSolvedRoute(route);
    }

}
