package sample;

/**
 * Created by gallaghers on 5/24/18.
 */
public interface Intersection {

    /**
     * @return the type of intersection
     */
    public String getTypeOfIntersection();

    /**
     * Grabs cars out of the correct intake road segments that this connects to.
     */
    public void takeCar();

    /**
     * Adds cars to the output road segments that this connects to.
     */
    public void sendCar();


}
