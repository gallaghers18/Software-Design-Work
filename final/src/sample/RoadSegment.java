package sample;

import java.util.ArrayList;

/**
 * Created by gallaghers on 5/24/18.
 */

public class RoadSegment {

    private ArrayList<Integer> road_state;

    /**
     * Constructor: creates an empty array list of length length
     *
     * @param length how long a road segment to make.
     * @return current road state as an arrayList
     */
    public void RoadSegment(int length) {
        road_state = new ArrayList(0);
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
        return;
    }

    /**
     * Returns the value of the car at source end, which is 0 if there is no car.
     *
     * @return road_state[0]
     */
    public int getCarAtFront() {
        return 0;
    }

    /**
     * Returns the value of the car at destination end, which is 0 if there is no car.
     *
     * @return road_state[length]
     */
    public int getCarAtEnd() {
        return 0;
    }

    /**
     * Replaces first item in road_state with car.
     *
     * @param car value of the car
     */
    public void addCarAtFront(int car) {
        return;
    }

    /**
     * Returns the value of the car at destination end, and then replaces it with 0,
     *
     * @return road_state[0]
     */
    public int removeCarAtEnd() {
        return 0;
    }


}



