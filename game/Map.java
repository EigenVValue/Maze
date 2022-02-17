/**
 * Store coordinates of mouse/cats/cheese.
 * Set a cheese randomly.
 * Store the number of cheese collected.
 */

package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Map {
    private boolean[][] maze;
    private List<Coordinate> coordinatesOfCats;
    private Coordinate coordinateOfCheese;
    private Coordinate coordinateOfMouse;

    private int maxCheese = 5;
    private int cheeseCollected = 0;

    public Map(boolean[][] maze) {
        this.maze = maze;
        setNewMap();
    }

    private void setNewMap() {
        // Put cats and mouse on their origins.
        // Set a cheese randomly.

        int x = maze.length;
        int y = maze[0].length;

        this.coordinatesOfCats = new ArrayList<>();

        Coordinate cat1 = new Coordinate(x-2, 1);
        Coordinate cat2 = new Coordinate(1, y-2);
        Coordinate cat3 = new Coordinate(x-2, y-2);
        this.coordinatesOfCats.add(cat1);
        this.coordinatesOfCats.add(cat2);
        this.coordinatesOfCats.add(cat3);

        this.coordinateOfMouse = new Coordinate(1, 1);

        this.coordinateOfCheese = randomCheese();
    }

    public List<Coordinate> getCoordinatesOfCats() {
        return coordinatesOfCats;
    }

    public void setCoordinatesOfCats(List<Coordinate> coordinatesOfCats) {
        this.coordinatesOfCats = coordinatesOfCats;
    }

    public Coordinate getCoordinateOfMouse() {
        return coordinateOfMouse;
    }

    public void setCoordinateOfMouse(Coordinate coordinateOfMouse) {
        this.coordinateOfMouse = coordinateOfMouse;
    }

    public Coordinate getCoordinateOfCheese() {
        return coordinateOfCheese;
    }

    public void setCoordinateOfCheese() {
        this.coordinateOfCheese = randomCheese();
    }

    public boolean[][] getMaze() {
        return maze;
    }

    public void setMaze(boolean[][] maze) {
        // Reset maze.
        // Put coordinates of mouse/cats to origins.
        // Set a new cheese randomly.
        this.maze = maze;
        setNewMap();
    }

    public int getMaxCheese() {
        return maxCheese;
    }

    public void setMaxCheese(int maxCheese) {
        this.maxCheese = maxCheese;
    }

    public int getCheeseCollected() {
        return cheeseCollected;
    }

    public void setCheeseCollected() {
        this.cheeseCollected++;
    }

    private Coordinate randomCheese () {
        // Set a cheese randomly.

        int x = 0;
        int y = 0;
        while (maze[x][y]) {
            Random random = new Random();
            x = random.nextInt(17)+1;
            y = random.nextInt(13)+1;
            if ((x == 1 && y == 1)
                    || (x == maze.length-1 && y == 1)
                    || (x == 1 && y == maze[0].length)
                    || (x == maze.length && y == 1)) {
                x = 0;
                y = 0;
            }
        }
        Coordinate cheese = new Coordinate(x, y);
        return cheese;
    }

    @Override
    public String toString() {
        return "Map{" +
                "maze=" + Arrays.toString(maze) +
                ", coordinatesOfCats=" + coordinatesOfCats +
                ", coordinateOfCheese=" + coordinateOfCheese +
                ", coordinateOfMouse=" + coordinateOfMouse +
                ", maxCheese=" + maxCheese +
                ", cheeseCollected=" + cheeseCollected +
                '}';
    }
}
