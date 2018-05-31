package traffic;

public class CarDeleter extends TrafficNode {

    private RoadSegment entranceRoad;

    public void CarDeleter() {}

    public String getTypeOfIntersection() {
        return "CarDeleter";
    }

    /**
     * Take cars into the deleter.
     */
    public void takeCar() {}

    /**
     * Cars don't leave their destination. They just disappear.
     */
    public void sendCar() {}

}
