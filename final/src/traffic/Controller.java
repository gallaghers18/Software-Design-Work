package traffic;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Stub class. Will be expanded on.
public class Controller {
    @FXML private TrafficView trafficView;
    private Model trafficModel;


    public Controller () {
    }

    public void initialize() {
        this.trafficModel = new Model();
        trafficModel.makeStoplightGrid(4,4);
        trafficView.updateStoplights(trafficModel);

        for (int i = 0; i< 20; i++) {
            trafficModel.update(i);
            trafficView.updateStoplights(trafficModel);

        }
    }
}