package core.render.fx;

import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Styler {


    static void changeColorToActiveLink(Labeled label) {
        label.setTextFill(Color.rgb(255, 50, 165));
    }

    static void changeColorToPassiveLink(Labeled label) {
        label.setTextFill(Color.rgb(50, 50, 255));
    }

    static void changeColorToActiveLink(MouseEvent e) {
        changeColorToActiveLink((Labeled) e.getSource());
    }

    static void changeColorToPassiveLink(MouseEvent e) {
        changeColorToPassiveLink((Labeled) e.getSource());
    }
}
