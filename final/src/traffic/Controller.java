package traffic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Sean Gallagher & David White
 * The Controller. Connects to both TrafficModel, ControlsView, and TrafficView.
 * Initializes and updates the views in accordance with model. Sends slider
 * and button inputs back to model.
 */
public class Controller {
    @FXML private TrafficView trafficView;
    @FXML private ControlsView controlsView;
    @FXML private Label statsLabel;
    private Model trafficModel;
    private double FRAMES_PER_SECOND = 10.0;
    private ArrayList<SliderGenerator> sliders;
    private Slider masterSlider;
    private Button button;
    private DecimalFormat df;

    /**
     * Constructor
     *
     * Just initializes the DecimalFormat object for use later.
     * Important initialization happens in Initialize() method.
     */
    public Controller () {
        df = new DecimalFormat("#.##");
    }

    /**
     * Initialize method that starts the model,
     * draws the initial parts of the traffic and
     * control views.
     */
    public void initialize() {
        //Model
        this.trafficModel = new Model();
        trafficModel.makeStoplightGrid(6,6);
        this.setUpSimulationTimer();
        //Traffic View
        trafficView.initializeRoads();
        trafficView.updateStoplights(trafficModel);
        sliders = this.createSliders();
        //Controls View
        masterSlider = controlsView.createMasterSlider(50,150);
        this.initializeControlButtons();
        //Reset Button
        button = controlsView.createButton(125,375,"Reset");
        button.setOnAction(value -> {
           reset();
        });
    }

    /**
     * Starts timer for simulation, running update on
     * a timer.
     * */
    private void setUpSimulationTimer() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateSimulation();
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        Timer timer = new java.util.Timer();
        timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * Updates the simulation when called, involving updating
     * model, stoplights view, roads view, and stats view.
     *  Also reads in slider values.
    * */
    private void updateSimulation() {
        readSliders();
        trafficModel.update();

        trafficView.updateStoplights(trafficModel);
        trafficView.updateRoads(trafficModel);
        this.statsLabel.setText("Lifespan: " + String.valueOf(df.format(trafficModel.averageLifespan())) + "\n"
                + "Density: " + String.valueOf(df.format(trafficModel.averageDensity())) + "\n"
                + "Throughput: " + String.valueOf(df.format(trafficModel.throughput())));


        if (masterSlider.isValueChanging()) {
            this.setSliders(masterSlider.getValue());
        }
    }

    /**
     * Resets model and view to original values
     * */
    private void reset() {
        for (RoadSegment road : trafficModel.getRoads()) {
            road.resetRoadState();
        }
        trafficModel.setStepNumber(0);
    }

    /**
     * Creates the panel of control buttons for the controls view
     * */
    private void initializeControlButtons() {
        button =  controlsView.createButton(50, 200, "1%");
        button.setOnAction(value -> {this.presetSliderValue(1);});
        button =  controlsView.createButton(125, 200, "5%");
        button.setOnAction(value -> {this.presetSliderValue(5);});
        button =  controlsView.createButton(200, 200, "10%");
        button.setOnAction(value -> {this.presetSliderValue(10);});
        button =  controlsView.createButton(50, 275, "25%");
        button.setOnAction(value -> {this.presetSliderValue(25);});
        button =  controlsView.createButton(125, 275, "50%");
        button.setOnAction(value -> {this.presetSliderValue(50);});
        button =  controlsView.createButton(200, 275, "100%");
        button.setOnAction(value -> {this.presetSliderValue(100);});
    }

    /**
     * Creates and returns list slider-generator pairs so that
     * each slider's value is linked to a given generator (also adds to trafficView)
     *
     * @return ArrayList<SliderGenerator> a list of SliderGenerator objects which
     * have getters for a slider and for a generator*/
    private ArrayList<SliderGenerator> createSliders() {
        ArrayList<SliderGenerator> sliderGeneratorList = new ArrayList<>();
        for (TrafficNode node : trafficModel.getNodes()) {
            if (node instanceof Generator) {
                int x = node.getXpos();
                int y = node.getYpos();
                Slider slider = trafficView.createSlider(x,y);
                sliderGeneratorList.add(new SliderGenerator(slider,(Generator) node));
                trafficView.getChildren().add(slider);
            }
        }
        return sliderGeneratorList;
    }


    /**
     * Reads each individual slider's value and sets its paired generator
     * spawn rate accordingly (used every loop in update)
     * */
    private void readSliders() {
        for (SliderGenerator pair : sliders) {
            pair.getGenerator().setSpawnRate(pair.getSlider().getValue());
        }
    }

    /**
     * Set all individual sliders to one value,
     * for use as master slider is moved
     *
     * @param value The value to set sliders to.
     * */
    private void setSliders(double value) {
        for (SliderGenerator pair : sliders) {
            pair.getSlider().setValue(value);
        }
    }

    /**
     * Set all individual sliders, including master, to one value,
     * for use with the preset buttons
     *
     * @param value The value to set sliders to.
     * */
    private void presetSliderValue(double value) {
        for (SliderGenerator pair : sliders) {
            pair.getSlider().setValue(value);
        }
        masterSlider.setValue(value);
    }

    /**
     * SliderGenerator subclass that pairs a Slider and
     * a Generator object
     *
     * Has two simple getters, one for slider and
     * other for generator.
     * */
    private class SliderGenerator{
        Slider slider;
        Generator generator;
        SliderGenerator(Slider slider, Generator generator){
            this.slider=slider;
            this.generator=generator;
        }

        private Slider getSlider() {
            return slider;
        }

        private Generator getGenerator() {
            return generator;
        }
    }
}