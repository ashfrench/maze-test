package ash.maze.explorer;

import ash.maze.domain.*;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import javaslang.control.Try;

import java.util.Objects;

public abstract class AbstractSolver implements SolverStrategy {

    protected Maze maze;
    protected Set<Point> visitedPoints;
    protected Array<Move> route;

    @Override
    public SolvedMaze solverMaze(Maze maze) {
        Objects.requireNonNull(maze, "Maze cannot be null");
        visitedPoints = HashSet.of(maze.getStart());
        route = Array.of(new Move(Direction.NORTH, maze.getStart()));
        this.maze = maze;
        return solveMaze();
    }

    protected abstract SolvedMaze solveMaze();

    protected abstract Array<Move> backTrack(Array<Move> route);

    protected Tuple2<Array<Move>, Set<Point>> applyMove(Direction currentDirection, Move last) {
        Point point = currentDirection.move(last.getPoint());
        Array<Move> tmpRoute = route.prepend(new Move(currentDirection, point));
        Set<Point> tmpVisitedPoints = visitedPoints.add(point);
        return Tuple.of(tmpRoute, tmpVisitedPoints);
    }

    protected boolean canMoveForward(Move lastMove){
        return canMove(lastMove.getDirection(), lastMove.getPoint());
    }

    protected boolean canTurnLeft(Move lastMove){
        return canMove(lastMove.getDirection().turnLeft(), lastMove.getPoint());
    }

    protected boolean canTurnRight(Move lastMove){
        return canMove(lastMove.getDirection().turnRight(), lastMove.getPoint());
    }

    private boolean canMove(Direction direction, Point point){
        Point move = direction.move(point);
        return Try.of(() -> maze.getCell(move))
                .map(cell -> (cell == Cell.FINISH || cell == Cell.SPACE) && !visitedPoints.contains(move))
                .getOrElse(false);
    }

    protected boolean hasFinished(){
        return !route.isEmpty() && maze.getCell(route.head().getPoint()) == Cell.FINISH;
    }

}
