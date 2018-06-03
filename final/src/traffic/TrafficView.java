package traffic;


import com.sun.xml.internal.fastinfoset.tools.XML_SAX_StAX_FI;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import sun.plugin2.applet.StopListener;

import java.util.ArrayList;


public class TrafficView extends Group {
    public int START_X = 50;
    public int START_Y = 50;
    public int ROAD_LENGTH = 96;
    public int NUM_ROADS =4;
    private GraphicsContext gc;
    public TrafficView() {

        //Canvas is added once, then changed in real time using GraphicsContext
        Canvas canvas = new Canvas(700, 700);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);

        //Vertical lines
        for (int x = 1; x < NUM_ROADS + 1; x++) {
            for (int i = -1; i < 2; i++) {
                gc.strokeLine(START_X + (ROAD_LENGTH * x + 8 * i), START_Y, START_X + (ROAD_LENGTH * x + 8 * i), START_Y + ((NUM_ROADS + 1) * ROAD_LENGTH));
            }
        }
        //Horizontal lines
        for (int y = 1; y < NUM_ROADS + 1; y++) {
            for (int i = -1; i < 2; i++) {
                gc.strokeLine(START_X, START_Y + (ROAD_LENGTH * y + 8 * i), START_X + ((NUM_ROADS + 1) * ROAD_LENGTH), START_Y + (ROAD_LENGTH * y + 8 * i));
            }
        }



        //THIS IS JUST TEMPORARY SO I CAN VISUALLY SEE WHERE THE CANVAS IS AS WINDOW RESHAPES.
//        gc.setFill(Color.rgb(0, 255, 0, 0.2));
//        gc.fillRect(0,0,700,700);



        //SLIDERS
//        ArrayList<Slider> sliderList = new ArrayList<>();
//        for (int x = 1; x < NUM_ROADS+1; x++) {
//            sliderList.add(createSlider(START_X + ROAD_LENGTH * x - 8, START_Y ));
//            sliderList.add(createSlider(START_X + ROAD_LENGTH * x - 8, START_Y+ROAD_LENGTH*4));
//
//        }
//        for (int y = 1; y < NUM_ROADS+1; y++) {
//            sliderList.add(createSlider(START_X  , START_Y + ROAD_LENGTH * y -8));
//            sliderList.add(createSlider(START_X +ROAD_LENGTH*4, START_Y + ROAD_LENGTH * y -8));
//        }
//        this.getChildren().addAll(sliderList);

        //createSlider(START_X + ROAD_LENGTH * x - 8, START_Y + ROAD_LENGTH * y - 8);

    }



    public void updateRoads(Model model) {
        for (RoadSegment road : model.roads) {
            TrafficNode in = road.getIn();
            TrafficNode out = road.getOut();
            ArrayList<Integer> carList = road.getCurrentState();

            //Decide base location from upper North-West corner
            int x = in.getXpos();
            int y = in.getYpos();
            if (in.getXpos() > out.getXpos()) {
                x = out.getXpos();
            }
            if (in.getYpos() > out.getYpos()) {
                y = out.getYpos();
            }

            //Decide orientation & side of road
            if (in.getXpos() == out.getXpos() ) {   //Vertical
//                if (in.getYpos() > out.getYpos()) { //Points up
//                    for (int i = 0; i < carList.size()-2; i++) {
//                        if (carList.get(i+1) != 0) {
//                            drawCar(START_X + ROAD_LENGTH*x+2, START_Y+ROAD_LENGTH*(y+1)-8*i-16 ,6,6);
//                        } else {
//                            gc.clearRect(START_X + ROAD_LENGTH*x+2, START_Y+ROAD_LENGTH*(y+1)-8*i-16 ,6,6);
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < carList.size()-2; i++) { //Points down
//                        if (carList.get(i+1) != 0) {
//                            drawCar(START_X + ROAD_LENGTH*x-6, START_Y+ROAD_LENGTH*(y)+8*i+8,6,6);
//                        } else {
//                            gc.clearRect(START_X + ROAD_LENGTH*x-6, START_Y+ROAD_LENGTH*(y)+8*i+8,6,6);
//                        }
//                    }
//                }
            }
            if (in.getYpos() == out.getYpos() ) {   //Horizontal
                if (in.getXpos() > out.getXpos()) { //Points right
                    for (int i = 0; i < carList.size()-2; i++) {
                        if (carList.get(i+1) != 0) {
                            drawCar(START_X+ROAD_LENGTH*(x)+8*i+8,START_Y + ROAD_LENGTH*y+2 ,6,6);
                        } else {
                            gc.clearRect(START_X+ROAD_LENGTH*(x)+8*i+8,START_Y + ROAD_LENGTH*y+2 ,6,6);
                        }
                    }
                } else { //Points left
                    for (int i = 0; i < carList.size()-2; i++) {
                        if (carList.get(i+1) != 0) {
                            drawCar(START_X+ROAD_LENGTH*(x+1)-8*i-16,START_Y + ROAD_LENGTH*y+2 ,6,6);
                        } else {
                            gc.clearRect(START_X+ROAD_LENGTH*(x+1)-8*i-16,START_Y + ROAD_LENGTH*y+2 ,6,6);
                        }
                    }
                }
            }


        }



    }



    public void drawCar(int x, int y, int width, int height) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.clearRect(x, y, width, height);
        gc.fillRect(x, y, width, height);
        gc.setFill(Color.BLACK);
    }


    public void updateStoplights(Model model) {
            for (TrafficNode node : model.nodes){
                System.out.println(node.getXpos() + " " + node.getYpos());
                if (node instanceof Stoplight) {
                    drawStoplight(((Stoplight) node).open_north_south, ((Stoplight) node).getXpos(), ((Stoplight) node).getYpos());
                }
            }
            System.out.println();
        }

    public void drawStoplight(boolean open_north_south, int x, int y) {
        gc.setFill(Color.BLACK);
        gc.clearRect(START_X+(ROAD_LENGTH*x-7),START_Y+(ROAD_LENGTH*y-7), 14, 14);
        if (open_north_south) {
            gc.fillPolygon(new double[]{START_X+ROAD_LENGTH*x-6, START_X+ROAD_LENGTH*x, START_X+ROAD_LENGTH*x+6},
                    new double[]{START_Y+ROAD_LENGTH*y+4, START_Y+ROAD_LENGTH*y+8, START_Y+ROAD_LENGTH*y+4}, 3);
            gc.fillPolygon(new double[]{START_X+ROAD_LENGTH*x-6, START_X+ROAD_LENGTH*x, START_X+ROAD_LENGTH*x+6},
                    new double[]{START_Y+ROAD_LENGTH*y-4, START_Y+ROAD_LENGTH*y-8, START_Y+ROAD_LENGTH*y-4}, 3);
            gc.fillRect(START_X+ROAD_LENGTH*x-2,START_Y+ROAD_LENGTH*y-4, 4, 8);
        } else {
            gc.fillPolygon(new double[]{START_X+ROAD_LENGTH*x+4, START_X+ROAD_LENGTH*x+8, START_X+ROAD_LENGTH*x+4},
                    new double[]{START_Y+ROAD_LENGTH*y-6, START_Y+ROAD_LENGTH*y, START_Y+ROAD_LENGTH*y+6}, 3);
            gc.fillPolygon(new double[]{START_X+ROAD_LENGTH*x-4, START_X+ROAD_LENGTH*x-8, START_X+ROAD_LENGTH*x-4},
                    new double[]{START_Y+ROAD_LENGTH*y-6, START_Y+ROAD_LENGTH*y, START_Y+ROAD_LENGTH*y+6}, 3);
            gc.fillRect(START_X+ROAD_LENGTH*x-4,START_Y+ROAD_LENGTH*y-2, 8, 4);
        }
    }


    public void createSliders(Model model) {
        ArrayList<Slider> sliderList = new ArrayList<>();
        for (TrafficNode node : model.nodes) {
            if (!(node instanceof Stoplight)) {
                int x = node.getXpos();
                int y = node.getYpos();
                createSlider(START_X+ROAD_LENGTH*x, START_Y+ROAD_LENGTH*y);
                //Some stuff here actually linking functionality somehow?
            }
        }
        this.getChildren().addAll(sliderList);
    }

    public Slider createSlider(int x, int y) {
        Slider slider = new Slider(0, 100,50);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        slider.setPrefSize(30, 8);
        slider.setTranslateX(x);
        slider.setTranslateY(y);
        return slider;
    }

}
