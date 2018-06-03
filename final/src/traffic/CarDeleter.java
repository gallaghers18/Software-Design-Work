package traffic;

public class CarDeleter extends TrafficNode {

    private RoadSegment entranceRoad;

    CarDeleter(RoadSegment entranceRoad) {
        this.entranceRoad=entranceRoad;
        entranceRoad.setOut(this);
    }

    public String getTypeOfIntersection() {
        return "CarDeleter";
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
