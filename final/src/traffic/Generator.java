package traffic;

/**
 * Created by gallaghers on 5/24/18.
 */
public class Generator extends TrafficNode {

    private RoadSegment exitRoad;

    Generator(RoadSegment exitRoad) {
        this.exitRoad=exitRoad;
    }

    public String getTypeOfIntersection() {
        return "Generator";
    }

    /**
     * Car spawners don't need to take any cars in.
     */
    public void takeCar() {}

    /**
     * Add a car to the exit road if possible.
     */
    public void sendCar() {
        if(exitRoad.getCarAtFront()==0) {
            exitRoad.addCarAtFront(1);
        }
    }


}

