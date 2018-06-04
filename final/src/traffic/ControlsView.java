package traffic;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class ControlsView extends Group {

    public ControlsView () {
    }

    public Slider createMasterSlider(int x,int y) {
        Slider masterSlider = new Slider(0, 100,5);
        masterSlider.setShowTickLabels(true);
        masterSlider.setShowTickMarks(true);
        masterSlider.setMajorTickUnit(20);
        masterSlider.setMinorTickCount(5);
        masterSlider.setBlockIncrement(10);
        masterSlider.setPrefSize(200, 15);
        masterSlider.setTranslateX(x);
        masterSlider.setTranslateY(y);
        this.getChildren().add(masterSlider);
        return masterSlider;
    }

    public Button createButton(int x, int y, String text) {
        Button button = new Button(text);
        button.setPrefSize(50, 35);
        button.setTranslateX(x);
        button.setTranslateY(y);
        this.getChildren().add(button);
        return button;
    }

}
