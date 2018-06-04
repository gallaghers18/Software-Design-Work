package traffic;

import java.util.ArrayList;

/**
 * Sean Gallagher & David White
 * Model class. Contains constructor and update logic of entire road system.
 */

public class Model {
    private ArrayList<RoadSegment> roads = new ArrayList<>();
    private ArrayList<TrafficNode> nodes = new ArrayList<>();
    private int step_number=0;

    /**
     * Replaces a given stoplight Traffic node with a stopsign Traffic node. Currently unused
     */
    public void swapStopLightAndSign(TrafficNode node){
        if(node instanceof Stoplight){
            nodes.add(new Stopsign(((Stoplight) node).getRoads(),node.getXpos(),node.getYpos()));
            nodes.remove(node);
        }
        else if(node instanceof Stopsign){
            nodes.add(new Stoplight(((Stopsign) node).getRoads(),node.getXpos(),node.getYpos()));
            nodes.remove(node);
        }
    }

    /**
     * Creates Roadsegments and TrafficNodes with proper values for a width * height grid
     */
    public void makeStoplightGrid(int width, int height){
        //Create the proper number of Roadsegments
        int rowend=width;
        for(int j=0;j<2*height+1;j++){
            int i=0;
            while(i<rowend){
                roads.add(new RoadSegment(10));
                roads.add(new RoadSegment(10));
                i++;
            }
            rowend=width+(1-(rowend-width));
        }

        //Weave the road segments from the 1d list into the 2d grid structure.
        for(int j=0;j<height;j++){
            for(int i=0; i<width; i++){
                ArrayList<RoadSegment> temp=new ArrayList<RoadSegment>();
                //Get north-south
                temp.add(roads.get(2*(j*(2*width+1)+i)));
                temp.add(roads.get(2*((j+1)*(2*width+1)+i)));
                //Get south-north
                temp.add(roads.get(2*((j+1)*(2*width+1)+i)+1));
                temp.add(roads.get(2*(j*(2*width+1)+i)+1));
                //Get east-west
                temp.add(roads.get(2*(j*(2*width+1)+width)+2*i));
                temp.add(roads.get(2*(j*(2*width+1)+width)+2*(i+1)));
                //Get west-east
                temp.add(roads.get(2*(j*(2*width+1)+width)+2*(i+1)+1));
                temp.add(roads.get(2*(j*(2*width+1)+width)+2*i+1));
                Stoplight stop=new Stoplight(temp, i+1, j+1);
                nodes.add(stop);
            }
            rowend=width+(2-(rowend-width));
        }

        //Create generators and car deleters for roads that need them. Also set x and y pos correctly for them.
        for(RoadSegment road: roads){
            if(road.getIn()==null){
                int xpos=0;
                int ypos=0;
                if(road.getOut() instanceof Stoplight){
                    int[] delta=((Stoplight) road.getOut()).deltaDirection(road);
                    ypos=road.getOut().getYpos()+delta[1];
                    xpos=road.getOut().getXpos()+delta[0];
                }
                nodes.add(new Generator(road, xpos,ypos));
            }
            if(road.getOut()==null){
                int xpos=0;
                int ypos=0;
                if(road.getIn() instanceof Stoplight){

                    int delta[]=((Stoplight) road.getIn()).deltaDirection(road);
                    ypos=road.getIn().getYpos()+delta[1];
                    xpos=road.getIn().getXpos()+delta[0];

                }
                nodes.add(new CarDeleter(road,xpos,ypos));
            }

        }
    }

    /**
     * Computes total time on map in ticks for all cars
     */
    public int totalCarLifespan(){
        int total=0;
        for(RoadSegment road:roads){
            total+=road.totalLifespan();
        }
        return total;
    }

    /**
     * Returns number of cars on map
     */
    public int countAllCars(){
        int total=0;
        for(RoadSegment road:roads){
            total+=road.countCars();
        }
        return total;
    }

    /**
     * Returns total length of all road segments
     */
    public int getTotalLength(){
        int total=0;
        for(RoadSegment road:roads){
            total+=road.getLength();
        }
        return total;
    }

    /**
     * Returns average lifespan of all cars
     */
    public double averageLifespan(){
        return ((double) totalCarLifespan())/((double) countAllCars());
    }

    /**
     * Returns number of cars/total road length
     */
    public double averageDensity(){
        return ((double) countAllCars())/((double) getTotalLength());
    }

    /**
     * Returns average cars created per tick of simulation
     */
    public double throughput(){
        int total=0;
        for(TrafficNode node: nodes){
            if(node instanceof Generator){
                total+=((Generator) node).getTotalCreated();
            }
        }
        return ((double) total)/((double) step_number);
    }

    /**
     * Console output for debugging
     */
    public void printAll(){
        for(RoadSegment road: roads){
            if(road.getIn()!=null && road.getOut()!=null){
                road.printRoad();
            }
        }
    }

    /**
     * Getter for roads
     */
    public ArrayList<RoadSegment> getRoads() {
        return roads;
    }

    /**
     * Getter for nodes
     */
    public ArrayList<TrafficNode> getNodes() {
        return nodes;
    }

    /**
     * Setter for reset button
     */
    public void setStepNumber(int i) {
        step_number=i;
    }

    /**
     * step the model forward one tick
     */
    public void update(){
        for(RoadSegment road: roads){
            road.moveCarsOneStep();
        }
        for(TrafficNode node: nodes){
            if(step_number % 10 ==0 && node instanceof Stoplight) {
                    ((Stoplight) node).changeLight();
            }
            node.sendCar();
            node.takeCar();
        }
        step_number++;

    }
}


