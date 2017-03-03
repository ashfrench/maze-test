package excelian.maze.domain;

import java.util.stream.Stream;

public enum Cell {

    WALL('X'),
    SPACE(' '),
    START('S'),
    FINISH('F'),
    PATH('+');

    private char cellChar;

    Cell(char cellChar){
        this.cellChar = cellChar;
    }

    public static Cell getCell(char c){
        char upperChar = ("" + c).toUpperCase().charAt(0);
        return Stream.of(values()).filter(cell -> cell.cellChar == upperChar)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No cell type for '" + c + "'"));
    }

    @Override
    public String toString() {
        return "" + cellChar;
    }
}
