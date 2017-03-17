package ash.maze.explorer;

import ash.maze.domain.Move;
import ash.maze.domain.Point;
import ash.maze.domain.SolvedMaze;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Set;
import javaslang.control.Try;

import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.API.Match.Pattern0.any;

public class BreadthFirstSolver extends AbstractSolver {

    protected SolvedMaze solveMaze(){
        while(!hasFinished()) {

            Move lastMove = Try.of(() -> route.head())
                    .getOrElseThrow(() -> new RuntimeException("Unable to find a solution points visited so far\n"
                                                    + maze.printSolvedRoute(visitedPoints.toArray())));

            Tuple2<Array<Move>, Set<Point>> tuple2 = Match(lastMove)
                    .of(
                        Case(this::canTurnLeft, move -> applyMove(move.getDirection().turnLeft(), move)),
                        Case(this::canTurnRight, move -> applyMove(move.getDirection().turnRight(), move)),
                        Case(this::canMoveForward, move -> applyMove(move.getDirection(), move)),
                        Case(any(), Tuple.of(backTrack(route), visitedPoints))
                    );

            route = tuple2._1();
            visitedPoints = tuple2._2();
        }

        return  new SolvedMaze(maze, route.map(Move::getPoint).reverse(), visitedPoints);
    }

    protected Array<Move> backTrack(Array<Move> route){
        return route.dropWhile(move -> !canTurnRight(move) && !canMoveForward(move));
    }

}
