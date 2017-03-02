package excelian.maze.explorer;

import excelian.maze.domain.Cell;
import excelian.maze.domain.Maze;
import excelian.maze.domain.Point;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Set;
import javaslang.control.Try;

public class Explorer {

    private final Maze maze;
    private List<Point> route;
    private Direction currentDirection;
    private Set<Point> visitedPoints;

    public Explorer(Maze maze){
        this.maze = maze;
        route = List.of(maze.getStart());
        visitedPoints = HashSet.ofAll(route);
        currentDirection = Direction.NORTH;
    }

    public List<Point> solve(){
        while(!hasFinished()) {
            route = moveUntilChoice();
            if(hasFinished()){
                break;
            }

            if (canMoveForward(route.last())) {
                Point move = currentDirection.move(route.last());
                route = route.append(move);
                visitedPoints = visitedPoints.add(move);
            } else if (canTurnLeft(route.last())){
                currentDirection = turnLeft();
            } else if (canTurnRight(route.last())){
                currentDirection = turnRight();
            } else {
                route = backTrack();
            }
        }

        return route;
    }

    public String getVisitedPoints(){
        return maze.printSolvedRoute(visitedPoints.toList());
    }

    public String getSolvedRoute(){
        return maze.printSolvedRoute(route);
    }

    private List<Point> backTrack(){
        List<Point> tempRoute = route.dropRight(1);
        while(!canTurnLeft(tempRoute.last()) && !canTurnRight(tempRoute.last())){

            //handle T junction
            if(canTurnAround(tempRoute.last())){
                currentDirection = currentDirection.turnLeft().turnLeft();
                break;
            }
            tempRoute = tempRoute.dropRight(1);
        }

        return tempRoute;
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

    private boolean canTurnAround(Point point){
        return canMove(currentDirection.turnLeft().turnLeft(), point);
    }

    private boolean canMove(Direction direction, Point point){
        Point move = direction.move(point);
        return Try.of(() -> maze.getCell(move))
                .map(cell -> (cell == Cell.FINISH || cell == Cell.SPACE) && !visitedPoints.contains(move))
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
            visitedPoints = visitedPoints.add(move);

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
