package excelian.maze.explorer;

import excelian.maze.domain.Cell;
import excelian.maze.domain.Maze;
import excelian.maze.domain.Point;
import javaslang.collection.Array;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static excelian.maze.domain.Cell.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class ExplorerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Cell[][] simpleGoodMaze = {{WALL, WALL, WALL, WALL, WALL},{START, SPACE, SPACE, SPACE, FINISH},{WALL, WALL, WALL, WALL, WALL}};
    private Maze maze = new Maze(simpleGoodMaze);

    @Test
    public void testNullMaze(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(equalTo("Cannot supply a null maze"));

        new Explorer(null);
    }

    @Test
    public void testCreation(){
        assertThat(new Explorer(maze), notNullValue());
    }

    @Test
    public void testSolveSimpleMaze(){
        Explorer explorer = new Explorer(maze);
        Array<Point> solve = explorer.solve();
        assertThat(solve, equalTo(Array.of(new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(1, 4))));
    }

    @Test
    public void testSolveSimpleMazeStringOutput(){
        Explorer explorer = new Explorer(maze);
        explorer.solve();
        String solvedRoute = explorer.getSolvedRoute();
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
        Explorer explorer = new Explorer(new Maze(cells));
        explorer.solve();

        String expectedVisited =
                "XXXXX\n" +
                "S+++X\n" +
                "XX+XX\n" +
                "XXFXX\n";

        assertThat(explorer.getVisitedPoints(), equalTo(expectedVisited));

        String expectedSolved =
                "XXXXX\n" +
                "S++ X\n" +
                "XX+XX\n" +
                "XXFXX\n";

        assertThat(explorer.getSolvedRoute(), equalTo(expectedSolved));

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
        Maze maze = new Maze(badMaze);
        Explorer explorer = new Explorer(maze);
        explorer.solve();
    }

}