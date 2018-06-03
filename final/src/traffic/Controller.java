package traffic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

//Stub class. Will be expanded on.
public class Controller {
    @FXML private TrafficView trafficView;
    private Model trafficModel;
    private double FRAMES_PER_SECOND = 20.0;

    public Controller () {
    }

    public void initialize() {
        this.trafficModel = new Model();
        trafficModel.makeStoplightGrid(4,4);
        trafficView.updateStoplights(trafficModel);
        trafficView.createSliders(trafficModel);

        this.setUpAnimationTimer();

//        for (int i = 0; i< 30; i++) {
//            trafficModel.update(i);
//            trafficView.updateStoplights(trafficModel);
//            trafficView.updateRoads(trafficModel);
//
//        }

    }

    public void updateAnimation() {
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

}