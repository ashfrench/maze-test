package excelian.maze.explorer;

import excelian.maze.domain.Cell;
import excelian.maze.domain.Maze;
import excelian.maze.domain.Point;
import javaslang.collection.List;
import javaslang.control.Try;

public class Explorer {

    private final Maze maze;
    private List<Point> route;
    private Direction currentDirection;
    private List<List<Point>> choices;

    public Explorer(Maze maze){
        this.maze = maze;
        route = List.of(maze.getStart());
        choices = List.of(route);
        currentDirection = Direction.NORTH;
    }

    public List<Point> solve(){
        while(!hasFinished()) {
            route = moveUntilChoice();

            if (canMoveForward(route.last())) {
                route = route.append(currentDirection.move(route.last()));
            } else if (canTurnLeft(route.last())){
                currentDirection = turnLeft();
            } else if (canTurnRight(route.last())){
                currentDirection = turnRight();
            } else {
                //todo deal with back tracking
            }
        }

        return route;
    }

    private boolean canMoveForward(Point point){
        return canMove(currentDirection, point);
    }

    private boolean canTurnLeft(Point point){
        return canMove(currentDirection.turnLeft(),point);
    }

    private boolean canTurnRight(Point point){
        return canMove(currentDirection.turnRight(), point);
    }

    private boolean canMove(Direction direction, Point point){
        return Try.of(() -> maze.getCell(direction.move(point)))
                .map(cell -> cell == Cell.FINISH || cell == Cell.SPACE)
                .getOrElse(false);
    }

    private boolean canOnlyMoveForward(Point point) {
        return !canTurnLeft(point) && !canTurnRight(point) && canMoveForward(point);
    }

    private List<Point> moveUntilChoice() {
        List<Point> tempRoute = List.ofAll(route);
        while(canOnlyMoveForward(tempRoute.last())){
            Point move = currentDirection.move(tempRoute.last());
            tempRoute = tempRoute.append(move);

            if(move == maze.getFinish()){
                break;
            }
        }
        return tempRoute;
    }

    private boolean hasFinished(){
        return maze.getCell(route.last()) == Cell.FINISH;
    }


    private Direction turnLeft() {
        return currentDirection.turnLeft();
    }

    private Direction turnRight() {
        return currentDirection.turnRight();
    }

}
