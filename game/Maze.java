/**
 * Maze class stores two variables: a 2D VisitMaze array and max coordinate of the maze.
 * About making maze:
 * 1. create a default 2D maze with all point is a road and not visited;
 * 2. change the edge to wall;
 * 3. start with coordinate (1,1) and do addWall;
 * 4. in isRoom, let current point be (2,2) of a 2x2 room.
 *  Then check if all of other points is same road or wall.
 * 5. in isReachable, it use a depth-first search from current point and mark it "visited".
 *  Then search the whole maze to show if there exists a road that is not visited.
 */

package game;

import java.util.Arrays;
import java.util.Random;

public class Maze {
    private VisitMaze[][] visitMazes; // True is wall and false is road.
    private Coordinate maxCoordinate; // (19, 14)

    public Maze(Coordinate maxCoordinate) {
        this.visitMazes = new VisitMaze[maxCoordinate.getX()+1][maxCoordinate.getY()+1];
        this.maxCoordinate = maxCoordinate;
        VisitMaze visitMaze = new VisitMaze();
        for (int x = 0; x <= maxCoordinate.getX(); x++) {
            Arrays.fill(visitMazes[x], visitMaze);
        }
    }

    public VisitMaze[][] getVisitMazes() {
        return visitMazes;
    }

    private void setVisitMazes(VisitMaze[][] visitMazes) {
        this.visitMazes = visitMazes;
    }

    public Coordinate getMaxCoordinate() {
        return maxCoordinate;
    }

    private void setMaxCoordinate(Coordinate maxCoordinate) {
        this.maxCoordinate = maxCoordinate;
    }

    public boolean[][] getMaze() {
        boolean[][] maze = new boolean[this.getMaxCoordinate().getX()+1][this.getMaxCoordinate().getY()+1];
        for (int x = 0; x <= getMaxCoordinate().getX(); x++) {
            for (int y = 0; y <= getMaxCoordinate().getY(); y++) {
                maze[x][y] = this.getVisitMazes()[x][y].getWall();
            }
        }
        return maze;
    }

    private boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private void makeWallAroundEdge() {
        for (int y = 0; y <= this.getMaxCoordinate().getY(); y++) {
            this.visitMazes[0][y].setWall(true);
            this.visitMazes[this.getMaxCoordinate().getX()][y].setWall(true);
        }
        for (int x = 1; x <= this.getMaxCoordinate().getX()-1; x++) {
            this.visitMazes[x][0].setWall(true);
            this.visitMazes[x][this.getMaxCoordinate().getY()].setWall(true);
        }
    }

    private boolean isRoom(VisitMaze[][] visitMazes, Coordinate coordinate) {
        if ((visitMazes[coordinate.getX()][coordinate.getY()].getWall()
                == visitMazes[coordinate.getX()-1][coordinate.getY()].getWall())
                && (visitMazes[coordinate.getX()][coordinate.getY()].getWall()
                == visitMazes[coordinate.getX()][coordinate.getY()-1].getWall())
                && (visitMazes[coordinate.getX()][coordinate.getY()].getWall()
                == (visitMazes[coordinate.getX()-1][coordinate.getY()-1].getWall()))) {
            return true;
        }
        return false;
    }

    private boolean isReachable(Coordinate point) {
        // Do a depth-first search to show that if all the roads are reachable.
        traversal(point);
        for (int x = 1; x < this.getMaxCoordinate().getX(); x++) {
            for (int y = 1; y < this.getMaxCoordinate().getY(); y++) {
                if (!visitMazes[x][y].getWall()
                &&  !visitMazes[x][y].getVisit()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void traversal(Coordinate point) {
        visitMazes[point.getX()][point.getY()] = new VisitMaze(visitMazes[point.getX()][point.getY()].getWall(), true);
        // Go left.
        if (!visitMazes[point.getX()-1][point.getY()].getVisit()
                && !visitMazes[point.getX()-1][point.getY()].getWall()) {
            point.setX(point.getX()-1);
            traversal(point);
            point.setX(point.getX()+1);
        }
        // Go right.
        if (!visitMazes[point.getX()+1][point.getY()].getVisit()
                && !visitMazes[point.getX()+1][point.getY()].getWall()) {
            point.setX(point.getX()+1);
            traversal(point);
            point.setX(point.getX()-1);
        }
        // Go up.
        if (!visitMazes[point.getX()][point.getY()-1].getVisit()
                && !visitMazes[point.getX()][point.getY()-1].getWall()) {
            point.setY(point.getY()-1);
            traversal(point);
            point.setY(point.getY()+1);
        }
        // Go down.
        if (!visitMazes[point.getX()][point.getY()+1].getVisit()
                && !visitMazes[point.getX()][point.getY()+1].getWall()) {
            point.setY(point.getY()+1);
            traversal(point);
            point.setY(point.getY()-1);
        }
    }

    private void clearVisit() {
        for (int x = 1; x < this.getMaxCoordinate().getX(); x++) {
            for (int y = 1; y < this.getMaxCoordinate().getY(); y++) {
                this.visitMazes[x][y].setVisit(false);
            }
        }
    }

    private void clearWall(Coordinate coordinate) {
        VisitMaze tempVisitMaze = new VisitMaze(false, false);
        for (int x = coordinate.getX(); x < this.getMaxCoordinate().getX(); x++) {
            this.visitMazes[x][coordinate.getY()] = tempVisitMaze;
        }
        for (int x = 1; x < this.getMaxCoordinate().getX(); x++) {
            for (int y = coordinate.getY()+1; y < this.getMaxCoordinate().getY(); y++) {
                this.visitMazes[x][y] = tempVisitMaze;
            }
        }
    }

    private Coordinate addWall(Coordinate coordinate) {
        // If this row ends, jump to next row.
        if (coordinate.getX() >= this.getMaxCoordinate().getX()) {
            coordinate.setX(1);
            coordinate.setY(coordinate.getY()+1);
        }

        // Check if it reaches the end.
        if (coordinate.getX() == this.getMaxCoordinate().getX()-1
                && coordinate.getY() == this.getMaxCoordinate().getY()-1) {
            // Check if it is valid.
            if (isRoom(this.visitMazes, coordinate) || !isReachable(coordinate)) {
                // If it is a room, x-1 and y-1. Clear all point behind the new point.
                coordinate.setX(coordinate.getX()-1);
                coordinate.setY(coordinate.getY()-1);
                this.clearWall(coordinate);
                return coordinate;
            }
            coordinate.setX(coordinate.getX()+1);
            return coordinate;
        }

        // Adding, If not reach the end.
        VisitMaze temp = new VisitMaze(this.getRandomBoolean(), false);
        this.visitMazes[coordinate.getX()][coordinate.getY()] = temp;

        // Check if it is a room.
        if (isRoom(this.visitMazes, coordinate)) {
            // Road changes to wall or wall changes to road.
            if (this.visitMazes[coordinate.getX()][coordinate.getY()].getWall())
                this.visitMazes[coordinate.getX()][coordinate.getY()] = new VisitMaze(false, false);
            else this.visitMazes[coordinate.getX()][coordinate.getY()] = new VisitMaze(true, false);
        }

        // After checking end point and room, check if it is reachable.
        if ((!this.getVisitMazes()[coordinate.getX()][coordinate.getY()].getWall())
            && (!isReachable(coordinate))) {
            if (coordinate.getX() > 1) {
                coordinate.setX(coordinate.getX()-1);
            }
            if (coordinate.getY() > 1) {
                coordinate.setY(coordinate.getY()-1);
            }
            this.clearWall(coordinate);
            return coordinate;
        }
        this.clearVisit();

        // Confirm this step.
        coordinate.setX(coordinate.getX()+1);
        return coordinate;
    }

    public void makeMaze() {
        this.clearWall(this.getMaxCoordinate());
        Coordinate coordinate = new Coordinate(1,1);
        this.makeWallAroundEdge();
        while (coordinate.getX() != (this.getMaxCoordinate().getX())
                    || coordinate.getY() != (this.getMaxCoordinate().getY()-1)) {
            if ((coordinate.getX() == this.getMaxCoordinate().getX()-1 && coordinate.getY() == 1)
                || (coordinate.getX() == 1 && coordinate.getY() == this.getMaxCoordinate().getY()-1)) {
                coordinate.setX(coordinate.getX() + 1);
                continue;
            }
            addWall(coordinate);
        }
    }

    @Override
    public String toString() {
        return "Maze{" +
                "visitMazes=" + Arrays.toString(visitMazes) +
                ", maxCoordinate=" + maxCoordinate +
                '}';
    }
}