package gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.DownloadManger;
import java.io.File;
import java.io.IOException;
import javafx.scene.paint.*;


public class DownloadWindow {
    TextField textField = new TextField();
    DownloadManger downloadManger;
    int size;
    ProgressBar progressBar;

    public void showDownloadWindow() {
        Stage stage = new Stage();
        Label label = new Label("drop the link here");
        label.setTextFill(Color.DARKGREEN);
        Button button = new Button("ok");
        button.setBackground(new Background(new BackgroundFill(Color.DARKGREEN,null,null)));
        VBox vBox = new VBox(label,textField,button);
        vBox.setAlignment(Pos.CENTER);
        stage.getIcons().add(Images.getImage("..\\img\\mate.png"));
        stage.setTitle("Download Manger");
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        button.setOnAction((e)->{
            Thread thread = new Thread(()->{
            try {
                downloadManger = new DownloadManger(textField.getText());
                downloadManger.download();

            } catch (IOException e1) {
                e1.printStackTrace();
            }});
            thread.start();
            showDownloadStatus();
            stage.close();
        });
    }

    private void showDownloadStatus() {
        while (downloadManger==null)
        {
            System.out.println("waiting");
        }
        File file = downloadManger.getFile();
        size= downloadManger.getFileSize();
        Stage stage = new Stage();
        stage.setTitle("Download information");
        stage.getIcons().add(Images.getImage("..\\img\\mate.png"));
        progressBar = new ProgressBar(0);
        Label label1 = new Label("file name : " + downloadManger.getFile().toString());
        Label label2 = new Label("downloaded "+ 0 + "KB from " +size/1024 + "KB");
        Button cancel = new Button("cancel");
        VBox vBox= new VBox(label1,label2,progressBar,cancel);
        vBox.setMinSize(200,100);
        Scene scene = new Scene(vBox);
        vBox.setAlignment(Pos.CENTER);
        cancel.setOnAction((e)->{
            try {
                downloadManger.cancel();
                stage.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        stage.setScene(scene);
        stage.show();
        Thread thread = new Thread(()->{

            while (!downloadManger.isFinished()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(()->{
                    label2.setText("downloaded "+ file.length()/1024 + "KB from " +size/1024 + "KB");
                    progressBar.setProgress(file.length()*1.0/size);});
                System.out.println("updated");
            }
            Platform.runLater(()->{
            cancel.setText("ok");
            label2.setText(label2.getText()  + " Download done");
            cancel.setOnAction((e)->stage.close());});
        });
        thread.start();
        label2.setText("downloaded "+ file.length()/1024 + "KB from " +size/1024 + "KB");
        progressBar.setProgress(file.length()*1.0/size);
    }
}
