/**
 * Logic of the game.
 * Set the game.
 * Deal with the move.
 * Check win or lose.
 */

package game;

import ca.cmpt213.ui.GameUI;

public class GameLogic {
    public static void main (String [] args) {
        // Set the game.

        Coordinate maxCoordinate = new Coordinate(19, 14);
        Maze maze = new Maze(maxCoordinate);
        maze.makeMaze();
        Map map = new Map(maze.getMaze());
        Game game = new Game(map);
        GameUI gameUI = new GameUI(map);

        // Print welcome and help to the user.
        gameUI.showWelcome();
        gameUI.presentMap();

        // Game start.
        boolean ifReset = false;
        while (map.getCheeseCollected() != map.getMaxCheese()) {
            // If the game is restarted, print the new maze.
            if (ifReset) {
                gameUI.presentMap();
                ifReset = false;
            }

            // Deal with the user's choice.
            int result = doChoice(game, gameUI);

            // Move occurs, print the maze.
            if (result == 2) {
                gameUI.presentMap();
            }

            // Cheese is eaten.
            if (result == 1) {
                System.out.println("Congratulations! You won!");

                // If all cheese has been eaten, game over.
                if (map.getCheeseCollected() == map.getMaxCheese()){
                    break;
                }

                // Reset the game
                maze.makeMaze();
                map.setMaze(maze.getMaze());
                game = new Game(map);
                gameUI = new GameUI(map);

                ifReset = true;
            }

            // Meet a cat, game lose.
            if (result == -1) {
                break;
            }
        }

        // Finally, show the result
        gameUI.showMap();
        gameUI.presentMap();
        if (map.getCheeseCollected() != map.getMaxCheese()) {
            System.out.println("GAME OVER; please try again.");
        }
    }

    private static int doChoice(Game game, GameUI gameUi) {
        // Get the choice input by the user.
        int choice = gameUi.readChoice();

        int result = 0;

        // case
        switch (choice) {
            // input W(up)
            case 1:
                result = game.mouseMove(1);
                break;
            // input A(left)
            case 2:
                result = game.mouseMove(2);
                break;
            // input S(down)
            case 3:
                result = game.mouseMove(3);
                break;
            // input D(right)
            case 4:
                result = game.mouseMove(4);
                break;
            // input m(map)
            case 5:
                gameUi.showMap();
                gameUi.presentMap();
                break;
            // input c(cheese)
            case 6:
                game.getGamMap().setMaxCheese(1);
                break;
            // input ?(help)
            case 7:
                gameUi.showHelp();
                break;
            // other input(not possible)
            default:
                System.out.println("default");
                break;

        }
        return result;
    }
}
