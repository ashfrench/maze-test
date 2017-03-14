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

        while(!hasFinished()) {

            Move lastMove = route.head();
            Direction currentDirection = lastMove.getDirection();

            if (canMoveForward(lastMove)) {
                applyMove(currentDirection, lastMove);
            } else if (canTurnLeft(lastMove)){
                applyMove(currentDirection.turnLeft(), lastMove);
            } else if (canTurnRight(lastMove)){
                applyMove(currentDirection.turnRight(), lastMove);
            } else {
                route = backTrack();
            }
        }

        solvedMaze = new SolvedMaze(maze, route.map(Move::getPoint).reverse(), visitedPoints);
        return solvedMaze;
    }

    private void applyMove(Direction currentDirection, Move last) {
        Point point = currentDirection.move(last.getPoint());
        route = route.prepend(new Move(currentDirection, point));
        visitedPoints = visitedPoints.add(point);
    }

    private Array<Move> backTrack(){
        return route.dropWhile(move -> !canTurnLeft(move) && !canTurnRight(move));
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
        return maze.getCell(route.head().getPoint()) == Cell.FINISH;
    }

}
