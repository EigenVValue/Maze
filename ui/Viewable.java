/**
 * Contains 2 arguments
 * isViewable: if the coordinate is viewable.
 * thingToView: things current in the coordinate.
 */

package ui;

public class Viewable {
    boolean isViewable;
    char thingToView;

    public Viewable() {
        this.isViewable = false;
        this.thingToView = ' ';
    }

    public Viewable(boolean isViewable, char thingToView) {
        this.isViewable = isViewable;
        this.thingToView = thingToView;
    }

    public boolean isViewable() {
        return isViewable;
    }

    public void setViewable(boolean viewable) {
        isViewable = viewable;
    }

    public char getThingToView() {
        return thingToView;
    }

    public void setThingToView(char thingToView) {
        this.thingToView = thingToView;
    }

    @Override
    public String toString() {
        return "Viewable{" +
                "isViewable=" + isViewable +
                ", thingToView=" + thingToView +
                '}';
    }
}
