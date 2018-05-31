package traffic;


import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class TrafficView extends Group {
    Line line;
    public TrafficView() {
        //Vertical lines
        for (int x=0; x<4; x++) {
            for (int i=0; i<3; i++) {
                Line line = new Line(442+100*x+8*i, 50,   442+100*x+8*i,   550);
                this.getChildren().add(line);
            }

        }
        //Horizontal lines
        for (int y=0; y<4; y++) {
            for (int i=0; i<3; i++) {
                Line line = new Line(350, 142+100*y+8*i, 850, 142+100*y+8*i);
                this.getChildren().add(line);
            }
        }

        //Stoplight boxes
        for (int x=0; x<4; x++) {
            for (int y=0; y<4; y++) {
                Rectangle r = new Rectangle(442+100*x, 142+100*y, 16, 16);
                this.getChildren().add(r);
            }
        }
    }


    public void drawRoadSegment(RoadSegment road) {
        Line line = new Line(road.getIn().getXpos(), road.getIn().getYpos(), road.getOut().getXpos(), road.getOut().getYpos());

    }

    public void drawTrafficNode(TrafficNode node) {
        Rectangle r = new Rectangle(node.getXpos()-8, node.getYpos()-8, 16, 16);
        this.getChildren().add(r);
    }

}
