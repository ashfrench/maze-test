package ash.maze.explorer;

import ash.maze.domain.Cell;
import ash.maze.domain.Maze;
import ash.maze.domain.Point;
import ash.maze.domain.SolvedMaze;
import ash.maze.explorer.impl.DepthFirstSolver;
import javaslang.collection.Array;
import javaslang.collection.HashSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static ash.maze.domain.Cell.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExplorerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Cell[][] simpleGoodMaze = {{WALL, WALL, WALL, WALL, WALL},{START, SPACE, SPACE, SPACE, FINISH},{WALL, WALL, WALL, WALL, WALL}};
    private Maze maze = new Maze(simpleGoodMaze);

    @Test
    public void testNullMaze(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(equalTo("Cannot supply a null maze"));

        new Explorer(null, new DepthFirstSolver());
    }

    @Test
    public void testNullStrategy(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(equalTo("Cannot supply a null strategy"));

        new Explorer(maze, null);
    }

    @Test
    public void testCreation(){
        assertThat(new Explorer(maze, new DepthFirstSolver()), notNullValue());
    }

    @Test
    public void testSolveSimpleMaze(){
        Explorer explorer = new Explorer(maze, new DepthFirstSolver());
        SolvedMaze solve = explorer.solve();
        assertThat(solve.getRoute(), equalTo(Array.of(new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(1, 4))));
    }

    @Test
    public void testSolveSimpleMazeStringOutput(){
        Explorer explorer = new Explorer(maze, new DepthFirstSolver());
        SolvedMaze solvedMaze = explorer.solve();
        String solvedRoute = solvedMaze.getSolvedRoute();
        String expected = "XXXXX\n" +
                          "S+++F\n" +
                          "XXXXX\n";
        assertThat(solvedRoute, equalTo(expected));
    }

    @Test
    public void testShowBackTrack(){
        Cell[][] cells = {{WALL, WALL, WALL, WALL, WALL},
                          {START, SPACE, SPACE, SPACE, WALL},
                          {WALL, WALL, SPACE, WALL, WALL},
                          {WALL, WALL, FINISH, WALL, WALL}};
        Explorer explorer = new Explorer(new Maze(cells), new DepthFirstSolver());
        SolvedMaze solvedMaze = explorer.solve();

        String expectedVisited =
                "XXXXX\n" +
                "S+++X\n" +
                "XX+XX\n" +
                "XXFXX\n";

        assertThat(solvedMaze.getVisitedPoints(), equalTo(expectedVisited));

        String expectedSolved =
                "XXXXX\n" +
                "S++ X\n" +
                "XX+XX\n" +
                "XXFXX\n";

        assertThat(solvedMaze.getSolvedRoute(), equalTo(expectedSolved));

    }

    @Test
    public void testReuse(){
        Cell[][] cells = {{WALL, WALL, WALL, WALL, WALL},
                {START, SPACE, SPACE, SPACE, WALL},
                {WALL, WALL, SPACE, WALL, WALL},
                {WALL, WALL, FINISH, WALL, WALL}};

        SolverStrategy strategy = mock(SolverStrategy.class);
        Maze maze = new Maze(cells);

        when(strategy.solverMaze(any())).thenReturn(new SolvedMaze(maze,
                Array.of(new Point(0, 1), new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(2,3)),
                HashSet.of(new Point(0, 1), new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(2, 2), new Point(2,3))));

        Explorer explorer = new Explorer(maze, strategy);

        SolvedMaze solvedMaze = explorer.solve();
        String expectedVisited =
                "XXXXX\n" +
                "S+++X\n" +
                "XX+XX\n" +
                "XXFXX\n";

        assertThat(solvedMaze.getVisitedPoints(), equalTo(expectedVisited));

        String expectedSolved =
                "XXXXX\n" +
                "S++ X\n" +
                "XX+XX\n" +
                "XXFXX\n";

        assertThat(solvedMaze.getSolvedRoute(), equalTo(expectedSolved));
        assertThat("Is same object", solvedMaze == explorer.solve());
        assertThat(solvedMaze.hashCode(), equalTo(explorer.solve().hashCode()));
    }

    @Test
    public void testUnsolvable(){
        thrown.expect(RuntimeException.class);
        String expectedMessage = "Unable to find a solution points visited so far\n" +
                "XXXXX\n" +
                "S+X F\n" +
                "XXXXX\n";
        thrown.expectMessage(equalTo(expectedMessage));

        Cell[][] badMaze = {{WALL, WALL, WALL, WALL, WALL},{START, SPACE, WALL, SPACE, FINISH},{WALL, WALL, WALL, WALL, WALL}};
        SolverStrategy strategy = mock(SolverStrategy.class);
        when(strategy.solverMaze(any())).thenThrow(new RuntimeException("Unable to find a solution points visited so far\n" +
                "XXXXX\n" +
                "S+X F\n" +
                "XXXXX\n"));

        Maze maze = new Maze(badMaze);
        Explorer explorer = new Explorer(maze, strategy);
        explorer.solve();
    }

}