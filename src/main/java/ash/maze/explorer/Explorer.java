package ash.maze.explorer;

import ash.maze.domain.Maze;
import ash.maze.domain.SolvedMaze;
import javaslang.control.Option;

import java.util.Objects;

public class Explorer {

    private final Maze maze;
    private SolvedMaze solvedMaze;
    private SolverStrategy solverStrategy;

    public Explorer(Maze maze, SolverStrategy solverStrategy){
        Objects.requireNonNull(maze, "Cannot supply a null maze");
        Objects.requireNonNull(solverStrategy, "Cannot supply a null strategy");

        this.maze = maze;
        this.solverStrategy = solverStrategy;
    }

    public SolvedMaze solve(){
        solvedMaze = Option.of(solvedMaze).getOrElse(() -> solverStrategy.solverMaze(maze));
        return solvedMaze;
    }
}
