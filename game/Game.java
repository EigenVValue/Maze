/**
 * Do the move of cats and mouse.
 * Check if the move is successful.
 */

package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Map gamMap;

    public Game(Map gamMap) {
        this.gamMap = gamMap;
    }

    public Map getGamMap() {
        return gamMap;
    }

    public void setGamMap(Map gamMap) {
        this.gamMap = gamMap;
    }

    public int mouseMove (int choice) {
        // Try to move to the coordinate that user choose.
        Coordinate currentCoordinate = moveTo(choice, getGamMap().getCoordinateOfMouse());

        // Return 0: move not occurs, continue.
        // Return 2: move occurs, continue.
        // Return -1: game over.
        // Return 1: game win.

        // There is a wall.
        if (thereIsWall(currentCoordinate)) {
            System.out.println("Invalid move: you cannot move through walls!");
            return 0;
        }

        // There is a cat.
        if (thereIsCats(currentCoordinate)) {
            return -1;
        }

        // There is a cheese.
        if (thereIsCheese(currentCoordinate)) {
            getGamMap().setCoordinateOfMouse(currentCoordinate);
            return 1;
        }

        // Move of the mouse is success, let cats move.
        catMove();

        // Check cat again.
        if (thereIsCats(currentCoordinate)) {
            return -1;
        }

        // Reset coordinate of mouse
        getGamMap().setCoordinateOfMouse(currentCoordinate);
        return 2;
    }

    private Coordinate moveTo(int choice, Coordinate currentCoordinate) {
        // Do the move.

        int x = currentCoordinate.getX();
        int y = currentCoordinate.getY();
        Coordinate temp = new Coordinate(x, y);

        switch (choice) {
            // input W(up)
            case 1:
                temp.setY(y - 1);
                break;
            // input A(left)
            case 2:
                temp.setX(x - 1);
                break;
            // input S(down)
            case 3:
                temp.setY(y + 1);
                break;
            // input D(right)
            case 4:
                temp.setX(x + 1);
                break;
            default:
                System.out.println("default");
                break;
        }
        return temp;
    }

    private void catMove () {
        List<Coordinate> listOfCats = new ArrayList<>();
        int numOfCats = 0;

        while (numOfCats < 3) {
            // Get a random number from 1 to 4.
            Random r = new Random();
            int choice = r.nextInt(4 - 1 + 1) + 1;

            // Do the move.
            Coordinate currentCoordinate = moveTo(choice, getGamMap().getCoordinatesOfCats().get(numOfCats));

            // Move success.
            if (!thereIsWall(currentCoordinate)) {
                listOfCats.add(currentCoordinate);

                // Let next cat moves.
                numOfCats++;
            }
        }

        getGamMap().setCoordinatesOfCats(listOfCats);
    }

    private boolean thereIsWall (Coordinate currentCoordinate) {
        // Check if walk into a wall.

        int x = currentCoordinate.getX();
        int y = currentCoordinate.getY();
        return getGamMap().getMaze()[x][y];
    }

    public boolean thereIsCats (Coordinate currentCoordinate) {
        // Check if mouse meets cat.

        for (int i = 0; i < getGamMap().getCoordinatesOfCats().size(); i++) {
            if (currentCoordinate.getX() == getGamMap().getCoordinatesOfCats().get(i).getX()
                    && currentCoordinate.getY() == getGamMap().getCoordinatesOfCats().get(i).getY()) {
                System.out.println("I'm sorry, you have been eaten!");
                return true;
            }
        }
        return false;
    }

    public boolean thereIsCheese (Coordinate currentCoordinate) {
        // Check if mouse meets cheese.

        if (getGamMap().getCoordinateOfCheese().getX() == currentCoordinate.getX()
                && getGamMap().getCoordinateOfCheese().getY() == currentCoordinate.getY()) {
            getGamMap().setCheeseCollected();
            return true;
        } else {
            return false;
        }
    }
}
