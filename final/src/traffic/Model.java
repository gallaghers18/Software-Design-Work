package traffic;

import java.util.ArrayList;

public class Model {
    ArrayList<RoadSegment> roads = new ArrayList<>();
    ArrayList<TrafficNode> nodes = new ArrayList<>();

    public void makeFourWayStop(){
        for (int i=0;i<8;i++){
            roads.add(new RoadSegment(10));
        }
        nodes.add(new Stoplight(roads,0,0));
        for (int i=0;i<8;i+=2){
            nodes.add(new Generator(roads.get(i)));
        }
        for (int i=1;i<8;i+=2){
            nodes.add(new CarDeleter(roads.get(i)));
        }
    }

    public void printAll(){
        for(RoadSegment road: roads){
            road.printRoad();
        }
    }

    public void update(int stepNumber){
        for(RoadSegment road: roads){
            road.moveCarsOneStep();
        }
        for(TrafficNode node: nodes){
            if(stepNumber % 10 ==0 && node instanceof Stoplight) {
                    ((Stoplight) node).changeLight();
            }
            node.sendCar();
            node.takeCar();
        }
        printAll();
        System.out.println();
    }
}

/* To step the model forward:
 * Loop through all objects implementing intersection, and run:
 *  sendCar()
 *  takeCar()
 * Loop through all road segments and run:
 *  moveCarsOneStep()
 *
 *
 *
 *
 */
