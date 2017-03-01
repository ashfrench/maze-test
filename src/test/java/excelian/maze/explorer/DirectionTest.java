package excelian.maze.explorer;

import org.junit.Test;

import static excelian.maze.explorer.Direction.*;
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

}