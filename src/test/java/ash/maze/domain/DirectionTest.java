package ash.maze.domain;

import ash.maze.domain.Direction;
import ash.maze.domain.Point;
import org.junit.Test;

import static ash.maze.domain.Direction.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DirectionTest {

    @Test
    public void testNorthTurnLeft(){
        Direction turnLeft = NORTH.turnLeft();
        assertThat(turnLeft, equalTo(WEST));
    }

    @Test
    public void testNorthTurnRight(){
        Direction turnRight = NORTH.turnRight();
        assertThat(turnRight, equalTo(EAST));
    }

    @Test
    public void testEastTurnLeft(){
        Direction turnLeft = EAST.turnLeft();
        assertThat(turnLeft, equalTo(NORTH));
    }

    @Test
    public void testEastTurnRight(){
        Direction turnRight = EAST.turnRight();
        assertThat(turnRight, equalTo(SOUTH));
    }

    @Test
    public void testSouthTurnLeft(){
        Direction turnLeft = SOUTH.turnLeft();
        assertThat(turnLeft, equalTo(EAST));
    }

    @Test
    public void testSouthTurnRight(){
        Direction turnRight = SOUTH.turnRight();
        assertThat(turnRight, equalTo(WEST));
    }

    @Test
    public void testWestTurnLeft(){
        Direction turnLeft = WEST.turnLeft();
        assertThat(turnLeft, equalTo(SOUTH));
    }

    @Test
    public void testWestTurnRight(){
        Direction turnRight = WEST.turnRight();
        assertThat(turnRight, equalTo(NORTH));
    }

    @Test
    public void testNorthMove(){
        Point move = NORTH.move(new Point(1, 1));
        assertThat(move, equalTo(new Point(2, 1)));
    }

    @Test
    public void testSouthMove(){
        Point move = SOUTH.move(new Point(1, 1));
        assertThat(move, equalTo(new Point(0, 1)));
    }

    @Test
    public void testEastMove(){
        Point move = EAST.move(new Point(1, 1));
        assertThat(move, equalTo(new Point(1, 2)));
    }

    @Test
    public void testWestMove(){
        Point move = WEST.move(new Point(1, 1));
        assertThat(move, equalTo(new Point(1, 0)));
    }

}