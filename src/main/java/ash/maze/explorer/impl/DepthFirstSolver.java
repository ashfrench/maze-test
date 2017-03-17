package ash.maze.explorer.impl;

import ash.maze.domain.Maze;
import ash.maze.domain.Move;
import ash.maze.domain.Point;
import ash.maze.explorer.AbstractSolver;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Set;

import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.API.Match.Pattern0.any;

public class DepthFirstSolver extends AbstractSolver {

    protected Tuple2<Array<Move>, Set<Point>> getUpdatedMoves(Maze maze, Array<Move> route, Set<Point> visitedPoints) {
        return Match(route.head())
                .of(
                    Case(move -> canMoveForward(maze, visitedPoints, move), move -> applyMove(move.getDirection(), move, route, visitedPoints)),
                    Case(move -> canTurnLeft(maze, visitedPoints, move), move -> applyMove(move.getDirection().turnLeft(), move, route, visitedPoints)),
                    Case(move -> canTurnRight(maze, visitedPoints, move), move -> applyMove(move.getDirection().turnRight(), move, route, visitedPoints)),
                    Case(any(), Tuple.of(route.dropWhile(move -> !canTurnLeft(maze, visitedPoints, move) && !canTurnRight(maze, visitedPoints, move)), visitedPoints))
                );
    }

}
