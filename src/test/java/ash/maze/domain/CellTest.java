package ash.maze.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CellTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetStartUpperCase(){
        Cell c = Cell.getCell('S');
        assertThat(c, equalTo(Cell.START));
    }

    @Test
    public void testGetStartLowerCase(){
        Cell c = Cell.getCell('s');
        assertThat(c, equalTo(Cell.START));
    }

    @Test
    public void testGetFinishUpperCase(){
        Cell c = Cell.getCell('F');
        assertThat(c, equalTo(Cell.FINISH));
    }

    @Test
    public void testGetFinishLowerCase(){
        Cell c = Cell.getCell('f');
        assertThat(c, equalTo(Cell.FINISH));
    }

    @Test
    public void testGetWallUpperCase(){
        Cell c = Cell.getCell('X');
        assertThat(c, equalTo(Cell.WALL));
    }

    @Test
    public void testGetWallLowerCase(){
        Cell c = Cell.getCell('x');
        assertThat(c, equalTo(Cell.WALL));
    }

    @Test
    public void testGetSpace(){
        Cell c = Cell.getCell(' ');
        assertThat(c, equalTo(Cell.SPACE));
    }

    @Test
    public void testGetPath(){
        Cell c = Cell.getCell('+');
        assertThat(c, equalTo(Cell.PATH));
    }

    @Test
    public void testUnexpectedCellValueLowerCase(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("No cell type for 'Z'"));

        Cell.getCell('Z');
    }

    @Test
    public void testUnexpectedCellValueUpperCase(){
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(equalTo("No cell type for 'z'"));

        Cell.getCell('z');
    }

}