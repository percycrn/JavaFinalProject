package com.client.cinterface.set;

import com.client.ClientStart;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SetHostPort extends Application {

    public static void launch() {
        launch(new String[0]);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ClientStart.setStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("SetHostPort.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Set Host and Port");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
