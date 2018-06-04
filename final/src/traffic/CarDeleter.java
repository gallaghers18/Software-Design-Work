package traffic;

/**
 * Sean Gallagher & David White
 * Class used in model. Deleted cars that have reached end of a road segment.
 */

public class CarDeleter extends TrafficNode {

    private RoadSegment entranceRoad;

    /**
     * Constructor
     */
    CarDeleter(RoadSegment entranceRoad,int xpos, int ypos) {
        this.entranceRoad=entranceRoad;
        super.setXpos(xpos);
        super.setYpos(ypos);
        entranceRoad.setOut(this);
    }

    /**
     * Take cars into the deleter.
     */
    public void takeCar() {
        entranceRoad.removeCarAtEnd();
    }

    /**
     * Cars don't leave their destination. They just disappear.
     */
    public void sendCar() {}

}
