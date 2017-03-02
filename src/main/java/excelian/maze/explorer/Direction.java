package excelian.maze.explorer;

import excelian.maze.domain.Point;

public enum Direction {

    NORTH {
        @Override
        public Direction turnLeft() {
            return WEST;
        }

        @Override
        public Direction turnRight() {
            return EAST;
        }

        @Override
        public Point move(Point point) {
            return new Point(point.getX() + 1, point.getY());
        }
    },
    SOUTH {
        @Override
        public Direction turnLeft() {
            return EAST;
        }

        @Override
        public Direction turnRight() {
            return WEST;
        }

        @Override
        public Point move(Point point) {
            return new Point(point.getX() - 1, point.getY());
        }
    },
    EAST {
        @Override
        public Direction turnLeft() {
            return NORTH;
        }

        @Override
        public Direction turnRight() {
            return SOUTH;
        }

        @Override
        public Point move(Point point) {
            return new Point(point.getX(), point.getY() + 1);
        }
    },
    WEST {
        @Override
        public Direction turnLeft() {
            return SOUTH;
        }

        @Override
        public Direction turnRight() {
            return NORTH;
        }

        @Override
        public Point move(Point point) {
            return new Point(point.getX(), point.getY() - 1);
        }
    };

    public abstract Direction turnLeft();
    public abstract Direction turnRight();
    public abstract Point move(Point point);
}
