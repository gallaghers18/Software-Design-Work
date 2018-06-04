package traffic;

import java.util.ArrayList;

/**
 * Sean Gallagher & David White
 * Model class. Builds structure and update logic of entire road system.
 */

public class Model {
    private ArrayList<RoadSegment> roads = new ArrayList<>();
    private ArrayList<TrafficNode> nodes = new ArrayList<>();
    private int step_number=0;

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

    public void makeStoplightGrid(int width, int height){
        /*
            1 2 3 4

            5 6 7 8

         */
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
        for(int j=0;j<height;j++){
            for(int i=0; i<width; i++){

                ArrayList<RoadSegment> temp=new ArrayList<RoadSegment>();
                temp.add(roads.get(2*(j*(2*width+1)+i)));
                temp.add(roads.get(2*((j+1)*(2*width+1)+i)));

                temp.add(roads.get(2*((j+1)*(2*width+1)+i)+1));
                temp.add(roads.get(2*(j*(2*width+1)+i)+1));

                temp.add(roads.get(2*(j*(2*width+1)+width)+2*i));
                temp.add(roads.get(2*(j*(2*width+1)+width)+2*(i+1)));

                temp.add(roads.get(2*(j*(2*width+1)+width)+2*(i+1)+1));
                temp.add(roads.get(2*(j*(2*width+1)+width)+2*i+1));
                Stoplight stop=new Stoplight(temp, i+1, j+1);
                nodes.add(stop);
            }
            rowend=width+(2-(rowend-width));
        }
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

    public int totalCarLifespan(){
        int total=0;
        for(RoadSegment road:roads){
            total+=road.totalLifespan();
        }
        return total;
    }
    public int countAllCars(){
        int total=0;
        for(RoadSegment road:roads){
            total+=road.countCars();
        }
        return total;
    }
    public int getTotalLength(){
        int total=0;
        for(RoadSegment road:roads){
            total+=road.getLength();
        }
        return total;
    }

    public double averageLifespan(){
        return ((double) totalCarLifespan())/((double) countAllCars());
    }

    public double averageDensity(){
        return ((double) countAllCars())/((double) getTotalLength());
    }
    public double throughput(){
        int total=0;
        for(TrafficNode node: nodes){
            if(node instanceof Generator){
                total+=((Generator) node).getTotalCreated();
            }
        }
        return ((double) total)/((double) step_number);
    }

    public void printAll(){
        for(RoadSegment road: roads){
            if(road.getIn()!=null && road.getOut()!=null){
                road.printRoad();
            }
        }
    }

    public ArrayList<RoadSegment> getRoads() {
        return roads;
    }

    public ArrayList<TrafficNode> getNodes() {
        return nodes;
    }


    public void setStepNumber(int i) {
        step_number=i;
    }

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


