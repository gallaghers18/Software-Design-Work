package traffic;

/**
 * Sean Gallagher & David White
 * Class used in Model. Spawns new cars to enter onto RoadSegments.
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

    /**
     * Get total cars created up to this point.
     */
    public int getTotalCreated(){
        return created;
    }

    /**
     * Set probability for spawning car
     */
    public void setSpawnRate(double rate){
        this.rate=rate;
    }


}

