package traffic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by gallaghers on 5/24/18.
 */

public class RoadSegment {

    private TrafficNode in;
    private TrafficNode out;


    private ArrayList<Integer> road_state;

    /**
     * Constructor: creates an empty array list of length length
     *
     * @param length how long a road segment to make.
     * @return current road state as an arrayList
     */
    RoadSegment(int length) {
        road_state = new ArrayList();
        for (int i=0;i<length+2;i++){
            road_state.add(0);
        }
        printRoad();
    }

    /**
     * Returns the current road state as an arrayList
     *
     * @return current road state as an arrayList
     */
    public ArrayList<Integer> getCurrentState() {
        return road_state;
    }

    /**
     * Updates road_state according to Rule 184 (cars move if open space)
     *
     */
    public void moveCarsOneStep() {
        ArrayList<Integer> next_road_state=new ArrayList<Integer>(Collections.nCopies(road_state.size(), 0));
        next_road_state.set(0,0);
        next_road_state.set(next_road_state.size()-1,1);
        for(int i=1;i<road_state.size()-1;i++){
            if(road_state.get(i+1)>0){
                if(road_state.get(i)>0) {
                    next_road_state.set(i, road_state.get(i) + 1);
                }
                else if(road_state.get(i-1)>0){
                    next_road_state.set(i, road_state.get(i-1) + 1);
                }
                else{
                    next_road_state.set(i, 0);
                }
            }
            else{
                if(road_state.get(i) > 0){
                    next_road_state.set(i,0);
                }
                else if(road_state.get(i-1)>0){
                    next_road_state.set(i, road_state.get(i-1) + 1);
                }
                else{
                    next_road_state.set(i, 0);
                }
            }
        }
        road_state=next_road_state;
    }

    /**
     * Returns the value of the car at source end, which is 0 if there is no car.
     *
     * @return road_state[0]
     */
    public int getCarAtFront() {
        return road_state.get(1);
    }

    /**
     * Returns the value of the car at destination end, which is 0 if there is no car.
     *
     * @return road_state[length]
     */
    public int getCarAtEnd() {
        return road_state.get(road_state.size()-2);
    }

    /**
     * Replaces first item in road_state with car.
     *
     * @param car value of the car
     */
    public void addCarAtFront(int car) {
        road_state.set(1,car);
    }

    /**
     * Returns the value of the car at destination end, and then replaces it with 0,
     *
     * @return road_state[0]
     */
    public int removeCarAtEnd() {
        int car = road_state.get(road_state.size()-2);
        road_state.set(road_state.size()-2,0);
        return car;
    }

    public TrafficNode getIn() {
        return in;
    }

    public TrafficNode getOut() {
        return out;
    }

    public void setIn(TrafficNode in){
        this.in=in;
    }
    public void setOut(TrafficNode out){
        this.out=out;
    }
    public void printRoad(){
        String out="";
        for(int i=1;i<road_state.size()-1;i++){
            out+=road_state.get(i).toString()+" ";
        }
        System.out.println(out);
    }
}



