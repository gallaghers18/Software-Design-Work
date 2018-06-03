package traffic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("traffic.fxml"));
        BorderPane root = loader.load();
        Controller controller = loader.getController();

        Scene scene = new Scene(root, 1000, 700);

        primaryStage.setTitle("Traffic Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
        //Model test=new Model();
        //test.makeStoplightGrid(4,4);
        //test.makeFourWayStop();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
