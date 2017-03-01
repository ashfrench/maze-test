package excelian.maze.io;

import excelian.maze.domain.Maze;
import excelian.maze.domain.Cell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class MazeFileReader {

    public static Maze readFile(Path path) throws IOException {
        Objects.requireNonNull(path, "Path cannot be null");
        List<String> strings = Files.readAllLines(path);

        Cell[][] cells = strings.stream()
                .map(s -> s.chars()
                        .mapToObj(charInt -> Cell.getCell((char) charInt))
                        .toArray(Cell[]::new))
                .toArray(Cell[][]::new);

        return new Maze(cells);
    }
}
