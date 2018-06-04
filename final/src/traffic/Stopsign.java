package traffic;

import java.util.ArrayList;

/**
 * Sean Gallagher & David White
 * Class used in model. Same idea as stoplight, but different switch logic.
 * CURRENTLY NOT IN USE IN ACTUAL APPLICATION. WE DIDN'T HAVE TIME.
 */

public class Stopsign extends TrafficNode {


    public ArrayList<RoadSegment> north_to_south; //ordered by: source, destination
    public ArrayList<RoadSegment> south_to_north;
    public ArrayList<RoadSegment> east_to_west;
    public ArrayList<RoadSegment> west_to_east;
    public int car=0;
    private int direction=0;

    /**
     * Constructor, creates links between this and roadsegments passed in.
     */
    public Stopsign(ArrayList<RoadSegment> roads, int xpos, int ypos) {
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
     * Take cars into the intersection (from road segments) based on stopsign rules.
     */
    @Override
    public void takeCar() {
        int check=(direction+1)%4;
        do {
            if (check == 0) {
                if (north_to_south.get(0).getCarAtEnd() != 0 && north_to_south.get(1).getCarAtFront() == 0) {
                    car = north_to_south.get(0).removeCarAtEnd();
                    direction = check;
                    return;
                }
            }
            if (check == 1) {
                if (east_to_west.get(0).getCarAtEnd() != 0 && east_to_west.get(1).getCarAtFront() == 0) {
                    car = east_to_west.get(0).removeCarAtEnd();
                    direction = check;
                    return;
                }
            }
            if (check == 2) {
                if (south_to_north.get(0).getCarAtEnd() != 0 && south_to_north.get(1).getCarAtFront() == 0) {
                    car = south_to_north.get(0).removeCarAtEnd();
                    direction = check;
                    return;
                }
            }
            if (check == 3) {
                if (west_to_east.get(0).getCarAtEnd() != 0 && west_to_east.get(1).getCarAtFront() == 0) {
                    car = west_to_east.get(0).removeCarAtEnd();
                    direction = check;
                    return;
                }
            }
            check=(check+1)%4;
        } while(check!=(direction+1)%4 && car==0);

    }

    /**
     * Sends cars out of the intersection (onto road segments) based on stopsign rules.
     */
    @Override
    public void sendCar() {
        if(car!=0){
            if(south_to_north.get(1).getCarAtFront()==0 && direction==2) {
                south_to_north.get(1).addCarAtFront(car+1);
                car=0;
            }
            if(north_to_south.get(1).getCarAtFront()==0 && direction==0) {
                north_to_south.get(1).addCarAtFront(car+1);
                car=0;
            }
            if(west_to_east.get(1).getCarAtFront()==0 && direction==3) {
                west_to_east.get(1).addCarAtFront(car+1);
                car=0;
            }
            if(east_to_west.get(1).getCarAtFront()==0 && direction==1) {
                east_to_west.get(1).addCarAtFront(car+1);
                car=0;
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
}


