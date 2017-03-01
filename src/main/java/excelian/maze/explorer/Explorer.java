package excelian.maze.explorer;

import excelian.maze.domain.Maze;
import excelian.maze.domain.Point;
import javaslang.collection.List;

public class Explorer {

    private final Maze maze;
    private List<Point> route;
    private Direction currentDirection;

    public Explorer(Maze maze){
        this.maze = maze;
        route = List.empty();
        currentDirection = Direction.NORTH;
    }

    private void move() {

    }

    private void turn() {

    }

}
