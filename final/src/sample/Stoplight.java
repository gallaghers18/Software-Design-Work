package sample;

/**
 * Created by gallaghers on 5/24/18.
 */
public class Stoplight implements Intersection{

    public ArrayList<RoadSegment> north_to_south; //ordered source, destination
    public ArrayList<RoadSegment> south_to_north;
    public ArrayList<RoadSegment> east_to_west;
    public ArrayList<RoadSegment> west_to_east;
    private boolean open_north_south;
    private int car_going_NE;
    private int car_going_SW;


    public void StopLight() {}


    public String getTypeOfIntersection() {
        return "Stoplight";
    }

    public void takeCar() {}

    public void sendCar() {}

}


