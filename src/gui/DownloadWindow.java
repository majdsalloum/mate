package gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
        TextField textField=new TextField();

        textField.setFont(Font.font("Segoe Print",15));
        Label label = new Label("drop the link here");
        label.setTextFill(Color.GREEN);
        label.setFont(Font.font("Segoe Print",40));
        Button button = new Button("ok");
        button.setFont(Font.font("Segoe Print",30));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN,null,null)));
        VBox vBox = new VBox(label,textField,button);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
        stage.getIcons().add(Images.getImage("..\\img\\mate.png"));
        stage.setTitle("Download Manger");
        stage.setWidth(400);
        stage.setHeight(400);
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
        label1.setFont(Font.font("Segoe Print",30));
        label1.setTextFill(Color.GREEN );
        Label label2 = new Label("downloaded "+ 0 + "KB from " +size/1024 + "KB");
        label2.setFont(Font.font("Segoe Print",20));
        label2.setTextFill(Color.GREEN );
        Button cancel = new Button("cancel");
        cancel.setTextFill(Color.WHITE );
        cancel.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN,null,null)));

        VBox vBox= new VBox(label1,label2,progressBar,cancel);
        vBox.setSpacing(30);
        vBox.setPrefSize(450,400);
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
