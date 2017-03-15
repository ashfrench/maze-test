package ash.maze.explorer;

import ash.maze.domain.*;
import javaslang.collection.Array;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import javaslang.control.Try;

import java.util.Objects;

public class BreadthFirstSolver implements SolverStrategy {

    private Maze maze;
    private Set<Point> visitedPoints;
    private Array<Move> route;

    @Override
    public SolvedMaze solverMaze(Maze maze) {
        Objects.requireNonNull(maze, "Maze cannot be null");
        visitedPoints = HashSet.of(maze.getStart());
        route = Array.of(new Move(Direction.NORTH, maze.getStart()));
        this.maze = maze;
        return solveMaze();
    }

    private SolvedMaze solveMaze(){

        while(!hasFinished()) {

            Move lastMove = Try.of(() -> route.head())
                    .getOrElseThrow(() -> new RuntimeException("Unable to find a solution points visited so far\n"
                                                    + maze.printSolvedRoute(visitedPoints.toArray())));
            Direction currentDirection = lastMove.getDirection();

            if (canTurnLeft(lastMove)){
                applyMove(currentDirection.turnLeft(), lastMove);
            } else if (canTurnRight(lastMove)) {
                applyMove(currentDirection.turnRight(), lastMove);
            } else if (canMoveForward(lastMove)) {
                applyMove(currentDirection, lastMove);
            } else {
                route = backTrack();
            }
        }

        return  new SolvedMaze(maze, route.map(Move::getPoint).reverse(), visitedPoints);
    }

    private void applyMove(Direction currentDirection, Move last) {
        Point point = currentDirection.move(last.getPoint());
        route = route.prepend(new Move(currentDirection, point));
        visitedPoints = visitedPoints.add(point);
    }

    private Array<Move> backTrack(){
        return route.dropWhile(move -> !canTurnRight(move) && !canMoveForward(move));
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
        return !route.isEmpty() && maze.getCell(route.head().getPoint()) == Cell.FINISH;
    }


}
