package traffic;


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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class TrafficView extends Group {
    public int START_X = 50;
    public int START_Y = 50;
    public int ROAD_LENGTH = 110;
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

    public void drawRoadSegment(RoadSegment road) {
       //take RoadSegment object and drawCar() based on each car in it.
//        for () {
//            if (car != 0) {
//                drawCar(5, 5); //PLACEHOLDER
//            }
//        }

    }

    public void drawCar(int x, int y) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.clearRect(x, y, 6, 6);
        gc.fillRect(x, y, 6, 6);
        gc.setFill(Color.BLACK);

    }


    public void updateStoplights(Model model) {
            for (TrafficNode node : model.nodes){
                if (node instanceof Stoplight) {
                    drawStoplight(((Stoplight) node).open_north_south, ((Stoplight) node).getXpos(), ((Stoplight) node).getYpos());
                }
            }
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

    public void drawTrafficNode(TrafficNode node) {
        Rectangle r = new Rectangle(node.getXpos()-8, node.getYpos()-8, 16, 16);
        this.getChildren().add(r);
    }


    //THIS WILL ACTUALLY LIVE IN THE CONTROLLER SORT OF, BUT JUST HERE SO I REMEMBER THE CODE
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
