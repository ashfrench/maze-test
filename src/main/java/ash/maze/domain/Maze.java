package ash.maze.domain;

import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Try;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

public class Maze {

    private Cell[][] maze;

    @Getter
    private Point start;
    @Getter
    private Point finish;

    public Maze(Cell[][] maze) {
        validateMaze(maze);
        this.maze = maze;
        setStartAndFinish();
    }

    public Cell getCell(Point point){
        Objects.requireNonNull(point, "Cannot get cell for null point");
        Try<Cell> tryCell = Try.of(() -> maze[point.getX()][point.getY()]);
        return tryCell.getOrElseThrow(() -> new RuntimeException("X Point must be in the range 0 -> " + (maze.length - 1) +
                                     " Y point must be in the range 0 -> " + (maze[0].length - 1) + "\n" +
                                     "You supplied " + point));
    }

    private void validateMaze(Cell[][] maze) {
        Objects.requireNonNull(maze, "Maze cannot be null");
        validateMazeSize(maze);
        validateMazeCellCounts(maze);
    }

    private void setStartAndFinish() {
        for(int x = 0; x < maze.length; x ++){
            for(int y = 0; y < maze[x].length; y ++){
                if(maze[x][y] == Cell.START) {
                    start = new Point(x, y);
                } else if(maze[x][y] == Cell.FINISH) {
                    finish = new Point(x, y);
                }
            }
        }
    }

    private void validateMazeSize(Cell[][] maze) {
        if(maze.length == 0){
            throw new RuntimeException("Maze must contain some cells, it cannot be empty");
        }

        int width = maze[0].length;
        if (width == 0){
            throw new RuntimeException("Maze must contain some cells");
        }

        if(!Stream.of(maze).allMatch(cells -> cells.length == width)){
            throw new RuntimeException("Maze must be square or rectangular");
        }
    }

    private void validateMazeCellCounts(Cell[][] maze) {
        Map<Cell, Integer> cellsCount = Array.of(maze)
                .flatMap(Array::of)
                .groupBy(c -> c)
                .mapValues(Array::length);

        cellsCount.get(Cell.START)
                .filter(i -> i == 1)
                .getOrElseThrow(() -> new RuntimeException("Maze should only contain one start cell"));

        cellsCount.get(Cell.FINISH)
                .filter(i -> i == 1)
                .getOrElseThrow(() -> new RuntimeException("Maze should only contain one finish cell"));

        //Throws is there are paths, otherwise you get back another empty option
        cellsCount.get(Cell.PATH)
                .map(aLong -> {throw new RuntimeException("Should have no path set when creating the maze");});

    }

    @Override
    public String toString() {
        return printSolvedRoute(Array.of());
    }

    public String printSolvedRoute(Array<Point> points){
        StringBuilder sb = new StringBuilder();
        for(int x = 0; x < maze.length; x ++){
            for(int y = 0; y < maze[x].length; y ++){
                Point point = new Point(x, y);
                Cell cell = maze[x][y];
                if(cell == Cell.SPACE){
                    if(points.contains(point)){
                        sb.append(Cell.PATH.toString());
                    } else {
                        sb.append(Cell.SPACE.toString());
                    }
                } else {
                    sb.append(cell.toString());
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
