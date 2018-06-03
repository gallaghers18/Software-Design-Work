package traffic;

import java.util.ArrayList;

public class Model {
    public ArrayList<RoadSegment> roads = new ArrayList<>();
    public ArrayList<TrafficNode> nodes = new ArrayList<>();
    private int step_number=0;

    public void makeFourWayStop(){
        for (int i=0;i<8;i++){
            roads.add(new RoadSegment(10));
        }
        nodes.add(new Stoplight(roads,0,0));
        for (int i=0;i<8;i+=2){
            nodes.add(new Generator(roads.get(i),0,0));
        }
        for (int i=1;i<8;i+=2){
            nodes.add(new CarDeleter(roads.get(i),0,0));
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

                temp.add(roads.get(2*(j*(2*width+1)+width+i)));
                temp.add(roads.get(2*(j*(2*width+1)+width+i+1)));

                temp.add(roads.get(2*(j*(2*width+1)+width+i+1)+1));
                temp.add(roads.get(2*(j*(2*width+1)+width+i)+1));
                nodes.add(new Stoplight(temp, i+1, j+1));
            }
            rowend=width+(2-(rowend-width));
        }
        for(RoadSegment road: roads){
            if(road.getIn()==null){
                int xpos=0;
                int ypos=0;
                if(road.getOut() instanceof Stoplight){
                    int delta=((Stoplight) road.getOut()).deltaDirection(road);
                    ypos=road.getOut().getYpos()+(delta % 10)-1;
                    xpos=road.getOut().getXpos()+(delta-(delta % 10))/10-1;
                }
                nodes.add(new Generator(road, xpos,ypos));
            }
            if(road.getOut()==null){
                int xpos=0;
                int ypos=0;
                if(road.getIn() instanceof Stoplight){
                    int delta=((Stoplight) road.getIn()).deltaDirection(road);
                    ypos=road.getIn().getYpos()+(delta % 10)-1;
                    xpos=road.getIn().getXpos()+(delta-(delta % 10))/10-1;
                }
                nodes.add(new CarDeleter(road,xpos,ypos));
            }

        }
    }

    public void printAll(){
        for(RoadSegment road: roads){
            if(road.getIn()!=null && road.getOut()!=null){
                road.printRoad();
            }
        }
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
//        printAll();
//        System.out.println();
    }
}


