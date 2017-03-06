package ash.maze.explorer;

import ash.maze.domain.Cell;
import ash.maze.domain.Maze;
import ash.maze.domain.Point;
import ash.maze.domain.SolvedMaze;
import javaslang.collection.Array;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import javaslang.control.Option;
import javaslang.control.Try;

import java.util.Objects;

public class Explorer {

    private final Maze maze;
    private Array<Point> route;
    private Direction currentDirection;
    private Set<Point> visitedPoints;
    private SolvedMaze solvedMaze;

    public Explorer(Maze maze){
        Objects.requireNonNull(maze, "Cannot supply a null maze");

        this.maze = maze;
        route = Array.of(maze.getStart());
        visitedPoints = HashSet.ofAll(route);
        currentDirection = Direction.NORTH;
    }

    public SolvedMaze solve(){
        return Try.of(() -> Option.of(solvedMaze).getOrElse(this::solveMaze))
                .getOrElseThrow(cause -> new RuntimeException("Unable to find a solution points visited so far\n"
                                                                     + maze.printSolvedRoute(visitedPoints.toArray()),
                                                            cause));
    }

    private SolvedMaze solveMaze(){
        while(!hasFinished()) {

            if (canMoveForward(route.last())) {
                Point move = currentDirection.move(route.last());
                route = route.append(move);
                visitedPoints = visitedPoints.add(move);
            } else if (canTurnLeft(route.last())){
                currentDirection = turnLeft();
            } else if (canTurnRight(route.last())){
                currentDirection = turnRight();
            } else {
                route = backTrack();
            }
        }

        solvedMaze = new SolvedMaze(maze, route, visitedPoints);
        return solvedMaze;
    }

    private Array<Point> backTrack(){
        Array<Point> tempRoute = route.dropRight(1);
        while(!canTurnLeft(tempRoute.last()) && !canTurnRight(tempRoute.last())){

            //handle T junction
            if(canTurnAround(tempRoute.last())){
                currentDirection = currentDirection.turnLeft().turnLeft();
                break;
            }
            tempRoute = tempRoute.dropRight(1);
        }

        return tempRoute;
    }

    private boolean canMoveForward(Point point){
        return canMove(currentDirection, point);
    }

    private boolean canTurnLeft(Point point){
        return canMove(currentDirection.turnLeft(),point);
    }

    private boolean canTurnRight(Point point){
        return canMove(currentDirection.turnRight(), point);
    }

    private boolean canTurnAround(Point point){
        return canMove(currentDirection.turnLeft().turnLeft(), point);
    }

    private boolean canMove(Direction direction, Point point){
        Point move = direction.move(point);
        return Try.of(() -> maze.getCell(move))
                .map(cell -> (cell == Cell.FINISH || cell == Cell.SPACE) && !visitedPoints.contains(move))
                .getOrElse(false);
    }

    private boolean hasFinished(){
        return maze.getCell(route.last()) == Cell.FINISH;
    }


    private Direction turnLeft() {
        return currentDirection.turnLeft();
    }

    private Direction turnRight() {
        return currentDirection.turnRight();
    }

}
