/**
 * It is a class to store one point.
 * It has tow variables to show if it is a wall
 * and if it is visited by depth-first search.
 */

package game;

public class VisitMaze {
    private boolean isWall; // True is wall and false is road
    private boolean isVisit;

    public VisitMaze() {
        this.isWall = false;
        this.isVisit = false;
    }

    public VisitMaze(boolean isWall, boolean isVisit) {
        this.isWall = isWall;
        this.isVisit = isVisit;
    }

    public boolean getWall() {
        return isWall;
    }

    public boolean getVisit() {
        return isVisit;
    }

    public void setWall(boolean wall) {
        this.isWall = wall;
    }

    public void setVisit(boolean visit) {
        this.isVisit = visit;
    }

    @Override
    public String toString() {
        return "VisitMaze{" +
                "isWall=" + isWall +
                ", isVisit=" + isVisit +
                '}';
    }
}
