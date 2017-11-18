package com.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ManageServer {
    private SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    private static ObservableList clientMessage = FXCollections.observableArrayList();

    protected ObservableList getClientMessage() {
        return clientMessage;
    }

    @SuppressWarnings("unchecked")
    protected void addClientMessage(String message) {
        clientMessage.add(message + " " + df.format(new Date()));
    }
}