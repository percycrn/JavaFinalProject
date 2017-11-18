package com.client;

import com.client.util.ClientS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.text.SimpleDateFormat;

public class ManageClient {
    private static ClientS clientS;
    private static String clientName;
    private static String targetName;
    private static ObservableList<String> leftMessage = FXCollections.observableArrayList();
    private static ObservableList<String> rightMessage = FXCollections.observableArrayList();
    protected SimpleDateFormat df=new SimpleDateFormat("YY-MM-DD HH-MM-SS");

    protected ClientS getClientS() {
        return clientS;
    }

    protected void setClientS(ClientS clientS) {
        ManageClient.clientS = clientS;
    }

    protected String getClientName() {
        return clientName;
    }

    protected void setClientName(String clientName) {
        ManageClient.clientName = clientName;
    }

    protected String getTargetName() {
        return targetName;
    }

    protected void setTargetName(String targetName) {
        ManageClient.targetName = targetName;
    }

    protected ObservableList<String> getLeftMessage() {
        return leftMessage;
    }

    protected ObservableList<String> getRightMessage() {
        return rightMessage;
    }
}
