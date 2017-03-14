package ash.maze.explorer;

import ash.maze.domain.*;
import javaslang.collection.Array;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import javaslang.control.Option;
import javaslang.control.Try;

import java.util.Objects;

public class Explorer {

    private final Maze maze;
    private Array<Move> route;
    private Set<Point> visitedPoints;
    private SolvedMaze solvedMaze;

    public Explorer(Maze maze){
        Objects.requireNonNull(maze, "Cannot supply a null maze");

        this.maze = maze;
        route = Array.of(new Move(Direction.NORTH, maze.getStart()));
        visitedPoints = HashSet.of(maze.getStart());
    }

    public SolvedMaze solve(){
        return Try.of(() -> Option.of(solvedMaze).getOrElse(this::solveMaze))
                .getOrElseThrow(cause -> new RuntimeException("Unable to find a solution points visited so far\n"
                                                                     + maze.printSolvedRoute(visitedPoints.toArray()),
                                                            cause));
    }

    private SolvedMaze solveMaze(){
        Direction currentDirection = Direction.NORTH;
        while(!hasFinished()) {

            Move last = route.last();
            if (canMoveForward(last)) {
                Point point = currentDirection.move(last.getPoint());
                route = route.append(new Move(currentDirection, point));
                visitedPoints = visitedPoints.add(point);
            } else if (canTurnLeft(last)){
                currentDirection = currentDirection.turnLeft();

                Point point = currentDirection.move(last.getPoint());
                route = route.append(new Move(currentDirection, point));
                visitedPoints = visitedPoints.add(point);

            } else if (canTurnRight(last)){
                currentDirection = currentDirection.turnRight();

                Point point = currentDirection.move(last.getPoint());
                route = route.append(new Move(currentDirection, point));
                visitedPoints = visitedPoints.add(point);
            } else {
                route = backTrack();
            }
        }

        solvedMaze = new SolvedMaze(maze, route.map(Move::getPoint), visitedPoints);
        return solvedMaze;
    }

    private Array<Move> backTrack(){
        Array<Move> tempRoute = route.dropRight(1);
        while(!canTurnLeft(tempRoute.last()) && !canTurnRight(tempRoute.last())){
            tempRoute = tempRoute.dropRight(1);
        }

        return tempRoute;
    }

    private boolean canMoveForward(Move lastMove){
        return canMove(lastMove.getDirection(), lastMove.getPoint());
    }

    private boolean canTurnLeft(Move lastMove){
        return canMove(lastMove.getDirection().turnLeft(), lastMove.getPoint());
    }

    private boolean canTurnRight(Move lastMove){
        return canMove(lastMove.getDirection().turnRight(), lastMove.getPoint());
    }

    private boolean canMove(Direction direction, Point point){
        Point move = direction.move(point);
        return Try.of(() -> maze.getCell(move))
                .map(cell -> (cell == Cell.FINISH || cell == Cell.SPACE) && !visitedPoints.contains(move))
                .getOrElse(false);
    }

    private boolean hasFinished(){
        return maze.getCell(route.last().getPoint()) == Cell.FINISH;
    }

}
