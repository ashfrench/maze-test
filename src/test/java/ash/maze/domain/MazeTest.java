package ash.maze.domain;

import javaslang.collection.Array;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MazeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Cell[][] simpleGoodMaze = {{Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},{Cell.START, Cell.SPACE, Cell.SPACE, Cell.SPACE, Cell.FINISH},{Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL}};

    @Test
    public void testMaze(){
        Maze maze = new Maze(simpleGoodMaze);

        String mazeString =
                "XXXXX\n" +
                "S   F\n" +
                "XXXXX\n";

        assertThat(maze.toString(), equalTo(mazeString));
    }

    @Test
    public void testMazeStartPoint(){
        Maze maze = new Maze(simpleGoodMaze);
        assertThat(maze.getStart(), equalTo(new Point(1, 0)));
    }

    @Test
    public void testMazeFinshPoint(){
        Maze maze = new Maze(simpleGoodMaze);
        assertThat(maze.getFinish(), equalTo(new Point(1, 4)));
    }

    @Test
    public void testGetPointWall(){
        Maze maze = new Maze(simpleGoodMaze);
        assertThat(maze.getCell(new Point(0, 0)), equalTo(Cell.WALL));
    }

    @Test
    public void testGetPointSpace(){
        Maze maze = new Maze(simpleGoodMaze);
        assertThat(maze.getCell(new Point(1, 3)), equalTo(Cell.SPACE));
    }

    @Test
    public void testGetPointStart(){
        Maze maze = new Maze(simpleGoodMaze);
        assertThat(maze.getCell(new Point(1, 0)), equalTo(Cell.START));
    }

    @Test
    public void testGetPointFinish(){
        Maze maze = new Maze(simpleGoodMaze);
        assertThat(maze.getCell(new Point(1, 4)), equalTo(Cell.FINISH));
    }

    @Test
    public void testGetPointOutOfBoundsX(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("X Point must be in the range 0 -> 2 Y point must be in the range 0 -> 4\nYou supplied Point(x=5, y=2)"));

        Maze maze = new Maze(simpleGoodMaze);
        maze.getCell(new Point(5, 2));
    }

    @Test
    public void testGetPointOutOfBoundsY(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("X Point must be in the range 0 -> 2 Y point must be in the range 0 -> 4\nYou supplied Point(x=2, y=10)"));

        Maze maze = new Maze(simpleGoodMaze);
        maze.getCell(new Point(2, 10));
    }

    @Test
    public void testGetPointOutOfBoundsNegativeX(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("X Point must be in the range 0 -> 2 Y point must be in the range 0 -> 4\nYou supplied Point(x=-1, y=2)"));

        Maze maze = new Maze(simpleGoodMaze);
        maze.getCell(new Point(-1, 2));
    }

    @Test
    public void testGetPointOutOfBoundsNegativeY(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("X Point must be in the range 0 -> 2 Y point must be in the range 0 -> 4\nYou supplied Point(x=2, y=-1)"));

        Maze maze = new Maze(simpleGoodMaze);
        maze.getCell(new Point(2, -1));
    }

    @Test
    public void testPrintPath(){
        Maze maze = new Maze(simpleGoodMaze);
        String pathString =
                "XXXXX\n" +
                "S+++F\n" +
                "XXXXX\n";

        Array<Point> points = Array.of(new Point(1, 1), new Point(1, 2), new Point(1, 3));
        assertThat(maze.printSolvedRoute(points), equalTo(pathString));
    }

    @Test
    public void testNullMaze(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(equalTo("Maze cannot be null"));

        new Maze(null);
    }

    @Test
    public void test2StartPoints(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("Maze should only contain one start cell"));

        Cell[][] twoStartPoints = {{Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},{Cell.START, Cell.START, Cell.SPACE, Cell.SPACE, Cell.FINISH},{Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL}};

        new Maze(twoStartPoints);
    }

    @Test
    public void test2FinishPoints(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("Maze should only contain one finish cell"));

        Cell[][] twoFinishPoints = {{Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},{Cell.START, Cell.FINISH, Cell.SPACE, Cell.SPACE, Cell.FINISH},{Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL}};

        new Maze(twoFinishPoints);
    }

    @Test
    public void testEmptyMaze(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("Maze must contain some cells, it cannot be empty"));

        Cell[][] empty = {};
        new Maze(empty);
    }

    @Test
    public void testEmpty2Maze(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("Maze must contain some cells"));

        Cell[][] empty = {{}};
        new Maze(empty);
    }

    @Test
    public void testNonRectangleMaze(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("Maze must be square or rectangular"));

        Cell[][] nonRectangle = {{Cell.WALL, Cell.WALL, Cell.WALL}, {Cell.START, Cell.FINISH}};
        new Maze(nonRectangle);
    }

    @Test
    public void testContainsPath(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("Should have no path set when creating the maze"));

        Cell[][] containsPath = {{Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},{Cell.START, Cell.PATH, Cell.SPACE, Cell.SPACE, Cell.FINISH},{Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL}};
        new Maze(containsPath);
    }
}