package traffic;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import java.util.ArrayList;


/**
 * Sean Gallagher & David White
 *
 * The Traffic View. This is where the actual simulation with
 * cars and roads is displayed. Contains initialize and update
 * methods for all of the graphics.
 *
 * Note: all location is being done by feeding absolute
 * values (0, 1, 2, etc) and scaling up to the appropriate pixel location
 * via stuff like START_X+ROAD_LENGTH*x, etc. (This is another area
 * we would clean up better and make a little more versatile if we had time)
 */
public class TrafficView extends Group {
    private int START_X = 50;
    private int START_Y = 50;
    private int ROAD_LENGTH = 96;
    private int NUM_ROADS =6;
    private Canvas canvas;
    private GraphicsContext gc;

    /**
     * Constructor, initializes the TrafficView, creating
     * canvas and graphicsContext for working with canvas.
     * */
    public TrafficView() {
        canvas = new Canvas(800, 800);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }

    /**
     * Draws the lines for the roads, which is only run once and then
     * stays on the canvas.
     * */
    public void initializeRoads() {
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
    }

    /**
     * Erases and redraws all of the cars
     * based on their new location in the model
     *
     * (Probably the ugliest section of code, but we got it
     * working and it is not the highest priority to clean up)
     *
     * @param model TrafficModel for getting road states.
     * */
    public void updateRoads(Model model) {
        for (RoadSegment road : model.getRoads()) {
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
                if (in.getYpos() > out.getYpos()) { //Points up
                    for (int i = 0; i < carList.size()-2; i++) {
                        if (carList.get(i+1) != 0) {
                            drawCar(START_X + ROAD_LENGTH*x+2, START_Y+ROAD_LENGTH*(y+1)-8*i-16 ,6,6);
                        } else {
                            gc.clearRect(START_X + ROAD_LENGTH*x+2, START_Y+ROAD_LENGTH*(y+1)-8*i-16 ,6,6);
                        }
                    }
                } else {
                    for (int i = 0; i < carList.size()-2; i++) { //Points down
                        if (carList.get(i+1) != 0) {
                            drawCar(START_X + ROAD_LENGTH*x-6, START_Y+ROAD_LENGTH*(y)+8*i+8,6,6);
                        } else {
                            gc.clearRect(START_X + ROAD_LENGTH*x-6, START_Y+ROAD_LENGTH*(y)+8*i+8,6,6);
                        }
                    }
                }
            }
            if (in.getYpos() == out.getYpos() ) {   //Horizontal
                if (in.getXpos() > out.getXpos()) { //Points right

                    for (int i = 0; i < carList.size()-2; i++) {
                        if (carList.get(i+1) != 0) {
                            drawCar(START_X+ROAD_LENGTH*(x+1)-8*i-16,START_Y + ROAD_LENGTH*y-6 ,6,6);
                        } else {
                            gc.clearRect(START_X+ROAD_LENGTH*(x+1)-8*i-16,START_Y + ROAD_LENGTH*y-6 ,6,6);
                        }
                    }
                } else { //Points left
                    for (int i = 0; i < carList.size()-2; i++) {
                        if (carList.get(i+1) != 0) {
                            drawCar(START_X+ROAD_LENGTH*(x)+8*i+8,START_Y + ROAD_LENGTH*y+2 ,6,6);
                        } else {
                            gc.clearRect(START_X+ROAD_LENGTH*(x)+8*i+8,START_Y + ROAD_LENGTH*y+2 ,6,6);
                        }
                    }
                }
            }
        }
    }



    /**
     * Draws a green car at the given raw location and size.
     *
     * @param height height of car.
     * @param width width of car.
     * @param x x-position of car.
     * @param y y-position of car.
     * */
    private void drawCar(int x, int y, int width, int height) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.clearRect(x, y, width, height);
        gc.fillRect(x, y, width, height);
        gc.setFill(Color.BLACK);
    }

    /**
     * Updates the stoplights view, switching direction when
     * appropriate
     *
     * @param model TrafficModel for getting stoplight states.
     * */
    public void updateStoplights(Model model) {
            for (TrafficNode node : model.getNodes()){
                if (node instanceof Stoplight) {
                    drawStoplight(((Stoplight) node).open_north_south, ((Stoplight) node).getXpos(), ((Stoplight) node).getYpos());
                }
            }
        }

    /**
     * Literal draw method used in updateStopLights, taking stoplight
     * position and status and drawing it on the canvas
     *
     * @param open_north_south true if stoplight is open north_south.
     * @param x absolute x value to position based off.
     * @param y absolute y value to position based off.
     * */
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

    /**
     * Creates a slider at a spot over the traffic view
     * canvas to be then hooked up to a generator
     *
     * @param x x position to be drawn at
     * @param y y position to be drawn at
     *
     * @return Slider the slider so that its value may be gathered.
     * */
    public Slider createSlider(int x, int y) {
        Slider slider = new Slider(0, 100,5);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        slider.setPrefSize(30, 8);
        slider.setTranslateX(START_X+ROAD_LENGTH*x-16);
        slider.setTranslateY(START_Y+ROAD_LENGTH*y);
        return slider;
    }

}
