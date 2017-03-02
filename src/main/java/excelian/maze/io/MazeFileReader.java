package excelian.maze.io;

import excelian.maze.domain.Cell;
import excelian.maze.domain.Maze;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class MazeFileReader {

    public static Maze readFile(Path path) throws IOException {
        Objects.requireNonNull(path, "Path cannot be null");
        Cell[][] cells = Files.readAllLines(path)
                .stream()
                .map(s -> s.chars()
                        .mapToObj(charInt -> Cell.getCell((char) charInt))
                        .toArray(Cell[]::new))
                .toArray(Cell[][]::new);

        return new Maze(cells);
    }
}
