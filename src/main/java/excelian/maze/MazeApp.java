package excelian.maze;

import excelian.maze.domain.Maze;
import excelian.maze.domain.Point;
import excelian.maze.explorer.Explorer;
import excelian.maze.io.MazeFileReader;
import javaslang.collection.List;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MazeApp {

    public static void main(String[] args) throws URISyntaxException, IOException {
        URL resource = MazeApp.class.getClassLoader().getResource("ExampleMaze-NoBackTrack.txt");
        Path path = Paths.get(resource.toURI());

        Maze maze = MazeFileReader.readFile(path);
        System.out.println(maze);

        Explorer explorer = new Explorer(maze);
        List<Point> solve = explorer.solve();

        System.out.println(maze.printSolvedRoute(solve));
    }
}
