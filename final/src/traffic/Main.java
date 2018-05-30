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

        Scene scene = new Scene(root, 900, 600);

        //THIS IS ALL DRAWING THE LINES FOR THE STREETS. I DON'T KNOW IF THIS SHOULD LIVE HERE, OR BE IN A DIFFERENT FILE
        //BUT FOR NOW IT WORKS HERE
        //==================================
        //Vertical lines
        for (int x=0; x<4; x++) {
            for (int i=0; i<3; i++) {
                Line line = new Line(442+100*x+8*i, 50,   442+100*x+8*i,   550);
                root.getChildren().add(line);
            }

        }
        //Horizontal lines
        for (int y=0; y<4; y++) {
            for (int i=0; i<3; i++) {
                Line line = new Line(350, 142+100*y+8*i, 850, 142+100*y+8*i);
                root.getChildren().add(line);
            }
        }

        //Stoplight boxes
        for (int x=0; x<4; x++) {
            for (int y=0; y<4; y++) {
                Rectangle r = new Rectangle(442+100*x, 142+100*y, 16, 16);
                root.getChildren().add(r);
            }
        }
        //=================================

        primaryStage.setTitle("Traffic Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
