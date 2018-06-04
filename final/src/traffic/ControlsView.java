package traffic;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

/**
 * Sean Gallagher & David White
 * Short view class for creating buttons and sliders that appear on the left side
 * control panel. All initialization and construction happens in controller
 * because it needs reference to pass to model.
 */
public class ControlsView extends Group {

    /**
     * Constructor: doesn't do anything special.
     * */
    public ControlsView () {
    }

    /**
     * Creates, adds to view, and returns a large slider
     *
     * @Return the slider itself so values may be passed to model
     */
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

    /**
     * Creates, adds to view, and returns a simple button
     *
     * @Return the button itself so values may be passed to model
     */
    public Button createButton(int x, int y, String text) {
        Button button = new Button(text);
        button.setPrefSize(50, 35);
        button.setTranslateX(x);
        button.setTranslateY(y);
        this.getChildren().add(button);
        return button;
    }

}
