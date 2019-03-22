package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TestPage extends Page {

    public TestPage(InputStream inputStream) {
        VBox vBox = new VBox();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = br.readLine();
            while (line != null) {
                vBox.getChildren().add(new Label(line));
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            vBox.getChildren().add(new Label("Page not founf :("));
        }
        flowPane.getChildren().add(vBox);
    }
}
