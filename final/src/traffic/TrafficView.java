package traffic;


import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

public class TrafficView extends Group {
    Line line;
    public TrafficView() {
        line = new Line(100, 10,   10,   110);
    }
}
