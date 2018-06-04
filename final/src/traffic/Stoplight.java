package traffic;

import java.util.ArrayList;

/**
 * Sean Gallagher & David White
 * Class used in Model. Acts as a link between many roads, allowing cars to travel between.
 */
public class Stoplight extends TrafficNode {


    public ArrayList<RoadSegment> north_to_south; //ordered by: source, destination
    public ArrayList<RoadSegment> south_to_north;
    public ArrayList<RoadSegment> east_to_west;
    public ArrayList<RoadSegment> west_to_east;
    public boolean open_north_south;
    private int car_going_NE=0;
    private int car_going_SW=0;

    /**
     * Constructor, creates links between this and roadsegments passed in.
     */
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
        super.setXpos(xpos);
        super.setYpos(ypos);
        open_north_south=false;
    }

    /**
     * Returns arraylist of all roadsegments attached to this.
     */
    public ArrayList<RoadSegment> getRoads(){
        ArrayList<RoadSegment> out=new ArrayList<>();
        out.addAll(north_to_south);
        out.addAll(south_to_north);
        out.addAll(east_to_west);
        out.addAll(west_to_east);
        return out;
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

    /**
     * Returns the relative location of the TrafficNode at the other end of road from this as [deltax, deltay]
     */
    public int[] deltaDirection(RoadSegment road){
        int[] out={0,0};
        if(north_to_south.contains(road)){
            if(north_to_south.get(0)==road){
                out[1]=-1;
            }
            else{
                out[1]=1;
            }
        }
        if(south_to_north.contains(road)){
            if(south_to_north.get(0)==road){
                out[1]=1;
            }
            else{
                out[1]=-1;
            }
        }
        if(east_to_west.contains(road)){
            if(east_to_west.get(0)==road){
                out[0]=-1;
            }
            else{
                out[0]=1;
            }
        }
        if(west_to_east.contains(road)){
            if(west_to_east.get(0)==road){
                out[0]=1;
            }
            else{
                out[0]=-1;
            }
        }
        return out;
    }

    /**
     * Red light. Green light.
     */
    public void changeLight() {
        open_north_south=!open_north_south;
    }
}


