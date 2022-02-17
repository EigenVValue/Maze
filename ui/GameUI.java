/**
 * Ask user's input.
 * Print information to user.
 */

package ui;

import game.Map;

import java.util.Arrays;
import java.util.Scanner;

public class GameUI {
    private Map map;
    private Viewable[][] emptyMap;

    public GameUI(Map map) {
        this.map = map;

        int x = map.getMaze().length;
        int y = map.getMaze()[0].length;

        // Set a new 2D array to show if a coordinate is viewable or not.
        this.emptyMap = new Viewable[x][y];

        for (int i = 0; i < x; i++) {
            Viewable viewable = new Viewable();
            Arrays.fill(emptyMap[i], viewable);
            for (int j = 0; j < y; j++) {
                // Read locations of walls.
                if (getMap().getMaze()[i][j]) {
                    emptyMap[i][j] = new Viewable(false, '#');
                }

                // Outside walls are always viewable.
                if (i == 0 || i == (x - 1) || j == 0 || j == (y - 1)) {
                    emptyMap[i][j].setViewable(true);
                }
            }
        }

        // Location of the cheese is also viewable.
        int xOfCheese = getMap().getCoordinateOfCheese().getX();
        int yOfCheese = getMap().getCoordinateOfCheese().getY();
        emptyMap[xOfCheese][yOfCheese] = new Viewable(true, '$');
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Viewable[][] getEmptyMap() {
        return emptyMap;
    }

    public void setEmptyMap(Viewable[][] emptyMap) {
        this.emptyMap = emptyMap;
    }

    public int readChoice() {
        // Ask user's input.

        System.out.println("Enter your move [WASD?]:");
        Scanner in = new Scanner(System.in);
        char choice = in.next().charAt(0);

        // Upper case and lower case is not important.
        // Turn the choice to number, so it is easier to be used in other classes.
        if (choice == 'W' || choice == 'w') {
            return 1;
        } else if (choice == 'A' || choice == 'a') {
            return 2;
        } else if (choice == 'S' || choice == 's') {
            return 3;
        } else if (choice == 'D' || choice == 'd') {
            return 4;
        } else if (choice == 'M' || choice == 'm') {
            return 5;
        } else if (choice == 'C' || choice == 'c') {
            return 6;
        } else if (choice == '?') {
            return 7;
        } else {
            System.out.println("Invalid move. " +
                    "Please enter just A (left), S (down), D (right), or W (up).");
            return readChoice();
        }
    }

    public void presentMap () {
        // Print the whole maze and other information to user.

        System.out.println();
        System.out.println("Maze:");

        // Get mouse's location.
        int xOfMouse = getMap().getCoordinateOfMouse().getX();
        int yOfMouse = getMap().getCoordinateOfMouse().getY();

        // Set new view range as mouse moved.
        for (int i = (xOfMouse-1); i <= (xOfMouse+1); i++) {
            for (int j = (yOfMouse-1); j <= (yOfMouse+1); j++) {
                char temp = emptyMap[i][j].getThingToView();
                emptyMap[i][j] = new Viewable(true, temp);
            }
        }

        // Print maze
        for (int y = 0; y < getEmptyMap()[0].length; y++) {
            for (int x = 0; x < getEmptyMap().length; x++) {

                // Print mouse.
                if (x == xOfMouse && y == yOfMouse) {
                    System.out.print('@');
                    continue;
                }

                // Print all 3 cats.
                boolean isCat = false;
                for (int k = 0; k < getMap().getCoordinatesOfCats().size(); k++) {
                    int xOfCat = getMap().getCoordinatesOfCats().get(k).getX();
                    int yOfCat = getMap().getCoordinatesOfCats().get(k).getY();
                    if (x == xOfCat && y == yOfCat) {
                        System.out.print('!');
                        isCat = true;
                    }
                }

                if (isCat) {
                    continue;
                }

                // Print other things
                if (emptyMap[x][y].isViewable()) {
                    System.out.print(emptyMap[x][y].getThingToView());
                }
                else {
                    System.out.print('·');
                }
            }
            System.out.println();
        }

        // Print the number of cheeses collected.
        System.out.println("Cheese collected: " +
                getMap().getCheeseCollected() +
                " of " +
                getMap().getMaxCheese());
    }

    public void showWelcome() {
        // Print the welcome words.

        String welcome = "Welcome to Cat and Mouse Maze Adventure!";
        String students = "by David and Skyler";

        printLine(welcome);
        System.out.println(welcome);
        System.out.println(students);
        printLine(welcome);

        System.out.println();
        showHelp();
        System.out.println();
    }

    private void printLine(String line){
        // Draw a line with suitable length.

        for (int i = 0; i < line.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public void showHelp() {
        // Print help information.
        System.out.println();
        System.out.println("DIRECTIONS:\n" +
                "\tFind 5 cheese before a cat eats you!\n" +
                "LEGEND:\n" +
                "\t#: Wall\n" +
                "\t@: You (a mouse)\n" +
                "\t!: Cat\n" +
                "\t$: Cheese\n" +
                "\t.: Unexplored space\n" +
                "MOVES:\n" +
                "\tUse W (up), A (left), S (down) and D (right) to move.\n" +
                "\t(You must press enter after each move).\n");
    }

    public void showMap() {
        // If user input 'm', set all coordinates to viewable.

        for (int i = 0; i < emptyMap.length; i++) {
            for (int j = 0; j < emptyMap[0].length; j++) {
                emptyMap[i][j].setViewable(true);
            }
        }
    }

    @Override
    public String toString() {
        return "GameUI{" +
                "map=" + map +
                ", emptyMap=" + Arrays.toString(emptyMap) +
                '}';
    }
}