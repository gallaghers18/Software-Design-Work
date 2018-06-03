package traffic;

import java.util.ArrayList;

/**
 * Created by gallaghers on 5/24/18.
 */
public class Stoplight extends TrafficNode {

    private int xpos;
    private int ypos;
    public ArrayList<RoadSegment> north_to_south; //ordered by: source, destination
    public ArrayList<RoadSegment> south_to_north;
    public ArrayList<RoadSegment> east_to_west;
    public ArrayList<RoadSegment> west_to_east;
    private boolean open_north_south;
    private int car_going_NE=0;
    private int car_going_SW=0;


    public Stoplight(ArrayList<RoadSegment> roads, int xpos, int ypos) {
        north_to_south = new ArrayList(roads.subList(0,2));
        if(north_to_south.get(0)!=null){
            north_to_south.get(0).setOut(this);
        }
        if(north_to_south.get(1)!=null){
            north_to_south.get(1).setIn(this);
        }
        south_to_north = new ArrayList(roads.subList(2,4));
        if(south_to_north.get(0)!=null){
            south_to_north.get(0).setOut(this);
        }
        if(south_to_north.get(1)!=null){
            south_to_north.get(1).setIn(this);
        }
        east_to_west = new ArrayList(roads.subList(4,6));
        if(east_to_west.get(0)!=null){
            east_to_west.get(0).setOut(this);
        }
        if(east_to_west.get(1)!=null){
            east_to_west.get(1).setIn(this);
        }
        west_to_east = new ArrayList(roads.subList(6,8));
        if(west_to_east.get(0)!=null){
            west_to_east.get(0).setOut(this);
        }
        if(west_to_east.get(1)!=null){
            west_to_east.get(1).setIn(this);
        }
        xpos=0;
        ypos=0;
        open_north_south=false;
    }


    public String getTypeOfIntersection() {
        return "Stoplight";
    }

    /**
     * Take cars into the intersection (from road segments) based on stoplight rules.
     */
    @Override
    public void takeCar() {
        if(open_north_south){
            if(south_to_north.get(0).getCarAtEnd()>0 && car_going_NE==0) {
                car_going_NE=south_to_north.get(0).removeCarAtEnd();
            }
            if(north_to_south.get(0).getCarAtEnd()>0 && car_going_SW==0) {
                car_going_SW=north_to_south.get(0).removeCarAtEnd();
            }
        }
        else{
            if(west_to_east.get(0).getCarAtEnd()>0 && car_going_NE==0) {
                car_going_NE=west_to_east.get(0).removeCarAtEnd();
            }
            if(east_to_west.get(0).getCarAtEnd()>0 && car_going_SW==0) {
                car_going_SW=east_to_west.get(0).removeCarAtEnd();
            }
        }
    }

    /**
     * Sends cars out of the intersection (onto road segments) based on stoplight rules.
     */
    @Override
    public void sendCar() {
        if(open_north_south){
            if(south_to_north.get(1).getCarAtFront()==0 && car_going_NE>0) {
                south_to_north.get(1).addCarAtFront(car_going_NE+1);
                car_going_NE=0;
            }
            if(north_to_south.get(1).getCarAtFront()==0 && car_going_SW>0) {
                north_to_south.get(1).addCarAtFront(car_going_SW+1);
                car_going_SW=0;
            }
        }
        else{
            if(west_to_east.get(1).getCarAtFront()==0 && car_going_NE>0) {
                west_to_east.get(1).addCarAtFront(car_going_NE+1);
                car_going_NE=0;
            }
            if(east_to_west.get(1).getCarAtFront()==0 && car_going_SW>0) {
                east_to_west.get(1).addCarAtFront(car_going_SW+1);
                car_going_SW=0;
            }
        }
    }

    public void changeLight() {
        open_north_south=!open_north_south;
    }
}


