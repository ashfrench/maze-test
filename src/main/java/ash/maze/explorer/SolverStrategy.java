package ash.maze.explorer;

import ash.maze.domain.Maze;
import ash.maze.domain.SolvedMaze;

public interface SolverStrategy {
    SolvedMaze solverMaze(Maze maze);
}
