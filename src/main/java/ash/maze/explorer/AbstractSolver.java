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

    @Override
    public SolvedMaze solverMaze(Maze maze) {
        Objects.requireNonNull(maze, "Maze cannot be null");
        Set<Point> visitedPoints = HashSet.of(maze.getStart());
        Array<Move> route = Array.of(new Move(Direction.NORTH, maze.getStart()));

        while(!hasFinished(maze, route)) {

            Set<Point> finalVisitedPoints = visitedPoints;
            Array<Move> finalRoute = route;

            Try.of(finalRoute::head)
                    .getOrElseThrow(() -> new RuntimeException("Unable to find a solution points visited so far\n"
                            + maze.printSolvedRoute(finalVisitedPoints.toArray())));

            Tuple2<Array<Move>, Set<Point>> tuple2 = getUpdatedMoves(maze, route, visitedPoints);

            route = tuple2._1();
            visitedPoints = tuple2._2();
        }

        return  new SolvedMaze(maze, route.map(Move::getPoint).reverse(), visitedPoints);
    }

    protected abstract Tuple2<Array<Move>, Set<Point>> getUpdatedMoves(Maze maze, Array<Move> route, Set<Point> visitedPoints);

    protected static Tuple2<Array<Move>, Set<Point>> applyMove(Direction currentDirection, Move last, Array<Move> route, Set<Point> visitedPoints) {
        Point point = currentDirection.move(last.getPoint());
        Array<Move> tmpRoute = route.prepend(new Move(currentDirection, point));
        Set<Point> tmpVisitedPoints = visitedPoints.add(point);
        return Tuple.of(tmpRoute, tmpVisitedPoints);
    }

    protected static boolean canMoveForward(Maze maze, Set<Point> visitedPoints, Move lastMove){
        return canMove(maze, visitedPoints, lastMove.getDirection(), lastMove.getPoint());
    }

    protected static boolean canTurnLeft(Maze maze, Set<Point> visitedPoints, Move lastMove){
        return canMove(maze, visitedPoints, lastMove.getDirection().turnLeft(), lastMove.getPoint());
    }

    protected static boolean canTurnRight(Maze maze, Set<Point> visitedPoints, Move lastMove){
        return canMove(maze, visitedPoints, lastMove.getDirection().turnRight(), lastMove.getPoint());
    }

    private static boolean canMove(Maze maze, Set<Point> visitedPoints, Direction direction, Point point){
        Point move = direction.move(point);
        return Try.of(() -> maze.getCell(move))
                .map(cell -> (cell == Cell.FINISH || cell == Cell.SPACE) && !visitedPoints.contains(move))
                .getOrElse(false);
    }

    private boolean hasFinished(Maze maze, Array<Move> route){
        return !route.isEmpty() && maze.getCell(route.head().getPoint()) == Cell.FINISH;
    }

}
