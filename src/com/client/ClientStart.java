package com.client;

import com.client.cinterface.set.SetHostPort;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientStart {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ClientStart.stage = stage;
    }

    public static void main(String[] args) {
        SetHostPort.launch();
    }

}