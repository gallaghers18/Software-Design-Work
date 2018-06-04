package traffic;

/**
 * Created by gallaghers on 5/24/18.
 */
import java.util.Random;

public class Generator extends TrafficNode {

    private RoadSegment exitRoad;
    private double rate;
    private int created=0;
    Random r;

    Generator(RoadSegment exitRoad,int xpos,int ypos) {
        super.setXpos(xpos);
        super.setYpos(ypos);
        this.rate=1.0;
        this.exitRoad=exitRoad;
        exitRoad.setIn(this);
        r = new Random();

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
            if(r.nextDouble()*100 < rate) {
                exitRoad.addCarAtFront(1);
                created+=1;
            }
        }
    }

    public int getTotalCreated(){
        return created;
    }

    public void setSpawnRate(double rate){
        this.rate=rate;
    }


}

