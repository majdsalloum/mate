package gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TestPage extends Page{
    InputStream inputStream;

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Node getContent()
    {   VBox vBox = new VBox();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = br.readLine();
            while (line != null) {
                vBox.getChildren().add(new Label(line));
                line = br.readLine();
            }
        }
        catch (Exception e){
            vBox.getChildren().add(new Label("Page not founf :("));
        }
        /*
        WebView webView = new WebView();
        WebEngine webEngine =webView.getEngine();
        vBox = new VBox();
        webView.setPrefHeight(720);
        vBox.getChildren().add(webView);
        webEngine.load("https://google.com");*/
        return new ScrollPane(vBox);
    }
}
