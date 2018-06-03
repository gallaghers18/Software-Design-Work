package traffic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//Stub class. Will be expanded on.
public class Controller {
    @FXML private TrafficView trafficView;
    private Model trafficModel;
    private double FRAMES_PER_SECOND = 10.0;
    private ArrayList<SliderGenerator> sliders;

    public Controller () {
    }

    public void initialize() {
        this.trafficModel = new Model();
        trafficModel.makeStoplightGrid(6,6);
        trafficView.updateStoplights(trafficModel);
        sliders = this.createSliders();
        this.setUpAnimationTimer();
    }

    public void updateAnimation() {
        readSliders();
        trafficModel.update();
        trafficView.updateStoplights(trafficModel);
        trafficView.updateRoads(trafficModel);
    }


    private void setUpAnimationTimer() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateAnimation();
                    }
                });
            }
        };

        final long startTimeInMilliseconds = 0;
        final long repetitionPeriodInMilliseconds = 100;
        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        Timer timer = new java.util.Timer();
        timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }



    public ArrayList<SliderGenerator> createSliders() {
        ArrayList<SliderGenerator> sliderGeneratorList = new ArrayList<>();
        for (TrafficNode node : trafficModel.nodes) {
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


    public void readSliders() {
        for (SliderGenerator pair : sliders) {
            pair.getGenerator().setSpawnRate(pair.getSlider().getValue());
        }
    }


    private class SliderGenerator{
        Slider slider;
        Generator generator;
        SliderGenerator(Slider slider, Generator generator){
            this.slider=slider;
            this.generator=generator;
        }

        public Slider getSlider() {
            return slider;
        }

        public Generator getGenerator() {
            return generator;
        }
    }
}