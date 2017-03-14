package ash.maze.domain;

import ash.maze.explorer.Direction;
import lombok.Value;

@Value
public class Move {

    private Direction direction;
    private Point point;
}
