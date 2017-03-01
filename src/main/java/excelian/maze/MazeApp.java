package excelian.maze;

import excelian.maze.domain.Maze;
import excelian.maze.io.MazeFileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MazeApp {

    public static void main(String[] args) throws URISyntaxException, IOException {
        URL resource = MazeApp.class.getClassLoader().getResource("ExampleMaze.txt");
        Path path = Paths.get(resource.toURI());

        Maze maze = MazeFileReader.readFile(path);
        System.out.println(maze);
    }
}
