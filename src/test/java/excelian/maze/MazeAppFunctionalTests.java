package excelian.maze;

import excelian.maze.domain.Maze;
import excelian.maze.explorer.Explorer;
import excelian.maze.io.MazeFileReader;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class MazeAppFunctionalTests {

    @Test
    public void testExampleMaze() throws Exception{
        Explorer explorer = getExplorer("ExampleMaze.txt");
        explorer.solve();

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

        assertThat(explorer.getSolvedRoute(), equalTo(expectedSolvedRoute));
    }

    @Test
    public void testExampleMaze_NoBackTrack() throws Exception{
        Explorer explorer = getExplorer("ExampleMaze-NoBackTrack.txt");
        explorer.solve();

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

        assertThat(explorer.getSolvedRoute(), equalTo(expectedSolvedRoute));
    }

    @Test
    public void testBackTrack() throws Exception{
        Explorer explorer = getExplorer("ExampleMaze-2.txt");
        explorer.solve();

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


        assertThat(explorer.getVisitedPoints(), equalTo(expectedVisitedPoints));

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

        assertThat(explorer.getSolvedRoute(), equalTo(expectedRoute));
    }

    @Test
    public void testTJunction() throws Exception{
        Explorer explorer = getExplorer("ExampleMaze-t-junction.txt");
        explorer.solve();

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


        assertThat(explorer.getVisitedPoints(), equalTo(expectedVisitedPoints));

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

        assertThat(explorer.getSolvedRoute(), equalTo(expectedRoute));
    }

    private Explorer getExplorer(String file) throws Exception{
        URL resource = getResource(file);
        Path path = Paths.get(resource.toURI());

        Maze maze = MazeFileReader.readFile(path);

        return new Explorer(maze);
    }

    private URL getResource(String resource){
        return getClass().getClassLoader().getResource(resource);
    }
}