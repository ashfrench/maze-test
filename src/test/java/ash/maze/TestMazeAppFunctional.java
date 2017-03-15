package ash.maze;

import ash.maze.domain.Maze;
import ash.maze.domain.SolvedMaze;
import ash.maze.explorer.BreadthFirstSolver;
import ash.maze.explorer.DepthFirstSolver;
import ash.maze.explorer.Explorer;
import ash.maze.io.MazeFileReader;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TestMazeAppFunctional {

    @Test
    public void testExampleMaze() throws Exception{
        Explorer explorer = getExplorer("ExampleMaze.txt");
        SolvedMaze solvedMaze = explorer.solve();

        String expectedSolvedRoute =
                "XXXXXXXXXXXXXXX\n" +
                "X+++++++++++++X\n" +
                "X+XXXXXXXXXXX+X\n" +
                "X+XS++++++++X+X\n" +
                "X+XXXXXXXXX+X+X\n" +
                "X+XXXXXXXXX+X+X\n" +
                "X+XXXX++++++X+X\n" +
                "X+XXXX+XXXX X+X\n" +
                "X+XXXX+XXXX X+X\n" +
                "X+X++++XXXXXX+X\n" +
                "X+X+XXXXXXXXX+X\n" +
                "X+X+XXXXXXXXX+X\n" +
                "X+X+++++++++X+X\n" +
                "X+XXXXXXXXX+++X\n" +
                "XFXXXXXXXXXXXXX\n";

        assertThat(solvedMaze.getSolvedRoute(), equalTo(expectedSolvedRoute));
    }

    @Test
    public void testExampleMaze_NoBackTrack() throws Exception{
        Explorer explorer = getExplorer("ExampleMaze-NoBackTrack.txt");
        SolvedMaze solvedMaze = explorer.solve();

        String expectedSolvedRoute =
                "XXXXXXXXXXXXXXX\n" +
                        "X+++++++++++++X\n" +
                        "X+XXXXXXXXXXX+X\n" +
                        "X+XS++++++++X+X\n" +
                        "X+XXXXXXXXX+X+X\n" +
                        "X+XXXXXXXXX+X+X\n" +
                        "X+XXXX++++++X+X\n" +
                        "X+XXXX+XXXXXX+X\n" +
                        "X+XXXX+XXXXXX+X\n" +
                        "X+X++++XXXXXX+X\n" +
                        "X+X+XXXXXXXXX+X\n" +
                        "X+X+XXXXXXXXX+X\n" +
                        "X+X+++++++++X+X\n" +
                        "X+XXXXXXXXX+++X\n" +
                        "XFXXXXXXXXXXXXX\n";

        assertThat(solvedMaze.getSolvedRoute(), equalTo(expectedSolvedRoute));
    }

    @Test
    public void testBackTrack() throws Exception{
        Explorer explorer = getExplorer("ExampleMaze-2.txt");
        SolvedMaze solvedMaze = explorer.solve();

        String expectedVisitedPoints =
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "X+++++++++++++++++++++++++++++XXXXXX\n" +
                "X+XXXXXXXXXXXXXXXXXXXXXXXXXXX+XX+XXX\n" +
                "X+XS++++++++X XX      XXXXXXX+XX+XXX\n" +
                "X+XXXXXXXXX+X XXXXXXX XXXX+++++++XXX\n" +
                "X+XXXXXXXXX+X       X XXXXXXXXXX+XXX\n" +
                "X+XXXX++++++X XX XXXX XX    +++++XXX\n" +
                "X+XXXX+XXXX+X XX XXXX XXXX X+XXXXXXX\n" +
                "X+XXXX+XXXX+X XX      XXXX X+XXXXXXX\n" +
                "X+X++++XXXXXX XX XXXXXXXXX X+XXXXXXX\n" +
                "X+X+XXXXXXXXX XXXXXXXXXXXX X+     XX\n" +
                "X+X+XXXXXXXXX XXXX      XX X+XXXXXXX\n" +
                "X+X+++++++++X XXXXXXXXX XXXX+XXXXXXX\n" +
                "X+XXXXXXXXX++++++++++++++++++XXXXXXX\n" +
                "XFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n";


        assertThat(solvedMaze.getVisitedPoints(), equalTo(expectedVisitedPoints));

        String expectedRoute =
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "X+++++++++++++++++++++++++++++XXXXXX\n" +
                "X+XXXXXXXXXXXXXXXXXXXXXXXXXXX+XX XXX\n" +
                "X+XS++++++++X XX      XXXXXXX+XX XXX\n" +
                "X+XXXXXXXXX+X XXXXXXX XXXX   ++++XXX\n" +
                "X+XXXXXXXXX+X       X XXXXXXXXXX+XXX\n" +
                "X+XXXX++++++X XX XXXX XX    +++++XXX\n" +
                "X+XXXX+XXXX X XX XXXX XXXX X+XXXXXXX\n" +
                "X+XXXX+XXXX X XX      XXXX X+XXXXXXX\n" +
                "X+X++++XXXXXX XX XXXXXXXXX X+XXXXXXX\n" +
                "X+X+XXXXXXXXX XXXXXXXXXXXX X+     XX\n" +
                "X+X+XXXXXXXXX XXXX      XX X+XXXXXXX\n" +
                "X+X+++++++++X XXXXXXXXX XXXX+XXXXXXX\n" +
                "X+XXXXXXXXX++++++++++++++++++XXXXXXX\n" +
                "XFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n";

        assertThat(solvedMaze.getSolvedRoute(), equalTo(expectedRoute));
    }

    @Test
    public void testTJunction() throws Exception{
        Explorer explorer = getExplorer("ExampleMaze-t-junction.txt");
        SolvedMaze solvedMaze = explorer.solve();

        String expectedVisitedPoints =
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "X+++++++++++++++++++++++++++++XXXXXX\n" +
                "X+XXXXXXXXXXXXXXXXX+XXXXXXXXXXXX+XXX\n" +
                "X+XS++++++++X+XX++++++XXXXXXX+XX+XXX\n" +
                "X+XXXXXXXXX+X+XXXXXXX+XXXX+++++++XXX\n" +
                "X+XXXXXXXXX+X+++++++X+XXXXXXXXXX+XXX\n" +
                "X+XXXX++++++X+XX+XXXX+XX+++++++++XXX\n" +
                "X+XXXX+XXXX+X+XX+XXXX+XXXX+X+XXXXXXX\n" +
                "X+XXXX+XXXX+X+XX++++++XXXX+X+XXXXXXX\n" +
                "X+X++++XXXXXX+XX+XXXXXXXXX+X+XXXXXXX\n" +
                "X+X+XXXXXXXXX+XXXXXXXXXXXX+X++++++XX\n" +
                "X+X+XXXXXXXXX+XXXX++++++XX+X+XXXXXXX\n" +
                "X+X+++++++++X+XXXXXXXXX+XXXX+XXXXXXX\n" +
                "X+XXXXXXXXX++++++++++++++++++XXXXXXX\n" +
                "XFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n";


        assertThat(solvedMaze.getVisitedPoints(), equalTo(expectedVisitedPoints));

        String expectedRoute =
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "X+++++++++++++++++++          XXXXXX\n" +
                "X+XXXXXXXXXXXXXXXXX+XXXXXXXXXXXX XXX\n" +
                "X+XS++++++++X XX   +++XXXXXXX XX XXX\n" +
                "X+XXXXXXXXX+X XXXXXXX+XXXX       XXX\n" +
                "X+XXXXXXXXX+X++++   X+XXXXXXXXXX XXX\n" +
                "X+XXXX++++++X+XX+XXXX+XX         XXX\n" +
                "X+XXXX+XXXX X+XX+XXXX+XXXX X XXXXXXX\n" +
                "X+XXXX+XXXX X+XX++++++XXXX X XXXXXXX\n" +
                "X+X++++XXXXXX+XX XXXXXXXXX X XXXXXXX\n" +
                "X+X+XXXXXXXXX+XXXXXXXXXXXX X      XX\n" +
                "X+X+XXXXXXXXX+XXXX      XX X XXXXXXX\n" +
                "X+X+++++++++X+XXXXXXXXX XXXX XXXXXXX\n" +
                "X+XXXXXXXXX+++               XXXXXXX\n" +
                "XFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n";

        assertThat(solvedMaze.getSolvedRoute(), equalTo(expectedRoute));
    }

    @Test
    public void testXJunction() throws Exception {
        Explorer explorer = getExplorer("ExampleMaze-X-junction.txt");
        SolvedMaze solvedMaze = explorer.solve();

        String expectedVisitedPoints =
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "X+++++++++++++X++++XXXXXXXXXXX+XXXXXXXXXXXXXX\n" +
                "X+XXXXXXXXXXX+++XX+XXXXXXXXXXX+XXXXXXXXXXXXXX\n" +
                "X+XS++++++++X XXXX+++++++++++++++XXXXXXXXXXXX\n" +
                "X+XXXXXXXXX+X X+XXXXXXXXXXXXXX+XXXXXXXXXXXXXX\n" +
                "X+XXXXXXXXX+X X+XXXXXXXXXXXXXX+XXXXXXXXXXXXXX\n" +
                "X+XXXX++++++X X+++++++++++++++++++++++++++XXX\n" +
                "X+XXXX+XXXXXX X+XXXXXXXXXXXXXX+XXXX+XXXXXXXXX\n" +
                "X+XXXX+XXXXXXXX+XXX+XXXXXXXXXX+XXXX+XXXXXXXXX\n" +
                "X+X++++XXXXXX X+XXX+XXX++++++++XXXX++++XXXXXX\n" +
                "X+X+XXXXXXXXX X+X+++XXX+XXXXXX+XXXX+XX+XXXXXX\n" +
                "X+X+XXXXXXXXX X+XXX+++++XXXXXX+XXXX+XX+++XXXX\n" +
                "X+X+++++++++X X+XXX+XXX+XXXXXX+XXXXXXXXX+XXXX\n" +
                "X+XXXXXXXXX+++++XXXXXXXXXXXXXXXXXXXXXXXX+XXXX\n" +
                "XFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n";

        assertThat(solvedMaze.getVisitedPoints(), equalTo(expectedVisitedPoints));

        String expectedRoute =
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "X+++++++++++++X++++XXXXXXXXXXX XXXXXXXXXXXXXX\n" +
                "X+XXXXXXXXXXX+++XX+XXXXXXXXXXX XXXXXXXXXXXXXX\n" +
                "X+XS++++++++X XXXX+++++++++++++  XXXXXXXXXXXX\n" +
                "X+XXXXXXXXX+X X XXXXXXXXXXXXXX+XXXXXXXXXXXXXX\n" +
                "X+XXXXXXXXX+X X XXXXXXXXXXXXXX+XXXXXXXXXXXXXX\n" +
                "X+XXXX++++++X X++++++++++++++++           XXX\n" +
                "X+XXXX+XXXXXX X+XXXXXXXXXXXXXX XXXX XXXXXXXXX\n" +
                "X+XXXX+XXXXXXXX+XXX XXXXXXXXXX XXXX XXXXXXXXX\n" +
                "X+X++++XXXXXX X+XXX XXX        XXXX    XXXXXX\n" +
                "X+X+XXXXXXXXX X+X   XXX XXXXXX XXXX XX XXXXXX\n" +
                "X+X+XXXXXXXXX X+XXX     XXXXXX XXXX XX   XXXX\n" +
                "X+X+++++++++X X+XXX XXX XXXXXX XXXXXXXXX XXXX\n" +
                "X+XXXXXXXXX+++++XXXXXXXXXXXXXXXXXXXXXXXX XXXX\n" +
                "XFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n";

        assertThat(solvedMaze.getSolvedRoute(), equalTo(expectedRoute));
    }

    private Explorer getExplorer(String file) throws Exception{
        URL resource = getResource(file);
        Path path = Paths.get(resource.toURI());

        Maze maze = MazeFileReader.readFile(path);

        return new Explorer(maze, new DepthFirstSolver());
//        return new Explorer(maze, new BreadthFirstSolver());
    }

    private URL getResource(String resource){
        return getClass().getClassLoader().getResource(resource);
    }
}