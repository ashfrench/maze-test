package ash.maze.io;

import ash.maze.domain.Maze;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class MazeFileReaderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstructor(){
        //test it doesnt throw exception for code coverage
        assertThat(new MazeFileReader(), notNullValue());
    }

    @Test
    public void testLoadValidMaze() throws IOException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("ExampleMaze.txt");
        Path path = Paths.get(resource.toURI());

        Maze maze = MazeFileReader.readFile(path);

        String expectedMaze =
                "XXXXXXXXXXXXXXX\n" +
                "X             X\n" +
                "X XXXXXXXXXXX X\n" +
                "X XS        X X\n" +
                "X XXXXXXXXX X X\n" +
                "X XXXXXXXXX X X\n" +
                "X XXXX      X X\n" +
                "X XXXX XXXX X X\n" +
                "X XXXX XXXX X X\n" +
                "X X    XXXXXX X\n" +
                "X X XXXXXXXXX X\n" +
                "X X XXXXXXXXX X\n" +
                "X X         X X\n" +
                "X XXXXXXXXX   X\n" +
                "XFXXXXXXXXXXXXX\n";

        assertThat(maze.toString(), equalTo(expectedMaze));
    }

    @Test
    public void testLoadInvalidMaze() throws IOException, URISyntaxException {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("No cell type for 'A'"));

        URL resource = getClass().getClassLoader().getResource("Invalid.txt");
        Path path = Paths.get(resource.toURI());

        MazeFileReader.readFile(path);
    }

    @Test
    public void testLoadNullPath() throws IOException {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(equalTo("Path cannot be null"));

        MazeFileReader.readFile(null);
    }

}