package traffic;

/**
 * Created by gallaghers on 5/24/18.
 * Superclass for anything a roadsegment connects to. Uncommented functions are meant to be overridden.
 */
public class TrafficNode {

    private int xpos;
    private int ypos;

    public TrafficNode() {}

    /**
     * Getter for all subclasses
     */
    public int getXpos() {
        return xpos;
    }

    /**
     * Getter for all subclasses
     */
    public int getYpos() {
        return ypos;
    }

    /**
     * Setter for all subclasses
     */
    public void setXpos(int x) {
        xpos = x;
    }

    /**
     * Setter for all subclasses
     */
    public void setYpos(int y) {
        ypos = y;
    }

    public void takeCar() {}

    public void sendCar() {}

}
