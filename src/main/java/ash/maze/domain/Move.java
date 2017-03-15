package ash.maze.domain;

import lombok.Value;

@Value
public class Move {

    private Direction direction;
    private Point point;
}
