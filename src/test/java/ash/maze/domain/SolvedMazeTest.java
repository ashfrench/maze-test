package ash.maze.domain;

import ash.maze.domain.Cell;
import ash.maze.domain.Maze;
import ash.maze.domain.Point;
import ash.maze.domain.SolvedMaze;
import javaslang.collection.Array;
import javaslang.collection.HashSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static ash.maze.domain.Cell.*;
import static ash.maze.domain.Cell.WALL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class SolvedMazeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Cell[][] simpleGoodMaze = {{WALL, WALL, WALL, WALL, WALL},{START, SPACE, SPACE, SPACE, FINISH},{WALL, WALL, WALL, WALL, WALL}};
    private Maze maze = new Maze(simpleGoodMaze);

    @Test
    public void testNullMaze(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(equalTo("Maze cannot be null"));

        new SolvedMaze(null, Array.empty(), HashSet.empty());
    }

    @Test
    public void testNullRoute(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(equalTo("Route cannot be null"));

        new SolvedMaze(maze, null, HashSet.empty());
    }

    @Test
    public void testNullVisitedPoints(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(equalTo("VisitedPoints cannot be null"));

        new SolvedMaze(maze, Array.empty(), null);
    }

    @Test
    public void testSolvedMaze(){
        assertThat(new SolvedMaze(maze, Array.empty(), HashSet.empty()), notNullValue());
    }

    @Test
    public void testGetRoute(){
        Cell[][] cells = {{WALL, WALL, WALL, WALL, WALL},
                {START, SPACE, SPACE, SPACE, WALL},
                {WALL, WALL, SPACE, WALL, WALL},
                {WALL, WALL, FINISH, WALL, WALL}};
        Maze maze = new Maze(cells);
        Array<Point> route = Array.of(new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2));
        HashSet<Point> visitedPoints = HashSet.of(new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(2, 2), new Point(3, 2));

        SolvedMaze solvedMaze = new SolvedMaze(maze, route, visitedPoints);
        assertThat(solvedMaze.getRoute(), equalTo(Array.ofAll(route)));
    }

    @Test
    public void testRoute(){
        Cell[][] cells = {{WALL, WALL, WALL, WALL, WALL},
                {START, SPACE, SPACE, SPACE, WALL},
                {WALL, WALL, SPACE, WALL, WALL},
                {WALL, WALL, FINISH, WALL, WALL}};
        Maze maze = new Maze(cells);
        Array<Point> route = Array.of(new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2));
        HashSet<Point> visitedPoints = HashSet.of(new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(2, 2), new Point(3, 2));

        SolvedMaze solvedMaze = new SolvedMaze(maze, route, visitedPoints);
        String expectedSolved =
                "XXXXX\n" +
                "S++ X\n" +
                "XX+XX\n" +
                "XXFXX\n";

        assertThat(solvedMaze.getSolvedRoute(), equalTo(expectedSolved));
    }

    @Test
    public void testVisitedPoints(){
        Cell[][] cells = {{WALL, WALL, WALL, WALL, WALL},
                {START, SPACE, SPACE, SPACE, WALL},
                {WALL, WALL, SPACE, WALL, WALL},
                {WALL, WALL, FINISH, WALL, WALL}};
        Maze maze = new Maze(cells);
        Array<Point> route = Array.of(new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2));
        HashSet<Point> visitedPoints = HashSet.of(new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(2, 2), new Point(3, 2));

        SolvedMaze solvedMaze = new SolvedMaze(maze, route, visitedPoints);
        String vistedPoints =
                "XXXXX\n" +
                "S+++X\n" +
                "XX+XX\n" +
                "XXFXX\n";

        assertThat(solvedMaze.getVisitedPoints(), equalTo(vistedPoints));
    }

}